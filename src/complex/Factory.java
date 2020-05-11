package complex;

import common.exceptions.DependencyException;

public interface Factory<E> {

    E create(Object... parameters) throws DependencyException;

}
