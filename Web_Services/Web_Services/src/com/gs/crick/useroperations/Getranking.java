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
import com.gs.crick.dto.RankDTO;
import com.gs.crick.dto.SeriesDTO;
import com.gs.crick.dto.UserDetailsDTO;
import com.gs.crick.service.IUser;
import com.gs.password.security.PasswordSecurity;
@Component
@Path("/ranking")
public class Getranking {

	@Autowired
	private IUser userervice;

	@GET
	@Path("/getRank")
	@Produces(MediaType.APPLICATION_JSON)
	public String printMessage(@QueryParam("playerRole") String playerRole, @QueryParam("matchType") String matchType) {
		System.out.println("getRank playerRole:::" + playerRole + ":::matchType:::" + matchType );
		List<RankDTO> rank = new ArrayList<>();
		rank= userervice.doGetRanks(Integer.valueOf(playerRole),Integer.valueOf(matchType));
		return constructJSON(rank);
	}

	@GET
	@Path("/getTeamRank")
	@Produces(MediaType.APPLICATION_JSON)
	public String getTeamRank(@QueryParam("matchType") String matchType) {
		System.out.println("getRank:::matchType:::" + matchType );
		List<RankDTO> teamrank= userervice.doGetTeamRanks(Integer.valueOf(matchType));
		return constructJSON(teamrank);
	}


public static String constructJSON(List<RankDTO> rank) {
	if(rank != null){		
		Gson gson = new Gson();
		Type type = new TypeToken<List<RankDTO>>() {}.getType();
		return gson.toJson(rank, type);
		}
		return null;
	}

	
}

