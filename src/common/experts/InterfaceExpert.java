package common.experts;

import common.exceptions.DependencyException;

import java.util.List;
import java.util.function.Supplier;

public class InterfaceExpert<E, T> implements InterfaceExpertInt<E, T> {


    private Supplier<E> supplier;
    private final List<T> deps;
    private final boolean isSingleton;

    public InterfaceExpert(Supplier<E> supplier, List<T> dependencies) {
        this(supplier, dependencies, false);
    }

    public InterfaceExpert(Supplier<E> supplier, List<T> dependencies, boolean isSingleton) {
        this.supplier = supplier;
        this.deps = dependencies;
        this.isSingleton = isSingleton;
    }

    @Override
    public List<T> getDependencies() {
        return this.deps;
    }

    @Override
    public boolean isSingleton() {
        return this.isSingleton;
    }

    @Override
    public Supplier<E> getInstance() throws DependencyException {
        return supplier;
    }
}
