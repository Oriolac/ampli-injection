package simple;

import common.DependencyException;

import java.util.*;

public class Container implements Injector {


    HashMap<String, Object> instances = new HashMap<>();
    HashMap<String, Factory> factories = new HashMap<>();
    HashMap<String, List<String>> dependencies = new HashMap<>();

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

    }

    @Override
    public Object getObject(String name) throws DependencyException {
        if (!isAlreadyRegistered(name))
            throw new DependencyException("Given name is not registered in the injector.");
        if (objectInDependencyCycle(name))
            throw new DependencyException("The given name class is in cycle of dependencies.");
        if (!hasAllDependenciesRegistered(name))
            throw new DependencyException("The given name class has not all de the dependencies registered.");
        if (instances.containsKey(name))
            return instances.get(name);
        return factories.get(name).create(getObjects(dependencies.get(name)));
    }

    private List<Object> getObjects(List<String> deps) throws DependencyException {
        List<Object> res = new LinkedList<>();
        for (String dep: deps) {
            if (instances.containsKey(dep))
                res.add(instances.get(dep));
            else
                res.add(factories.get(dep).create(dependencies.get(dep)));
        }
        return res;
    }

    private boolean objectInDependencyCycle(String name) {
        Set<String> visited = new HashSet<>();
        Stack<String> search = new Stack<>();
        search.push(name);
        while (!search.isEmpty()) {
            String currentName = search.pop();
            if (visited.contains(currentName))
                return true;
            for (String dep : dependencies.getOrDefault(currentName, Collections.emptyList()))
                search.push(dep);
            visited.add(currentName);
        }
        return false;
    }

    private boolean hasAllDependenciesRegistered(String name) {
        for (String dep : dependencies.getOrDefault(name, Collections.emptyList())) {
            if (factories.containsKey(dep)) {
                if (!hasAllDependenciesRegistered(dep))
                    return false;
            } else if (!instances.containsKey(dep)) {
                return false;
            } else {
                return false;
            }
        }
        return true;
    }

}
