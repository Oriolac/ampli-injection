package mock.implementations;

import mock.interfaces.InterfaceA;
import mock.interfaces.InterfaceB;
import mock.interfaces.InterfaceC;

public class ImplementationA1 implements InterfaceA {
    private final int a;

    public ImplementationA1(InterfaceB intB, InterfaceC intC)
    {
        this.a = intB.getB() * intC.getC();
    }

    @Override
    public int getA() {
        return this.a;
    }
}
