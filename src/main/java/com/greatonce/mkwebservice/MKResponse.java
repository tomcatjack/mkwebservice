package com.greatonce.mkwebservice;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;

/**
 * @sum ：
 * @description：
 * @date ：Created in 2019/6/20
 */
@Data
public class MKResponse {

  private String status;
  private JSONArray data;

}
