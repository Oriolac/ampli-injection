package mock;

public class ImplementationE2 implements InterfaceE {


    final private int e;

    public ImplementationE2(InterfaceE intE) {
        this.e = 1 + intE.getE();
    }

    @Override
    public int getE() {
        return this.e;
    }
}
