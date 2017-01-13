package org.onion.ezorm.executor;

import com.alibaba.fastjson.JSON;
import org.onion.ezorm.meta.Correlation;
import org.onion.ezorm.meta.DatabaseMetaData;
import org.onion.ezorm.meta.FieldMetaData;
import org.onion.ezorm.meta.TableMetaData;
import org.onion.ezorm.meta.expand.SimpleMapWrapper;
import org.onion.ezorm.meta.expand.Trigger;
import org.onion.ezorm.meta.parser.H2TableMetaParser;
import org.onion.ezorm.param.Term;
import org.onion.ezorm.param.TermType;
import org.onion.ezorm.render.dialect.H2DatabaseMeta;
import org.onion.ezorm.render.dialect.OracleDatabaseMeta;
import org.onion.ezorm.render.support.simple.SimpleSQL;
import org.onion.ezorm.run.Database;
import org.onion.ezorm.run.Query;
import org.onion.ezorm.run.Table;
import org.onion.ezorm.run.simple.SimpleDatabase;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.JDBCType;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by zhouhao on 16-6-4.
 */
public class SimpleTest {
    SqlExecutor sqlExecutor;

    @Before
    public void setup() throws Exception {
        Class.forName("org.h2.Driver");
        sqlExecutor = new AbstractJdbcSqlExecutor() {
            @Override
            public Connection getConnection() {
                try {
                    return DriverManager.getConnection("jdbc:h2:mem:onion", "sa", "");
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
    public void testExec() throws Exception {
        DatabaseMetaData databaseMetaData = new H2DatabaseMeta();
        TableMetaData metaData = new TableMetaData();
        metaData.setName("s_user");
        metaData.setAlias("user");
        Correlation correlation = new Correlation();
        correlation.setTargetTable("s_area");
        correlation.setAlias("area1");
        Term term = new Term();
        term.setTermType(TermType.func);
        term.setField("area1.id");
        term.setValue("area1.id=user.area_id");
        correlation.setTerms(Arrays.asList(term));
        metaData.addCorrelation(correlation);

        correlation = new Correlation();
        correlation.setTargetTable("s_area");
        correlation.setAlias("area2");
        term = new Term();
        term.setTermType(TermType.func);
        term.setField("area2.id");
        term.setValue("area2.name=user.name");
        correlation.setTerms(Arrays.asList(term));
        metaData.addCorrelation(correlation);

        TableMetaData area = new TableMetaData();
        area.setName("s_area");
        area.setAlias("area");
        databaseMetaData.putTable(area);
        FieldMetaData area_id = new FieldMetaData();
        area_id.setName("id");
        area_id.setJavaType(String.class);
        area_id.setJdbcType(JDBCType.VARCHAR);
        area_id.setDataType("varchar(64)");
        FieldMetaData area_name = new FieldMetaData();
        area_name.setName("name");
        area_name.setJavaType(String.class);
        area_name.setJdbcType(JDBCType.VARCHAR);
        area_name.setDataType("varchar(64)");

        area.addField(area_id).addField(area_name);

        FieldMetaData fieldMetaData = new FieldMetaData();
        fieldMetaData.setName("user_name");
        fieldMetaData.setAlias("userName");
        fieldMetaData.setJavaType(String.class);
        fieldMetaData.setJdbcType(JDBCType.VARCHAR);
        fieldMetaData.setDataType("varchar(64)");
        FieldMetaData f2 = new FieldMetaData();
        f2.setName("name");
        f2.setJavaType(String.class);
        f2.setJdbcType(JDBCType.VARCHAR);
        f2.setDataType("varchar(64)");
        metaData.addField(fieldMetaData).addField(f2);

//        databaseMetaData.putTable(metaData);

//        databaseMetaData.putTable(area);

        databaseMetaData.init();

        metaData.on(Trigger.select_wrapper_done, context -> System.out.println("触发器" + context.get("instance")));
        Database database = new SimpleDatabase(databaseMetaData, sqlExecutor);
        area.setPrimaryKeys(new HashSet<>(Arrays.asList("id", "name")));
        database.createTable(metaData);
        database.createTable(area);


        Table<Map<String, Object>> table = database.getTable("s_user");
        List<Map<String, Object>> datas = JSON.parseObject("[{\"userName\":\"admin\",\"name\":\"张三\"},{\"userName\":\"admin2\",\"name\":\"张2\"}]", List.class);

        table.createInsert().values(datas).exec();

        Query<Map<String, Object>> query = table.createQuery();
        query.select("userName", "name", "area2.*")
                .nest().and("name$LIKE", "张%").or("name$LIKE", "李%");
        query.where("name", "张三");

        query.orderByDesc("name").noPaging().list();

//        H2TableMetaParser parser = new H2TableMetaParser(sqlExecutor);
//        TableMetaData metaData1 = parser.parse("s_user");
//        metaData1.getFields().forEach(System.out::println);
//        metaData1.findFieldByName("user_name").setName("test");
//        metaData1.findFieldByName("user_name").setProperty("not-null", true);
//        database.alterTable(metaData1);
//        metaData1 = parser.parse("s_user");
//        metaData1.findFieldByName("test").setProperty("not-null", false);
//        database.alterTable(metaData1);
    }

    @Test
    public void testAutoParser() throws SQLException {
        System.out.println(sqlExecutor.list(new SimpleSQL("select '1' as name , '2' as name "), new SimpleMapWrapper(){
            @Override
            public Map<String, Object> newInstance() {
                return new IdentityHashMap<>();
            }

            @Override
            public void wrapper(Map<String, Object> instance, int index, String attr, Object value) {
                super.wrapper(instance, index, attr, value);
            }
        }));

    }

}