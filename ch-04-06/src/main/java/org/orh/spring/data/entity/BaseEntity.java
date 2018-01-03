package org.orh.spring.data.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * 基本抽象类：具有 id、创建时间、修改时间
 */
@Data
@MappedSuperclass //表示类不是一个完整的的实体类，不会映射到数据库表
public abstract class BaseEntity implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @CreationTimestamp
    private Date createTime;

    @UpdateTimestamp
    private Date updateTime;
}
