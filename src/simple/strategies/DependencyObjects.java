package simple.strategies;

import common.exceptions.DependencyException;
import common.experts.InterfaceExpert;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class DependencyObjects<E, T> {

    HashMap<T, InterfaceExpert<E, T>> expert;

    public DependencyObjects(HashMap<T, InterfaceExpert<E, T>> expert) {
        this.expert = expert;
    }

    @SafeVarargs
    public final Object[] getObjects(T... deps) throws DependencyException {
        List<Object> res = new LinkedList<>();
        for (T dep : deps) {
            if (expert.containsKey(dep))
                res.add(expert.get(dep).getInstance().get());
            else
                throw new DependencyException("Dependency " + dep + " not registered yet. This exception" +
                        " should never be thrown because it is checked before.");
        }
        return res.toArray();
    }
}
