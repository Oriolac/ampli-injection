package common.experts;

import common.exceptions.DependencyException;

import java.util.List;
import java.util.function.Supplier;

public class ComplexInterfaceExpert {

    InterfaceExpert<?, Class<?>> expert;

    public ComplexInterfaceExpert(InterfaceExpert<?, Class<?>> expert) {
        this.expert = expert;
    }

    public List<Class<?>> getDependencies() {
        return expert.getDependencies();
    }

    public boolean isSingleton() {
        return expert.isSingleton();
    }

    public Supplier<?> getInstance() throws DependencyException {
        return expert.getInstance();
    }


}
