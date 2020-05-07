package simple.factories;

import common.DependencyException;
import mock.*;
import simple.Factory;

import java.util.Optional;

public class FactoryC1 implements Factory {
    @Override
    public ImplementationC1 create(Object... parameters) throws DependencyException {
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
