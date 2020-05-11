package common;

import java.util.List;
import java.util.function.Supplier;

public class InterfaceExpert<E,T> implements InterfaceExpertInt<E,T> {


    public  InterfaceExpert(Supplier<E> supplier, List<T> dependencies, boolean isSingleton){

    }

    @Override
    public List<T> getDependencies() {

    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    @Override
    public Supplier<E> getInstance() {
        return null;
    }
}
