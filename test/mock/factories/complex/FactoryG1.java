package mock.factories.complex;

import common.exceptions.DependencyException;
import complex.Factory;
import mock.factories.AbstractSpyFactory;
import mock.implementations.ImplementationG1;
import mock.interfaces.InterfaceB;
import mock.interfaces.InterfaceG;
import mock.interfaces.InterfaceH;

public class FactoryG1 extends AbstractSpyFactory implements Factory<InterfaceG> {
    @Override
    public InterfaceG create(Object... parameters) throws DependencyException {
        InterfaceH i;
        try {
            i = (InterfaceH) parameters[0];
        } catch (ClassCastException | ArrayIndexOutOfBoundsException ex) {
            throw new DependencyException(ex);
        }
        return new ImplementationG1(i);
    }
}
