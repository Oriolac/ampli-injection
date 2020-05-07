package simple;

import mock.factories.simple.*;
import org.junit.jupiter.api.BeforeEach;

public abstract class TreeTest {

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
}
