package mock.factories.simple;

import common.DependencyException;
import mock.implementations.ImplementationF1;
import mock.interfaces.InterfaceG;
import simple.Factory;

public class FactoryF1 implements Factory {
    @Override
    public ImplementationF1 create(Object... parameters) throws DependencyException {
        InterfaceG i;
        try {
            i = (InterfaceG) parameters[0];
        } catch (ClassCastException | ArrayIndexOutOfBoundsException ex) {
            throw new DependencyException(ex);
        }
        return new ImplementationF1(i);
    }
}
