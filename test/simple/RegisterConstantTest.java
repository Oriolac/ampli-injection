package simple;

import cases.RegisterConstantTestInt;
import common.DependencyException;
import mock.implementations.ImplementationD1;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterConstantTest implements RegisterConstantTestInt {

    Injector injector;
    final int VALUE = 42;
    ImplementationD1 d;

    @BeforeEach
    void setUp() {
        injector = new Container();
    }

    @Test
    public void registerIntegerConstant() throws DependencyException {
        injector.registerConstant("I", VALUE);
        Object objectReceived = injector.getObject("I");
        assertTrue(objectReceived instanceof Integer);
        int i = (int) objectReceived;
        assertEquals(VALUE, i);
    }

    @Test
    public void gettingUnexpectedConstantDependencyException() throws DependencyException {
        assertThrows(DependencyException.class, () -> injector.getObject("A"));
        injector.registerConstant("I", VALUE);
        assertThrows(DependencyException.class, () -> injector.getObject("A"));
    }

    @Test
    public void alreadyRegisteredConstantException() throws DependencyException {
        injector.registerConstant("I", VALUE);
        assertThrows(DependencyException.class, () -> injector.registerConstant("I", VALUE));
        assertThrows(DependencyException.class, () -> injector.registerConstant("I", d));
    }


}
