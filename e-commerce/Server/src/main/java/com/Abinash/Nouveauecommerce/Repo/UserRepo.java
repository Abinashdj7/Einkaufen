package com.Abinash.Nouveauecommerce.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Abinash.Nouveauecommerce.Model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long>{
	
	User findByEmail(String email);
	
}
