package cn.dachui.session.defaults;

import cn.dachui.proxy.MapperRegister;
import cn.dachui.session.SqlSession;
import cn.dachui.session.SqlSessionFactory;

public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private MapperRegister mapperRegister;

    public DefaultSqlSessionFactory(MapperRegister mapperRegister) {
        this.mapperRegister = mapperRegister;
    }


    @Override
    public SqlSession openSession() {
        SqlSession session = new DefaultSqlSession(mapperRegister);
        return session;
    }
}
