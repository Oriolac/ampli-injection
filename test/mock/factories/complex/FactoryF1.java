package mock.factories.complex;

import common.exceptions.DependencyException;
import complex.Factory;
import mock.factories.AbstractSpyFactory;
import mock.implementations.ImplementationF1;
import mock.interfaces.InterfaceB;
import mock.interfaces.InterfaceF;
import mock.interfaces.InterfaceG;

public class FactoryF1 extends AbstractSpyFactory implements Factory<InterfaceF> {
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
