package mock;

public class ImplementationG1 implements InterfaceG {
    final private int g;

    public ImplementationG1(InterfaceF intF) {
        this.g = intF.getF();

    }

    @Override
    public int getG() {
        return this.g;
    }
}
