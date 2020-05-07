package simple;

import common.DependencyException;
import mock.factories.simple.*;
import mock.interfaces.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FactoryTreeTest extends TreeTest {

    @Test
    void registerFactory() throws DependencyException {
        injector.registerFactory("B", factB1, "C");
        injector.registerFactory("A", factA1, "B", "C");
        injector.registerFactory("C", factC1, "E", "D");
        injector.registerFactory("D", factD1, "E", "I");
        injector.registerFactory("E", factE1, "I");
        injector.registerConstant("I", VALUE);
        Object i = injector.getObject("I");
        Object objE = injector.getObject("E");
        Object objD = injector.getObject("D");
        Object objC = injector.getObject("C");
        Object objB = injector.getObject("B");
        Object objA = injector.getObject("A");
        assertTrue(objA instanceof InterfaceA);
        assertTrue(objB instanceof InterfaceB);
        assertTrue(objC instanceof InterfaceC);
        assertTrue(objD instanceof InterfaceD);
        assertTrue(objE instanceof InterfaceE);
        assertEquals(4, ((InterfaceA) objA).getA());
        assertEquals(2, ((InterfaceB) objB).getB());
        assertEquals(2, ((InterfaceC) objC).getC());
        assertEquals(2, ((InterfaceD) objD).getD());
        assertEquals(1, ((InterfaceE) objE).getE());
    }

    @Test
    void usingComposite() throws DependencyException {
        injector.registerFactory("E1", factE1, "I");
        injector.registerFactory("E2", factE2, "E1");
        injector.registerConstant("I", VALUE);
        Object obj = injector.getObject("E1");
        assertTrue(obj instanceof InterfaceE);
        InterfaceE intE1 = (InterfaceE) obj;
        obj = injector.getObject("E2");
        assertTrue(obj instanceof InterfaceE);
        InterfaceE intE2 = (InterfaceE) obj;
        assertEquals(1, intE1.getE());
        assertEquals(2, intE2.getE());
    }

    @Test
    void notDirectlyDependencyUnregistered() throws DependencyException {
        injector.registerFactory("B", factB1, "C");
        injector.registerFactory("A", factA1, "B", "C");
        assertThrows(DependencyException.class, () -> injector.getObject("B"));
        assertThrows(DependencyException.class, () -> injector.getObject("A"));
        injector.registerFactory("C", factC1, "E, D");
        assertThrows(DependencyException.class, () -> injector.getObject("A"));
        assertThrows(DependencyException.class, () -> injector.getObject("B"));
    }

    @Test
    void directlyDependencyFactoryUnregistered() throws DependencyException {
        injector.registerFactory("D", factD1, "E", "I");
        injector.registerConstant("I", VALUE);
        assertThrows(DependencyException.class, () -> injector.getObject("D"));
    }

    @Test
    void directlyDependencyConstantUnregistered() throws DependencyException {
        injector.registerFactory("D", factD1, "E", "I");
        injector.registerFactory("E", factE1, "I");
        assertThrows(DependencyException.class, () -> injector.getObject("D"));
    }

    @Test
    void unformedDependencyRegistration() throws DependencyException {
        injector.registerFactory("A", factA1, "I");
        injector.registerConstant("I", VALUE);
        assertThrows(DependencyException.class, () -> injector.getObject("A"));
    }


}
