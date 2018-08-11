package com.gs.crick.dao;

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

public interface IUserDao {

	List<UserDetailsDTO> fetchUserDetails();

	int doCheckDuplicateUserID(String login_id);

	boolean doSaveUser(UserDetailsDTO user);

	UserDetailsDTO getUserInfoBasedOnUserName(String username);

	List<RankDTO> doGetRanks(int playerRole, int matchType);

	List<RankDTO> doGetTeamRanks(int matchType);

	List<SeriesDTO> doGetSeries(int valueOf);

	List<MatchesDTO> doGetMatches(Integer seriesid);

	List<TeamsDTO> doGetTeams(int matchCatId);

	List<PlayersDTO> getPlayes(String playername);

	List<NewsDTO> getNews();

	List<MatchesDTO> doGetMatcheswithStatus(String status);

	List<QuestionsDTO> getQuestions(int matchID);

	List<ContestDTO> getContestes(int matchID);

	boolean dosaveUserContestData(List<QuestionAnswerPayment> userData);

	List<ContestDTO> getCurrentContestes(String userName, int matchID);

	List<UserQuestionAnswerDTO> getCurrentQuestionsAnswers(String userName, int matcheid, int contestid, int contestcount);

	List<MatchArgumentsDTO> getMatchArguments(int matcheid);

	List<MatchWinDTO> getMatchWiners( int matcheid, int contestid);

}
