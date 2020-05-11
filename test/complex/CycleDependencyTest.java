package complex;

import cases.CycleDependencyTestInt;
import common.exceptions.DependencyException;
import mock.factories.complex.*;
import mock.implementations.*;
import mock.interfaces.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CycleDependencyTest implements CycleDependencyTestInt  {

    Injector injector;
    FactoryF1 factF1;
    FactoryG1 factG1;
    FactoryH1 factH1;
    FactoryE2 factE2;
    FactoryA1 factA1;

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
        injector.registerFactory(InterfaceF.class, factF1, InterfaceG.class);
        injector.registerFactory(InterfaceG.class, factG1, InterfaceH.class);
        injector.registerFactory(InterfaceH.class, factH1, InterfaceF.class);
        assertThrows(DependencyException.class, () -> injector.getObject(InterfaceG.class));
    }

    @Override
    @Test
    public void throwsInUniCycleDependency() throws DependencyException {
        injector.registerFactory(InterfaceE.class, factE2, InterfaceE.class);
        assertThrows(DependencyException.class, () -> injector.getObject(InterfaceE.class));

    }

}
