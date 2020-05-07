package mock.implementations;

import mock.interfaces.InterfaceG;
import mock.interfaces.InterfaceH;

public class ImplementationG1 implements InterfaceG {
    private final int g;

    public ImplementationG1(InterfaceH intH) {
        this.g = intH.getH();

    }

    @Override
    public int getG() {
        return this.g;
    }
}
