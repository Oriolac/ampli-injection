package mock.implementations;

import mock.interfaces.InterfaceF;
import mock.interfaces.InterfaceH;

public class ImplementationH1 implements InterfaceH {

    private final int h;

    public ImplementationH1(InterfaceF intF) {
        this.h = intF.getF();
    }

    @Override
    public int getH() {
        return this.h;
    }
}
