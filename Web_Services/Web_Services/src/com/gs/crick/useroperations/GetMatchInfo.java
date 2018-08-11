package com.gs.crick.useroperations;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.google.gson.Gson;
import com.gs.crick.dto.ContestDTO;
import com.gs.crick.dto.MatchArgumentsDTO;
import com.gs.crick.dto.MatchWinDTO;
import com.gs.crick.dto.MatchesDTO;
import com.gs.crick.dto.NewsDTO;
import com.gs.crick.dto.PlayersDTO;
import com.gs.crick.dto.QuestionAnswerPayment;
import com.gs.crick.dto.QuestionsDTO;
import com.gs.crick.dto.RankDTO;
import com.gs.crick.dto.SeriesDTO;
import com.gs.crick.dto.UserDetailsDTO;
import com.gs.crick.dto.UserQuestionAnswerDTO;
import com.gs.crick.service.IUser;
import com.gs.password.security.PasswordSecurity;
import com.paytm.pg.merchant.CheckSumServiceHelper;

@Component
@Path("/match")
public class GetMatchInfo {
	String MercahntKey="LzlxlvK6qvCNF0eG";
	@Autowired
	private IUser userervice;

	@GET
	@Path("/getSeries")
	@Produces(MediaType.APPLICATION_JSON)
	public String getSeries(@QueryParam("matchcategory") String matchcategory) {
		System.out.println("getSeries matchcategory:::" + matchcategory);
		List<SeriesDTO> series = new ArrayList<>();
		series = userervice.doGetSeries(Integer.valueOf(matchcategory));
		return constructJSONforSeries(series);
	}
	
