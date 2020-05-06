package simple.factories;

import common.DependencyException;
import mock.ImplementationA1;
import mock.ImplementationB1;
import mock.InterfaceB;
import mock.InterfaceC;
import simple.Factory;

import java.util.Optional;

public class FactoryA1 implements Factory {
    @Override
    public Object create(Object... parameters) throws DependencyException {
        Optional<InterfaceB> intB = Optional.empty();
        Optional<InterfaceC> intC = Optional.empty();
        try {
            for(Object param : parameters) {
                if (param instanceof InterfaceB)
                    intB = Optional.of((InterfaceB) param);
                else
                    intC = Optional.of((InterfaceC) param);
            }
        } catch (ClassCastException | ArrayIndexOutOfBoundsException ex) {
            throw new DependencyException(ex);
        }
        if (intB.isPresent() && intC.isPresent())
            return new ImplementationA1(intB.get(), intC.get());
        throw new DependencyException("Parameters in FactoryA1 are not correct.");
    }
}
