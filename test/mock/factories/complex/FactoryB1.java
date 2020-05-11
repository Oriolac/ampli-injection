package mock.factories.complex;

import common.exceptions.DependencyException;
import complex.Factory;
import mock.factories.AbstractSpyFactory;
import mock.implementations.ImplementationB1;
import mock.interfaces.InterfaceB;
import mock.interfaces.InterfaceC;

public class FactoryB1 extends AbstractSpyFactory implements Factory<InterfaceB> {
    @Override
    public InterfaceB create(Object... parameters) throws DependencyException {
        InterfaceC i;
        try {
            i = (InterfaceC) parameters[0];
        } catch (ClassCastException | ArrayIndexOutOfBoundsException ex) {
            throw new DependencyException(ex);
        }
        return new ImplementationB1(i);
    }
}
