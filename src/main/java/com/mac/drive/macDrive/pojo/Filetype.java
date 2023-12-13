package com.mac.drive.macDrive.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author bin
 * @since 2022-04-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_filetype")
@ApiModel(value="Filetype对象", description="")
public class Filetype implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer Id;

    @ApiModelProperty(value = "文件类型名")
    private String fileTypeName;


}
