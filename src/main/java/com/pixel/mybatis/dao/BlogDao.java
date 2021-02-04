package com.pixel.mybatis.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.pixel.model.AddFormDetailModel;
import com.pixel.model.AddFormModel;
import com.pixel.model.BlogCommentBean;
import com.pixel.model.BlogCommentReplyBean;
import com.pixel.model.BlogDescripitionModel;
import com.pixel.model.CodeModel;
import com.pixel.model.ContentLVModel;
import com.pixel.model.InterviewQuestionsBean;
import com.pixel.model.LoginBean;
import com.pixel.model.MenuBean;
import com.pixel.model.QuestionOfTheDayBean;
import com.pixel.model.YouTubeModel;

@Mapper
public interface BlogDao {

	@Select("select * from blog_def")
	public List<BlogDescripitionModel> getBlogDescriptionList();

	@Select("select * from menu_detail where is_visible = 'Y'")
	public List<MenuBean> getMenuDetail();

	@Select("select * from content_desc")
	public List<Map<String, Object>> getContentTitleIdMap();

	@Select("select a.content_id, a.header as title_id,a.short_desc as short_desc_id,a.menu_id,a.creation_date,a.next,a.prev,a.tags, b.parent,"
			+ " b.menu_desc,ifnull(c.like_count,0) as like_count FROM content_desc"
			+ " a INNER JOIN  menu_detail b ON a.menu_id = b.menu_id and a.menu_id=#{menuId} LEFT OUTER JOIN content_lv c	ON a.content_id=c.content_id"
			+ "  order by a.creation_date desc ")
	public List<AddFormModel> getContentByMenuId(int menuId);

	@Select("select * from menu_detail")
	public List<Map<String, Object>> getMenuTitleIdMap();

	@Select("select a.content_id , a.header as title_id,  a.short_desc as short_desc_id,  a.menu_id ,a.creation_date,  a.next,  a.prev ,a.tags,"
			+ " b.parent, b.menu_desc from content_desc a , menu_detail b where a.menu_id=b.menu_id and content_id=#{topicId} ")
	public List<AddFormModel> getTopicDetailByTopicId(int topicId);

	@Select("select content_id , long_desc as long_desc_id,  content_type as add_more_type,  order_id ,content_unique_id from content_detail"
			+ " where content_id=#{topicId} order by order_id asc")
	public List<AddFormDetailModel> getTopicDetailForTopic(Integer topicId);

	@Insert("INSERT INTO content_detail(content_id,long_desc,content_type,order_id,creation_date) "
			+ "values(#{countent_id}, #{longDescId}, #{addMoreType},"
			+ "					#{orderId}, current_timestamp )")
	public void insertContentDetails(@Param("countent_id") int countent_id, @Param("longDescId") String longDescId,
			@Param("addMoreType") String addMoreType, @Param("orderId") String orderId);

	@Insert("INSERT INTO content_desc ( content_id, header,  short_desc as short_desc_id, menu_id ,  next,  prev, creation_date,tags)"
			+ " values( #{countent_id}, #{bean.titleId}, #{bean.shortDescId},"
			+ "#{bean.menuId}, #{bean.prevId}, #{bean.nextId}, current_timestamp," + "#{bean.tags} )")
	public void insertAddForm(@Param("bean") AddFormModel addFormModel, @Param("countent_id") int countent_id);

	@Insert("INSERT INTO interview_questions_ans_details(content_id,long_desc,content_type,order_id,creation_date)"
			+ " values(#{countent_id}, #{bean.longDescId}, #{bean.addMoreType},"
			+ "#{bean.orderId}, currrent_timestamp )")
	public void insertInterviewQuestionAnsDetails(@Param("countent_id") int countent_id,
			@Param("bean") AddFormDetailModel addFormModel);

	@Insert("INSERT INTO interview_questions ( pk, company_name,  interview_question, answer)"
			+ " values(#{countent_id}, #{bean.companyName}, #{bean.interviewQuestion}, #{bean.shortAnswer})")
	public void insertInterviewQuestionDetails(@Param("countent_id") int countent_id,
			@Param("bean") InterviewQuestionsBean addFormModel);

	@Select("SELECT MAX(content_id)+1 FROM content_desc")
	public int selectMaxContentId();

	@Select("SELECT MAX(pk)+1 FROM interview_questions")
	public int selectMaxInterviewContentId();

