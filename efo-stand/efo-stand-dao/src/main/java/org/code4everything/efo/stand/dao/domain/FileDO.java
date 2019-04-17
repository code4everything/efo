package org.code4everything.efo.stand.dao.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.code4everything.boot.bean.BaseBean;

import javax.persistence.Entity;
import javax.persistence.Table;
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
public class FileDO implements BaseBean, Serializable {

    private static final long serialVersionUID = 5947621674068025981L;
}
