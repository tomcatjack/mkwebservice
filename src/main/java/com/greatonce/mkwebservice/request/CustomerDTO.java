package com.greatonce.mkwebservice.request;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class CustomerDTO {

    @NotEmpty
    private String code;
    private String email;
    private String levelName;
    private String mobile;
    private String name;
    private String nationalName;
    private String sex;
    @NotEmpty
    private String speedDelivery;
    private String status;
    private String storeCode;
    private String tagName;

}
