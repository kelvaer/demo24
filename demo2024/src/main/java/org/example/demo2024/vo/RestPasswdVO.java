package org.example.demo2024.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 重置密码
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2023/6/11
 */
@Data
@EqualsAndHashCode
@ToString
public class RestPasswdVO implements Serializable {
    /**
     * 旧密码
     */
    @Schema(description = "旧密码")
    private String oldPassword;
    /**
     * 新密码
     */
    @NotBlank(message = "新密码不能为空")
    private String newPassword;
    /**
     * 确认密码
     */
    @NotBlank(message = "确认密码不能为空")
    private String confirmPassword;
}
