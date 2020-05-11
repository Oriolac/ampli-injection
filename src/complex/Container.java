package complex;

import common.DependencyException;

import java.util.*;
import java.util.function.Supplier;

public class Container implements Injector {

    HashMap<Class<?>, Supplier<?>> objects = new HashMap<>();
    HashMap<Class<?>, List<Class<?>>> dependencies = new HashMap<>();
    HashSet<Class<?>> singletons = new HashSet<>();

    @Override
    public <E> void registerConstant(Class<E> name, E value) throws DependencyException {
        if (isAlreadyRegistered(name))
            throw new DependencyException("Constant name is already in registered in the injector.");
        objects.put(name, () -> value);
    }

    private <E> boolean isAlreadyRegistered(Class<E> name) {
        return objects.containsKey(name);
    }

    @Override
    public <E> void registerFactory(Class<E> name, Factory<? extends E> creator, Class<?>... parameters) throws DependencyException {
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
    public <E> void registerSingleton(Class<E> name, Factory<? extends E> creator, Class<?>... parameters) throws DependencyException {
        registerFactory(name, creator, parameters);
        singletons.add(name);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <E> E getObject(Class<E> name) throws DependencyException {
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
        return (E) objects.get(name).get();
    }

    private <E> boolean hasAnyDependenciesUnregistered(Class<E> name) {
        for (Class<?> dep : dependencies.getOrDefault(name, Collections.emptyList())) {
            if (!objects.containsKey(dep)) {
                return true;
            } else if (hasAnyDependenciesUnregistered(dep))
                return true;
        }
        return false;
    }

    private <E> boolean objectInDependencyCycle(Class<E> name) {
        Set<Class<?>> visited = new HashSet<>();
        Queue<Class<?>> search = new LinkedList<>();
        search.add(name);
        while (!search.isEmpty()) {
            Class<?> currentName = search.remove();
            if (visited.contains(currentName))
                return true;
            for (Class<?> dep : dependencies.getOrDefault(currentName, Collections.emptyList())) {
                if (!search.contains(dep))
                    search.add(dep);
            }
            visited.add(currentName);
        }
        return false;

    }

    private Object[] getObjects(Class<?>... deps) throws DependencyException {
        List<Object> res = new LinkedList<>();
        for (Class<?> dep : deps) {
            if (objects.containsKey(dep))
                res.add(objects.get(dep).get());
            else
                throw new DependencyException("Dependency " + dep + " not registered yet. This exception" +
                        " should never be throwned");
        }
        return res.toArray();
    }
}
