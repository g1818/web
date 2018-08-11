package com.gs.crick.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.gs.crick.base.BaseDaoImpl;
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

public class IUserDaoImpl extends BaseDaoImpl implements IUserDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	static final private Logger LOGGER = Logger.getLogger(IUserDaoImpl.class
			.getName());

	@Override
	public UserDetailsDTO getUserInfoBasedOnUserName(String email) {
		LOGGER.info("fetchUserDetails enter");
		String finalQry = "select * from crick.tbl_user_mst where email_id=? ";
		LOGGER.info("Created query  = " + finalQry);
		List<UserDetailsDTO> list = jdbcTemplate.query(finalQry,
				BeanPropertyRowMapper.newInstance(UserDetailsDTO.class),email);
		return list.get(0);
	}

	@Override
	public int doCheckDuplicateUserID(String email_id) {
		String query = "select count(email_id) from crick.tbl_user_mst  where email_id=?";
		return jdbcTemplate.queryForInt(query, email_id);
	}

	@Override
	public boolean doSaveUser(UserDetailsDTO user) {
		try {
			jdbcTemplate.update(
					"INSERT INTO  crick.TBL_USER_MST(USER_ID, email_id,phone_number,password,user_type"
							+ ",create_date,end_date,saltvalue,user_name"
							+ ") VALUES (?,?,?,?,?,?,?,?,?)",
					new Object[] { user.getUser_id(), user.getEmail_id(), user.getPhone_number(), user.getPassword(),
							user.getUser_type(), user.getCreate_date(), user.getEnd_date(), user.getSaltValue(),
							user.getUserName()});
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}

	@Override
	public List<UserDetailsDTO> fetchUserDetails() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RankDTO> doGetRanks(int playerRole, int matchType) {
		LOGGER.info("doGetRanks enter");
		String finalQry = "select rk.RANKING_NUMBNER as ranknumber,pm.PLAYER_NAME as playerName,cm.Country_Sh_name as countryName from tbl_ranking_mst rk,tbl_player_mst pm,"
				+ " tbl_country_mst cm where rk.PLAYER_ID=pm.PLAYER_ID and pm.Country_ID=cm.Country_ID "
				+ " and  PLAYER_ROLE_ID=? and match_type_id=?";
		LOGGER.info("Created query  = " + finalQry);
		List<RankDTO> list = jdbcTemplate.query(finalQry,
				BeanPropertyRowMapper.newInstance(RankDTO.class),playerRole,matchType);
		return list;
	}

	@Override
	public List<RankDTO> doGetTeamRanks(int matchType) {
		String finalQry = "select rk.RANKING_NUMBNER as ranknumber,tm.team_name as playerName,rk.POINTS as countryName "
				+ "    from tbl_team_ranking rk,tbl_tems_mst tm"
				+ "  where rk.TEAM_ID=tm.TEAM_ID   and match_type_id=? ";
		List<RankDTO> list = jdbcTemplate.query(finalQry,
				BeanPropertyRowMapper.newInstance(RankDTO.class),matchType);
		return list;
	}

	@Override
	public List<SeriesDTO> doGetSeries(int matchType) {
		String finalQry = "SELECT series_name as seriesName, series_start_date as startDate,"
				+ "   series_end_date as end_date,series_id as seriesId FROM crick.tbl_series_mst where display_flag='Y'"
				+ "  and match_category_id=? ";
		List<SeriesDTO> list = jdbcTemplate.query(finalQry,
				BeanPropertyRowMapper.newInstance(SeriesDTO.class),matchType);
		return list;
	}

	@Override
	public List<MatchesDTO> doGetMatches(Integer seriesid) {
		/*String finalQry = " select matcheid1 as matcheid ,firstTeam,secondTeam,startDate,end_date,result,venue,matchType  from"
				+ "          ("
				+ "            select  matche_id as matcheid1,match_start_date as startDate ,match_end_date as end_date,"
				+ "            match_result as result,vm.citi_name as venue,tmt.match_type_name as matchType"
				+ "            from tbl_matches_mst mm, tbl_match_type_mst tmt, tbl_venues_mst vm"
				+ "             where mm.venue_id=vm.venue_id and mm.match_type_id=tmt.match_type_id  and mm.series_id=?)tbl, "
				+ "           (select  matche_id as matcheid2,team_name as firstTeam  from tbl_matches_mst mm, tbl_tems_mst tm"
				+ "             where tm.team_id=mm.first_team_id  and mm.series_id=?) tbl1,"
				+ "           (select  matche_id as matcheid3,team_name as secondTeam  from tbl_matches_mst mm,tbl_tems_mst tm"
				+ "             where tm.team_id=mm.second_team_id  and mm.series_id=?) tbl2"
				+ "        where matcheid1=matcheid2 and matcheid1=matcheid3 ";*/
		String finalQry="select matcheid1 as matcheid ,firstTeam,secondTeam,startDate,end_date,result,venue,matchType ,matchcomment,resultcomment,firstteamscore,"
				+ "  secondteamscore from ( select  matche_id as matcheid1,match_start_date as startDate ,match_end_date as end_date,"
				+ "  match_result as result,vm.citi_name as venue,tmt.match_type_name as matchType,"
				+ "  match_comment as matchcomment,result_comment as resultcomment,first_team_score firstteamscore,second_team_score as "
				+ "   secondteamscore from tbl_matches_mst mm, tbl_match_type_mst tmt, tbl_venues_mst vm"
				+ "   where mm.venue_id=vm.venue_id and mm.match_type_id=tmt.match_type_id  and mm.series_id=?)tbl,"
				+ "   (select  matche_id as matcheid2,team_name as firstTeam  from tbl_matches_mst mm, tbl_tems_mst tm"
				+ "   where tm.team_id=mm.first_team_id  and mm.series_id=?) tbl1,"
				+ "   (select  matche_id as matcheid3,team_name as secondTeam  from tbl_matches_mst mm,tbl_tems_mst tm"
				+ "    where tm.team_id=mm.second_team_id  and mm.series_id=?) tbl2"
				+ "    where matcheid1=matcheid2 and matcheid1=matcheid3";
				List<MatchesDTO> list = jdbcTemplate.query(finalQry,
				BeanPropertyRowMapper.newInstance(MatchesDTO.class),seriesid,seriesid,seriesid);
		return list;
	}

	@Override
	public List<TeamsDTO> doGetTeams(int matchCatId) {
		String finalQry = " SELECT team_id as teamid,team_name as teamname,team_sh_name as teamshname,"
				          + "   team_image as teamimage FROM crick.tbl_tems_mst where match_category_id =? ";
		List<TeamsDTO> list = jdbcTemplate.query(finalQry,
				BeanPropertyRowMapper.newInstance(TeamsDTO.class),matchCatId);
		return list;

	}

	@Override
	public List<PlayersDTO> getPlayes(String playername) {
		System.out.println("playername::::"+playername);
		String finalQry = " select pm.PLAYER_ID as playerid,pm.PLAYER_NAME as playername,cm.country_name as country  from "
				+ " tbl_player_mst pm,tbl_country_mst cm where pm.Country_ID=cm.Country_ID ";
		System.out.println(playername.isEmpty());
		System.out.println(playername!=null);
				if(playername!=null&&!playername.isEmpty()){
					finalQry=finalQry+	"  and PLAYER_NAME like '%'"+playername+"'%'";
				}
List<PlayersDTO> list = jdbcTemplate.query(finalQry,
		BeanPropertyRowMapper.newInstance(PlayersDTO.class));
		return list;
	}

	@Override
	public List<NewsDTO> getNews() {
		String finalQry = " SELECT NEWS_ID as newsid,NEWS_NAME as newsName,NEWS_DESC as newsDesc FROM crick.tbl_news_mst order by 1 desc";
		
    List<NewsDTO> list = jdbcTemplate.query(finalQry,
	        	BeanPropertyRowMapper.newInstance(NewsDTO.class));
		return list;
	}

	@Override
	public List<MatchesDTO> doGetMatcheswithStatus(String status) {
		String finalQry="select matcheid1 as matcheid ,firstTeam,secondTeam,startDate,end_date,result,venue,matchType ,matchcomment,resultcomment,firstteamscore,"
				+ "  secondteamscore from ( select  matche_id as matcheid1,match_start_date as startDate ,match_end_date as end_date,"
				+ "  match_result as result,vm.citi_name as venue,tmt.match_type_name as matchType,"
				+ "  match_comment as matchcomment,result_comment as resultcomment,first_team_score firstteamscore,second_team_score as "
				+ "   secondteamscore from tbl_matches_mst mm, tbl_match_type_mst tmt, tbl_venues_mst vm"
				+ "   where mm.venue_id=vm.venue_id and mm.match_type_id=tmt.match_type_id  and mm.match_result=?)tbl,"
				+ "   (select  matche_id as matcheid2,team_name as firstTeam  from tbl_matches_mst mm, tbl_tems_mst tm"
				+ "   where tm.team_id=mm.first_team_id  and mm.match_result=?) tbl1,"
				+ "   (select  matche_id as matcheid3,team_name as secondTeam  from tbl_matches_mst mm,tbl_tems_mst tm"
				+ "    where tm.team_id=mm.second_team_id  and mm.match_result=?) tbl2"
				+ "    where matcheid1=matcheid2 and matcheid1=matcheid3";
				List<MatchesDTO> list = jdbcTemplate.query(finalQry,
				BeanPropertyRowMapper.newInstance(MatchesDTO.class),status,status,status);
		return list;
	}

	@Override
	public List<QuestionsDTO> getQuestions(int matchID) {
		String finalQry="SELECT MATCH_QUESTIONS_ID as matchQuestionsID,TMQ.QUESTION_ID as questionsID, QUESTION_DESC,FIRST_OPTION,SECOND_OPTION,ANSWERS FROM tbl_match_questions_mst TMQ,tbl_questions_mst TQ"
				+ " WHERE TMQ.QUESTION_ID=TQ.QUESTION_ID AND TMQ.matche_id=?";
				List<QuestionsDTO> list = jdbcTemplate.query(finalQry,
				BeanPropertyRowMapper.newInstance(QuestionsDTO.class),matchID);
		return list;
	}

	@Override
	public List<ContestDTO> getContestes(int matchID) {
		String finalQry="select CONTEST_ID as contestId,CONTEST_NAME as contestName,CONTEST_FEE as contestFee,"
				+ " CONTEST_WINER_COUNT as contestWinerCount,CONTEST_MAX_ENTRYIES as contestMaxEntryies,ENTRYIES_LEFT as entryiesLeft"
				+ " from tbl_contests_mst where matche_id=?";
				List<ContestDTO> list = jdbcTemplate.query(finalQry,
				BeanPropertyRowMapper.newInstance(ContestDTO.class),matchID);
		return list;
	}

	@Override
	public boolean dosaveUserContestData(List<QuestionAnswerPayment> userData) {
		try {
			
			String SQL = "select max(ANSWERS_MST_ID) as ANSWERS_MST_ID from TBL_ANSWERS_MST";
			String SQL1 = "select max(CONTEST_COUNT) as CONTEST_COUNT from TBL_ANSWERS_MST where USER_EMAIL=? and CONTEST_ID=? and matche_id=? ";
			System.out.println("SQL::::"+SQL+":::SQL1:::"+SQL1);
			Integer count = jdbcTemplate.queryForObject(SQL, new Object[] {  }, Integer.class);
			Integer contestCount = jdbcTemplate.queryForObject(SQL1, new Object[] {userData.get(0).getUseremail(),userData.get(0).getContestId(),userData.get(0).getMatchId() }, Integer.class);
			System.out.println("count::::"+count+":::contestCount:::"+contestCount);
			if(count==null){
				count=1;
			}else{
					count++;
				}
			if(contestCount==null){
				contestCount=0;
			}
			System.out.println("after  count::::"+count+":::contestCount:::"+contestCount);
			List<Object[]> parameters = new ArrayList<Object[]>();
			for (QuestionAnswerPayment data : userData) {
				System.out.println("count::"+count);
				System.out.println("contestCount::"+contestCount);
				System.out.println("data.getMatchQuestionsID()::"+data.getMatchQuestionsID());
				System.out.println("data.getMatchQuestionsID()::"+data.getQuestionID());
				System.out.println("data.getUseremail()::"+data.getAnswers());
				System.out.println("data.getUseremail()::"+data.getUseremail());
				System.out.println("data.getUseremail()::"+data.getPaymentStatus());
				System.out.println("data.getContestId()::"+data.getContestId());
				System.out.println("data.getMatchId()::"+data.getMatchId());
				parameters.add(new Object[] { count, contestCount+1, data.getQuestionID(),data.getMatchQuestionsID(),data.getAnswers(),data.getUseremail(),data.getContestId(),data.getMatchId(),data.getPaymentStatus(),new Date() });
				count++;
			}
			String sqlinsert="INSERT INTO  crick.TBL_ANSWERS_MST(ANSWERS_MST_ID, CONTEST_COUNT,Question_ID,MATCH_QUESTIONS_ID,USER_ANSWERS,USER_EMAIL,CONTEST_ID,matche_id,payment_Status "
							+ ",create_date) VALUES (?,?,?,?,?,?,?,?,?,?)";
			System.out.println("sqlinsert::::"+sqlinsert);
		jdbcTemplate.batchUpdate(sqlinsert,parameters);
		jdbcTemplate.update(
				"update tbl_contests_mst set ENTRYIES_LEFT=ENTRYIES_LEFT-1 where matche_id=? and CONTEST_ID=?",
				new Object[] {userData.get(0).getMatchId(),
						userData.get(0).getContestId()});
		
		
		System.out.println("Done::::");
		return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<ContestDTO> getCurrentContestes(String userName,int matchID) {
		/*String finalQry="select CONTEST_ID as contestId,CONTEST_NAME as contestName,CONTEST_FEE as contestFee,"
				+ "  CONTEST_WINER_COUNT as contestWinerCount "
				+ " from tbl_contests_mst where CONTEST_ID in( select distinct CONTEST_ID from tbl_answers_mst where USER_EMAIL=?)";*/
		 String finalQry="select distinct am.CONTEST_COUNT as contestCount, cm.CONTEST_ID as contestId,cm.CONTEST_NAME as contestName,cm.CONTEST_FEE as contestFee,"
		 		         + " cm.CONTEST_WINER_COUNT as contestWinerCount"
		 		         + " from tbl_contests_mst cm,tbl_answers_mst am where cm.CONTEST_ID=am.CONTEST_ID and  am.matche_id=?"
		 		         + " and am.USER_EMAIL=?   order by 2,1";
				List<ContestDTO> list = jdbcTemplate.query(finalQry,
				BeanPropertyRowMapper.newInstance(ContestDTO.class),matchID,userName);
		return list;
	}

	@Override
	public List<UserQuestionAnswerDTO> getCurrentQuestionsAnswers(String userName, int matcheid, int contestid, int contestcount) {
		 String finalQry="select qm.QUESTION_DESC,USER_ANSWERS,current_answers,answer_point "
		 		+ " from tbl_questions_mst qm,tbl_answers_mst am where qm.QUESTION_ID=am.QUESTION_ID"
		 		+ " and am.matche_id=? and am.CONTEST_ID=?  and am.CONTEST_COUNT=? and am.USER_EMAIL=?";
		List<UserQuestionAnswerDTO> list = jdbcTemplate.query(finalQry,
		BeanPropertyRowMapper.newInstance(UserQuestionAnswerDTO.class),matcheid,contestid,contestcount,userName);
return list;
	}

	@Override
	public List<MatchArgumentsDTO> getMatchArguments(int matcheid) {
		 String finalQry="select MATCH_argument_ID as matchArgumentId,Argument_DESC as argumentDesc, "
		 		+ "  Argument_value as argumentValue  from tbl_match_argument_mst where matche_id=? order by 1 desc ";
			List<MatchArgumentsDTO> list = jdbcTemplate.query(finalQry,
			BeanPropertyRowMapper.newInstance(MatchArgumentsDTO.class),matcheid);
			return list;
	}

	@Override
	public List<MatchWinDTO> getMatchWiners(int matcheid, int contestid) {
		String finalQuery="    select user_name as name,email_id as email, sum(answer_point) as ponits from tbl_answers_mst am,tbl_user_mst um"
				+ "         where am.USER_EMAIL=um.email_id and am.matche_id=? and am.CONTEST_ID=?  " ;
		List<MatchWinDTO> list = jdbcTemplate.query(finalQuery,
				BeanPropertyRowMapper.newInstance(MatchWinDTO.class),matcheid,contestid);
				return list;
	}

	
	


}
