package mock.factories.complex;

import common.exceptions.DependencyException;
import complex.Factory;
import mock.factories.AbstractSpyFactory;
import mock.implementations.ImplementationD1;
import mock.interfaces.InterfaceD;
import mock.interfaces.InterfaceE;

import java.util.Optional;

public class FactoryD1 extends AbstractSpyFactory implements Factory<InterfaceD> {
    @Override
    public InterfaceD create(Object... parameters) throws DependencyException {
        Optional<Integer> i = Optional.empty();
        Optional<InterfaceE> intE = Optional.empty();
        try {
            for (Object param : parameters) {
                if (param instanceof Integer)
                    i = Optional.of((int) param);
                else
                    intE = Optional.of((InterfaceE) param);
            }
        } catch (ClassCastException | ArrayIndexOutOfBoundsException ex) {
            throw new DependencyException(ex);
        }
        if (i.isPresent() && intE.isPresent())
            return new ImplementationD1(intE.get(), i.get());
        throw new DependencyException("Parameters in FactoryD1 are not correct.");

    }
}
