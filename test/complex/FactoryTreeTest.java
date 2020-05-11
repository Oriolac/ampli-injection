package complex;

import cases.CompositeTestInt;
import cases.DependencyExceptionTestInt;
import common.exceptions.DependencyException;
import mock.implementations.ImplementationE1;
import mock.implementations.ImplementationE2;
import mock.interfaces.*;
import org.junit.jupiter.api.Test;
import complex.TreeTest;

import static org.junit.jupiter.api.Assertions.*;

public class FactoryTreeTest extends TreeTest implements DependencyExceptionTestInt {

    @Test
    void registerFactory() throws DependencyException {
        injector.registerFactory(InterfaceB.class, factB1, InterfaceC.class);
        injector.registerFactory(InterfaceA.class, factA1, InterfaceB.class, InterfaceC.class);
        injector.registerFactory(InterfaceC.class, factC1, InterfaceE.class, InterfaceD.class);
        injector.registerFactory(InterfaceD.class, factD1, InterfaceE.class, Integer.class);
        injector.registerFactory(InterfaceE.class, factE1, Integer.class);
        injector.registerConstant(Integer.class, VALUE);
        Integer i = injector.getObject(Integer.class);
        InterfaceE objE = injector.getObject(InterfaceE.class);
        InterfaceD objD = injector.getObject(InterfaceD.class);
        InterfaceC objC = injector.getObject(InterfaceC.class);
        InterfaceB objB = injector.getObject(InterfaceB.class);
        InterfaceA objA = injector.getObject(InterfaceA.class);
        assertNotNull(objA);
        assertNotNull(objB);
        assertNotNull(objC);
        assertNotNull(objD);
        assertNotNull(objE);
        assertEquals(64, objA.getA());
        assertEquals(8, objB.getB());
        assertEquals(8, objC.getC());
        assertEquals(4, objD.getD());
        assertEquals(2, objE.getE());
    }

    @Override
    public void notDirectlyDependencyUnregistered() throws DependencyException {
        injector.registerFactory(InterfaceB.class, factB1, InterfaceC.class);
        injector.registerFactory(InterfaceA.class, factA1, InterfaceB.class, InterfaceC.class);
        assertThrows(DependencyException.class, () -> injector.getObject(InterfaceB.class));
        assertThrows(DependencyException.class, () -> injector.getObject(InterfaceA.class));
        injector.registerFactory(InterfaceC.class, factC1, InterfaceE.class, InterfaceD.class);
        assertThrows(DependencyException.class, () -> injector.getObject(InterfaceA.class));
        assertThrows(DependencyException.class, () -> injector.getObject(InterfaceB.class));
    }

    @Override
    public void directlyDependencyFactoryUnregistered() throws DependencyException {
        injector.registerFactory(InterfaceD.class, factD1, InterfaceE.class, Integer.class);
        injector.registerConstant(Integer.class, VALUE);
        assertThrows(DependencyException.class, () -> injector.getObject(InterfaceD.class));
    }

    @Override
    public void directlyDependencyConstantUnregistered() throws DependencyException {
        injector.registerFactory(InterfaceD.class, factD1, InterfaceE.class, Integer.class);
        injector.registerFactory(InterfaceE.class, factE1, Integer.class);
        assertThrows(DependencyException.class, () -> injector.getObject(InterfaceD.class));
    }

    @Override
    public void unformedDependencyRegistration() throws DependencyException {
        injector.registerFactory(InterfaceA.class, factA1, Integer.class);
        injector.registerConstant(Integer.class, VALUE);
        assertThrows(DependencyException.class, () -> injector.getObject(InterfaceA.class));
    }

}
