package simple.factories;

import common.DependencyException;
import mock.ImplementationF1;
import mock.ImplementationG1;
import mock.InterfaceF;
import mock.InterfaceG;
import simple.Factory;

public class FactoryG1 implements Factory {
    @Override
    public Object create(Object... parameters) throws DependencyException {
        InterfaceF i;
        try {
            i = (InterfaceF) parameters[0];
        } catch (ClassCastException | ArrayIndexOutOfBoundsException ex) {
            throw new DependencyException(ex);
        }
        return new ImplementationG1(i);
    }
}
