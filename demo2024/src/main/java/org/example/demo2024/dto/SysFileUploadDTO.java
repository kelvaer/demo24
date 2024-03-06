package org.example.demo2024.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@ApiModel(value = "文件表查询VO")
@Data
public class SysFileUploadDTO implements Serializable {

    @ApiModelProperty(value = "表单id")
    private String formId;

    @ApiModelProperty(value = "文件", dataType = "__file", required = true)
    @NotEmpty(message = "文件不能为空")
    private List<MultipartFile> files;

}
