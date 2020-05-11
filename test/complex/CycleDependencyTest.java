package complex;

import cases.CycleDependencyTestInt;
import common.exceptions.DependencyException;
import mock.factories.complex.*;
import mock.implementations.*;
import mock.interfaces.SuperInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import complex.Container;
import complex.Factory;
import complex.Injector;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CycleDependencyTest implements CycleDependencyTestInt  {

    Injector injector;
    FactoryF1 factF1;
    FactoryG1 factG1;
    FactoryH1 factH1;
    FactoryE2 factE2;
    FactoryA1 factA1;
    final Class<ImplementationF1> impF = ImplementationF1.class;
    final Class<ImplementationG1> impG = ImplementationG1.class;
    final Class<ImplementationH1> impH = ImplementationH1.class;
    final Class<ImplementationE2> impE = ImplementationE2.class;
    final Class<ImplementationA1> impA = ImplementationA1.class;


    @BeforeEach
    public void setUp() {
        injector = new Container();
        factA1 = new FactoryA1();
        factF1 = new FactoryF1();
        factG1 = new FactoryG1();
        factH1 = new FactoryH1();
        factE2 = new FactoryE2();
    }

    @Override
    @Test
    public void throwsInTriangleCycleDependency() throws DependencyException {
    }

    @Override
    @Test
    public void throwsInUniCycleDependency() throws DependencyException {/*
        injector.registerFactory(impE, factE2, impE);
        injector.registerFactory(impA, factA1, impE);
        assertThrows(DependencyException.class, () -> injector.getObject(impA));*/
    }

}
