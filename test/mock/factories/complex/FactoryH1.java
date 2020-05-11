package mock.factories.complex;

import common.exceptions.DependencyException;
import complex.Factory;
import mock.factories.AbstractSpyFactory;
import mock.implementations.ImplementationH1;
import mock.interfaces.InterfaceB;
import mock.interfaces.InterfaceF;
import mock.interfaces.InterfaceH;

public class FactoryH1 extends AbstractSpyFactory implements Factory<InterfaceH> {
    @Override
    public InterfaceH create(Object... parameters) throws DependencyException {
        InterfaceF i;
        try {
            i = (InterfaceF) parameters[0];
        } catch (ClassCastException | ArrayIndexOutOfBoundsException ex) {
            throw new DependencyException(ex);
        }
        return new ImplementationH1(i);
    }
}
