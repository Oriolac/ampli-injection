package mock.factories.complex;

import common.exceptions.DependencyException;
import complex.Factory;
import mock.factories.AbstractSpyFactory;
import mock.implementations.ImplementationC1;
import mock.interfaces.InterfaceB;
import mock.interfaces.InterfaceC;
import mock.interfaces.InterfaceD;
import mock.interfaces.InterfaceE;

import java.util.Optional;

public class FactoryC1 extends AbstractSpyFactory implements Factory<InterfaceC> {
    @Override
    public InterfaceC create(Object... parameters) throws DependencyException {
        Optional<InterfaceD> intD = Optional.empty();
        Optional<InterfaceE> intE = Optional.empty();
        try {
            for(Object param : parameters) {
                if (param instanceof InterfaceD)
                    intD = Optional.of((InterfaceD) param);
                else
                    intE = Optional.of((InterfaceE) param);
            }
        } catch (ClassCastException | ArrayIndexOutOfBoundsException ex) {
            throw new DependencyException(ex);
        }
        if (intD.isPresent() && intE.isPresent())
            return new ImplementationC1(intD.get(), intE.get());
        throw new DependencyException("Parameters in FactoryC1 are not correct.");

    }
}
