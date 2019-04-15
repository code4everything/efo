package org.code4everything.efo.base.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.code4everything.boot.bean.BaseBean;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author pantao
 * @since 2019-04-15
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "用户注册")
public class RegisterVO implements BaseBean, Serializable {

    @NotBlank
    @ApiModelProperty(value = "用户名：^[a-zA-Z][a-zA-Z0-9]{3,9}$", required = true)
    private String username;

    @Email
    @ApiModelProperty(value = "邮箱", required = true)
    private String email;

    @NotBlank
    @ApiModelProperty(value = "密码：^.{6,20}$", required = true)
    private String password;

    @NotBlank
    @ApiModelProperty(value = "验证码", required = true)
    private String code;
}
