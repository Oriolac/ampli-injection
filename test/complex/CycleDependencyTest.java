package complex;

import cases.CycleDependencyTestInt;
import common.DependencyException;
import mock.factories.complex.FactoryE2;
import mock.factories.complex.FactoryF1;
import mock.factories.complex.FactoryG1;
import mock.factories.complex.FactoryH1;
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
    Factory<SuperInterface> factF1, factG1, factH1, factE2, factA1;
    final Class<ImplementationF1> impF = ImplementationF1.class;
    final Class<ImplementationG1> impG = ImplementationG1.class;
    final Class<ImplementationH1> impH = ImplementationH1.class;
    final Class<ImplementationE2> impE = ImplementationE2.class;
    final Class<ImplementationA1> impA = ImplementationA1.class;


    @BeforeEach
    public void setUp() {
        injector = new Container();
        factA1 = new FactoryE2<>();
        factF1 = new FactoryF1<>();
        factG1 = new FactoryG1<>();
        factH1 = new FactoryH1<>();
        factE2 = new FactoryE2<>();
    }

    @Override
    @Test
    public void throwsInTriangleCycleDependency() throws DependencyException {
        injector.registerFactory(impF, factF1, impG);
        injector.registerFactory(impG, factG1, impF);
        injector.registerFactory(impH, factH1, impG);
        assertThrows(DependencyException.class, () -> injector.getObject(impG));
    }

    @Override
    @Test
    public void throwsInUniCycleDependency() throws DependencyException {
        injector.registerFactory(impE, factE2, impE);
        injector.registerFactory(impA, factA1, impE);
        assertThrows(DependencyException.class, () -> injector.getObject(impA));
    }

}
