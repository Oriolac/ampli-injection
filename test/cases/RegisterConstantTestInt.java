package cases;

import common.exceptions.DependencyException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public interface RegisterConstantTestInt {

    void registerIntegerConstant() throws DependencyException;

    void gettingUnexpectedConstantDependencyException() throws DependencyException;

    void alreadyRegisteredConstantException() throws DependencyException;

    void UnregisteredConstantDependencyException();


}
