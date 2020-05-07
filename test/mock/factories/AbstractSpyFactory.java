package mock.factories;

public abstract class AbstractSpyFactory {

    private int count;

    public int getCountCreate() {
        return count;
    }

    protected void incCount() {
        count++;
    }
}
