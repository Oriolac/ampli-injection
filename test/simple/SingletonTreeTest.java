package simple;

import cases.SingletonTreeTestInt;
import common.DependencyException;
import mock.factories.simple.*;
import mock.interfaces.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SingletonTreeTest extends TreeTest implements SingletonTreeTestInt {


    @Test
    public void hasSingletonObjectSameHashCode() throws DependencyException {
        injector.registerSingleton("D", factD1, "E", "I");
        injector.registerFactory("E", factE1, "I");
        injector.registerConstant("I", VALUE);
        Object objD = injector.getObject("D");
        Object objE = injector.getObject("E");
        assertTrue(objD instanceof InterfaceD);
        assertTrue(objE instanceof InterfaceE);
        Object obj2D = injector.getObject("D");
        Object obj2E = injector.getObject("E");
        assertTrue(obj2D instanceof InterfaceD);
        assertTrue(obj2E instanceof InterfaceE);
        assertEquals(obj2D.hashCode(), objD.hashCode());
        assertNotEquals(obj2E.hashCode(), objE.hashCode());
    }

    @Test
    public void registerFactory() throws DependencyException {
        injector.registerFactory("B", factB1, "C");
        injector.registerSingleton("A", factA1, "B", "C");
        injector.registerFactory("C", factC1, "E", "D");
        injector.registerFactory("D", factD1, "E", "I");
        injector.registerFactory("E", factE1, "I");
        injector.registerConstant("I", VALUE);
        Object objB = injector.getObject("B");
        Object objA = injector.getObject("A");
        assertTrue(objA instanceof InterfaceA);
        assertTrue(objB instanceof InterfaceB);
        assertEquals(64, ((InterfaceA) objA).getA());
        assertEquals(8, ((InterfaceB) objB).getB());
    }

    @Test
    public void alreadyRegisteredSingleton() throws DependencyException {
        assertDoesNotThrow(() -> injector.registerSingleton("A", factA1, "B", "C"));
        assertThrows(DependencyException.class, () -> injector.registerSingleton("A", factA1, "B", "C"));
    }
}
