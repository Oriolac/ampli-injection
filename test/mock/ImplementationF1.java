package mock;

public class ImplementationF1 implements InterfaceF {
    final private int f;

    public ImplementationF1(InterfaceG intG) {
        this.f = intG.getG();
    }

    @Override
    public int getF() {
        return this.f;
    }
}
