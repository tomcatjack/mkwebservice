package com.greatonce.mkwebservice.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.zip.GZIPInputStream;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Web请求工具类
 * @author buer
 * @version 2017-08-21 18:24 1.0
 */
public abstract class WebUtil {

    public static final String DEFAULT_CHARSET = Constants.CHARSET_UTF8;
    public static final String METHOD_POST = "POST";
    public static final String METHOD_GET = "GET";
    public static final String METHOD_PUT = "PUT";
    public static final String METHOD_DELETE = "DELETE";
    public static final String CTYPE_FORM_DATA = "application/x-www-form-urlencoded";
    public static final String CTYPE_FILE_UPLOAD = "multipart/form-data";
    public static final String CTYPE_TEXT_XML = "text/xml";
    public static final String CTYPE_TEXT_PLAIN = "text/plain";
    public static final String CTYPE_APP_JSON = "application/json";
    public static final boolean IGNORE_SSL_CHECK = true; // 忽略SSL检查
    public static final boolean IGNORE_HOST_CHECK = true; // 忽略HOST检查
    public static final int DEFAULT_CONNECT_TIMEOUT = 60 * 1000;
    public static final int DEFAULT_READ_TIMEOUT = 60 * 1000;

    /**
     * 执行HTTP POST请求
     * @param url  请求地址
     * @param json 请求参数（json)
     * @return 响应字符串
     */
    public static String doPostJson(String url, String json) throws IOException{
        return doPostJson(url, json, DEFAULT_CHARSET, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT, null);
    }

