package com.gs.crick.service;

import java.util.List;

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



public interface IUser {
	

	public List<UserDetailsDTO> fetchUserDetails();

	public boolean doCheckDuplicateUserID(String login_id);

	public boolean doSaveUserDetails(UserDetailsDTO user);

	public UserDetailsDTO getUserInfoBasedOnUserName(String username);

	public List<RankDTO> doGetRanks(int playerRole, int matchType);

	public List<RankDTO> doGetTeamRanks(int valueOf);

	public List<SeriesDTO> doGetSeries(int valueOf);

	public List<MatchesDTO> doGetMatches(Integer valueOf);

	public List<TeamsDTO> doGetTeams(int valueOf);

	public List<PlayersDTO> getPlayes(String playername);

	public List<NewsDTO> getNews();

	public List<MatchesDTO> doGetMatcheswithStatus(String status);

	public List<QuestionsDTO> getQuestions(int matchID);

	public List<ContestDTO> getContestes(int matchID);

	public boolean dosaveUserContestData(List<QuestionAnswerPayment> userData);

	public List<ContestDTO> getCurrentContestes(String userName, int matchID);

	public List<UserQuestionAnswerDTO> getCurrentQuestionsAnswers(String userName, int matcheid, int contestid, int contestcount);

	public List<MatchArgumentsDTO> getMatchArguments(int matcheid);

	public List<MatchWinDTO> getMatchWiners(int matcheid, int contestid);

}
