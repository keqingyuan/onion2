package org.onion.web.mybatis.plgins.pager.dialect;

import org.onion.web.bean.common.QueryParam;
import org.onion.web.mybatis.plgins.pager.PagerHelper;
import org.springframework.stereotype.Component;
import org.onion.commons.StringUtils;

/**
 * Created by zhouhao on 16-4-13.
 */
@Component
public class OraclePagerHelper implements PagerHelper {
    @Override
    public String doPaging(QueryParam param, String sql) {
        if (!param.isPaging()) {
            return sql;
        }
        int startWith = param.getPageSize() * (param.getPageIndex() + 1);
        StringBuilder builder = new StringBuilder()
                .append("select * from ( select row_.*, rownum rownum_ from (")
                .append(sql)
                .append(") row_ )")
                .append("where rownum_ <= ")
                .append(startWith)
                .append(" and rownum_ > ")
                .append(param.getPageSize() * param.getPageIndex());
        return builder.toString();
    }

    @Override
    public String getDialect() {
        return "oracle";
    }
}
