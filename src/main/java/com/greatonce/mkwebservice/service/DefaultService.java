package com.greatonce.mkwebservice.service;

import com.greatonce.mkwebservice.MKResponse;
import javax.jws.WebService;

/**
 * @sum ：
 * @description：
 * @date ：Created in 2019/6/20
 */
@WebService
public interface DefaultService {

    String sayHello(String user);

    MKResponse getStore(String code);
}
