package simple;

import common.DependencyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
/*
    @Test
    void dependencyException() {
        assertThrows(DependencyException.class, () ->)
    }*/

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

    static class FactoryD1 implements Factory {

        @Override
        public InterfaceD create(Object... parameters) throws DependencyException {
            int i;
            try {
                i = (int) parameters[0];
            } catch (ClassCastException | ArrayIndexOutOfBoundsException ex) {
                throw new DependencyException(ex);
            }
            return new ImplementationD1(i);
        }
    }

}
