package simple;

import common.DependencyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterConstantTest {

    Injector injector;

    @BeforeEach
    void setUp() {
        injector = new Container();
    }

    @Test
    void registerConstant() throws DependencyException {
        injector.registerConstant("I", 42);
        Object objectReceived = injector.getObject("I");
        assertTrue(objectReceived instanceof Integer);
        int i = (int) objectReceived;
        assertEquals(42, i);
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