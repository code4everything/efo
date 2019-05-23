package org.code4everything.efo.base.model.vo.file;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.code4everything.boot.base.bean.BaseBean;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author pantao
 * @since 2019/5/23
 **/
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "文件上传携带信息")
public class FileUploadVO implements BaseBean, Serializable {

    private static final long serialVersionUID = -3036223797630943979L;

    @ApiModelProperty("分类编号")
    private Integer categoryId;

    @ApiModelProperty("文件描述")
    private String description;

    @ApiModelProperty("文件标签")
    private String[] tags;

    @ApiModelProperty(hidden = true)
    private Long userId;

    public Long getUserId() {
        Objects.requireNonNull(userId, "userId must no be null");
        return userId;
    }

    @Generated
    public Integer getCategoryId() {
        // 0 表示未分类
        return ObjectUtil.defaultIfNull(categoryId, 0);
    }

    @Generated
    public String getTags() {
        if (ArrayUtil.isEmpty(tags)) {
            return ",,";
        }
        StringBuilder builder = new StringBuilder().append(",");
        for (String tag : tags) {
            builder.append(tag).append(",");
        }
        return builder.toString();
    }
}
