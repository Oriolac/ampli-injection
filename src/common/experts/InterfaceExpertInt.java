package common.experts;

import common.exceptions.DependencyException;

import java.util.List;
import java.util.function.Supplier;

public interface InterfaceExpertInt<E, T> {

    List<T> getDependencies();

    boolean isSingleton();

    Supplier<E> getInstance() throws DependencyException;

}
