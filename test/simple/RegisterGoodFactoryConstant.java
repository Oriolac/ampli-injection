package simple;

import common.DependencyException;
import mock.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import simple.factories.FactoryA1;
import simple.factories.FactoryB1;
import simple.factories.FactoryC1;
import simple.factories.FactoryD1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegisterGoodFactoryConstant {


    Injector injector;
    final int VALUE = 2;
    InterfaceA intA;
    InterfaceB intB;
    InterfaceC intC;
    InterfaceD intD;

    FactoryA1 factA1;
    FactoryB1 factB1;
    FactoryC1 factC1;
    FactoryD1 factD1;


    @BeforeEach
    void setUp() {
        injector = new Container();
        factA1 = new FactoryA1();
        factB1 = new FactoryB1();
        factC1 = new FactoryC1();
        factD1 = new FactoryD1();
    }

    @Test
    void registerFactory() throws DependencyException {
        injector.registerFactory("B", factB1,"C");
        injector.registerFactory("A", factA1,"B","C");
        injector.registerFactory("C", factC1,"D");
        injector.registerFactory("D", factD1,"I");
        injector.registerConstant("I", VALUE);
        Object i = injector.getObject("I");
        Object objD = injector.getObject("D");
        Object objC = injector.getObject("C");
        Object objB = injector.getObject("B");
        Object objA = injector.getObject("A");
        assertTrue(objA instanceof InterfaceA);
        assertTrue(objB instanceof InterfaceB);
        intA = (InterfaceA) objA;
        intB = (InterfaceB) objB;
        intC = (InterfaceC) objC;
        intD = (InterfaceD) objD;
        assertEquals(4, intA.getA());
        assertEquals(2, intB.getB());
        assertEquals(2, intC.getC());
        assertEquals(2, intD.getD());
    }

}
