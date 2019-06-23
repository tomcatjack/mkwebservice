package com.greatonce.mkwebservice.util;


/**
 * @sum ：
 * @description：
 * @date ：Created in 2019/6/20
 */
public class MkResponseUtil {

    public static MKResponse resultSuccessResponse(String response_Msg) {
        MKResponse response = new MKResponse();
        response.setStatus("0");
        response.setData(response_Msg);
        return response;

    }

    public static MKResponse resultFailureResponse(String response_Msg) {
        MKResponse response = new MKResponse();
        response.setStatus("1");
        response.setData(response_Msg);
        return response;
    }

}
