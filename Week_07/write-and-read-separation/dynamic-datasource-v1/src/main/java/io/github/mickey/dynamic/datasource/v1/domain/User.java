package io.github.mickey.dynamic.datasource.v1.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author mickey
 * @date 3/6/21 14:29
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

    private Long id;
    private String nickname;
    private String mobile;
    private Integer status;
    private String avatar;
    private String password;
    private Date createdTime;
    private Date updatedTime;

}
