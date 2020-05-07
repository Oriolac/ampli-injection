package mock.factories.simple;

import common.DependencyException;
import mock.implementations.ImplementationE1;
import simple.Factory;

public class FactoryE1 implements Factory {
    @Override
    public ImplementationE1 create(Object... parameters) throws DependencyException {
        int i;
        try {
            i = (int) parameters[0];
        } catch (ClassCastException | ArrayIndexOutOfBoundsException ex) {
            throw new DependencyException(ex);
        }
        return new ImplementationE1();
    }
}
