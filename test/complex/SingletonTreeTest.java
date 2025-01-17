package complex;

import cases.SingletonTreeTestInt;
import common.exceptions.DependencyException;
import mock.factories.simple.*;
import mock.implementations.*;
import mock.interfaces.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


public class SingletonTreeTest extends TreeTest implements SingletonTreeTestInt {

    @Override
    @Test
    public void hasSingletonObjectSameHashCode() throws DependencyException {
        injector.registerSingleton(InterfaceD.class, factD1, InterfaceE.class, Integer.class);
        injector.registerFactory(InterfaceE.class, factE1, Integer.class);
        injector.registerConstant(Integer.class, VALUE);
        Object objD = injector.getObject(InterfaceD.class);
        Object objE = injector.getObject(InterfaceE.class);
        assertNotNull(objD);
        assertNotNull(objE);
        Object obj2D = injector.getObject(InterfaceD.class);
        Object obj2E = injector.getObject(InterfaceE.class);
        assertNotNull(obj2D);
        assertNotNull(obj2E);
        assertEquals(obj2D.hashCode(), objD.hashCode());
        assertNotEquals(obj2E.hashCode(), objE.hashCode());

    }

    @Override
    @Test
    public void registerFactory() throws DependencyException {
        injector.registerFactory(InterfaceB.class, factB1, InterfaceC.class);
        injector.registerSingleton(InterfaceA.class, factA1, InterfaceB.class, InterfaceC.class);
        injector.registerFactory(InterfaceC.class, factC1, InterfaceE.class, InterfaceD.class);
        injector.registerFactory(InterfaceD.class, factD1, InterfaceE.class, Integer.class);
        injector.registerFactory(InterfaceE.class, factE1, Integer.class);
        injector.registerConstant(Integer.class, VALUE);
        InterfaceE objE = injector.getObject(InterfaceE.class);
        assertNotNull(objE);
        InterfaceD objD = injector.getObject(InterfaceD.class);
        assertNotNull(objD);
        InterfaceC objC = injector.getObject(InterfaceC.class);
        assertNotNull(objC);
        InterfaceB objB = injector.getObject(InterfaceB.class);
        InterfaceA objA = injector.getObject(InterfaceA.class);
        assertNotNull(objB);
        assertNotNull(objA);
        assertEquals(64, objA.getA());
        assertEquals(8, objB.getB());
    }

    @Override
    @Test
    public void alreadyRegisteredSingleton() throws DependencyException {
        assertDoesNotThrow(() -> injector.registerSingleton(SuperInterface.class, factC1, InterfaceD.class, InterfaceE.class));
        assertThrows(DependencyException.class, () -> injector.registerSingleton(SuperInterface.class, factC1, InterfaceD.class, InterfaceE.class));
    }
}
