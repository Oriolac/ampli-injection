package simple.factories;

import common.DependencyException;
import mock.ImplementationB1;
import mock.InterfaceC;
import simple.Factory;

public class FactoryB1 implements Factory {
    @Override
    public Object create(Object... parameters) throws DependencyException {
        InterfaceC i;
        try {
            i = (InterfaceC) parameters[0];
        } catch (ClassCastException | ArrayIndexOutOfBoundsException ex) {
            throw new DependencyException(ex);
        }
        return new ImplementationB1(i);
    }
}
