package org.onion.web.service.impl.basic;

import org.onion.ezorm.executor.AbstractJdbcSqlExecutor;
import org.onion.ezorm.executor.SQL;
import org.onion.ezorm.meta.expand.ObjectWrapper;
import org.onion.ezorm.meta.expand.SimpleMapWrapper;
import org.onion.ezorm.render.support.simple.SimpleSQL;
import org.onion.web.core.authorize.ExpressionScopeBean;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service(value = "sqlExecutor")
@Transactional(rollbackFor = Throwable.class)
public class SqlExecutorService extends AbstractJdbcSqlExecutor implements ExpressionScopeBean {

    @Resource
    private DataSource dataSource;

    @Override
    public Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }

    @Override
    public void releaseConnection(Connection connection) {
        DataSourceUtils.releaseConnection(connection, dataSource);
    }


    @Override
    @Transactional(readOnly = true)
    public <T> List<T> list(SQL sql, ObjectWrapper<T> wrapper) throws SQLException {
        return super.list(sql, wrapper);
    }

    @Override
    @Transactional(readOnly = true)
    public <T> T single(SQL sql, ObjectWrapper<T> wrapper) throws SQLException {
        return super.single(sql, wrapper);
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> list(SQL sql) throws SQLException {
        List<Map<String, Object>> data = list(sql, new SimpleMapWrapper());
        return data;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> single(SQL sql) throws Exception {
        Map<String, Object> data = single(sql, new SimpleMapWrapper());
        return data;
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> list(String sql) throws Exception {
        List<Map<String, Object>> data = list(create(sql), new SimpleMapWrapper());
        return data;
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> list(String sql, Map<String, Object> param) throws Exception {
        List<Map<String, Object>> data = list(create(sql, param), new SimpleMapWrapper());
        return data;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> single(String sql) throws Exception {
        Map<String, Object> data = single(create(sql));
        return data;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> single(String sql, Map<String, Object> param) throws Exception {
        Map<String, Object> data = single(create(sql, param));
        return data;
    }

    @Transactional
    public int update(String sql, Map<String, Object> param) throws SQLException {
        return super.update(new SimpleSQL(sql, param));
    }

    @Transactional
    public int update(String sql) throws SQLException {
        return super.update(new SimpleSQL(sql));
    }

    @Transactional
    public int delete(String sql, Map<String, Object> param) throws SQLException {
        return super.delete(new SimpleSQL(sql, param));
    }

    @Transactional
    public int delete(String sql) throws SQLException {
        return super.delete(new SimpleSQL(sql));
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void exec(String sql) throws SQLException {
        super.exec(new SimpleSQL(sql));
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void exec(SQL sql) throws SQLException {
        super.exec(sql);
    }

    public SQL create(String sql) {
        return new SimpleSQL(sql);
    }

    public SQL create(String sql, Map<String, Object> param) {
        SimpleSQL sql1 = new SimpleSQL(sql, param);
        return sql1;
    }

    public SqlExecutorService setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        return this;
    }
}
