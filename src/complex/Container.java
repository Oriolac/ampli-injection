package complex;

import common.exceptions.DependencyException;
import common.experts.InterfaceExpert;

import java.util.*;

public class Container implements Injector {

    HashMap<Class<?>, InterfaceExpert<?, Class<?>>> objects = new HashMap<>();


    @Override
    public <E> void registerConstant(Class<E> name, E value) throws DependencyException {
        if (isAlreadyRegistered(name))
            throw new DependencyException("Constant name is already in registered in the injector.");
        objects.put(name, new InterfaceExpert<>(() -> value, Collections.emptyList()));
    }

    private <E> boolean isAlreadyRegistered(Class<E> name) {
        return objects.containsKey(name);
    }

    @Override
    public <E> void registerFactory(Class<E> name, Factory<? extends E> creator, Class<?>... parameters) throws DependencyException {
        register(name, creator, false, parameters);
    }

    private <E> void register(Class<E> name, Factory<? extends E> creator, boolean isSingleton, Class<?>... parameters) throws DependencyException {
        if (isAlreadyRegistered(name))
            throw new DependencyException("Factory name is already in registered in the injector.");
        InterfaceExpert<E, Class<?>> expert = new InterfaceExpert<>(() -> {
            try {
                return creator.create(getObjects(parameters));
            } catch (DependencyException e) {
                return null;
            }
        }, List.of(parameters), isSingleton);
        objects.put(name, expert);
    }

    @Override
    public <E> void registerSingleton(Class<E> name, Factory<? extends E> creator, Class<?>... parameters) throws DependencyException {
        register(name, creator, true, parameters);
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public <E> E getObject(Class<E> name) throws DependencyException {
        if (!isAlreadyRegistered(name))
            throw new DependencyException("Given name is not registered in the injector.");
        if (hasAnyDependenciesUnregistered(name, new HashSet<>()))
            throw new DependencyException("The given name class has not all de the dependencies registered.");
        if (objectInDependencyCycle(name))
            throw new DependencyException("The given name class is in cycle of dependencies.");
        InterfaceExpert<?, Class<?>> expert = objects.get(name);
        expert.setInstance();
        if (expert.getInstance().get() == null)
            throw new DependencyException("ClassCastException or ArrayIndexOutOfBoundsException exception");
        return (E) expert.getInstance().get();
    }

    private <E> boolean hasAnyDependenciesUnregistered(Class<E> name, Set<Class<?>> visited) {
        visited.add(name);
        for (Class<?> dep : objects.get(name).getDependencies()) {
            if (!visited.contains(dep)) {
                if (!objects.containsKey(dep)) {
                    return true;
                } else if (hasAnyDependenciesUnregistered(dep, visited))
                    return true;
            }

        }
        return false;
    }

    private <E> boolean objectInDependencyCycle(Class<E> name) {
        Set<Class<?>> visited = new HashSet<>();
        Stack<Class<?>> stack = new Stack<>();
        stack.add(name);
        while (!stack.isEmpty()) {
            Class<?> currentName = stack.peek();
            if (!visited.contains(currentName))
                visited.add(currentName);
            else
                stack.pop();
            for (Class<?> dep : objects.get(currentName).getDependencies()) {
                if (!visited.contains(dep) && !stack.contains(dep))
                    stack.add(dep);
                else if (visited.contains(dep) && stack.contains(dep))
                    return true;
            }
        }
        return false;
    }

    private Object[] getObjects(Class<?>... deps) throws DependencyException {
        List<Object> res = new LinkedList<>();
        for (Class<?> dep : deps) {
            if (objects.containsKey(dep))
                res.add(objects.get(dep).getInstance().get());
            else
                throw new DependencyException("Dependency " + dep + " not registered yet. This exception" +
                        " should never be thrown because it is checked before.");
        }
        return res.toArray();
    }
}
