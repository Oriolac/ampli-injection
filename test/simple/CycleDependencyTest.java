package simple;

import common.DependencyException;
import mock.factories.simple.FactoryE2;
import mock.factories.simple.FactoryF1;
import mock.factories.simple.FactoryG1;
import mock.factories.simple.FactoryH1;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CycleDependencyTest {

    Injector injector;
    Factory factF1, factG1, factH1, factE2;

    @BeforeEach
    void setUp() {
        injector = new Container();
        factF1 = new FactoryF1();
        factG1 = new FactoryG1();
        factH1 = new FactoryH1();
        factE2 = new FactoryE2();
    }

    @Test
    void throwsInTriangleCycleDependency() throws DependencyException {
        injector.registerFactory("F", factF1, "G");
        injector.registerFactory("G", factG1, "H");
        injector.registerFactory("H", factH1, "G");
        assertThrows(DependencyException.class, () -> injector.getObject("G"));
    }

    @Test
    void throwsInUniCycleDependency() throws DependencyException {
        injector.registerFactory("E", factE2, "E");
        assertThrows(DependencyException.class, () -> injector.getObject("E"));
    }

}
