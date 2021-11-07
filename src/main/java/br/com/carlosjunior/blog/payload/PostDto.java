package br.com.carlosjunior.blog.payload;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@ApiModel(description = "Post model information")
@Data
public class PostDto {

    @ApiModelProperty(value = "Blog post id")
    private Long id;

    @ApiModelProperty(value = "Blog post Title")
    @NotEmpty
    @Size(min = 2, message = "Post title should have at least 2 characteres")
    private String title;

    @ApiModelProperty(value = "Blog post Description")
    @NotEmpty
    @Size(min = 2, message = "Post description should have at least 10 characteres")
    private String description;

    @ApiModelProperty(value = "Blog post Content")
    @NotEmpty
    private String content;

    private Set<CommentDto> comments;
}
