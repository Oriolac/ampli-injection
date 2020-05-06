package simple.factories;

import common.DependencyException;
import mock.InterfaceA;
import simple.Factory;

public class SingletonA1 implements Factory {


    private InterfaceA intA1;

    public SingletonA1() {
        this.intA1 = null;
    }

    @Override
    public InterfaceA create(Object... parameters) throws DependencyException {
        if (this.intA1 == null)
            this.intA1 = new FactoryA1().create(parameters);
        return this.intA1;
    }
}
