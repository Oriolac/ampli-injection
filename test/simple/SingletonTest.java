package simple;

import common.DependencyException;
import mock.factories.simple.*;
import mock.interfaces.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SingletonTest {

    Injector injector;
    final int VALUE = 2;
    Factory singlA1, factB1, factC1, factD1, factE1, factE2;

    @BeforeEach
    void setUp() {
        injector = new Container();
        singlA1 = new FactoryA1();
        factB1 = new FactoryB1();
        factC1 = new FactoryC1();
        factD1 = new FactoryD1();
        factE1 = new FactoryE1();
        factE2 = new FactoryE2();
    }

    @Test
    void registerFactory() throws DependencyException {
        injector.registerFactory("B", factB1, "C");
        injector.registerSingleton("A", singlA1, "B", "C");
        injector.registerFactory("C", factC1, "E", "D");
        injector.registerFactory("D", factD1, "E", "I");
        injector.registerFactory("E", factE1, "I");
        injector.registerConstant("I", VALUE);
        Object objB = injector.getObject("B");
        Object objA = injector.getObject("A");
        assertTrue(objA instanceof InterfaceA);
        assertTrue(objB instanceof InterfaceB);
        assertEquals(4, ((InterfaceA) objA).getA());
        assertEquals(2, ((InterfaceB) objB).getB());
        Object obj2B = injector.getObject("B");
        Object obj2A = injector.getObject("A");
        assertTrue(obj2A instanceof InterfaceA);
        assertTrue(obj2B instanceof InterfaceB);
        assertEquals(obj2A.hashCode(), objA.hashCode());
        assertEquals(obj2A, objA);
        assertNotEquals(obj2B.hashCode(), objB.hashCode());
        assertNotEquals(obj2B, objB);
    }
}
