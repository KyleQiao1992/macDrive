package com.mac.drive.macDrive.pojo.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Login User Object
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "Admin Login Object", description = "")
public class AdminLoginParam {
    @ApiModelProperty(value = "Username", required = true)
    private String username;

    @ApiModelProperty(value = "Password", required = true)
    private String password;

    @ApiModelProperty(value = "Captcha", required = true)
    private String code;

}
