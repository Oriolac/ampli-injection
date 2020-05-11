package cases;

import common.DependencyException;
import mock.interfaces.InterfaceE;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public interface CompositeTestInt {

    void usingComposite() throws DependencyException;
}
