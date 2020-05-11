package complex;

import cases.RegisterConstantTestInt;
import common.DependencyException;

public class RegisterConstantTest implements RegisterConstantTestInt {
    @Override
    public void registerIntegerConstant() throws DependencyException {

    }

    @Override
    public void gettingUnexpectedConstantDependencyException() throws DependencyException {

    }

    @Override
    public void alreadyRegisteredConstantException() throws DependencyException {

    }
}
