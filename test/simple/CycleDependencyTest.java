package simple;

import common.DependencyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import simple.factories.FactoryF1;
import simple.factories.FactoryG1;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CycleDependencyTest {

    Injector injector;
    Factory factF1, factG1;

    @BeforeEach
    void setUp() {
        injector = new Container();
        factF1 = new FactoryF1();
        factG1 = new FactoryG1();
    }

    @Test
    void throwsInCycleDependency() throws DependencyException {
        injector.registerFactory("F", factF1, "G");
        injector.registerFactory("G", factF1, "F");
        assertThrows(DependencyException.class, () -> injector.getObject("G"));
    }
}
