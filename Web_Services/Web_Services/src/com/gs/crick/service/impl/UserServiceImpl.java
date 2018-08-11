package com.gs.crick.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import com.gs.password.security.PasswordSecurity;

import org.springframework.beans.factory.annotation.Autowired;

import com.gs.crick.dao.IUserDao;
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
import com.gs.crick.dto.TeamsDTO;
import com.gs.crick.dto.UserDetailsDTO;
import com.gs.crick.dto.UserQuestionAnswerDTO;
import com.gs.crick.service.IUser;

public class UserServiceImpl implements IUser {
	
	@Autowired
	private  IUserDao userdao;

	@Override
	public List<UserDetailsDTO> fetchUserDetails() {
		return userdao.fetchUserDetails();
	}

	@Override
	public boolean doCheckDuplicateUserID(String email) {
	 boolean duplicateUser=false;
	int userCount=userdao.doCheckDuplicateUserID(email);
		
	if(userCount>0){
		duplicateUser=true;
	}else{
		duplicateUser=false;
	}
		return duplicateUser;
	}

	@Override
	public boolean doSaveUserDetails(UserDetailsDTO user) {
		PasswordSecurity passwordSecurity = new PasswordSecurity();
		String Hashedpass = passwordSecurity.generateHashedPassword(user, user.getPassword());
		String[] hashedPassword = Hashedpass.split(":");
		user.setPassword(hashedPassword[4]);
		user.setSaltValue(hashedPassword[3]);
		UUID uniqueKey = UUID.randomUUID(); 
		user.setUser_id(uniqueKey.toString().replace("-", ""));
		user.setUser_type("E");
		user.setCreate_date(new Date());
		user.setEnd_date(null);
		
		return userdao.doSaveUser(user);
	}

	@Override
	public UserDetailsDTO getUserInfoBasedOnUserName(String email) {
		return userdao.getUserInfoBasedOnUserName(email);
	}

	@Override
	public List<RankDTO> doGetRanks(int playerRole, int matchType) {
		return userdao.doGetRanks(playerRole,matchType);
	}

	@Override
	public List<RankDTO> doGetTeamRanks(int matchType) {
		return userdao.doGetTeamRanks(matchType);
	}

	@Override
	public List<SeriesDTO> doGetSeries(int valueOf) {
		return userdao.doGetSeries(valueOf);
	}

	@Override
	public List<MatchesDTO> doGetMatches(Integer seriesid) {
		return userdao.doGetMatches(seriesid);
	}

	@Override
	public List<TeamsDTO> doGetTeams(int matchCatId) {
		return userdao.doGetTeams(matchCatId);
	}

	@Override
	public List<PlayersDTO> getPlayes(String playername) {
		return userdao.getPlayes(playername);
	}

	@Override
	public List<NewsDTO> getNews() {
		return userdao.getNews();
	}

	@Override
	public List<MatchesDTO> doGetMatcheswithStatus(String status) {
		return userdao.doGetMatcheswithStatus(status);
	}

	@Override
	public List<QuestionsDTO> getQuestions(int matchID) {
		return userdao.getQuestions(matchID);
	}

	@Override
	public List<ContestDTO> getContestes(int matchID) {
		return userdao.getContestes(matchID);
	}

	@Override
	public boolean dosaveUserContestData(List<QuestionAnswerPayment> userData) {
		return userdao.dosaveUserContestData(userData);
	}

	@Override
	public List<ContestDTO> getCurrentContestes(String userName,int matchID) {
		return userdao.getCurrentContestes(userName,matchID);
	}

	@Override
	public List<UserQuestionAnswerDTO> getCurrentQuestionsAnswers(String userName, int matcheid, int contestid, int contestcount) {
		return userdao.getCurrentQuestionsAnswers(userName,matcheid,contestid,contestcount);
	}

	@Override
	public List<MatchArgumentsDTO> getMatchArguments(int matcheid) {
		return userdao.getMatchArguments(matcheid);
	}

	@Override
	public List<MatchWinDTO> getMatchWiners(int matcheid, int contestid) {
		return userdao.getMatchWiners(  matcheid, contestid);
	}

}
