package simple;

import common.DependencyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterConstantTest {

    Injector injector;
    final int VALUE = 42;

    @BeforeEach
    void setUp() {
        injector = new Container();
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
        ImplementationD1 d = new ImplementationD1(VALUE);
        injector.registerConstant("I", d);
        Object objectReceived = injector.getObject("I");
        assertTrue(objectReceived instanceof InterfaceD);
        assertTrue(objectReceived instanceof ImplementationD1);
        InterfaceD intD = (InterfaceD) objectReceived;
        assertEquals(VALUE, intD.getI());
    }

    @Test
    void dependencyException() throws DependencyException {
        assertThrows(DependencyException.class, () -> injector.getObject("A"));
        injector.registerConstant("I", 42);
        assertThrows(DependencyException.class, () -> injector.getObject("A"));
    }

    interface InterfaceD {
        int getI();
    }

    static class ImplementationD1 implements InterfaceD {

        private final int i;

        public ImplementationD1(int i) {
            this.i = i;
        }

        @Override
        public int getI() {
            return i;
        }
    }

}