	@POST
	@Path("/generateChecksum")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String genarateChecksum(String json) {
		TreeMap paramMap=new TreeMap<>();
		String MID = null;
		String ORDER_ID = null;
		String CUST_ID=null;
		String INDUSTRY_TYPE_ID=null;
		String CHANNEL_ID=null;
		String TXN_AMOUNT=null;
		String WEBSITE=null;
		String CALLBACK_URL=null;
		String checkSum=null;
		JSONObject outputJsonObj = new JSONObject();
		try (JsonReader reader = Json.createReader(new StringReader(json))) {
			JsonObject object = reader.readObject();
			MID = object.getString("MID");
			ORDER_ID = object.getString("ORDER_ID");
			CUST_ID = object.getString("CUST_ID");
			INDUSTRY_TYPE_ID = object.getString("INDUSTRY_TYPE_ID");
			CHANNEL_ID = object.getString("CHANNEL_ID");
			TXN_AMOUNT = object.getString("TXN_AMOUNT");
			WEBSITE = object.getString("WEBSITE");
			CALLBACK_URL = object.getString("CALLBACK_URL");
			
	
		paramMap.put("MID" , MID);
		paramMap.put("ORDER_ID" , ORDER_ID);
		paramMap.put("CUST_ID" , CUST_ID);
		paramMap.put("INDUSTRY_TYPE_ID" , INDUSTRY_TYPE_ID);
		paramMap.put("CHANNEL_ID" , CHANNEL_ID);
		paramMap.put("TXN_AMOUNT" , TXN_AMOUNT);
		paramMap.put("WEBSITE" , WEBSITE);
		paramMap.put("CALLBACK_URL" , CALLBACK_URL);
		checkSum =  CheckSumServiceHelper.getCheckSumServiceHelper().genrateCheckSum(MercahntKey, paramMap);
		paramMap.put("CHECKSUMHASH" , checkSum);
		System.out.println("Paytm Payload: "+ paramMap);
		System.out.println("Paytm Checksum: "+ checkSum);
		outputJsonObj.put("CHECKSUMHASH", checkSum);
		
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return checkSum;
		
	}



	@GET
	@Path("/getMatches")
	@Produces(MediaType.APPLICATION_JSON)
	public String getMatches(@QueryParam("seriesid") String seriesid) {
		System.out.println("getMatches seriesid:::" + seriesid);
		List<MatchesDTO> matches = new ArrayList<>();
		matches = userervice.doGetMatches(Integer.valueOf(seriesid));
		return constructJSONforMatches(matches);
	}
	
	@GET
	@Path("/getMatcheswithStatus")
	@Produces(MediaType.APPLICATION_JSON)
	public String getMatcheswithStatus(@QueryParam("status") String status) {
		System.out.println("getMatcheswithStatus status:::" + status);
		List<MatchesDTO> matches = new ArrayList<>();
		matches = userervice.doGetMatcheswithStatus(status);
		return constructJSONforMatches(matches);
	}

	@GET
	@Path("/playerDetails")
	@Produces(MediaType.APPLICATION_JSON)
	public String dogetplayerDetails(@QueryParam("playername") String playername) {
	
		System.out.println("dogetplayerDetails playername:::" + playername);
		List<PlayersDTO> playes = new ArrayList<>();
		playes = userervice.getPlayes(playername);
		return prepareJsonForPlayer(playes);
	}
	
	@GET
	@Path("/GETQUESTIONS")
	@Produces(MediaType.APPLICATION_JSON)
	public String dogetMatchQuestions(@QueryParam("matchID") int matchID) {
	
		System.out.println("dogetMatchQuestions matchID:::" + matchID);
		List<QuestionsDTO> questions = new ArrayList<>();
		questions = userervice.getQuestions(matchID);
		return prepareJsonForQuestions(questions);
	}
	
	
	@GET
	@Path("/GETCONTEST")
	@Produces(MediaType.APPLICATION_JSON)
	public String dogetMatchContests(@QueryParam("matchID") int matchID) {
		System.out.println("dogetMatchContests matchID:::" + matchID);
		List<ContestDTO> contests = new ArrayList<>();
		contests = userervice.getContestes(matchID);
		return prepareJsonForContests(contests);
	}
	
	  @POST
      @Consumes({MediaType.APPLICATION_JSON})
      @Produces({MediaType.TEXT_PLAIN})
      @Path("/addAnswers")
   public boolean addUserAnswers(List<QuestionAnswerPayment> userData) throws Exception{
          return userervice.dosaveUserContestData(userData);
      }
	  
	  
	  @GET
		@Path("/getcurrentcontest")
		@Produces(MediaType.APPLICATION_JSON)
		public String dogetMatchCurrentContests(@QueryParam("userName") String userName,@QueryParam("matchID") int matchID) {
			System.out.println("dogetMatchCurrentContests userName:::" + userName+"::matchID::"+matchID);
			List<ContestDTO> contests = new ArrayList<>();
			contests = userervice.getCurrentContestes(userName,matchID);
			return prepareJsonForContests(contests);
		}
  
	  @GET
		@Path("/getcontestQusAns")
		@Produces(MediaType.APPLICATION_JSON)
		public String dogetContestsQuestionsAnswers(@QueryParam("userName") String userName,@QueryParam("matcheid") int matcheid,
				@QueryParam("contestid") int contestid,@QueryParam("contestcount") int contestcount) {
			System.out.println("dogetMatchCurrentContests userName:::" + userName+"::matcheid::"+matcheid+"::contestid::"+contestid+"::contestcount::"+contestcount);
			List<UserQuestionAnswerDTO> QuestionAnswers = new ArrayList<>();
			List<MatchArgumentsDTO> argument = new ArrayList<>();
			QuestionAnswers = userervice.getCurrentQuestionsAnswers(userName,matcheid,contestid,contestcount);
			argument=userervice.getMatchArguments(matcheid);
			return prepareJsonForUserQueAns(QuestionAnswers,argument);
		}

	  private String prepareJsonForUserQueAns(List<UserQuestionAnswerDTO> queans, List<MatchArgumentsDTO> argument) {
			Gson gson = new Gson();
			Type type = new TypeToken<List<UserQuestionAnswerDTO>>() {
			}.getType();
			String json = gson.toJson(queans, type);
			Type type2 = new TypeToken<List<MatchArgumentsDTO>>() {
			}.getType();
			String json1 = gson.toJson(argument, type2);
			String data=json+"#argument#"+json1;
			return data;
		}
	  
	  @GET
		@Path("/getmatchwiners")
		@Produces(MediaType.APPLICATION_JSON)
		public String dogetMatchWiners(@QueryParam("matcheid") int matcheid,
				@QueryParam("contestid") int contestid) {
			System.out.println("dogetMatchCurrentContests :::::matcheid::"+matcheid+"::contestid::"+contestid+"::contestcount::");
			List<MatchWinDTO> matchWin = new ArrayList<>();
			matchWin = userervice.getMatchWiners(matcheid,contestid);
			return prepareJsonForMatchWiners(matchWin);
		}
	  
	  private String prepareJsonForMatchWiners(List<MatchWinDTO> matchWin) {
			Gson gson = new Gson();
			Type type = new TypeToken<List<ContestDTO>>() {
			}.getType();
			String json = gson.toJson(matchWin, type);
			return json;
		}
	
	private String prepareJsonForContests(List<ContestDTO> contests) {
		Gson gson = new Gson();
		Type type = new TypeToken<List<ContestDTO>>() {
		}.getType();
		String json = gson.toJson(contests, type);
		return json;
	}

	private String prepareJsonForQuestions(List<QuestionsDTO> questions) {
		Gson gson = new Gson();
		Type type = new TypeToken<List<QuestionsDTO>>() {
		}.getType();
		String json = gson.toJson(questions, type);
		return json;
	}

	@GET
	@Path("/news")
	@Produces(MediaType.APPLICATION_JSON)
	public String dogetNews() {
		List<NewsDTO> news = new ArrayList<>();
		news = userervice.getNews();
		return prepareJsonForNews(news);
	}
	
	public String prepareJsonForNews(List<NewsDTO> news) {
		Gson gson = new Gson();
		Type type = new TypeToken<List<NewsDTO>>() {
		}.getType();
		String json = gson.toJson(news, type);
		return json;
	}

	public String prepareJsonForPlayer(List<PlayersDTO> playes) {
		Gson gson = new Gson();
		Type type = new TypeToken<List<PlayersDTO>>() {
		}.getType();
		String json = gson.toJson(playes, type);
		return json;
	}

	private String constructJSONforMatches(List<MatchesDTO> matches) {
		if (matches != null) {
			Gson gson = new Gson();
			Type type = new TypeToken<List<MatchesDTO>>() {
			}.getType();
			return gson.toJson(matches, type);
		}
		return null;
	}

	public static String constructJSONforSeries(List<SeriesDTO> series) {
		if (series != null) {
			Gson gson = new Gson();
			Type type = new TypeToken<List<SeriesDTO>>() {
			}.getType();
			return gson.toJson(series, type);
		}
		return null;
	}
}
