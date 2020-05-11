package complex;

import cases.CycleDependencyTestInt;
import common.DependencyException;
import mock.factories.complex.FactoryE2;
import mock.factories.complex.FactoryF1;
import mock.factories.complex.FactoryG1;
import mock.factories.complex.FactoryH1;
import mock.implementations.*;
import mock.interfaces.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CycleDependencyTest implements CycleDependencyTestInt  {

    Injector injector;
    Factory<InterfaceF> factF1;
    Factory<InterfaceG> factG1;
    Factory<InterfaceH> factH1;
    Factory<InterfaceE> factE2;

    final Class<ImplementationF1> impF = ImplementationF1.class;
    final Class<ImplementationG1> impG = ImplementationG1.class;
    final Class<ImplementationH1> impH = ImplementationH1.class;
    final Class<ImplementationE2> impE = ImplementationE2.class;


    @BeforeEach
    public void setUp() {
        injector = new Container();
        factF1 = new FactoryF1<>();
        factG1 = new FactoryG1<>();
        factH1 = new FactoryH1<>();
        factE2 = new FactoryE2<>();
    }

    @Override
    @Test
    public void throwsInTriangleCycleDependency() throws DependencyException {
        injector.registerFactory(InterfaceF.class, factF1, impG);
        injector.registerFactory(InterfaceG.class, factG1, impH);
        injector.registerFactory(InterfaceH.class, factH1, impF);
        assertThrows(DependencyException.class, () -> injector.getObject(impG));
    }

    @Override
    @Test
    public void throwsInUniCycleDependency() throws DependencyException {
        injector.registerFactory(SuperInterface.class, factE2, impE);
        assertThrows(DependencyException.class, () -> injector.getObject(impE));
    }

}
