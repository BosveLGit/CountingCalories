package com.demo.countingcalories.model.dao;

import com.demo.countingcalories.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositoryDAO extends JpaRepository<User, Long> {



}
