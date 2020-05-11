package mock.factories.simple;

import common.exceptions.DependencyException;
import mock.implementations.ImplementationH1;
import mock.interfaces.InterfaceF;
import simple.Factory;

public class FactoryH1  implements Factory {
    @Override
    public ImplementationH1 create(Object... parameters) throws DependencyException {
        InterfaceF i;
        try {
            i = (InterfaceF) parameters[0];
        } catch (ClassCastException | ArrayIndexOutOfBoundsException ex) {
            throw new DependencyException(ex);
        }
        return new ImplementationH1(i);
    }
}
