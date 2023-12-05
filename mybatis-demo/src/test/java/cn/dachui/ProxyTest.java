package cn.dachui;

import cn.dachui.dao.IUserDao;

import cn.dachui.proxy.MapperProxyFactory;
import cn.dachui.proxy.MapperRegister;
import cn.dachui.session.SqlSession;
import cn.dachui.session.SqlSessionFactory;
import cn.dachui.session.defaults.DefaultSqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class ProxyTest {


    private static final Logger logger = LoggerFactory.getLogger(ProxyTest.class);

    @Test
    public void testJDKProxy() {
        IUserDao userDao = (IUserDao) Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class[]{IUserDao.class},
                ((proxy, method, args) -> "你被代理了")
        );
        String userName = userDao.queryUserName("1");
        logger.info("userName: {}", userName);
    }

    @Test
    public void testMapperProxy() {
//        Map<String, String> sqlSession = new HashMap<>();
//        sqlSession.put("cn.dachui.dao.IUserDao.queryUserName", "这是一条要执行的sql语句");
//        MapperProxyFactory<IUserDao> mapperProxyFactory = new MapperProxyFactory<>(IUserDao.class);
//        IUserDao userDao = mapperProxyFactory.newInstance(sqlSession);
//        logger.info(userDao.queryUserName("1"));
        MapperRegister mapperRegister = new MapperRegister();
        mapperRegister.addMapper("cn.dachui.dao");
        SqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(mapperRegister);
        SqlSession session = sqlSessionFactory.openSession();
        IUserDao mapper = session.getMapper(IUserDao.class);
        String result = mapper.queryUserName("111");
        logger.info("result: {}", result);
    }

}
