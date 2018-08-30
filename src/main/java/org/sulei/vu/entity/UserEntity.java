package org.sulei.vu.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_user")
@Getter
@Setter
public class UserEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private int id;

	private Date gmtCreate;
	
	private Date gmtModify;

	private String name;
	
	private Date birthday;
	
	private String phone;
	
	private String email;
	
	private String authType;
	
	private String authOpenid;
	
	private String authUsername;

	private String authNickname;

	private String authLogo;
	
	private String recommendOpenid;
	
	private BigDecimal balance;
	
	private Integer adviserId;
	
	private Integer regionId;

}
