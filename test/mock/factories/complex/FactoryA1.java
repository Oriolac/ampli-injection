package mock.factories.complex;

import common.exceptions.DependencyException;
import complex.Factory;
import mock.factories.AbstractSpyFactory;

public class FactoryA1<E> extends AbstractSpyFactory implements Factory<E> {
    @Override
    public E create(Object... parameters) throws DependencyException {
        return null;
    }
}
