package mock;

public class ImplementationC1 implements InterfaceC {
    final private int c;

    public ImplementationC1(InterfaceD intD) {
        this.c = intD.getD();
    }

    @Override
    public int getC() {
        return this.c;
    }
}
