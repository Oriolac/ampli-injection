package common;

import common.experts.InterfaceExpert;
import common.strategies.CycleFinder;
import common.strategies.DependencyObjects;
import common.strategies.UnregisteredDependencies;

import java.util.HashMap;

public class AbstractFactoryStrategies<E, T> {
    HashMap<T, InterfaceExpert<E, T>> expert;
    CycleFinder<E, T> cycle;
    UnregisteredDependencies<E, T> unregistered;
    DependencyObjects<E, T> depObjects;

    public AbstractFactoryStrategies(HashMap<T, InterfaceExpert<E, T>> expert){
        this.expert = expert;
    }
    public CycleFinder<E, T> getCycleFinder() {
        if (cycle == null)
            cycle = new CycleFinder<>(expert);
        return cycle;
    }

    public UnregisteredDependencies<E, T> getUnregistered() {
        if (unregistered == null)
            unregistered = new UnregisteredDependencies<>(expert);
        return unregistered;
    }

    public DependencyObjects<E, T> getDepObjects() {
        if (depObjects == null)
            depObjects = new DependencyObjects<>(expert);
        return depObjects;
    }
}
