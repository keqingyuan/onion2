package org.onion.ezorm.test;

import org.onion.ezorm.executor.AbstractJdbcSqlExecutor;
import org.onion.ezorm.executor.SqlExecutor;
import org.onion.ezorm.meta.Correlation;
import org.onion.ezorm.meta.DatabaseMetaData;
import org.onion.ezorm.meta.TableMetaData;
import org.onion.ezorm.meta.parser.MysqlTableMetaParser;
import org.onion.ezorm.meta.parser.OracleTableMetaParser;
import org.onion.ezorm.render.dialect.MysqlDatabaseMeta;
import org.onion.ezorm.render.dialect.OracleDatabaseMeta;
import org.onion.ezorm.run.Query;
import org.onion.ezorm.run.Table;
import org.onion.ezorm.run.simple.SimpleDatabase;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by zhouhao on 16-6-5.
 */
public class AutoParserTest {
    SqlExecutor sqlExecutor;

    @Before
    public void setup() throws Exception {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        sqlExecutor = new AbstractJdbcSqlExecutor() {
            @Override
            public Connection getConnection() {
                try {
                    Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:XE", "data_center", "data_center");
                    connection.setAutoCommit(false);
                    return connection;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public void releaseConnection(Connection connection) throws SQLException {
                connection.close();
            }
        };
    }

    @Test
    public void testParser() throws SQLException {
        DatabaseMetaData metaData = new OracleDatabaseMeta();
        SimpleDatabase database = new SimpleDatabase(metaData, sqlExecutor);
        metaData.setParser(new OracleTableMetaParser(sqlExecutor));
        metaData.init();

        Table<Object> user = database.getTable("s_user");
        Query query = user.createQuery().select("username").where("name$like", "张%");
        query.list();

        user.createQuery()
                .select("*")
                .where("name$like", "1").and("name", 2)
                .nest("name", "1").or("name", 2);

        Table resources = database.getTable("s_resources");
//

//
//        //设置表关联
//        resources.getMeta().addCorrelation(
//                new Correlation("s_user", "creator", "creator.u_id=s_resources.creator_id").leftJoin()
//        );
//        resources.createUpdate().includes("name").set("name", "111").where("u_id", "aa").exec();
//        resources.createDelete().where("u_id", "11").exec();

        System.out.println(resources.createQuery()
                .select("u_id", "name", "creator.username")
                .noPaging().forUpdate().single());
    }

    @Test
    public void testAlter() throws Exception {
        DatabaseMetaData metaData = new OracleDatabaseMeta();
        SimpleDatabase database = new SimpleDatabase(metaData, sqlExecutor);
        metaData.setParser(new OracleTableMetaParser(sqlExecutor));
        metaData.init();
        TableMetaData old = metaData.getParser().parse("s_script");
        metaData.putTable(old);
        TableMetaData newTable = metaData.getParser().parse("s_script");
        newTable.findFieldByName("name").setName("name2");
        database.alterTable(newTable);

    }
}
