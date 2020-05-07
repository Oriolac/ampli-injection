package mock.implementations;

import mock.interfaces.InterfaceC;
import mock.interfaces.InterfaceD;
import mock.interfaces.InterfaceE;

public class ImplementationC1 implements InterfaceC {
    final private int c;

    public ImplementationC1(InterfaceD intD, InterfaceE intE) {
        this.c = intD.getD() * intE.getE();
    }

    @Override
    public int getC() {
        return this.c;
    }
}
