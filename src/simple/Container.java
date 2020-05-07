package simple;

import common.DependencyException;

import java.util.*;
import java.util.function.Supplier;

public class Container implements Injector {

    HashMap<String, Supplier<Object>> objects = new HashMap<>();
    HashMap<String, List<String>> dependencies = new HashMap<>();
    HashSet<String> instancies = new HashSet<>();

    @Override
    public void registerConstant(String name, Object value) throws DependencyException {
        if (isAlreadyRegistered(name))
            throw new DependencyException("Constant name is already in registered in the injector.");
        objects.put(name, () -> value);
        instancies.add(name);
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
                e.printStackTrace();
            }
            return null;
        });
        dependencies.put(name, List.of(parameters));
    }

    @Override
    public void registerSingleton(String name, Factory creator, String... parameters) throws DependencyException {
        registerFactory(name, creator, parameters);
        instancies.add(name);
    }


    @Override
    public Object getObject(String name) throws DependencyException {
        if (!isAlreadyRegistered(name))
            throw new DependencyException("Given name is not registered in the injector.");
        if (objectInDependencyCycle(name))
            throw new DependencyException("The given name class is in cycle of dependencies.");
        if (hasAnyDependenciesUnregistered(name))
            throw new DependencyException("The given name class has not all de the dependencies registered.");
        if (instancies.contains(name)) {
            Object obj = objects.get(name).get();
            objects.put(name, () -> obj);
        }
        return objects.get(name).get();
    }

    private Object[] getObjects(String... deps) throws DependencyException {
        List<Object> res = new LinkedList<>();
        for (String dep : deps) {
            if (objects.containsKey(dep))
                res.add(objects.get(dep).get());
            else
                throw new DependencyException("Dependency " + dep + " not registered yet.");
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

    private boolean hasAnyDependenciesUnregistered(String name) throws DependencyException {
        for (String dep : dependencies.getOrDefault(name, Collections.emptyList())) {
            if (!objects.containsKey(dep) && instancies.contains(dep)) {
                return true;
            } else if (objects.containsKey(dep)) {
                if (hasAnyDependenciesUnregistered(dep))
                    return true;
            } else
                throw new DependencyException("Dependency " + dep + " not registered yet.");
        }
        return false;
    }

}
