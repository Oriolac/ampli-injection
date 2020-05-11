package complex;

import mock.factories.complex.*;
import mock.interfaces.*;
import org.junit.jupiter.api.BeforeEach;
import complex.Container;
import complex.Factory;
import complex.Injector;

public abstract class TreeTest {

    Injector injector;
    final int VALUE = 2;
    FactoryA1 factA1;
    FactoryB1 factB1;
    FactoryC1 factC1;
    FactoryD1 factD1;
    FactoryE1 factE1;
    FactoryE2 factE2;

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
