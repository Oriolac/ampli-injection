package mock.factories.simple;

import common.exceptions.DependencyException;
import mock.implementations.ImplementationB1;
import mock.interfaces.InterfaceC;
import simple.Factory;

public class FactoryB1 implements Factory {
    @Override
    public ImplementationB1 create(Object... parameters) throws DependencyException {
        InterfaceC i;
        try {
            i = (InterfaceC) parameters[0];
        } catch (ClassCastException | ArrayIndexOutOfBoundsException ex) {
            throw new DependencyException(ex);
        }
        return new ImplementationB1(i);
    }
}
