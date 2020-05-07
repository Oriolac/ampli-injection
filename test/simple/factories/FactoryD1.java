package simple.factories;

import common.DependencyException;
import mock.ImplementationC1;
import mock.ImplementationD1;
import mock.InterfaceD;
import mock.InterfaceE;
import simple.Factory;

import java.util.Optional;

public class FactoryD1 implements Factory {

    @Override
    public ImplementationD1 create(Object... parameters) throws DependencyException {
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
