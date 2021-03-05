package io.github.mickey.jdbc.domain;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
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
