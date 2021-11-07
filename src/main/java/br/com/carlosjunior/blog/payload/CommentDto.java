package br.com.carlosjunior.blog.payload;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@ApiModel(description = "Comment model information")
@Data
public class CommentDto {

    @ApiModelProperty(value = "Blog comment id")
    private Long id;

    @ApiModelProperty(value = "Blog comment Name")
    @NotEmpty(message = "Name should not be null or empty")
    private String name;

    @ApiModelProperty(value = "Blog comment E-mail")
    @NotEmpty(message = "Email shoul not be null or empty")
    @Email
    private String email;

    @ApiModelProperty(value = "Blog comment Body")
    @NotEmpty
    @Size(min = 10, message = "Comment body must be minimum 10 caracters")
    private String body;

}
