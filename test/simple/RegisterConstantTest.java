package simple;

import common.DependencyException;
import mock.ImplementationD1;
import mock.InterfaceD;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterConstantTest {

    Injector injector;
    final int VALUE = 42;
    ImplementationD1 d;

    @BeforeEach
    void setUp() {
        injector = new Container();
        d = new ImplementationD1(VALUE);
    }

    @Test
    void registerIntegerConstant() throws DependencyException {
        injector.registerConstant("I", VALUE);
        Object objectReceived = injector.getObject("I");
        assertTrue(objectReceived instanceof Integer);
        int i = (int) objectReceived;
        assertEquals(VALUE, i);
    }


    @Test
    void registerInterfaceConstant() throws DependencyException {
        injector.registerConstant("I", d);
        Object objectReceived = injector.getObject("I");
        assertTrue(objectReceived instanceof InterfaceD);
        assertTrue(objectReceived instanceof ImplementationD1);
        mock.InterfaceD intD = (mock.InterfaceD) objectReceived;
        assertEquals(VALUE, intD.getD());
    }

    @Test
    void gettingUnexpectedConstantDependencyException() throws DependencyException {
        assertThrows(DependencyException.class, () -> injector.getObject("A"));
        injector.registerConstant("I", VALUE);
        assertThrows(DependencyException.class, () -> injector.getObject("A"));
    }

    @Test
    void duplicateRegisterDependencyException() throws DependencyException {
        injector.registerConstant("I", VALUE);
        assertThrows(DependencyException.class, () -> injector.registerConstant("I", VALUE));
        assertThrows(DependencyException.class, () -> injector.registerConstant("I", d));
    }


}