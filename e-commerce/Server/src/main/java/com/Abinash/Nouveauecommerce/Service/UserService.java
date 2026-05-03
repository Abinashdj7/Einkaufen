package com.Abinash.Nouveauecommerce.Service;

import com.Abinash.Nouveauecommerce.Exception.UserException;
import com.Abinash.Nouveauecommerce.Model.User;

public interface UserService {
	public User findUserById(Long id) throws UserException;
	
	public User findUserProfileByJwt(String jwt) throws UserException;
}
