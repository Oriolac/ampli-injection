package simple;

import mock.ImplementationD1;
import org.junit.jupiter.api.BeforeEach;

public class RegisterFactoryConstant {


    Injector injector;
    final int VALUE = 42;
    ImplementationD1 d;


    @BeforeEach
    void setUp() {
        injector = new Container();
        d = new ImplementationD1(VALUE);

    }

}
