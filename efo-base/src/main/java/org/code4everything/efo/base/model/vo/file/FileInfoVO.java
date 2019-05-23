package org.code4everything.efo.base.model.vo.file;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.code4everything.boot.base.bean.BaseBean;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author pantao
 * @since 2019/5/22
 **/
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "文件信息")
public class FileInfoVO implements BaseBean, Serializable {

    private static final long serialVersionUID = -4872715321078712444L;

    @ApiModelProperty("访问路径")
    private String accessUrl;

    @ApiModelProperty("文件大小")
    private Long size;

    @ApiModelProperty("文件分类")
    private List<String> categories;

    @ApiModelProperty("文件状态：0不能被访问，1允许访问但不列出，2允许访问并列出'")
    private Character status;

    @ApiModelProperty("文件描述")
    private String description;

    @ApiModelProperty("文件标签")
    private String[] tags;

    @ApiModelProperty("上传时间")
    private LocalDateTime createTime;
}
