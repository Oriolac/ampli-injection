package simple;

import common.DependencyException;

public class Container implements Injector{
    @Override
    public void registerConstant(String name, Object value) throws DependencyException {
        
    }

    @Override
    public void registerFactory(String name, Factory creator, String... parameters) throws DependencyException {

    }

    @Override
    public void registersSingleton(String name, Factory creator, String... parameters) throws DependencyException {

    }

    @Override
    public Object getObject(String name) throws DependencyException {
        return null;
    }
}
