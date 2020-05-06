package simple.factories;

import common.DependencyException;
import mock.ImplementationE1;
import simple.Factory;

public class FactoryE1 implements Factory {
    @Override
    public Object create(Object... parameters) throws DependencyException {
        return new ImplementationE1();
    }
}
