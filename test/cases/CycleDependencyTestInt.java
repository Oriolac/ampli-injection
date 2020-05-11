package cases;

import common.exceptions.DependencyException;

public interface CycleDependencyTestInt {

    void setUp();

    void throwsInTriangleCycleDependency() throws DependencyException;

    void throwsInUniCycleDependency() throws DependencyException;
}
