package simple;

import common.exceptions.DependencyException;

public interface Factory {

    Object create(Object... parameters) throws DependencyException;
}
