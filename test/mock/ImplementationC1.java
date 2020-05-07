package mock;

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
