package mock.factories.complex;

import common.exceptions.DependencyException;
import complex.Factory;
import mock.factories.AbstractSpyFactory;
import mock.implementations.ImplementationA1;
import mock.implementations.ImplementationD1;
import mock.interfaces.InterfaceB;
import mock.interfaces.InterfaceC;

import java.util.Optional;

public class FactoryA1 extends AbstractSpyFactory implements Factory<ImplementationA1> {
    @Override
    public ImplementationA1 create(Object... parameters) throws DependencyException {
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
        if (intB.isPresent() && intC.isPresent()){

            super.incCount();
            return new ImplementationA1(intB.get(), intC.get());
        }
        throw new DependencyException("Parameters in FactoryA1 are not correct.");
    }
}
