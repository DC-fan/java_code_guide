package cn.dachui.proxy;

import cn.dachui.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

public class MapperProxy<T> implements InvocationHandler, Serializable {

    private static final Logger logger = LoggerFactory.getLogger(MapperProxy.class);

    private SqlSession sqlSession;

    private final Class<T> mapperInterface;

    public MapperProxy(SqlSession sqlSession, Class<T> mapperInterface) {
        this.sqlSession = sqlSession;
        this.mapperInterface = mapperInterface;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        logger.info("当前执行的方法是：" + mapperInterface.getName() + "." + method.getName());
        return "你被代理了, 当前执行的方式是" + sqlSession.selectOne(method.getName(), args);
    }
}
