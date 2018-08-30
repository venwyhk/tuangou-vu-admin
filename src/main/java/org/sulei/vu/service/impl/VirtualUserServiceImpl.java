package org.sulei.vu.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sulei.vu.entity.UserEntity;
import org.sulei.vu.repository.UserRepo;
import org.sulei.vu.service.VirtualUserDTO;
import org.sulei.vu.service.VirtualUserService;
import org.sulei.vu.utils.Base64Util;

@Service("VirtualUserService")
public class VirtualUserServiceImpl implements VirtualUserService {

	@Value("${imagePath}")
	private String imagePath;

	@Value("${imageUrl}")
	private String imageUrl;

	@Value("${imageSuffix:.jpg}")
	private String imageSuffix;

	@Autowired
	private UserRepo userRepo;

	@Override
	@Transactional
	public synchronized int addVirtualUser(VirtualUserDTO virtualUserDto, String img) {
		int imageId = new Random().nextInt(99999);
		InputStream is = null;
		OutputStream out = null;
		try {
			File file = new File(imagePath + imageId + imageSuffix);
			if (file.createNewFile() && img.indexOf("base64,") > -1) {
				String imgStr = img.split("base64,")[1];
				// String imgStr = img.replaceAll("data:image/png;base64,", "");
				byte[] b = Base64Util.decodeBase64(imgStr);
				for (int i = 0; i < b.length; ++i)
					if (b[i] < 0)
						b[i] += 256;
				out = new FileOutputStream(imagePath + imageId + imageSuffix);
				out.write(b);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null)
				try {
					is.close();
				} catch (IOException e) {
				}
			if (out != null)
				try {
					out.close();
				} catch (IOException e) {
				}
		}
		UserEntity user = new UserEntity();
		user.setGmtCreate(new Date());
		user.setGmtModify(new Date());
		user.setName(virtualUserDto.getName());
		user.setBirthday(new Date());
		user.setPhone("0272930562");
		user.setEmail("lei.sul@zhinanzhen.org");
		user.setAuthType("V");
		user.setAuthOpenid("virtualUser");
		user.setAuthUsername("virtualUser");
		try {
			user.setAuthNickname(new String(Base64Util.encodeBase64(virtualUserDto.getAuthNickname().getBytes())));
		} catch (Exception e) {
			user.setAuthNickname("5oyH5Y2X6ICF");
		}
		user.setAuthLogo(imageUrl + imageId + imageSuffix);
		user.setBalance(new BigDecimal(0.00));
		user.setAdviserId(null);
		user.setRegionId(0);
		return userRepo.saveAndFlush(user).getId();
	}

	@Override
	public List<VirtualUserDTO> listVirtualUser() {
		List<UserEntity> userList = userRepo.findByAuthTypeOrderByIdDesc("V");
		List<VirtualUserDTO> virtualUserList = new ArrayList<>();
		for (UserEntity user : userList)
			try {
				virtualUserList.add(new VirtualUserDTO(user.getId(), user.getGmtCreate(), user.getName(),
						new String(Base64Util.decodeBase64(user.getAuthNickname()), "utf-8"), user.getAuthLogo()));
			} catch (Exception e) {
				virtualUserList.add(new VirtualUserDTO(user.getId(), user.getGmtCreate(), user.getName(), "指南者",
						user.getAuthLogo()));
			}
		return virtualUserList;
	}

	@Override
	public void deleteById(int id) {
		userRepo.delete(id);
	}

}
