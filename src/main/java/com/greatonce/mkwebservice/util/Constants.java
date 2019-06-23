package com.greatonce.mkwebservice.util;

import java.time.ZoneOffset;

/**
 * 公用常量类。
 * @author carver.gu
 * @since 1.0, Sep 12, 2009
 */
public abstract class Constants {

    /**
     * 默认时间格式
     **/
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * Date默认时区
     **/
    public static final String DATE_TIMEZONE = "GMT+8";
    /**
     * UTF-8字符集
     **/
    public static final String CHARSET_UTF8 = "UTF-8";
    /**
     * GBK字符集
     **/
    public static final String CHARSET_GBK = "GBK";
    /**
     * JSON 应格式
     */
    public static final String FORMAT_JSON = "json";
    /**
     * XML 应格式
     */
    public static final String FORMAT_XML = "xml";
    /**
     * MD5签名方式
     */
    public static final String SIGN_METHOD_MD5 = "md5";
    /**
     * HMAC签名方式
     */
    public static final String SIGN_METHOD_HMAC = "hmac";
    /**
     * 响应编码
     */
    public static final String ACCEPT_ENCODING = "Accept-Encoding";
    /**
     * 内容编码头
     */
    public static final String CONTENT_ENCODING = "Content-Encoding";
    /**
     * GZIP
     */
    public static final String CONTENT_ENCODING_GZIP = "gzip";
    /**
     * 默认媒体类型
     **/
    public static final String MIME_TYPE_DEFAULT = "application/octet-stream";
    /**
     * JSON媒体类型
     */
    public static final String MIME_TYPE_JSON = "application/json";
    /**
     * 默认流式读取缓冲区大小
     **/
    public static final int READ_BUFFER_SIZE = 1024 * 4;
    /**
     * 英文逗号分隔符
     */
    public static final String STRING_SEPARATOR = ",";
    /**
     * HmacSHA1
     */
    public static final String SECURITY_HMAC_SHA1 = "HmacSHA1";
    /**
     * HmacSHA256
     */
    public static final String SECURITY_HMAC_SHA256 = "HmacSHA256";
    /**
     * AES
     */
    public static final String SECURITY_AES = "AES";
    /**
     * 时区
     */
    public static final ZoneOffset DEFAULT_ZONE_OFF_SET = ZoneOffset.ofHours(8);
}
