package io.github.mickey.shardingsphere.v1.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author mickey
 * @date 3/6/21 14:29
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "t_user")
public class User {

    @Id
    private Long id;

    private String nickname;
    private String mobile;
    private Integer status;
    private String avatar;
    private String password;
    private Date createdTime;
    private Date updatedTime;

}
