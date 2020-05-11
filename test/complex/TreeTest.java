package complex;

import mock.factories.complex.*;
import mock.interfaces.SuperInterface;
import org.junit.jupiter.api.BeforeEach;
import complex.Container;
import complex.Factory;
import complex.Injector;

public abstract class TreeTest {

    Injector injector;
    final int VALUE = 2;
    Factory<SuperInterface> factA1, factB1, factC1, factD1, factE1, factE2;

    @BeforeEach
    void setUp() {
        injector = new Container();
        factA1 = new FactoryA1<SuperInterface>();
        factB1 = new FactoryB1<SuperInterface>();
        factC1 = new FactoryA1<SuperInterface>();
        factD1 = new FactoryB1<SuperInterface>();
        factE1 = new FactoryA1<SuperInterface>();
        factE2 = new FactoryB1<SuperInterface>();

    }
}
