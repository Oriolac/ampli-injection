package common.strategies;

import common.experts.InterfaceExpert;

import java.util.HashMap;
import java.util.Set;

public class UnregisteredDependencies<E, T> {
    HashMap<T, InterfaceExpert<E, T>> expert;
    
    public UnregisteredDependencies(HashMap<T, InterfaceExpert<E, T>> expert) {
        this.expert = expert;
    }

    public boolean hasAnyDependenciesUnregistered(T name, Set<T> visited) {
        visited.add(name);
        for (T dep : expert.get(name).getDependencies()) {
            if (!visited.contains(dep)) {
                visited.add(dep);
                if (!expert.containsKey(dep)) {
                    return true;
                } else if (hasAnyDependenciesUnregistered(dep, visited))
                    return true;
            }
        }
        return false;
    }

}
