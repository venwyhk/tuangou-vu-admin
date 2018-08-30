package org.sulei.vu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.sulei.vu.entity.UserEntity;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Integer>{
	List<UserEntity> findByAuthTypeOrderByIdDesc(String authType);
}
