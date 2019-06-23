package com.greatonce.mkwebservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.greatonce.mkwebservice.MKResponse;
import com.greatonce.mkwebservice.service.DefaultService;
import com.greatonce.mkwebservice.util.WebUtil;
import java.io.IOException;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @sum ：
 * @description：
 * @date ：Created in 2019/6/20
 */
public class DefaultServiceImpl implements DefaultService {

  private static final Logger LOGGER = LoggerFactory.getLogger(DefaultServiceImpl.class);

  @Override
  public String sayHello(String user) {
    LOGGER.info("请求信息:{}",user);
    return user+"_"+ LocalDateTime.now();
  }

  @Override
  public MKResponse getStore(String code) {
    LOGGER.info("请求信息:{}",code);
    String appkey = "884845954";
    String appSecreat = "57010385-5dcc-4ad5-99b6-e42fba808203";
    //按照你请求接口填具体的method
    String method = "greatonce.returnOrder.qcConfirm";
    //拼装url  注意URl里面是不能有特殊字符 （空格等）
    String url = "http://39.98.73.106:30003/api.do?method="+method+"&v=v3.0";
    System.out.println(url);
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("code",code);
    try {
      String s = WebUtil.doPost(url, jsonObject.toJSONString());
      JSONObject jsonObject1 = JSONObject.parseObject(s);
      MKResponse mkResponse = new MKResponse();
      if("0".equalsIgnoreCase(jsonObject1.getString("status"))){
        mkResponse.setStatus("0");
//        mkResponse.setData(jsonObject1.getJSONArray("data"));
        return mkResponse;
      }else{
        mkResponse.setStatus("1");
      }
      return mkResponse;
    } catch (IOException e) {
      LOGGER.error("异常",e);
      return null;
    }
  }
}
