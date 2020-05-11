package mock.factories.simple;

import common.exceptions.DependencyException;
import mock.implementations.ImplementationG1;
import mock.interfaces.InterfaceH;
import simple.Factory;

public class FactoryG1 implements Factory {
    @Override
    public ImplementationG1 create(Object... parameters) throws DependencyException {
        InterfaceH i;
        try {
            i = (InterfaceH) parameters[0];
        } catch (ClassCastException | ArrayIndexOutOfBoundsException ex) {
            throw new DependencyException(ex);
        }
        return new ImplementationG1(i);
    }
}
