package simple.factories;

import common.DependencyException;
import mock.ImplementationC1;
import mock.InterfaceD;
import simple.Factory;

public class FactoryC1 implements Factory {
    @Override
    public Object create(Object... parameters) throws DependencyException {
        InterfaceD i;
        try {
            i = (InterfaceD) parameters[0];
        } catch (ClassCastException | ArrayIndexOutOfBoundsException ex) {
            throw new DependencyException(ex);
        }
        return new ImplementationC1(i);
    }
}