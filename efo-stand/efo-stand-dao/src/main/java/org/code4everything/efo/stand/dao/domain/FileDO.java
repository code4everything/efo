package org.code4everything.efo.stand.dao.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.code4everything.boot.base.bean.BaseDomain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author pantao
 * @since 2019-04-17
 */
@Data
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "efo_file")
public class FileDO implements BaseDomain, Serializable {

    private static final long serialVersionUID = 5947621674068025981L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @Override
    public Serializable primaryKey() {
        return id;
    }
}
