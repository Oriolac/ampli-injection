package mock.factories.simple;

import common.exceptions.DependencyException;
import mock.implementations.ImplementationE2;
import mock.interfaces.InterfaceE;
import simple.Factory;

public class FactoryE2 implements Factory {
    @Override
    public ImplementationE2 create(Object... parameters) throws DependencyException {
        InterfaceE i;
        try {
            i = (InterfaceE) parameters[0];
        } catch (ClassCastException | ArrayIndexOutOfBoundsException ex) {
            throw new DependencyException(ex);
        }
        return new ImplementationE2(i);
    }
}
