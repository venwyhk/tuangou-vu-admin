package org.sulei.vu.service;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class VirtualUserDTO {
	
	private int id;

	private Date gmtCreate;
	
	private String name;
	
	private String authNickname;

	private String authLogo;

}
