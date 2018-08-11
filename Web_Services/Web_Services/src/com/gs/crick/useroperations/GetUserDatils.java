package com.gs.crick.useroperations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gs.crick.dto.MatchesDTO;
import com.gs.crick.dto.PlayersDTO;
import com.gs.crick.dto.UserDetailsDTO;
import com.gs.crick.service.IUser;

import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Type;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;


@Component
@Path("/getUserDatils")
public class GetUserDatils {
	
	@Autowired
	private IUser userervice;
	
	@GET
	@Path("/userDetails")
	@Produces(MediaType.APPLICATION_JSON) 
	public String dogetuserDetails(){
		
	 	return prepareJsonForUsers(getDashboardMenu());
	}
	
	private List<UserDetailsDTO> getDashboardMenu() {
		List<UserDetailsDTO> userDetails=new ArrayList<>();
		userDetails=userervice.fetchUserDetails();
		return userDetails;
	}

	
	public String prepareJsonForUsers(List<UserDetailsDTO> userdetails){
		
		Gson gson = new Gson();
		Type type = new TypeToken<List<UserDetailsDTO>>() {}.getType();
		String json = gson.toJson(userdetails, type);
		return json;
	}
}
