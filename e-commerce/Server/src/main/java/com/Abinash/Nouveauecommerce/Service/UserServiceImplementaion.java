package com.Abinash.Nouveauecommerce.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Abinash.Nouveauecommerce.Config.JwtProvider;
import com.Abinash.Nouveauecommerce.Exception.UserException;
import com.Abinash.Nouveauecommerce.Model.User;
import com.Abinash.Nouveauecommerce.Repo.UserRepo;

@Service
public class UserServiceImplementaion implements UserService{

	@Autowired
	UserRepo userRepo;
	
	@Autowired
	JwtProvider jwtProvider;
	
	@Override
	public User findUserById(Long id) throws UserException {
		// TODO Auto-generated method stub
		Optional<User> user=userRepo.findById(id);
		if(user.isPresent()) {
			return user.get();
		}
		throw new UserException("User not found with id "+id);
	}

	@Override
	public User findUserProfileByJwt(String jwt) throws UserException {
		String email=jwtProvider.getEmailFromToken(jwt);
		User user =userRepo.findByEmail(email);
		if(user==null) {
			throw new UserException("User not found with email"+email);
		}
		return user;
	} 

}
