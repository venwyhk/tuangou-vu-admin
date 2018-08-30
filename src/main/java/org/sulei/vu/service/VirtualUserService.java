package org.sulei.vu.service;

import java.util.List;

public interface VirtualUserService {

	int addVirtualUser(VirtualUserDTO virtualUserDto, String img);

	List<VirtualUserDTO> listVirtualUser();

	void deleteById(int id);

}
