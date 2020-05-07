package mock.implementations;

import mock.interfaces.InterfaceB;
import mock.interfaces.InterfaceC;

public class ImplementationB1 implements InterfaceB {
    final private int b;

    public ImplementationB1(InterfaceC intC) {
        this.b = intC.getC();
    }

    @Override
    public int getB() {
        return this.b;
    }
}
