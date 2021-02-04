package com.pixel.blog.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.pixel.blog.service.BlogService;
import com.pixel.model.AddFormDetailModel;
import com.pixel.model.AddFormModel;
import com.pixel.model.BlogCommentBean;
import com.pixel.model.BlogDescripitionModel;
import com.pixel.model.CodeModel;
import com.pixel.model.ContentLVModel;
import com.pixel.model.InterviewQuestionsBean;
import com.pixel.model.LoginBean;
import com.pixel.model.MenuBean;
import com.pixel.model.QuestionOfTheDayBean;
import com.pixel.model.YouTubeModel;
import com.pixel.mybatis.dao.BlogDao;
import com.pixel.utils.CommonIFace;
import com.pixel.utils.CommonUtils;

@Service
public class BlogServiceImpl implements BlogService {

	@Autowired
	private BlogDao blogDao;

	@Autowired
	JdbcTemplate jdbcTemplate;

	public List<BlogDescripitionModel> getBlogDescriptionList() {

		return blogDao.getBlogDescriptionList();
	}

	public List<MenuBean> getMenuDetail() {

		return blogDao.getMenuDetail();
	}

	public Map<String, Integer> getContentTitleIdMap() {
		List<Map<String, Object>> list = blogDao.getContentTitleIdMap();

		Map<String, Integer> returnMap = new HashMap<>();
		for (Map<String, Object> tmpMap : list) {
			returnMap.put(
					String.valueOf(tmpMap.get("header")).replaceAll(" ", "-").replaceAll("/", "-")
							.replaceAll("\\?", "-").replaceAll("\\.", "-").replaceAll("\\,", "-"),
					Integer.parseInt(String.valueOf(tmpMap.get("content_id"))));
		}

		return returnMap;
	}

	public Map<String, Integer> getMenuTitleIdMap() {
		List<Map<String, Object>> list = blogDao.getMenuTitleIdMap();

		Map<String, Integer> returnMap = new HashMap<>();
		for (Map<String, Object> tmpMap : list) {
			returnMap.put(
					String.valueOf(tmpMap.get("menu_desc")).replaceAll(" ", "-").replaceAll("/", "-")
							.replaceAll("\\?", "-").replaceAll("\\.", "-").replaceAll("\\,", "-"),
					Integer.parseInt(String.valueOf(tmpMap.get("menu_id"))));
		}

		return returnMap;
	}

