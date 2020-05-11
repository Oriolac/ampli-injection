package complex;

import cases.SingletonTreeTestInt;
import common.DependencyException;
import mock.factories.simple.*;
import mock.implementations.*;
import mock.interfaces.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


public class SingletonTreeTest extends TreeTest implements SingletonTreeTestInt {

    @Override
    public void hasSingletonObjectSameHashCode() throws DependencyException {
        injector.registerSingleton(SuperInterface.class, factA1, InterfaceB.class, InterfaceC.class);
        injector.registerFactory(SuperInterface.class, factB1, InterfaceC.class);
        //injector.registerConstant(InterfaceC.class, VALUE);

        Object objB = injector.getObject(ImplementationB1.class);
        Object objA = injector.getObject(ImplementationA1.class);

        assertTrue(objB instanceof InterfaceB);
        assertTrue(objA instanceof  InterfaceA);

        Object obj2B = injector.getObject(ImplementationB1.class);
        Object obj2A = injector.getObject(ImplementationB1.class);

        assertTrue(obj2B instanceof InterfaceB);
        assertTrue(obj2A instanceof InterfaceA);

        assertEquals(obj2B.hashCode(), objB.hashCode());
        assertNotEquals(obj2A.hashCode(), objA.hashCode());

    }

    @Override
    public void registerFactory() throws DependencyException {
        injector.registerFactory(SuperInterface.class, factB1, InterfaceB.class);
        injector.registerSingleton(SuperInterface.class, factA1, InterfaceB.class, InterfaceC.class);
        injector.registerFactory(SuperInterface.class, factC1, InterfaceE.class, InterfaceD.class);
        injector.registerFactory(SuperInterface.class, factD1, InterfaceE.class);
        injector.registerFactory(SuperInterface.class, factE1, InterfaceE.class);
        //injector.registerConstant(.class, VALUE);
        Object objB = injector.getObject(InterfaceB.class);
        Object objA = injector.getObject(InterfaceA.class);
        assertTrue(objA instanceof InterfaceA);
        assertTrue(objB instanceof InterfaceB);
        assertEquals(64, ((InterfaceA) objA).getA());
        assertEquals(8, ((InterfaceB) objB).getB());
    }

    @Override
    public void alreadyRegisteredSingleton() throws DependencyException {
        assertDoesNotThrow(() -> injector.registerSingleton(SuperInterface.class, factC1, InterfaceD.class, InterfaceE.class));
        assertThrows(DependencyException.class, () -> injector.registerSingleton(SuperInterface.class, factC1, InterfaceD.class, InterfaceE.class));
    }
}
