package cases;

import common.DependencyException;

public interface CycleDependencyTestInt {

    void setUp();

    void throwsInTriangleCycleDependency() throws DependencyException;

    void throwsInUniCycleDependency() throws DependencyException;
}
