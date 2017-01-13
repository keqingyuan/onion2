package org.onion.web.controller.system;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.onion.ezorm.meta.FieldMetaData;
import org.onion.ezorm.meta.TableMetaData;
import org.onion.ezorm.render.SqlAppender;
import org.onion.web.bean.po.user.User;
import org.onion.web.core.authorize.annotation.Authorize;
import org.onion.web.core.datasource.DynamicDataSource;
import org.onion.web.core.exception.AuthorizeForbiddenException;
import org.onion.web.core.logger.annotation.AccessLogger;
import org.onion.web.core.message.ResponseMessage;
import org.onion.web.core.utils.WebUtil;
import org.onion.web.service.system.DataBaseManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/database")
@Authorize(module = "database")
@AccessLogger("数据库管理")
public class DatabaseManagerController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private DataBaseManagerService dataBaseManagerService;

    @Autowired(required = false)
    protected DynamicDataSource dynamicDataSource;

    protected void checkDynamicDataSourceSupport() {
        if (dynamicDataSource == null)
            logger.warn("\ndynamicDataSource is not support! if you want use it,please import " +
                    "\n<!--------------------------------------------------------->\n" +
                    "       <dependency>\n" +
                    "            <groupId>org.onion</groupId>\n" +
                    "            <artifactId>onion-web-datasource</artifactId>\n" +
                    "       </dependency>" +
                    "\n<!--------------------------------------------------------->" +
                    "\n to pom.xml");
    }

    @RequestMapping(value = "/tables", method = RequestMethod.GET)
    @Authorize(action = "R")
    @AccessLogger("获取所有表结构")
    public ResponseMessage showTables() throws SQLException {
        return ResponseMessage.ok(dataBaseManagerService.getTableList())
                .include(TableMetaData.class, "name", "alias", "comment", "fields")
                .include(FieldMetaData.class, "name", "alias", "comment", "dataType", "properties")
                .onlyData();
    }

    @RequestMapping(value = "/exec", method = RequestMethod.POST)
    @AccessLogger("执行SQL")
    public ResponseMessage exec(@RequestBody String sql) throws Exception {
        return ResponseMessage.ok(dataBaseManagerService.execSql(buildSqlList(sql)));
    }

    @RequestMapping(value = "/sql/alter", method = RequestMethod.POST)
    @AccessLogger("查询修改表结构SQL")
    public ResponseMessage showAlterSql(@RequestBody JSONObject jsonObject) throws Exception {
        return ResponseMessage.ok(dataBaseManagerService.createAlterSql(createTableMetaDataByJson(jsonObject)));
    }

    @RequestMapping(value = "/sql/create", method = RequestMethod.POST)
    @AccessLogger("查询创建表结构SQL")
    public ResponseMessage showCreateSql(@RequestBody JSONObject jsonObject) throws Exception {
        return ResponseMessage.ok(dataBaseManagerService.createCreateSql(createTableMetaDataByJson(jsonObject)));
    }

    @RequestMapping(value = "/tables/{dataSourceId}", method = RequestMethod.GET)
    @Authorize(action = "R")
    @AccessLogger("指定数据源获取表结构")
    public ResponseMessage showTables(@PathVariable("dataSourceId") String dataSourceId) throws SQLException {
        try {
            checkDynamicDataSourceSupport();
            DynamicDataSource.use(dataSourceId);
            return ResponseMessage.ok(dataBaseManagerService.getTableList())
                    .include(TableMetaData.class, "name", "alias", "comment", "fields")
                    .include(FieldMetaData.class, "name", "alias", "comment", "dataType", "properties")
                    .onlyData();
        } finally {
            DynamicDataSource.useDefault(false);
        }
    }

    public List<String> buildSqlList(String sql) {
        String[] list = sql.split("[\n]");
        List<SqlAppender> sqlList = new LinkedList<>();
        SqlAppender[] tmp = {new SqlAppender()};
        Arrays.asList(list).stream()
                .filter(s -> !s.startsWith("--") && s.trim().length() != 0)
                .forEach(s1 -> {
                    if (s1.trim().endsWith(";")) {
                        s1 = s1.trim();
                        tmp[0].add(s1.substring(0, s1.length() - 1), "\n");
                        sqlList.add(tmp[0]);
                        tmp[0] = new SqlAppender();
                    } else {
                        tmp[0].add(s1, "\n");
                    }
                });
        if (!tmp[0].isEmpty()) sqlList.add(tmp[0]);
        List<String> sqlStringList = new ArrayList<>();
        User user = WebUtil.getLoginUser();
        for (SqlAppender appender : sqlList) {
            String sqlLine = appender.toString().trim();
            String type = sqlLine.split("[ ]")[0];
            if (!user.hasAccessModuleAction("database", type.toLowerCase()))
                throw new AuthorizeForbiddenException("权限不足");
            sqlStringList.add(sqlLine);
        }
        return sqlStringList;
    }

    @RequestMapping(value = "/exec/{dataSourceId}", method = RequestMethod.POST)
    @AccessLogger("指定数据源执行SQL")
    public ResponseMessage exec(@PathVariable("dataSourceId") String dataSourceId, @RequestBody String sql) throws Exception {
        checkDynamicDataSourceSupport();
        DynamicDataSource.use(dataSourceId);
        try {
            return ResponseMessage.ok(dataBaseManagerService.execSql(buildSqlList(sql)));
        } finally {
            DynamicDataSource.useDefault(false);
        }
    }

    @RequestMapping(value = "/sql/alter/{dataSourceId}", method = RequestMethod.POST)
    @AccessLogger("指定数据源查询修改表结构SQL")
    public ResponseMessage showAlterSql(@PathVariable("dataSourceId") String dataSourceId, @RequestBody JSONObject jsonObject) throws Exception {
        try {
            checkDynamicDataSourceSupport();
            DynamicDataSource.use(dataSourceId);
            return ResponseMessage.ok(dataBaseManagerService.createAlterSql(createTableMetaDataByJson(jsonObject)));
        } finally {
            DynamicDataSource.useDefault(false);
        }
    }

    @RequestMapping(value = "/sql/create/{dataSourceId}", method = RequestMethod.POST)
    @AccessLogger("指定数据源查询创建表结构SQL")
    public ResponseMessage showCreateSql(@PathVariable("dataSourceId") String dataSourceId, @RequestBody JSONObject jsonObject) throws Exception {
        try {
            checkDynamicDataSourceSupport();
            DynamicDataSource.use(dataSourceId);
            return ResponseMessage.ok(dataBaseManagerService.createCreateSql(createTableMetaDataByJson(jsonObject)));
        } finally {
            DynamicDataSource.useDefault(false);
        }
    }

    protected TableMetaData createTableMetaDataByJson(JSONObject jsonObject) {
        TableMetaData tableMetaData = new TableMetaData();
        tableMetaData.setName(jsonObject.getString("name"));
        tableMetaData.setComment(jsonObject.getString("comment"));
        JSONArray jsonArray = jsonObject.getJSONArray("fields");
        for (int i = 0; i < jsonArray.size(); i++) {
            FieldMetaData field = jsonArray.getObject(i, FieldMetaData.class);
            tableMetaData.addField(field);
        }
        return tableMetaData;
    }
}
