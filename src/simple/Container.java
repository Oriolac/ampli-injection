package simple;

import common.DependencyException;

import java.util.HashMap;
import java.util.List;

public class Container implements Injector{


    HashMap<String, Object> instances = new HashMap<>();
    HashMap<String, Factory> factory = new HashMap<>();
    HashMap<String, List<String>> dependencies = new HashMap<>();

    @Override
    public void registerConstant(String name, Object value) throws DependencyException {
        if (isConstantAlreadyRegistered(name)) throw new DependencyException("Constant name is already in registered in the injector.");
    }

    private boolean isConstantAlreadyRegistered(String name) {
        return false;
    }

    @Override
    public void registerFactory(String name, Factory creator, String... parameters) throws DependencyException {
        if (isFactoryAlreadyRegistered(name)) throw new DependencyException("Factory name is already in registered in the injector.");

    }

    private boolean isFactoryAlreadyRegistered(String name) {
        return false;
    }

    @Override
    public void registerSingleton(String name, Factory creator, String... parameters) throws DependencyException {
        if (isFactoryAlreadyRegistered(name)) throw new DependencyException("Factory name is already in registered in the injector.");

    }

    @Override
    public Object getObject(String name) throws DependencyException {
        if (notRegisteredName(name))
            throw new DependencyException("Given name is not registered in the injector.");
        if (!hasAllDependenciesRegistered(name))
            throw new DependencyException("The given name class has not all de the dependencies registered.");
        if (!objectInDependencyCycle(name))
            throw new DependencyException("The given name class is in cycle of dependencies.");
        return null;
    }

    private boolean objectInDependencyCycle(String name) {
        return false;
    }

    private boolean hasAllDependenciesRegistered(String name) {
        return false;
    }

    private boolean notRegisteredName(String name) {
        return false;
    }
}
