package mock.factories.complex;

import common.exceptions.DependencyException;
import complex.Factory;
import mock.factories.AbstractSpyFactory;
import mock.implementations.ImplementationE2;
import mock.interfaces.InterfaceE;

public class FactoryE2 extends AbstractSpyFactory implements Factory<InterfaceE> {
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
