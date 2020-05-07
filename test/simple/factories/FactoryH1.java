package simple.factories;

import common.DependencyException;
import mock.ImplementationG1;
import mock.ImplementationH1;
import mock.InterfaceF;
import mock.InterfaceH;
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
