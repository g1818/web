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
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.gs.crick.dto.MatchesDTO;
import com.gs.crick.dto.RankDTO;
import com.gs.crick.dto.SeriesDTO;
import com.gs.crick.dto.TeamsDTO;
import com.gs.crick.dto.UserDetailsDTO;
import com.gs.crick.service.IUser;
import com.gs.password.security.PasswordSecurity;
@Component
@Path("/teams")
public class GetTeams {

	@Autowired
	private IUser userervice;

	@GET
	@Path("/getTeams")
	@Produces(MediaType.APPLICATION_JSON)
	public String getTeams(@QueryParam("matchcategory") String matchcategory) {
		System.out.println("getTeams matchcategory:::" + matchcategory);
		List<TeamsDTO> teams = new ArrayList<>();
		teams= userervice.doGetTeams(Integer.valueOf(matchcategory));
		return constructJSONforSeries(teams);
	}
	
	@GET
	@Path("/getMatches")
	@Produces(MediaType.APPLICATION_JSON)
	public String getMatches(@QueryParam("seriesid") String seriesid) {
		System.out.println("getMatches seriesid:::" + seriesid);
		List<MatchesDTO> matches = new ArrayList<>();
		matches= userervice.doGetMatches(Integer.valueOf(seriesid));
		return constructJSONforMatches(matches);
	}

private String constructJSONforMatches(List<MatchesDTO> matches) {
	if(matches != null){		
		Gson gson = new Gson();
		Type type = new TypeToken<List<MatchesDTO>>() {}.getType();
		return gson.toJson(matches, type);
		}
		return null;
	}


public static String constructJSONforSeries(List<TeamsDTO> Teams) {
	if(Teams != null){		
		Gson gson = new Gson();
		Type type = new TypeToken<List<TeamsDTO>>() {}.getType();
		return gson.toJson(Teams, type);
		}
		return null;
	}
}

