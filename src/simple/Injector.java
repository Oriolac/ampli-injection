package simple;

import common.DependencyException;

public interface Injector {

    void registerConstant(String name, Object value) throws DependencyException;
    void registerFactory(String name, Factory creator, String... parameters) throws DependencyException;
    void registersSingleton(String name, Factory creator, String... parameters) throws DependencyException;

    Object getObject(String name) throws DependencyException;
}
