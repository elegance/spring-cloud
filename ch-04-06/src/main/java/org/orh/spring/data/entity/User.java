package org.orh.spring.data.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tb_user")
@Data
public class User extends BaseEntity {

    private String username, password, passwordSalt;

}
