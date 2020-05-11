package cases;

import common.DependencyException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public interface DependencyExceptionTestInt {

    void notDirectlyDependencyUnregistered() throws DependencyException;

    void directlyDependencyFactoryUnregistered() throws DependencyException;

    void directlyDependencyConstantUnregistered() throws DependencyException;

    void unformedDependencyRegistration() throws DependencyException;
}
