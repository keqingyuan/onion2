package org.onion.web.mybatis.plgins.pager;

import org.onion.web.bean.common.QueryParam;

/**
 * Created by zhouhao on 16-4-13.
 */
public interface PagerHelper {

    String getDialect();

    String doPaging(QueryParam param, String sql);
}
