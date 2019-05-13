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
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "efo_category", indexes = {@Index(name = "name", columnList = "name"), @Index(name = "parent_id",
        columnList = "parentId")})
public class CategoryDO implements BaseDomain {

    private static final long serialVersionUID = -6971723545574282972L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Integer id;

    @Column(columnDefinition = "varchar(8)", nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer parentId;

    @Column(columnDefinition = "datetime default current_timestamp")
    private LocalDateTime createTime;

    @Override
    public Serializable primaryKey() {
        return id;
    }
}
