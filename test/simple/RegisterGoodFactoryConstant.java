package simple;

import common.DependencyException;
import mock.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import simple.factories.FactoryA1;
import simple.factories.FactoryB1;
import simple.factories.FactoryC1;
import simple.factories.FactoryD1;

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
        injector.registerFactory("C", factB1,"D");
        injector.registerFactory("C", factB1,"I");
        injector.registerConstant("I", VALUE);
        Object objA = injector.getObject("A");
        Object objB = injector.getObject("B");
        assertTrue(objA instanceof InterfaceA);
        assertTrue(objB instanceof InterfaceB);
        intA = (InterfaceA) objA;
        intB = (InterfaceB) objB;
        System.out.println(intA.getA());
        System.out.println(intB.getB());
    }

}
