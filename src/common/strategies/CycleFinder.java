package common.strategies;

import common.experts.InterfaceExpert;
import common.experts.InterfaceExpertInt;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class CycleFinder<E, T> {


    HashMap<T, InterfaceExpert<E, T>> expert;

    public CycleFinder(HashMap<T, InterfaceExpert<E, T>> expert) {
        this.expert = expert;
    }

    public boolean objectInDependencyCycle(T name) {
        Set<T> visited = new HashSet<>();
        Stack<T> stack = new Stack<>();
        stack.add(name);
        while (!stack.isEmpty()) {
            T currentName = stack.peek();
            if (!visited.contains(currentName))
                visited.add(currentName);
            else
                stack.pop();
            for (T dep : expert.get(currentName).getDependencies()) {
                if (!visited.contains(dep) && !stack.contains(dep))
                    stack.add(dep);
                else if (visited.contains(dep) && stack.contains(dep))
                    return true;
            }
        }
        return false;
    }


}
