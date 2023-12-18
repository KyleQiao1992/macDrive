package com.mac.drive.macDrive.pojo.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Traverse Directory Tree
 **/
@Data
public class TreeNodeVO {

    @ApiModelProperty(value = "Node ID")
    private Long id;

    @ApiModelProperty(value = "Node Name")
    private String label;

    @ApiModelProperty(value = "Depth")
    private Long depth;

    @ApiModelProperty(value = "Is Closed")
    private String state = "closed";

    @ApiModelProperty(value = "Attributes Collection")
    private Map<String, String> attributes = new HashMap<>();

    @ApiModelProperty(value = "List of Child Nodes")
    private List<TreeNodeVO> children = new ArrayList<>();

}
