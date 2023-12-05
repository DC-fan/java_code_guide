package cn.dachui.proxy;

import cn.dachui.session.SqlSession;
import cn.hutool.core.lang.ClassScanner;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MapperRegister {

    private final Map<Class<?>, MapperProxyFactory<?>> knowMapper = new HashMap<>();

    public void addMapper(Class<?> type) {
        if(type.isInterface()) {
            if(knowMapper.containsKey(type)) {
                throw new RuntimeException("该类型已经存在了");
            }
            knowMapper.put(type, new MapperProxyFactory<>(type));
        }
    }

    public void addMapper(String path) {
        Set<Class<?>> classes = ClassScanner.scanPackage(path);
        for(Class<?> type : classes) {
            addMapper(type);
        }
    }

    public <T> T getMapper(Class<T> type, SqlSession session) {
        return (T)knowMapper.get(type).newInstance(session);
    }

}