	@Insert("INSERT INTO menu_detail ( menu_desc, parent,  target, is_visible) "
			+ " values(#{MENU_DESC}, #{PARENT},#{TARGET}, #{IS_VISIBLE})")
	public void insertMenuForm(@Param("MENU_DESC") String menuDesc, @Param("PARENT") String parent,
			@Param("TARGET") String target, @Param("IS_VISIBLE") String isVisible);

	@Select("select distinct parent from menu_detail where is_visible = 'Y' order by parent_order_id,child_order_id")
	public List<Map<String, Object>> getParentMenues();

	@Select("select menu_id, menu_desc from menu_detail where parent = #{parent} order by parent_order_id,child_order_id")
	public List<Map<String, Object>> getSubMenues(String parent);

	@Select("select a.content_id, a.header as title_id,a.short_desc as short_desc_id,a.menu_id,a.creation_date,a.next,a.prev,a.tags,"
			+ " b.parent, b.menu_desc,ifnull(c.like_count,0) as like_count FROM content_desc a INNER JOIN  menu_detail b ON a.menu_id ="
			+ " b.menu_id ${menuIdQuery} LEFT OUTER JOIN content_lv c	ON a.content_id=c.content_id where 1=1 ${whereCondition} order by a.creation_date desc limit #{startIndex} , #{endIndex}")
	public List<AddFormModel> getContentByMenuIdWithLimit(@Param("startIndex") int startIndex,
			@Param("endIndex") int endIndex, @Param("whereCondition") String whereCondition,
			@Param("menuIdQuery") String menuIdQuery);

	@Select("select count(*) from content_desc  where 1=1   ${whereCondition} ")
	public int rowCount(@Param("whereCondition") String whereCondition);

	@Select("select concat(first_name, ' ' , last_name) as user_name from login_detail where email_id=#{emailId} and password = #{password} and"
			+ " status='A'")
	public String checkLogin(@Param("emailId") String emailId, @Param("password") String password);

	@Select("select a.content_id,(select count(*) from blog_like_details bld"
			+ " where bld.topic_id = a.content_id) as like_count, a.header as title_id,a.short_desc as short_desc_id,a.menu_id,"
			+ "a.creation_date,a.next,a.prev,a.tags, b.parent, "
			+ "b.menu_desc FROM content_desc a INNER JOIN  menu_detail b ON a.menu_id = b.menu_id"
			+ " order by a.creation_date desc limit #{limit}")
	public List<AddFormModel> getLatestContent(@Param("limit") int limit);

	@Select("select a.content_id,(select count(*) from blog_like_details bld"
			+ " where bld.topic_id = a.content_id) as like_count, a.header as title_id,a.short_desc as short_desc_id,a.menu_id,"
			+ "a.creation_date,a.next,a.prev,a.tags, b.parent, "
			+ "b.menu_desc FROM content_desc a INNER JOIN  menu_detail b ON a.menu_id = b.menu_id and b.parent_order_id=#{parentMenuId}"
			+ " order by a.creation_date desc limit #{limit}")
	public List<AddFormModel> getLatestContentByParentId(@Param("limit") int limit,
			@Param("parentMenuId") int parentMenuId);

	@Delete("DELETE FROM content_desc ${whereCondition}")
	public void deleteContentQuery(@Param("whereCondition") String whereCondition);

	@Delete("DELETE FROM content_detail")
	public void deleteContentDetailsQuery(@Param("whereCondition") String whereCondition);

	@Select("select count(1) from subscriber_list where email_id = #{emailId}")
	public int isExistsEmail(@Param("emailId") String emailId);

	@Insert("insert into subscriber_list(email_id) values(#{emailId})")
	public void subscribe(@Param("emailId") String emailId);

	@Update("update content_desc a set a.header =#{bean.titleId},  a.short_desc=#{bean.shortDescId},  "
			+ " a.menu_id =#{bean.menuId}, a.tags =#{bean.tags} where content_id= #{bean.contentId}")
	public void updateContentDesc(@Param("bean") AddFormModel addFormModel);

	@Update("update content_lv a set a.like_count =a.like_count+1 where content_id= #{contentId}")
	public void updateContentLike(@Param("contentId") int contentId);

	@Insert("INSERT INTO content_lv(content_id,like_count,view_count) values(#{content_id},#{like_count},#{view_count})")
	public void insertContentLike(@Param("content_id") int content_id, @Param("like_count") int like_count,
			@Param("view_count") int view_count);

	@Select("SELECT content_id, like_count,view_count from content_lv where content_id= #{contentId}")
	public List<ContentLVModel> getContentLike(@Param("contentId") int contentId);

