package org.code4everything.efo.stand.dao.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.code4everything.boot.base.bean.BaseDomain;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author pantao
 * @since 2019/5/13
 **/
@Data
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "efo_tag", indexes = {@Index(name = "user_id", columnList = "userId"), @Index(name = "tag", columnList
        = "tag")})
public class TagDO implements BaseDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false, columnDefinition = "varchar(8)")
    private String tag;

    @Column(columnDefinition = "datetime default current_timestamp")
    private LocalDateTime createTime;

    @Override
    public Serializable primaryKey() {
        return id;
    }
}
