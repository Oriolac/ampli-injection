package cases;

import common.DependencyException;
import mock.interfaces.InterfaceA;
import mock.interfaces.InterfaceB;
import mock.interfaces.InterfaceD;
import mock.interfaces.InterfaceE;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public interface SingletonTreeTestInt {

    void hasSingletonObjectSameHashCode() throws DependencyException;

    void registerFactory() throws DependencyException;

    void alreadyRegisteredSingleton() throws DependencyException;

}
