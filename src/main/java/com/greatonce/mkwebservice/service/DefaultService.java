package com.greatonce.mkwebservice.service;

import com.greatonce.mkwebservice.request.CustomerDTO;
import com.greatonce.mkwebservice.util.MKResponse;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * @sum ：
 * @description：
 * @date ：Created in 2019/6/20
 */
@WebService
public interface DefaultService {

    @WebMethod
    String sayHello(String user);

    @WebMethod
    MKResponse insertCustomer(CustomerDTO customer);


}
