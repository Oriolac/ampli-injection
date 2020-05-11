package complex;

import cases.RegisterConstantTestInt;
import common.exceptions.DependencyException;
import mock.implementations.ImplementationD1;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterConstantTest implements RegisterConstantTestInt {

    Injector injector;
    final int VALUE = 42;
    final Class<Integer> intClass = Integer.class;

    @BeforeEach
    void setUp() {
        injector = new Container();
    }

    @Override
    @Test
    public void registerIntegerConstant() throws DependencyException {
        injector.registerConstant(intClass, 42);
        Integer objectReceived = injector.getObject(intClass);
        assertNotNull(objectReceived);
        assertEquals(VALUE, objectReceived.intValue());
    }

    @Override
    @Test
    public void gettingUnexpectedConstantDependencyException() throws DependencyException {
        injector.registerConstant(intClass, 42);
        Class<String> stringClass = String.class;
        assertThrows(DependencyException.class, () -> injector.getObject(stringClass));
    }

    @Override
    @Test
    public void alreadyRegisteredConstantException() throws DependencyException {
        injector.registerConstant(intClass, 42);
        assertThrows(DependencyException.class, () -> injector.registerConstant(intClass, VALUE));

    }

    @Override
    @Test
    public void UnregisteredConstantDependencyException() {
        assertThrows(DependencyException.class, () -> injector.getObject(intClass));
    }
}
