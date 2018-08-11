package com.gs.crick.useroperations;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.gs.crick.dto.UserDetailsDTO;
import com.gs.crick.service.IUser;
import com.gs.password.security.PasswordSecurity;
@Component
@Path("/userCreation")
public class UserRegistration {

	@Autowired
	private IUser userervice;

	@GET
	@Path("/doRegistration")
	@Produces(MediaType.APPLICATION_JSON)
	public String printMessage(@QueryParam("userName") String userName, @QueryParam("password") String password,
			@QueryParam("phone_no") String phone_no, @QueryParam("email") String email) {
		System.out.println("Registration Details:::" + userName + "::" + password + "::" + phone_no + "::" + email);
		UserDetailsDTO user = new UserDetailsDTO();
		UserDetailsDTO jsonUser = new UserDetailsDTO();
		jsonUser.setUserName(userName);
		jsonUser.setEmail_id(email);
		jsonUser.setPhone_number(phone_no);
		if (!validateUserCredentials(email)) {
			user.setUserName(userName);
			user.setPassword(password);
			user.setEmail_id(email);
			user.setPhone_number(phone_no);
			userervice.doSaveUserDetails(user);
			jsonUser.setStatus(true);
		} else {

			jsonUser.setStatus(false);

		}
		return constructJSON(jsonUser);

	}


private boolean validateUserCredentials(String email) {
	System.out.println("validateUserCredentials::Start::"+email);
	return userervice.doCheckDuplicateUserID(email);
}

@GET
@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
public String login(@QueryParam("email") String email, @QueryParam("password") String password){
	
	
	UserDetailsDTO userDetailsDTO=new UserDetailsDTO();
	PasswordSecurity passwordSecurity=new PasswordSecurity();
	
	userDetailsDTO=userervice.getUserInfoBasedOnUserName(email);
	if(userDetailsDTO == null){
		
		userDetailsDTO=new UserDetailsDTO();
		userDetailsDTO.setStatus(false);
		
	}
	else{
		
		
	boolean verifyPassword=passwordSecurity.verifyPassword(password, userDetailsDTO.getPassword(), userDetailsDTO.getSaltValue(), userDetailsDTO);
		
	userDetailsDTO.setStatus(verifyPassword);
	}
	
	return constructJSON(userDetailsDTO);
	
}


public static String constructJSON(UserDetailsDTO user) {
	if(user != null){
		
		Gson gson = new Gson();
		Type type = new TypeToken<UserDetailsDTO>() {
		}.getType();
		return gson.toJson(user, type);
		}
		return null;
	}

	
}

