package com.greatonce.mkwebservice.util;

import com.alibaba.fastjson.JSONObject;
import com.sun.jndi.toolkit.ctx.StringHeadTail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MkRequestUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(MkRequestUtil.class);

    @Value("${mk.api.url:http://39.98.73.106:30003/api.do}")
    private String url;

    public JSONObject dopost(String method,JSONObject data){
        JSONObject response = new JSONObject();

        MkResponseUtil.resultFailureResponse("内部错误");
        try {
            String result = WebUtil.doPost(url+"?method="+method+"&v=v3.0", data.toJSONString());
            JSONObject jsonObject = JSONObject.parseObject(result);
            if("0".equals(jsonObject.getString("status"))){
                response.put("status","0");
            }else{
                response.put("status","1");
            }
            response.put("message",jsonObject.getString("message"));
            return response;
        } catch (IOException e) {
            LOGGER.error("请求api接口异常",e);
            response.put("status","1");
            response.put("message","内部错误");
            return response;
        }
    }
}
