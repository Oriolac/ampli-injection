package simple;

import common.DependencyException;

import java.util.*;
import java.util.function.Supplier;

public class Container implements Injector {

    HashMap<String, Supplier<Object>> objects = new HashMap<>();
    HashMap<String, List<String>> dependencies = new HashMap<>();
    HashSet<String> singletons = new HashSet<>();

    @Override
    public void registerConstant(String name, Object value) throws DependencyException {
        if (isAlreadyRegistered(name))
            throw new DependencyException("Constant name is already in registered in the injector.");
        objects.put(name, () -> value);
    }

    private boolean isAlreadyRegistered(String name) {
        return objects.containsKey(name);
    }

    @Override
    public void registerFactory(String name, Factory creator, String... parameters) throws DependencyException {
        if (isAlreadyRegistered(name))
            throw new DependencyException("Factory name is already in registered in the injector.");
        objects.put(name, () -> {
            try {
                return creator.create(getObjects(parameters));
            } catch (DependencyException e) {
                return e;
            }
        });
        dependencies.put(name, List.of(parameters));
    }

    @Override
    public void registerSingleton(String name, Factory creator, String... parameters) throws DependencyException {
        registerFactory(name, creator, parameters);
        singletons.add(name);
    }


    @Override
    public Object getObject(String name) throws DependencyException {
        if (!isAlreadyRegistered(name))
            throw new DependencyException("Given name is not registered in the injector.");
        if (objectInDependencyCycle(name))
            throw new DependencyException("The given name class is in cycle of dependencies.");
        if (hasAnyDependenciesUnregistered(name))
            throw new DependencyException("The given name class has not all de the dependencies registered.");
        Object obj = objects.get(name).get();
        if (obj instanceof DependencyException) {
            throw (DependencyException) obj;
        }
        if (singletons.contains(name)) {
            objects.put(name, () -> obj);
            singletons.remove(name);
        }
        return objects.get(name).get();
    }

    private Object[] getObjects(String... deps) throws DependencyException {
        List<Object> res = new LinkedList<>();
        for (String dep : deps) {
            if (objects.containsKey(dep))
                res.add(objects.get(dep).get());
            else
                throw new DependencyException("Dependency " + dep + " not registered yet. This exception" +
                        " should never be throwned");
        }
        return res.toArray();
    }

    private boolean objectInDependencyCycle(String name) {
        Set<String> visited = new HashSet<>();
        Queue<String> search = new PriorityQueue<>();
        search.add(name);
        while (!search.isEmpty()) {
            String currentName = search.remove();
            if (visited.contains(currentName))
                return true;
            for (String dep : dependencies.getOrDefault(currentName, Collections.emptyList())) {
                if (!search.contains(dep))
                    search.add(dep);
            }
            visited.add(currentName);
        }
        return false;
    }

    private boolean hasAnyDependenciesUnregistered(String name) {
        for (String dep : dependencies.getOrDefault(name, Collections.emptyList())) {
            if (!objects.containsKey(dep)) {
                return true;
            } else if (hasAnyDependenciesUnregistered(dep))
                return true;
        }
        return false;
    }

}
