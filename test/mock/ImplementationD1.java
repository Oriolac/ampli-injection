package mock;

public class ImplementationD1 implements InterfaceD {

    private final int i;

    public ImplementationD1(InterfaceE intE, int i) {
        this.i = i * intE.getE();
    }

    @Override
    public int getD() {
        return i;
    }
}