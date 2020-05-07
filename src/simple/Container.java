package simple;

import common.DependencyException;

import java.util.*;

public class Container implements Injector {


    HashMap<String, Object> instances = new HashMap<>();
    HashMap<String, Factory> factories = new HashMap<>();
    HashMap<String, List<String>> dependencies = new HashMap<>();
    Set<String> singletons = new HashSet<>();

    @Override
    public void registerConstant(String name, Object value) throws DependencyException {
        if (isAlreadyRegistered(name))
            throw new DependencyException("Constant name is already in registered in the injector.");
        instances.put(name, value);
    }

    private boolean isAlreadyRegistered(String name) {
        return instances.containsKey(name) || factories.containsKey(name);
    }

    @Override
    public void registerFactory(String name, Factory creator, String... parameters) throws DependencyException {
        if (isAlreadyRegistered(name))
            throw new DependencyException("Factory name is already in registered in the injector.");
        factories.put(name, creator);
        dependencies.put(name, List.of(parameters));
    }


    @Override
    public void registerSingleton(String name, Factory creator, String... parameters) throws DependencyException {
        if (isAlreadyRegistered(name))
            throw new DependencyException("Factory name is already in registered in the injector.");
        factories.put(name, creator);
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
        if (instances.containsKey(name))
            return instances.get(name);
        if (singletons.contains(name)) {
            // Must refactor
        }
        return factories.get(name).create(getObjects(dependencies.get(name)));
    }

    private Object[] getObjects(List<String> deps) throws DependencyException {
        List<Object> res = new LinkedList<>();
        for (String dep: deps) {
            if (instances.containsKey(dep))
                res.add(instances.get(dep));
            else
                res.add(factories.get(dep).create(getObjects(dependencies.get(dep))));
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
            if (factories.containsKey(dep)) {
                if (hasAnyDependenciesUnregistered(dep))
                    return true;
            } else if (!instances.containsKey(dep)) {
                return true;
            }
        }
        return false;
    }

}
