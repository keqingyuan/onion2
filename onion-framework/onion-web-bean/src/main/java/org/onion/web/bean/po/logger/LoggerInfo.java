package org.onion.web.bean.po.logger;

import org.onion.web.bean.po.GenericPo;

/**
 * Created by 浩 on 2015-09-11 0011.
 */
public class LoggerInfo extends GenericPo<String> {
    private static final long serialVersionUID = 8910856253780046561L;
    /**
     * 请求者ip
     */
    private String clientIp;

    /**
     * 请求路径
     */
    private String requestUri;

    /**
     * 完整路径
     */
    private String requestUrl;

    /**
     * 对应的方法,格式为 HTTP方法+java方法 如:GET.list()
     */
    private String requestMethod;

    /**
     * 响应结果
     */
    private String responseContent;

    /**
     * 用户主键
     */
    private String userId;

    /**
     * 请求时间
     */
    private long requestTime;

    /**
     * 响应时间
     */
    private long responseTime;

    /**
     * 请求耗时
     */
    private long useTime = 1;

    /**
     * referer信息
     */
    private String referer;

    /**
     * 客户端标识
     */
    private String userAgent;

    /**
     * 响应码
     */
    private String responseCode;

    /**
     * 请求头信息
     */
    private String requestHeader;

    /**
     * 对应类名
     */
    private String className;

    /**
     * 功能摘要
     */
    private String moduleDesc;

    /**
     * 请求参数
     */
    private String requestParam;

    /**
     * 响应异常
     */
    private String exceptionInfo;

    /**
     * 命中缓存
     */
    private String cacheKey;


    public String getUserId() {
        if (userId == null)
            userId = "";
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(long requestTime) {
        this.requestTime = requestTime;
    }

    public long getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(long responseTime) {
        this.responseTime = responseTime;
    }

    public long getUseTime() {
        if (useTime == 1)
            useTime = getResponseTime() - getRequestTime();
        return useTime;
    }

    public void setUseTime(long useTime) {
        this.useTime = useTime;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }


    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getResponseContent() {
        return responseContent;
    }

    public void setResponseContent(String responseContent) {
        this.responseContent = responseContent;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getRequestHeader() {
        return requestHeader;
    }

    public void setRequestHeader(String requestHeader) {
        this.requestHeader = requestHeader;
    }

    public String getModuleDesc() {
        return moduleDesc;
    }

    public void setModuleDesc(String moduleDesc) {
        this.moduleDesc = moduleDesc;
    }

    public String getRequestParam() {
        return requestParam;
    }

    public void setRequestParam(String requestParam) {
        this.requestParam = requestParam;
    }

    public String getExceptionInfo() {
        return exceptionInfo;
    }

    public void setExceptionInfo(String exceptionInfo) {
        this.exceptionInfo = exceptionInfo;
    }

    public String getCacheKey() {
        return cacheKey;
    }

    public void setCacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
    }
}