	public List<AddFormModel> getContentByMenuId(int menuId) {
		List<AddFormModel> list = null;
		try {
			list = blogDao.getContentByMenuId(menuId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public AddFormModel getTopicDetailByTopicId(int topicId) {

		List<AddFormModel> list = null;
		try {
			list = blogDao.getTopicDetailByTopicId(topicId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		AddFormModel model = list.get(0);
		model.setAddFormDetailModel(getTopicDetailForTopic(model.getContentId()));

		return model;
	}

	public List<AddFormDetailModel> getTopicDetailForTopic(Integer topicId) {

		List<AddFormDetailModel> list = null;

		try {
			list = blogDao.getTopicDetailForTopic(topicId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<AddFormDetailModel> getIngterviewQuestionAnsDetailsById(Integer topicId) {

		List<AddFormDetailModel> list = null;
		try {
			list = blogDao.getInterViewQuestionDetailById(topicId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public void insertAddForm(AddFormModel addFormModel) {

		Integer countent_id = selectMaxContentId();

		blogDao.insertAddForm(addFormModel, countent_id);

		List<AddFormDetailModel> addFormDetailModelList = addFormModel.getAddFormDetailModel();

		insertAddDetail(addFormDetailModelList, countent_id);

	}

	public void insertInterviewQuestionDetails(InterviewQuestionsBean addFormModel) {

		Integer countent_id = blogDao.selectMaxInterviewContentId();
		blogDao.insertInterviewQuestionDetails(countent_id, addFormModel);

		List<AddFormDetailModel> addFormDetailModelList = addFormModel.getAnswerDetails();

		insertInterviewQuesAnsDetail(addFormDetailModelList, countent_id);

	}

	private void insertAddDetail(List<AddFormDetailModel> addFormDetailModelList, Integer countent_id) {

		Iterator<AddFormDetailModel> itr = addFormDetailModelList.iterator();

		while (itr.hasNext()) {

			AddFormDetailModel model = itr.next();
			blogDao.insertContentDetails(countent_id, model.getLongDescId(), model.getAddMoreType(),
					model.getOrderId());

		}
	}

	private void insertInterviewQuesAnsDetail(List<AddFormDetailModel> addFormDetailModelList, Integer countent_id) {

		Iterator<AddFormDetailModel> itr = addFormDetailModelList.iterator();

		while (itr.hasNext()) {
			AddFormDetailModel model = itr.next();
			blogDao.insertInterviewQuestionAnsDetails(countent_id, model);

		}
	}

	public int selectMaxContentId() {

		return blogDao.selectMaxContentId();

	}

	public void insertMenuForm(Map<String, String> parameters) {
		blogDao.insertMenuForm(parameters.get("MENU_DESC"), parameters.get("PARENT"), parameters.get("TARGET"),
				parameters.get("IS_VISIBLE"));
	}

	public Map<String, String> getParentMenues() {

		Map<String, String> result = new HashMap<String, String>();
		List<Map<String, Object>> data = blogDao.getParentMenues();
		Iterator<Map<String, Object>> itr = data.iterator();
		while (itr.hasNext()) {
			Map<String, Object> map = itr.next();
			result.put(String.valueOf(map.get("parent")), String.valueOf(map.get("parent")));

		}

		return result;
	}

	public Map<String, String> getSubMenues(String parent) {
		Map<String, String> result = new HashMap<String, String>();
		List<Map<String, Object>> data = blogDao.getSubMenues(parent);
		Iterator<Map<String, Object>> itr = data.iterator();
		while (itr.hasNext()) {
			Map<String, Object> map = itr.next();
			result.put(String.valueOf(map.get("menu_id")), String.valueOf(map.get("menu_desc")));

		}

		return result;
	}

	public List<AddFormModel> getContentByMenuIdWithLimit(int menuId, int pageNo, String searchKeyword) {
		
		int length = 15;
		int startIndex = (pageNo - 1) * length;
		// int endIndex = startIndex + (length);
		int endIndex = length;
		
		List<AddFormModel> list = null;
		try {
			String menuIdQuery = "";
			String whereCondition = "";
			if (menuId != 0) {
				menuIdQuery = " and a.menu_id=" + menuId;
			}
			if (searchKeyword != null && !"".equals(searchKeyword)) {
				whereCondition = " and (a.header like '%" + searchKeyword + "%' or a.tags like '%" + searchKeyword
						+ "%')";
			}
			list = blogDao.getContentByMenuIdWithLimit(startIndex, endIndex, whereCondition, menuIdQuery);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public int rowCount(int menuId, String searchKeyword) {
		int rowCount = 0;
		try {

			String whereCondition = "";
			if (menuId != 0) {
				whereCondition = whereCondition + " and menu_id=" + menuId;
			}
			if (searchKeyword != null && !"".equals(searchKeyword)) {
				whereCondition = whereCondition + " and (header like '%" + searchKeyword + "%' or tags like '%"
						+ searchKeyword + "%')";
			}
			rowCount = blogDao.rowCount(whereCondition);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rowCount;
	}

	public String checkLogin(Map<String, String> parameters) {
		String name = "";
		try {
			name = blogDao.checkLogin(parameters.get("EMAIL_ID"), parameters.get("PASSWORD"));
		} catch (Exception e) {

		}
		return name;
	}

	public List<AddFormModel> getLatestContent(int limit, int parentMenuId) {
		List<AddFormModel> list = null;
		try {
			if (parentMenuId == -1) {
				list = blogDao.getLatestContent(limit);
			} else {
				list = blogDao.getLatestContentByParentId(limit, parentMenuId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public void deleteBlogContent(String[] deleteArr, String type) {
		String in = "";
		String whereCondition = "";
		for (int i = 0; i < deleteArr.length; i++) {
			in = in + "," + deleteArr[i];
		}
		if (in.length() > 0) {
			in = in.substring(1);
			whereCondition = " where content_id in (" + in + ")";
		}

		try {

			if (type.equalsIgnoreCase("CONTENT_DESC")) {
				blogDao.deleteContentQuery(whereCondition);
			} else if (type.equalsIgnoreCase("CONTENT_DETAIL")) {
				blogDao.deleteContentDetailsQuery(whereCondition);
			} else if (type.equalsIgnoreCase("ALL")) {
				blogDao.deleteContentQuery(whereCondition);
				blogDao.deleteContentDetailsQuery(whereCondition);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isExistsEmail(String emailId) {
		boolean flag = false;
		int name = 0;
		try {
			name = blogDao.isExistsEmail(emailId);
		} catch (Exception e) {

		}
		if (name > 0) {
			flag = true;
		}

		return flag;
	}

	public void subscribe(String emailId) {
		blogDao.subscribe(emailId);
	}

	public void updateContentDesc(AddFormModel addFormModel) {

		blogDao.updateContentDesc(addFormModel);

		String deleteArr[] = { "" + addFormModel.getContentId() };
		deleteBlogContent(deleteArr, "CONTENT_DETAIL");

		List<AddFormDetailModel> addFormDetailModelList = addFormModel.getAddFormDetailModel();

		insertAddDetail(addFormDetailModelList, addFormModel.getContentId());
	}

	public synchronized void updateContentLike(int contentId) {

		List<ContentLVModel> list = getContentLike(contentId);

		if (list != null && list.size() > 0) {
			blogDao.updateContentLike(contentId);
		} else {
			blogDao.insertContentLike(contentId, 1, 1);
		}
	}

	public List<ContentLVModel> getContentLike(int contentId) {
		List<ContentLVModel> list = blogDao.getContentLike(contentId);
		return list;
	}

	public List<AddFormModel> getSearchContent(String searchKeyword) {
		List<AddFormModel> list = null;

		try {
			list = blogDao.getSearchContent(searchKeyword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<String> getTagList() {
		return blogDao.getTagList();
	}

	public List<CodeModel> getCodeList() {
		List<CodeModel> list = blogDao.getCodeList();
		return list;
	}

	public List<BlogCommentBean> getBlogCommentList(int topicId) {

		List<BlogCommentBean> commentList = blogDao.getBlogCommentList(topicId);
		for(BlogCommentBean bean : commentList) {
			bean.setReplyBeanList(blogDao.getBlogReplyList(bean.getSeq()));
		}
		
		return commentList;
	}

	public void submitComment(Map<String, Object> params) {

		blogDao.submitComment((Integer) params.get("topicId"), (String) params.get("comment"),
				(String) params.get("loggerInUserId"));
	}

	public void submitReply(Map<String, Object> params) {

		blogDao.submitReply((Integer) params.get("commentId"), (String) params.get("reply"),
				(String) params.get("loggerInUserId"));

	}

	public List<LoginBean> userLogin(Map<String, String> params) {
		List<LoginBean> list = blogDao.checkUserLogin(params.get("userId"), params.get("password"),
				params.get("admin"));

		return list;
	}

	public int submitLike(Map<String, Object> params) {

		int userLikeCount = blogDao.countLikeQuery(String.valueOf(params.get("loggerInUserId")),
				(Integer) params.get("contentId"));
		if (userLikeCount == 0) {
			blogDao.submitLike(String.valueOf(params.get("loggerInUserId")), (Integer) params.get("contentId"));
		}

		updateContentLike((Integer) params.get("contentId"));

		return 0;
	}

	public boolean checkUserIdExists(String userId) {

		List<Map<String, Object>> value = blogDao.checkUserIdExists(userId);
		if (value != null && value.size() > 0) {
			return true;
		}

		return false;
	}

	public void registerUser(Map<String, String> params) {
		jdbcTemplate.execute("insert into login_detail(first_name, last_name, email_id, password, is_admin) values('"
				+ params.get("firstName") + "'" + ",'" + params.get("lastName") + "','" + params.get("userName") + "','"
				+ params.get("password") + "','N')");
	}

	public int getBlogCount() {

		return blogDao.getBlogCount();
	}

	public List<YouTubeModel> getYouTubeList(int contentId) {
		List<YouTubeModel> list = null;
		try {
			list = blogDao.getYouTubeList(contentId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public void addYouTubeList(int contentId, List<String> youtubeCodeList) {

		try {
			blogDao.deleteYoutubeList(contentId);

			// Insert
			int size = youtubeCodeList.size();
			for (int i = 0; i < size; i++) {

				String youtubeCode = youtubeCodeList.get(i);
				blogDao.addYoutubeList(contentId, CommonIFace.YOUTUBE_URL_EMBED + youtubeCode, youtubeCode);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insertIPdetails(String ipAddress) {
		List<Map<String, Object>> list = null;
		try {
			list = blogDao.checkIpExistsQuery(ipAddress);
			if (list == null || list.size() == 0) {

				blogDao.insertIPdetails(ipAddress);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getVisitCount() {

		return blogDao.getVisitCount();
	}

	public QuestionOfTheDayBean getQuestionOfTheDay() {

		List<QuestionOfTheDayBean> list = blogDao.getQuestionOfTheDay();
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public Map<String, List<InterviewQuestionsBean>> getInterviewQuestions() {

		Map<String, List<InterviewQuestionsBean>> map = new LinkedHashMap<String, List<InterviewQuestionsBean>>();

		List<Map<String, Object>> data = blogDao.getInterviewQuestions();
		for (Map<String, Object> tmpMap : data) {

			String companyName = String.valueOf(tmpMap.get("company_name"));
			String interviewQuestion = String.valueOf(tmpMap.get("interview_question"));
			String answer = String.valueOf(tmpMap.get("answer"));

			int pk = Integer.parseInt(String.valueOf(tmpMap.get("pk")));

			InterviewQuestionsBean bean = new InterviewQuestionsBean();
			bean.setShortAnswer(answer);
			bean.setCompanyName(companyName);
			bean.setInterviewQuestion(interviewQuestion);
			bean.setPk(pk);
			bean.setCreatedDate(CommonUtils.getDisplayDate((Date) tmpMap.get("created_date")));

			if (map.containsKey(companyName)) {
				map.get(companyName).add(bean);
			} else {
				ArrayList<InterviewQuestionsBean> tmpList = new ArrayList<InterviewQuestionsBean>();
				tmpList.add(bean);

				map.put(companyName, tmpList);
			}
		}

		return map;
	}

	@Override
	public InterviewQuestionsBean getInterViewQuestionDetailById(int topicId) {

		List<InterviewQuestionsBean> list = null;
		try {
			list = blogDao.getInterViewQuestionsById(topicId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		InterviewQuestionsBean model = list.get(0);
		model.setAnswerDetails(getIngterviewQuestionAnsDetailsById(topicId));

		return model;
	}

	@Override
	public boolean userLogin(Map<String, String> params, HttpServletRequest request) {
		List<LoginBean> list = blogDao.checkUserLogin(params.get("userId"), params.get("password"),
				params.get("admin"));
		if (list != null && list.size() > 0) {
			request.getSession().setAttribute("loggerInUserName", list.get(0).getUserName());
			request.getSession().setAttribute("loggerInUserId", list.get(0).getUserId());

			return true;
		}
		return false;
	}

	@Override
	public void getContentTitleIdMap(ServletContext context) {
		context.setAttribute("contentTitleMap", getContentTitleIdMap());
	}

	@Override
	public void getMenuTitleIdMap(ServletContext context) {
		context.setAttribute("menuTitleMap", getMenuTitleIdMap());
	}
}
