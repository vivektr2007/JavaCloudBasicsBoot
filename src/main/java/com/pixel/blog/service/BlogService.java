package com.pixel.blog.service;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.pixel.model.AddFormDetailModel;
import com.pixel.model.AddFormModel;
import com.pixel.model.BlogCommentBean;
import com.pixel.model.CodeModel;
import com.pixel.model.ContentLVModel;
import com.pixel.model.InterviewQuestionsBean;
import com.pixel.model.QuestionOfTheDayBean;
import com.pixel.model.YouTubeModel;

public interface BlogService {

	public List<AddFormModel> getContentByMenuId(int menuId);

	public AddFormModel getTopicDetailByTopicId(int topicId);

	public int selectMaxContentId();

	public void insertAddForm(AddFormModel addFormModel);

	public void insertMenuForm(Map<String, String> parameters);

	public Map<String, String> getParentMenues();

	public Map<String, String> getSubMenues(String parent);

	public int rowCount(int menuId, String searchKeywork);

	public String checkLogin(Map<String, String> parameters);

	public List<AddFormModel> getLatestContent(int limit, int parentMenuId);

	public void deleteBlogContent(String[] deleteArr, String type);

	public boolean isExistsEmail(String emailId);

	public void subscribe(String emailId);

	public List<AddFormDetailModel> getTopicDetailForTopic(Integer topicId);

	public void updateContentDesc(AddFormModel addFormModel);

	public void updateContentLike(int contentId);

	public List<ContentLVModel> getContentLike(int contentId);

	public List<AddFormModel> getSearchContent(String searchKeyword);

	public List<CodeModel> getCodeList();

	public List<BlogCommentBean> getBlogCommentList(int topicId);

	public void submitComment(Map<String, Object> params);

	public void submitReply(Map<String, Object> params);

	public int submitLike(Map<String, Object> params);

	public boolean checkUserIdExists(String userId);

	public void registerUser(Map<String, String> params);

	public int getBlogCount();

	public List<YouTubeModel> getYouTubeList(int contentId);

	public void addYouTubeList(int contentId, List<String> youtubeList);

	public void insertIPdetails(String ipAddress);

	public int getVisitCount();

	public QuestionOfTheDayBean getQuestionOfTheDay();

	public Map<String, List<InterviewQuestionsBean>> getInterviewQuestions();

	public void insertInterviewQuestionDetails(InterviewQuestionsBean addFormModel);

	public InterviewQuestionsBean getInterViewQuestionDetailById(int topicId);
	
	public boolean userLogin(Map<String, String> params, HttpServletRequest request);
	
	public List<AddFormModel> getContentByMenuIdWithLimit(int menuId, int pageNo, String searchKeywork);
	
	public void getContentTitleIdMap(ServletContext context);
	
	public void getMenuTitleIdMap(ServletContext context);
}
