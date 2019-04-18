package org.code4everything.efo.stand.dao.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.code4everything.boot.bean.BaseBean;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author pantao
 * @since 2019-04-11
 */
@Data
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "efo_user", indexes = {@Index(name = "username_index", columnList = "username", unique = true),
        @Index(name = "email_index", columnList = "email", unique = true)})
public class UserDO implements BaseBean, Serializable {

    private static final long serialVersionUID = -7213874850215589561L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @Column(nullable = false, unique = true, columnDefinition = "varchar(64)")
    private String email;

    @Column(nullable = false, columnDefinition = "varchar(12)", unique = true)
    private String username;

    @Column(columnDefinition = "varchar(12)")
    private String nickname;

    @Column(columnDefinition = "varchar(64)", nullable = false)
    private String password;

    @Column(columnDefinition = "char(6)", nullable = false)
    private String salt;

    @Column(columnDefinition = "varchar(64)")
    private String avatar;

    @Column(columnDefinition = "char(1) default '0' comment '性别：0未知，1男，2女'", nullable = false)
    private String gender;

    @Column(columnDefinition = "char(1) default '7' comment '状态：0冻结，7正常'", nullable = false)
    private String status;

    @Column(columnDefinition = "datetime default current_timestamp", nullable = false)
    private LocalDateTime createTime;
}
