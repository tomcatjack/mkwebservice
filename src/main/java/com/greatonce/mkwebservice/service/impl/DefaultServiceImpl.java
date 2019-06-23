package com.greatonce.mkwebservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.greatonce.mkwebservice.request.CustomerDTO;
import com.greatonce.mkwebservice.util.MKResponse;
import com.greatonce.mkwebservice.service.DefaultService;

import java.io.IOException;
import java.time.LocalDateTime;

import com.greatonce.mkwebservice.util.MkRequestUtil;
import com.greatonce.mkwebservice.util.MkResponseUtil;
import com.greatonce.mkwebservice.util.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @sum ：
 * @description：
 * @date ：Created in 2019/6/20
 */
public class DefaultServiceImpl implements DefaultService {

  private static final Logger LOGGER = LoggerFactory.getLogger(DefaultServiceImpl.class);

  @Autowired
  private MkRequestUtil mkRequestUtil;

  @Override
  public String sayHello(String user) {
    LOGGER.info("请求信息:{}",user);
    return user+"_"+ LocalDateTime.now();
  }

  @Override
  public MKResponse insertCustomer(CustomerDTO customer) {
    LOGGER.info("请求信息:{}", JSONObject.toJSON(customer));
    //校验参数合法性
    //TODO
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("code",customer.getCode());
    JSONObject dopost = mkRequestUtil.dopost("greatonce.customer.create", jsonObject);
    if("0".equals(dopost.getString("status"))){
        return MkResponseUtil.resultSuccessResponse("新增会员成功");
    }else{
      return MkResponseUtil.resultFailureResponse(dopost.getString("message"));
    }

  }
}
