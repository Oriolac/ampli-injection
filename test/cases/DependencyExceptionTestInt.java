package cases;

import common.exceptions.DependencyException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public interface DependencyExceptionTestInt {

    void notDirectlyDependencyUnregistered() throws DependencyException;

    void directlyDependencyFactoryUnregistered() throws DependencyException;

    void directlyDependencyConstantUnregistered() throws DependencyException;

    void unformedDependencyRegistration() throws DependencyException;
}
