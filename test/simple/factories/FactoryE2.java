package simple.factories;

import common.DependencyException;
import mock.*;
import simple.Factory;

public class FactoryE2 implements Factory {
    @Override
    public Object create(Object... parameters) throws DependencyException {
        InterfaceE i;
        try {
            i = (InterfaceE) parameters[0];
        } catch (ClassCastException | ArrayIndexOutOfBoundsException ex) {
            throw new DependencyException(ex);
        }
        return new ImplementationE2(i);
    }
}
