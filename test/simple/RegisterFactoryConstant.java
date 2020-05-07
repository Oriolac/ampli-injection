package simple;

import common.DependencyException;
import mock.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import simple.factories.*;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterFactoryConstant {


    Injector injector;
    final int VALUE = 2;
    Factory factA1, factB1, factC1, factD1, factE1, factE2;

    @BeforeEach
    void setUp() {
        injector = new Container();
        factA1 = new FactoryA1();
        factB1 = new FactoryB1();
        factC1 = new FactoryC1();
        factD1 = new FactoryD1();
        factE1 = new FactoryE1();
        factE2 = new FactoryE2();
    }

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


}
