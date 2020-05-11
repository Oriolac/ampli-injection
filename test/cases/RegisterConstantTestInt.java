package cases;

import common.DependencyException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public interface RegisterConstantTestInt {

    void registerIntegerConstant() throws DependencyException;

    void gettingUnexpectedConstantDependencyException() throws DependencyException;

    void alreadyRegisteredConstantException() throws DependencyException;

    void UnregisteredConstantDependencyException();


}
