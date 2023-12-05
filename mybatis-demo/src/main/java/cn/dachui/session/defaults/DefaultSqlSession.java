package cn.dachui.session.defaults;

import cn.dachui.proxy.MapperRegister;
import cn.dachui.session.SqlSession;

public class DefaultSqlSession implements SqlSession {

    private MapperRegister mapperRegister;

    public DefaultSqlSession(MapperRegister mapperRegister) {
        this.mapperRegister = mapperRegister;
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return mapperRegister.getMapper(type, this);
    }

    @Override
    public <T> T selectOne(String statement) {
        return selectOne(statement, null);
    }

    @Override
    public <T> T selectOne(String statement, Object parameter) {
        return (T) ("这是一个代理方法, statement: " + statement + ": " + parameter);
    }
}
