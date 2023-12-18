package com.mac.drive.macDrive.pojo.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Register User
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "Admin Registration Object", description = "")
public class AdminRegisterParam {
    @ApiModelProperty(value = "Username", required = true)
    private String username;

    @ApiModelProperty(value = "Password", required = true)
    private String password;

    @ApiModelProperty(value = "Email", required = true)
    private String email;

    @ApiModelProperty(value = "Email Verification Code", required = true)
    private String emailCode;

}
