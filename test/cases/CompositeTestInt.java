package cases;

import common.exceptions.DependencyException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public interface CompositeTestInt {

    void usingComposite() throws DependencyException;
}
