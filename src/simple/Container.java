package simple;

import common.DependencyException;

import java.util.*;
import java.util.function.Supplier;

public class Container implements Injector {

    HashMap<String, Supplier<Object>> objects = new HashMap<>();
    HashMap<String, Set<String>> dependencies = new HashMap<String, Set<String>>();


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
                return creator.create(dependencies.get(name));
            } catch (DependencyException e) {
                e.printStackTrace();
            }
            return null;
        });
        for (String param : parameters) {
            if (!dependencies.containsKey(param))
                dependencies.put(param, new HashSet<>());
            dependencies.get(param).add(name);
        }
    }

    @Override
    public void registerSingleton(String name, Factory creator, String... parameters) throws DependencyException {
        registerFactory(name, creator, parameters);
    }

    @Override
    public Object getObject(String name) throws DependencyException {
        if (!isAlreadyRegistered(name))
            throw new DependencyException("Given name is not registered in the injector.");
        if (!objectInDependencyCycle(name))
            throw new DependencyException("The given name class is in cycle of dependencies.");
        if (!hasAllDependenciesRegistered(name))
            throw new DependencyException("The given name class has not all de the dependencies registered.");
        return objects.get(name).get();
    }

    private boolean objectInDependencyCycle(String name) {
        return true;
    }

    private boolean hasAllDependenciesRegistered(String name) {
        for (String dep : dependencies.getOrDefault(name, Collections.emptySet())) {
            if (objects.containsKey(dep)) {
                if (!hasAllDependenciesRegistered(dep))
                    return false;
            } else if (!objects.containsKey(dep)) {
                return false;
            } else {
                return false;
            }
        }
        return true;
    }

}
