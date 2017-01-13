package org.onion.web.dao.form;

import org.onion.web.bean.common.QueryParam;
import org.onion.web.dao.GenericMapper;
import org.onion.web.bean.po.form.Form;

import java.util.List;

/**
 * 自定义表单数据映射接口
 * Created by generator
 */
public interface FormMapper extends GenericMapper<Form, String> {

    /**
     * 查看当前正在使用的表单
     *
     * @param name 表单名字
     * @return 表单对象
     * @throws Exception
     */
    Form selectUsing(String name);

    /**
     * 查询最新版本的表单列表
     *
     * @param param 查询参数
     * @return 表单列表
     */
    List<Form> selectLatestList(QueryParam param);

    /**
     * 查询最新版本的表单数量
     *
     * @param param 查询参数
     * @return 表单数量
     */
    int countLatestList(QueryParam param);

}
