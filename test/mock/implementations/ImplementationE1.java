package mock.implementations;

import mock.interfaces.InterfaceE;

public class ImplementationE1 implements InterfaceE {

    private final int i;

    public ImplementationE1(int i) {
        this.i = i;
    }

    @Override
    public int getE() {
        return i;
    }
}
