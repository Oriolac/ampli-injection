package simple;

import common.AbstractFactoryStrategies;
import common.exceptions.DependencyException;
import common.experts.InterfaceExpert;
import common.strategies.CycleFinder;
import common.strategies.DependencyObjects;
import common.strategies.UnregisteredDependencies;

import java.util.*;

public class Container implements Injector {

    HashMap<String, InterfaceExpert<Object, String>> objects = new HashMap<>();
    AbstractFactoryStrategies<Object, String> factoryStrategies = new AbstractFactoryStrategies<>(objects);

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
        if (isAlreadyRegistered(name))
            throw new DependencyException("Factory name is already in registered in the injector.");
        register(name, creator, false, parameters);
    }

    private void register(String name, Factory creator, boolean isSingleton, String... parameters) throws DependencyException {
        objects.put(name, new InterfaceExpert<>(() -> {
            try {
                return creator.create(getObjects(parameters));
            } catch (DependencyException e) {
                return e;
            }
        }, List.of(parameters), isSingleton));
    }

    @Override
    public void registerSingleton(String name, Factory creator, String... parameters) throws DependencyException {
        if (isAlreadyRegistered(name))
            throw new DependencyException("Factory name is already in registered in the injector.");
        register(name, creator, true, parameters);
    }


    @Override
    public Object getObject(String name) throws DependencyException {
        if (!isAlreadyRegistered(name))
            throw new DependencyException("Given name is not registered in the injector.");
        if (hasAnyDependenciesUnregistered(name))
            throw new DependencyException("The given name class has not all de the dependencies registered.");
        if (objectInDependencyCycle(name))
            throw new DependencyException("The given name class is in cycle of dependencies.");
        InterfaceExpert<Object, String> expert = objects.get(name);
        expert.setInstance();
        if (expert.getInstance().get() instanceof DependencyException)
            throw new DependencyException((DependencyException) expert.getInstance().get());
        return expert.getInstance().get();
    }

    private Object[] getObjects(String... deps) throws DependencyException {
        return factoryStrategies.getDepObjects().getObjects(deps);
    }

    private boolean objectInDependencyCycle(String name) {
        return factoryStrategies.getCycleFinder().objectInDependencyCycle(name);
    }

    private boolean hasAnyDependenciesUnregistered(String name) {
        return factoryStrategies.getUnregistered().hasAnyDependenciesUnregistered(name,  new HashSet<>());
    }

}
