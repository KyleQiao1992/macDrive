package com.mac.drive.macDrive.pojo.DTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * File Classification Query Interface
 **/
@Data
public class FileListByTypeParam {
    @ApiModelProperty(value = "File type")
    private String fileType;
    @ApiModelProperty(value = "Current page number")
    private Long currentPage;
    @ApiModelProperty(value = "Number of items per page")
    private Long pageCount;
}