	@Select("select a.content_id, a.header as title_id,a.short_desc as short_desc_id,a.menu_id,a.creation_date,a.next,a.prev,a.tags, b.parent,"
			+ " b.menu_desc,ifnull(c.like_count,0) as like_count FROM content_desc a INNER JOIN  menu_detail b ON a.menu_id = b.menu_id"
			+ " LEFT OUTER JOIN content_lv c ON a.content_id=c.content_id where a.header like #{searchKeyword} order by a.creation_date desc")
	public List<AddFormModel> getSearchContent(@Param("searchKeyword") String searchKeyword);

	@Select("select distinct tags from content_desc")
	public List<String> getTagList();

	@Select("select code_id,code_info,code_value,code_desc,del_yn from code_info")
	public List<CodeModel> getCodeList();

	@Select("select * from blog_comment_detail where topic_id=#{topicId}")
	public List<BlogCommentBean> getBlogCommentList(@Param("topicId") int topicId);

	@Select("select * from blog_comment_reply_detail where comment_id = #{commentId} order by reply_date desc")
	public List<BlogCommentReplyBean> getBlogReplyList(@Param("commentId") int commentId);

	@Insert("insert into blog_comment_detail(topic_id, comment_desc, comment_by) values(#{topicId},#{comment},#{loggerInUserId})")
	public void submitComment(@Param("topicId") int topicId, @Param("comment") String comment,
			@Param("loggerInUserId") String loggerInUserId);

	@Insert("insert into blog_comment_reply_detail(comment_id, reply_desc, reply_by) values(#{commentId},#{reply},#{loggerInUserId})")
	public void submitReply(@Param("commentId") int commentId, @Param("reply") String reply,
			@Param("loggerInUserId") String loggerInUserId);

	@Select("select email_id as user_id, password, is_admin,concat(first_name,' ',last_name) as user_name  from login_detail where email_id=#{userId} and password = #{password} and is_admin=#{admin}")
	public List<LoginBean> checkUserLogin(@Param("userId") String userId, @Param("password") String password,
			@Param("admin") String admin);

	@Select("select count(*) from blog_like_details where user_id = #{userId} and topic_id=#{topic_id}")
	public int countLikeQuery(@Param("userId") String userId, @Param("topic_id") int topic_id);

	@Insert("insert into blog_like_details(user_id, topic_id) values(#{userId},#{topic_id})")
	public void submitLike(@Param("userId") String userId, @Param("topic_id") int topic_id);

	@Select("select email_id as user_id, password, is_admin,concat(first_name,' ',last_name) as user_name   from login_detail where email_id=#{userId}")
	public List<Map<String, Object>> checkUserIdExists(@Param("userId") String userId);

	@Select("select count(*) from content_desc")
	public int getBlogCount();

	@Insert("insert into youtube_content_info(content_id, youtubeurl,youtubecode) values(#{contentId},#{youtubeurl},#{youtubecode})")
	public void addYoutubeList(@Param("contentId") int contentId, @Param("youtubeurl") String youtubeurl,
			@Param("youtubecode") String youtubecode);

	@Delete("delete from youtube_content_info where content_id=#{contentId}")
	public void deleteYoutubeList(@Param("contentId") int contentId);

	@Select("select content_id, youtubeurl, youtubecode from youtube_content_info where content_id=#{contentId}")
	public List<YouTubeModel> getYouTubeList(@Param("contentId") int contentId);

	@Select("select * from ip_tracking where ip_address=#{ip_address}")
	public List<Map<String, Object>> checkIpExistsQuery(@Param("ip_address") String ip_address);

	@Insert("insert into ip_tracking(ip_address, access_date) values(#{ipAddress},now())")
	public void insertIPdetails(@Param("ipAddress") String ipAddress);

	@Select("select count(*) from ip_tracking")
	public int getVisitCount();

	@Select("select seq , heading , question_details , "
			+ "date_format(creation_date,'%d-%m-%Y') creation_date from question_of_the_day order by creation_date desc limit 1")
	public List<QuestionOfTheDayBean> getQuestionOfTheDay();

	@Select("select * from interview_questions where del_yn = 'n' order by company_name, created_date")
	public List<Map<String, Object>> getInterviewQuestions();

	@Select("select * from interview_questions where pk=#{topicId}")
	public List<InterviewQuestionsBean> getInterViewQuestionsById(@Param("topicId") int topicId);

	@Select("select * from interview_questions_ans_details where content_id=#{topicId} order by order_id")
	public List<AddFormDetailModel> getInterViewQuestionDetailById(@Param("topicId") int topicId);

}
