package com.github.reyhanmichiels.shortlyservice.business.repository.user;

import com.github.reyhanmichiels.shortlyservice.business.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
}
