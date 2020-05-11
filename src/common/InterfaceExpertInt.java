package common;

import java.util.List;
import java.util.function.Supplier;

public interface InterfaceExpertInt<E, T> {

    List<T> getDependencies();

    boolean isSingleton();

    E getInstance() throws DependencyException;

}
