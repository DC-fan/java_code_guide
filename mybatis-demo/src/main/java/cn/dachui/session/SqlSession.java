package cn.dachui.session;

public interface SqlSession {

    <T> T getMapper(Class<T> type);

    <T> T selectOne(String statement);

    <T> T selectOne(String statement, Object parameter);




}
