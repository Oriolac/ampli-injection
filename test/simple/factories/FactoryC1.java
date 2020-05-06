package simple.factories;

import common.DependencyException;
import mock.ImplementationC1;
import mock.InterfaceC;
import mock.InterfaceD;
import simple.Factory;

public class FactoryC1 implements Factory {
    @Override
    public ImplementationC1 create(Object... parameters) throws DependencyException {
        InterfaceD i;
        try {
            i = (InterfaceD) parameters[0];
        } catch (ClassCastException | ArrayIndexOutOfBoundsException ex) {
            throw new DependencyException(ex);
        }
        return new ImplementationC1(i);
    }
}
