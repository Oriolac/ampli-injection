package mock.factories.complex;

import common.exceptions.DependencyException;
import complex.Factory;
import mock.factories.AbstractSpyFactory;
import mock.implementations.ImplementationE1;
import mock.interfaces.InterfaceE;

public class FactoryE1 extends AbstractSpyFactory implements Factory<InterfaceE> {

    @Override
    public ImplementationE1 create(Object... parameters) throws DependencyException {
        int i;
        try {
            i = (int) parameters[0];
        } catch (ClassCastException | ArrayIndexOutOfBoundsException ex) {
            throw new DependencyException(ex);
        }
        return new ImplementationE1(i);
    }
}