    /**
     * 执行HTTP POST请求。
     * @param url    请求地址
     * @param params 请求参数
     * @return 响应字符串
     */
    public static String doPost(String url, Map<String,String> params) throws IOException{
        return doPost(url, params, DEFAULT_CHARSET, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * 执行HTTP POST请求。
     * @param url            请求地址
     * @param params         请求参数
     * @param connectTimeout 链接超时
     * @param readTimeout    读取超时
     * @return 响应字符串
     */
    public static String doPost(String url, Map<String,String> params, int connectTimeout, int readTimeout) throws IOException{
        return doPost(url, params, DEFAULT_CHARSET, connectTimeout, readTimeout);
    }

    /**
     * 执行HTTP POST请求。
     * @param url            请求地址
     * @param params         请求参数
     * @param charset        字符集，如UTF-8, GBK, GB2312
     * @param connectTimeout 链接超时
     * @param readTimeout    读取超时
     * @return 响应字符串
     */
    public static String doPost(String url, Map<String,String> params, String charset, int connectTimeout, int readTimeout) throws IOException{
        return doPost(url, params, charset, connectTimeout, readTimeout, null);
    }

    /**
     * 执行HTTP POST请求
     * @param url            请求地址
     * @param json           json字符串
     * @param charset        字符集
     * @param connectTimeout 连接超时
     * @param readTimeout    读取超时
     * @param headerMap      head参数
     * @return 响应字符串
     */
    public static String doPostJson(String url, String json, String charset, int connectTimeout, int readTimeout, Map<String,String> headerMap) throws IOException{
        String ctype = "application/json;charset=" + charset;
        byte[] content = json.getBytes(charset);
        return executePost(url, ctype, content, connectTimeout, readTimeout, headerMap);
    }

    public static String doPost(String url, Map<String,String> params, String charset, int connectTimeout, int readTimeout, Map<String,String> headerMap) throws IOException{
        String ctype = "application/x-www-form-urlencoded;charset=" + charset;
        String query = buildQuery(params, charset);
        byte[] content = {};
        if(query != null){
            content = query.getBytes(charset);
        }
        return executePost(url, ctype, content, connectTimeout, readTimeout, headerMap);
    }

    /**
     * 执行HTTP POST请求。
     * @param url     请求地址
     * @param apiBody body内容
     */
    public static String doPost(String url, String apiBody) throws IOException{
        return doPost(url, apiBody, DEFAULT_CHARSET, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    public static String doPost(String url, String ctype, String apiBody) throws IOException{
        return doPost(url, ctype, apiBody, DEFAULT_CHARSET, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT, null);
    }

    /**
     * 执行HTTP POST请求。
     * @param url            请求地址
     * @param apiBody        body内容
     * @param charset        字符集
     * @param connectTimeout 链接超时时间
     * @param readTimeout    读取超时时间
     */
    public static String doPost(String url, String apiBody, String charset, int connectTimeout, int readTimeout) throws IOException{
        return doPost(url, apiBody, charset, connectTimeout, readTimeout, null);
    }

    public static String doPost(String url, String apiBody, String charset, int connectTimeout, int readTimeout, Map<String,String> headerMap) throws IOException{
        String ctype = "text/plain;charset=" + charset;
        byte[] content = apiBody.getBytes(charset);
        return executePost(url, ctype, content, connectTimeout, readTimeout, headerMap);
    }

    public static String doPost(String url, String ctype, String apiBody, String charset, int connectTimeout, int readTimeout, Map<String,String> headerMap) throws IOException{
        byte[] content = apiBody.getBytes(charset);
        return executePost(url, ctype, content, connectTimeout, readTimeout, headerMap);
    }

    /**
     * 执行HTTP POST请求。
     * @param url            请求地址
     * @param ctype          请求类型
     * @param content        请求字节数组
     * @param connectTimeout 链接超时
     * @param readTimeout    读取超时
     * @return 响应字符串
     */
    public static String doPost(String url, String ctype, byte[] content, int connectTimeout, int readTimeout) throws IOException{
        return executePost(url, ctype, content, connectTimeout, readTimeout, null);
    }

    /**
     * 执行HTTP POST请求。
     * @param url            请求地址
     * @param ctype          请求类型
     * @param content        请求字节数组
     * @param connectTimeout 链接超时
     * @param readTimeout    读取超时
     * @param headerMap      请求头部参数
     * @return 响应字符串
     */
    public static String doPost(String url, String ctype, byte[] content, int connectTimeout, int readTimeout, Map<String,String> headerMap) throws IOException{
        return executePost(url, ctype, content, connectTimeout, readTimeout, headerMap);
    }

    /**
     * 执行带文件上传的HTTP POST请求。
     * @param url            请求地址
     * @param params         文本请求参数
     * @param fileParams     文件请求参数
     * @param connectTimeout 链接超时
     * @param readTimeout    读取超时
     * @return 响应字符串
     */
    public static String doPost(String url, Map<String,String> params, Map<String,FileItem> fileParams, int connectTimeout, int readTimeout) throws IOException{
        if(fileParams == null || fileParams.isEmpty()){
            return doPost(url, params, DEFAULT_CHARSET, connectTimeout, readTimeout);
        } else{
            return doPost(url, params, fileParams, DEFAULT_CHARSET, connectTimeout, readTimeout);
        }
    }

    /**
     * @param url
     * @param params
     * @param fileParams
     * @param charset
     * @param connectTimeout
     * @param readTimeout
     */
    public static String doPost(String url, Map<String,String> params, Map<String,FileItem> fileParams, String charset, int connectTimeout, int readTimeout) throws IOException{
        return doPost(url, params, fileParams, charset, connectTimeout, readTimeout, null);
    }

    /**
     * 执行带文件上传的HTTP POST请求。
     * @param url        请求地址
     * @param params     文本请求参数
     * @param fileParams 文件请求参数
     * @param charset    字符集，如UT；F-8, GBK, GB2312
     * @param headerMap  需要传递的header头，可以为空
     * @return 响应字符串
     */
    public static String doPost(String url, Map<String,String> params, Map<String,FileItem> fileParams, String charset,
                                int connectTimeout, int readTimeout, Map<String,String> headerMap) throws IOException{
        if(fileParams == null || fileParams.isEmpty()){
            return doPost(url, params, charset, connectTimeout, readTimeout, headerMap);
        } else{
            return executePostWithFile(url, params, fileParams, charset, connectTimeout, readTimeout, headerMap);
        }
    }

    private static String executePost(String url, String ctype, byte[] content, int connectTimeout, int readTimeout, Map<String,String> headerMap) throws IOException{
        return execute(url, ctype, content, connectTimeout, readTimeout, headerMap, METHOD_POST);
    }

    private static String execute(String url, String ctype, byte[] content, int connectTimeout, int readTimeout, Map<String,String> headerMap, String methodPost) throws IOException{
        String rsp;
        HttpURLConnection conn = null;
        OutputStream out = null;
        try{
            conn = getConnection(new URL(url), methodPost, ctype, headerMap);
            conn.setConnectTimeout(connectTimeout);
            conn.setReadTimeout(readTimeout);
            out = conn.getOutputStream();
            out.write(content);
            rsp = getResponseAsString(conn);
        } finally{
            if(out != null){
                out.close();
            }
            if(conn != null){
                conn.disconnect();
            }
        }
        return rsp;
    }

    private static String executePostWithFile(String url, Map<String,String> params, Map<String,FileItem> fileParams,
                                              String charset, int connectTimeout, int readTimeout, Map<String,String> headerMap) throws IOException{
        String boundary = String.valueOf(System.nanoTime()); // 随机分隔线
        HttpURLConnection conn = null;
        OutputStream out = null;
        String rsp = null;
        try{
            String ctype = "multipart/form-data;charset=" + charset + ";boundary=" + boundary;
            conn = getConnection(new URL(url), METHOD_POST, ctype, headerMap);
            conn.setConnectTimeout(connectTimeout);
            conn.setReadTimeout(readTimeout);
            out = conn.getOutputStream();
            byte[] entryBoundaryBytes = ("\r\n--" + boundary + "\r\n").getBytes(charset);
            // 组装文本请求参数
            Set<Entry<String,String>> textEntrySet = params.entrySet();
            for(Entry<String,String> textEntry : textEntrySet){
                byte[] textBytes = getTextEntry(textEntry.getKey(), textEntry.getValue(), charset);
                out.write(entryBoundaryBytes);
                out.write(textBytes);
            }
            // 组装文件请求参数
            Set<Entry<String,FileItem>> fileEntrySet = fileParams.entrySet();
            for(Entry<String,FileItem> fileEntry : fileEntrySet){
                FileItem fileItem = fileEntry.getValue();
                if(!fileItem.isValid()){
                    throw new IOException("FileItem is invalid");
                }
                byte[] fileBytes = getFileEntry(fileEntry.getKey(), fileItem.getFileName(), fileItem.getMimeType(), charset);
                out.write(entryBoundaryBytes);
                out.write(fileBytes);
                fileItem.write(out);
            }
            // 添加请求结束标志
            byte[] endBoundaryBytes = ("\r\n--" + boundary + "--\r\n").getBytes(charset);
            out.write(endBoundaryBytes);
            rsp = getResponseAsString(conn);
        } finally{
            if(out != null){
                out.close();
            }
            if(conn != null){
                conn.disconnect();
            }
        }
        return rsp;
    }

    /**
     * 执行PUT
     * @param url    连接地址
     * @param params 参数
     */
    public static String doPut(String url, Map<String,String> params) throws IOException{
        return doPut(url, params, DEFAULT_CHARSET, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * 执行PUT
     * @param url  连接地址
     * @param json json字符串
     */
    public static String doPutJson(String url, String json) throws IOException{
        return doPutJson(url, json, null);
    }

    /**
     * 执行PUT
     * @param url       连接地址
     * @param json      json字符串
     * @param headerMap headers
     */
    public static String doPutJson(String url, String json, Map<String,String> headerMap) throws IOException{
        byte[] content = json.getBytes(DEFAULT_CHARSET);
        return executePut(url, CTYPE_APP_JSON, content, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT, headerMap);
    }

    /**
     * 执行PUT
     * @param url            连接地址
     * @param params         参数
     * @param charset        字符集
     * @param connectTimeout 链接超时
     * @param readTimeout    读取超时
     */
    public static String doPut(String url, Map<String,String> params, String charset, int connectTimeout, int readTimeout) throws IOException{
        String ctype = "application/x-www-form-urlencoded;charset=" + charset;
        String query = buildQuery(params, charset);
        byte[] content = {};
        if(query != null){
            content = query.getBytes(charset);
        }
        return executePut(url, ctype, content, connectTimeout, readTimeout, null);
    }

    private static String executePut(String url, String ctype, byte[] content, int connectTimeout, int readTimeout, Map<String,String> headerMap) throws IOException{
        return execute(url, ctype, content, connectTimeout, readTimeout, headerMap, METHOD_PUT);
    }

    private static byte[] getTextEntry(String fieldName, String fieldValue, String charset) throws IOException{
        String entry = "Content-Disposition:form-data;name=\"" +
                fieldName +
                "\"\r\nContent-Type:text/plain\r\n\r\n" +
                fieldValue;
        return entry.getBytes(charset);
    }

    private static byte[] getFileEntry(String fieldName, String fileName, String mimeType, String charset) throws IOException{
        String entry = "Content-Disposition:form-data;name=\"" +
                fieldName +
                "\";filename=\"" +
                fileName +
                "\"\r\nContent-Type:" +
                mimeType +
                "\r\n\r\n";
        return entry.getBytes(charset);
    }

    /**
     * 执行HTTP GET请求。
     * @param url    请求地址
     * @param params 请求参数
     * @return 响应字符串
     */
    public static String doGet(String url, Map<String,String> params) throws IOException{
        return doGet(url, params, DEFAULT_CHARSET);
    }

    /**
     * 执行HTTP GET请求。
     * @param url     请求地址
     * @param params  请求参数
     * @param charset 字符集
     */
    public static String doGet(String url, Map<String,String> params, String charset) throws IOException{
        return doGet(url, params, charset, null);
    }

    /**
     * 执行HTTP GET请求。
     * @param url       请求地址
     * @param params    请求参数
     * @param headerMap headers
     */
    public static String doGet(String url, Map<String,String> params, Map<String,String> headerMap) throws IOException{
        return doGet(url, params, DEFAULT_CHARSET, headerMap);
    }

    /**
     * 执行HTTP GET请求。
     * @param url       请求地址
     * @param params    请求参数
     * @param charset   字符集，如UTF-8, GBK, GB2312
     * @param headerMap headers
     * @return 响应字符串
     */
    public static String doGet(String url, Map<String,String> params, String charset, Map<String,String> headerMap) throws IOException{
        HttpURLConnection conn = null;
        String rsp;
        try{
            String ctype = "application/x-www-form-urlencoded;charset=" + charset;
            String query = buildQuery(params, charset);
            conn = getConnection(buildGetUrl(url, query), METHOD_GET, ctype, headerMap);
            rsp = getResponseAsString(conn);
        } finally{
            if(conn != null){
                conn.disconnect();
            }
        }
        return rsp;
    }

    private static HttpURLConnection getConnection(URL url, String method, String ctype, Map<String,String> headerMap) throws IOException{
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        if(conn instanceof HttpsURLConnection){
            HttpsURLConnection connHttps = (HttpsURLConnection) conn;
            if(IGNORE_SSL_CHECK){
                try{
                    SSLContext ctx = SSLContext.getInstance("TLS");
                    ctx.init(null, new TrustManager[]{new TrustAllTrustManager()}, new SecureRandom());
                    connHttps.setSSLSocketFactory(ctx.getSocketFactory());
                    connHttps.setHostnameVerifier((hostname, session)->true);
                } catch(Exception e){
                    throw new IOException(e.toString());
                }
            } else{
                if(IGNORE_HOST_CHECK){
                    connHttps.setHostnameVerifier((hostname, session)->true);
                }
            }
            conn = connHttps;
        }
        conn.setRequestMethod(method);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestProperty("Host", url.getHost());
        conn.setRequestProperty("User-Agent", "greatonce-base");
        conn.setRequestProperty("Content-Type", ctype);
        if(headerMap != null){
            for(Entry<String,String> entry : headerMap.entrySet()){
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        return conn;
    }

    private static URL buildGetUrl(String url, String query) throws IOException{
        if(StringUtil.isEmpty(query)){
            return new URL(url);
        }
        return new URL(buildRequestUrl(url, query));
    }

    public static String buildRequestUrl(String url, String... queries){
        if(queries == null || queries.length == 0){
            return url;
        }
        StringBuilder newUrl = new StringBuilder(url);
        boolean hasQuery = url.contains("?");
        boolean hasPrepend = url.endsWith("?") || url.endsWith("&");
        for(String query : queries){
            if(!StringUtil.isEmpty(query)){
                if(!hasPrepend){
                    if(hasQuery){
                        newUrl.append("&");
                    } else{
                        newUrl.append("?");
                        hasQuery = true;
                    }
                }
                newUrl.append(query);
                hasPrepend = false;
            }
        }
        return newUrl.toString();
    }

    public static String buildQuery(Map<String,String> params, String charset) throws IOException{
        if(params == null || params.isEmpty()){
            return null;
        }
        StringBuilder query = new StringBuilder();
        Set<Entry<String,String>> entries = params.entrySet();
        boolean hasParam = false;
        for(Entry<String,String> entry : entries){
            String name = entry.getKey();
            String value = entry.getValue();
            // 忽略参数名或参数值为空的参数
            if(StringUtil.areNotEmpty(name)){
                if(hasParam){
                    query.append("&");
                } else{
                    hasParam = true;
                }
                query.append(name).append("=").append(URLEncoder.encode(value, charset));
            }
        }
        return query.toString();
    }

    protected static String getResponseAsString(HttpURLConnection conn) throws IOException{
        String charset = getResponseCharset(conn.getContentType());
        if(conn.getResponseCode() < 400){
            String contentEncoding = conn.getContentEncoding();
            if(Constants.CONTENT_ENCODING_GZIP.equalsIgnoreCase(contentEncoding)){
                return getStreamAsString(new GZIPInputStream(conn.getInputStream()), charset);
            } else{
                return getStreamAsString(conn.getInputStream(), charset);
            }
        } else{// Client Error 4xx and Server Error 5xx
            throw new IOException(conn.getResponseCode() + " " + conn.getResponseMessage());
        }
    }

    public static String getStreamAsString(InputStream stream, String charset) throws IOException{
        try{
            Reader reader = new InputStreamReader(stream, charset);
            StringBuilder response = new StringBuilder();
            final char[] buff = new char[1024];
            int read = 0;
            while((read = reader.read(buff)) > 0){
                response.append(buff, 0, read);
            }
            return response.toString();
        } finally{
            if(stream != null){
                stream.close();
            }
        }
    }

    public static String getResponseCharset(String ctype){
        String charset = DEFAULT_CHARSET;
        if(!StringUtil.isEmpty(ctype)){
            String[] params = ctype.split(";");
            for(String param : params){
                param = param.trim();
                if(param.startsWith("charset")){
                    String[] pair = param.split("=", 2);
                    if(pair.length == 2){
                        if(!StringUtil.isEmpty(pair[1])){
                            charset = pair[1].trim();
                        }
                    }
                    break;
                }
            }
        }
        return charset;
    }

    /**
     * 使用默认的UTF-8字符集反编码请求参数值。
     * @param value 参数值
     * @return 反编码后的参数值
     */
    public static String decode(String value){
        return decode(value, DEFAULT_CHARSET);
    }

    /**
     * 使用默认的UTF-8字符集编码请求参数值。
     * @param value 参数值
     * @return 编码后的参数值
     */
    public static String encode(String value){
        return encode(value, DEFAULT_CHARSET);
    }

    /**
     * 使用指定的字符集反编码请求参数值。
     * @param value   参数值
     * @param charset 字符集
     * @return 反编码后的参数值
     */
    public static String decode(String value, String charset){
        String result = null;
        if(!StringUtil.isEmpty(value)){
            try{
                result = URLDecoder.decode(value, charset);
            } catch(IOException e){
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    /**
     * 使用指定的字符集编码请求参数值。
     * @param value   参数值
     * @param charset 字符集
     * @return 编码后的参数值
     */
    public static String encode(String value, String charset){
        String result = null;
        if(!StringUtil.isEmpty(value)){
            try{
                result = URLEncoder.encode(value, charset);
            } catch(IOException e){
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    /**
     * 从URL中提取所有的参数。
     * @param query URL地址
     * @return 参数映射
     */
    public static Map<String,String> splitUrlQuery(String query){
        Map<String,String> result = new HashMap<>();
        String[] pairs = query.split("&");
        if(pairs.length > 0){
            for(String pair : pairs){
                String[] param = pair.split("=", 2);
                if(param.length == 2){
                    result.put(param[0], param[1]);
                }
            }
        }
        return result;
    }

    public static class TrustAllTrustManager implements X509TrustManager{

        public X509Certificate[] getAcceptedIssuers(){
            return null;
        }

        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException{
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException{
        }
    }
    /**
     * 执行HTTP POST请求
     *
     * @param url         请求地址
     * @param params      请求参数
     * @param contentType
     * @return
     * @throws IOException
     */
    public static String doPost(String url, Map<String, String> params, String contentType) throws IOException {
        return doPost(url, params, contentType, DEFAULT_CHARSET, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT, null);
    }

    /**
     * 执行HTTP POST请求
     *
     * @param url         请求地址
     * @param params      请求参数
     * @param contentType
     * @param headerMap   header
     * @return
     * @throws IOException
     */
    public static String doPost(String url, Map<String, String> params, String contentType, Map<String, String> headerMap) throws IOException {
        return doPost(url, params, contentType, DEFAULT_CHARSET, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT, headerMap);
    }

    /**
     * 执行HTTP POST请求。
     *
     * @param url 请求地址
     */

    public static String doPost(String url, Map<String, String> params, String contentType, String charset, int connectTimeout, int readTimeout, Map<String, String> headerMap) throws IOException {
        String query = buildQuery(params, charset);
        byte[] content = {};
        if (query != null) {
            content = query.getBytes(charset);
        }
        return doPost(url, contentType, content, connectTimeout, readTimeout, headerMap);
    }
}
