package cases;

import common.exceptions.DependencyException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public interface SingletonTreeTestInt {

    void hasSingletonObjectSameHashCode() throws DependencyException;

    void registerFactory() throws DependencyException;

    void alreadyRegisteredSingleton() throws DependencyException;

}
