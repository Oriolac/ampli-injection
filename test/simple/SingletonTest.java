package simple;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import simple.factories.*;

public class SingletonTest {

    Injector injector;
    final int VALUE = 2;
    Factory singlA1, factB1, factC1, factD1, factE1, factE2;

    @BeforeEach
    void setUp() {
        injector = new Container();
        factB1 = new FactoryB1();
        factC1 = new FactoryC1();
        factD1 = new FactoryD1();
    }

    @Test
    void creatingBefore() {

        //singlA1.create();
    }
}
