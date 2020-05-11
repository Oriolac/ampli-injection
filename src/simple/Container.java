package simple;

import common.DependencyException;
import common.InterfaceExpert;

import java.util.*;
import java.util.function.Supplier;

public class Container implements Injector {

    HashMap<String, InterfaceExpert<Object, String>> objects = new HashMap<>();

    @Override
    public void registerConstant(String name, Object value) throws DependencyException {
        if (isAlreadyRegistered(name))
            throw new DependencyException("Constant name is already in registered in the injector.");
        objects.put(name, new InterfaceExpert<>(() -> value, Collections.emptyList()));
    }

    private boolean isAlreadyRegistered(String name) {
        return objects.containsKey(name);
    }

    @Override
    public void registerFactory(String name, Factory creator, String... parameters) throws DependencyException {
        register(name, creator, false, parameters);
    }

    private void register(String name, Factory creator, boolean isSingleton, String... parameters) throws DependencyException {
        if (isAlreadyRegistered(name))
            throw new DependencyException("Factory name is already in registered in the injector.");
        objects.put(name, new InterfaceExpert<>(() -> {
            try {
                return creator.create(getObjects(parameters));
            } catch (DependencyException e) {
                return null;
            }
        }, List.of(parameters), isSingleton));
    }

    @Override
    public void registerSingleton(String name, Factory creator, String... parameters) throws DependencyException {
        register(name, creator, true, parameters);
    }


    @Override
    public Object getObject(String name) throws DependencyException {
        if (!isAlreadyRegistered(name))
            throw new DependencyException("Given name is not registered in the injector.");
        if (hasAnyDependenciesUnregistered(name, new HashSet<>()))
            throw new DependencyException("The given name class has not all de the dependencies registered.");
        if (objectInDependencyCycle(name))
            throw new DependencyException("The given name class is in cycle of dependencies.");
        return objects.get(name).getInstance();
    }

    private Object[] getObjects(String... deps) throws DependencyException {
        List<Object> res = new LinkedList<>();
        for (String dep : deps) {
            if (objects.containsKey(dep))
                res.add(objects.get(dep).getInstance());
            else
                throw new DependencyException("Dependency " + dep + " not registered yet. This exception" +
                        " should never be thrown because it is checked before.");
        }
        return res.toArray();
    }

    private boolean objectInDependencyCycle(String name) {
        Set<String> visited = new HashSet<>();
        Stack<String> stack = new Stack<>();
        stack.add(name);
        while (!stack.isEmpty()) {
            String currentName = stack.peek();
            if (!visited.contains(currentName))
                visited.add(currentName);
            else
                stack.pop();
            for (String dep : objects.get(currentName).getDependencies()) {
                if (!visited.contains(dep) && !stack.contains(dep))
                    stack.add(dep);
                else if (visited.contains(dep) && stack.contains(dep))
                    return true;
            }
        }
        return false;
    }

    private boolean hasAnyDependenciesUnregistered(String name, Set<String> visited) {
        visited.add(name);
        for (String dep : objects.get(name).getDependencies()) {
            if (!visited.contains(dep)) {
                visited.add(dep);
                if (!objects.containsKey(dep)) {
                    return true;
                } else if (hasAnyDependenciesUnregistered(dep, visited))
                    return true;
            }
        }
        return false;
    }

}
