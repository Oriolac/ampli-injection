package mock;

public class ImplementationD1 implements InterfaceD {

    private final int i;

    public ImplementationD1(int i) {
        this.i = i;
    }

    @Override
    public int getD() {
        return i;
    }
}