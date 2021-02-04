package com.pixel.controller;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.pixel.aws.service.S3Service;
import com.pixel.blog.service.BlogService;
import com.pixel.blog.serviceImpl.MenuLoader;
import com.pixel.model.AddFormDetailModel;
import com.pixel.model.AddFormModel;
import com.pixel.model.BlogCommentBean;
import com.pixel.model.InterviewQuestionsBean;
import com.pixel.model.QuestionOfTheDayBean;
import com.pixel.model.YouTubeModel;
import com.pixel.utils.CommonUtils;

@Controller
@PropertySource("classpath:/log4j.properties")
public class BlogController {

	private static final Logger LOGGER = LoggerFactory.getLogger(BlogController.class);

	@Autowired
	BlogService blogService;

	@Autowired
	S3Service s3Service;

	@Autowired
	MenuLoader menuLoader;

	@Autowired
	Environment env;
	
	@Autowired
	ServletContext context;

	@PostConstruct
	public void init() {
		blogService.getContentTitleIdMap(context);
		blogService.getMenuTitleIdMap(context);
	}
	
	@RequestMapping(value = { "/", "homePage", "index", "m", "mobile" }, method = { RequestMethod.GET })
	public String homePage(Model model, HttpServletRequest request) {

		blogService.insertIPdetails(CommonUtils.getClientIp(request));

		List<AddFormModel> parentMenu1ContentList = blogService.getLatestContent(15, 1);// Limit, ParentMenuId
		model.addAttribute("parentMenu1ContentList", parentMenu1ContentList);
		model.addAttribute("parentMenu1ContentListCount", parentMenu1ContentList.size());

		List<AddFormModel> parentMenu2ContentList = blogService.getLatestContent(15, 2);// Limit, ParentMenuId
		model.addAttribute("parentMenu2ContentList", parentMenu2ContentList);
		model.addAttribute("parentMenu2ContentListCount", parentMenu2ContentList.size());

		List<AddFormModel> parentMenu3ContentList = blogService.getLatestContent(15, 3);// Limit, ParentMenuId
		model.addAttribute("parentMenu3ContentList", parentMenu3ContentList);
		model.addAttribute("parentMenu3ContentListCount", parentMenu3ContentList.size());

		List<AddFormModel> parentMenu4ContentList = blogService.getLatestContent(15, 4);// Limit, ParentMenuId
		model.addAttribute("parentMenu4ContentList", parentMenu4ContentList);
		model.addAttribute("parentMenu4ContentListCount", parentMenu4ContentList.size());

		// Variable is used to Identify for Home Page with Limited Content
		model.addAttribute("selectorId", "RequestFromHomePage");

		String searchKeyword = request.getParameter("searchKeyword");
		int id = Integer.parseInt(request.getParameter("id") != null ? request.getParameter("id") : "0");
		int rowCount = blogService.rowCount(id, searchKeyword);
		model.addAttribute("rowCount", rowCount);

		model.addAttribute("newTopicContentList", MenuLoader.newTopicContentList);
		model.addAttribute("tagsSet", MenuLoader.tagsSet);
		model.addAttribute("codeList", MenuLoader.codeList);
		request.setAttribute("hotTopic", "Y");

		return "topics";
	}

	@RequestMapping(value = "interviewQuestion", method = { RequestMethod.GET, RequestMethod.POST })
	public String interviewQuestion(Model model) {

		model.addAttribute("selectorId", "RequestNotFromHomePage");

		model.addAttribute("newTopicContentList", MenuLoader.newTopicContentList);
		model.addAttribute("tagsSet", MenuLoader.tagsSet);
		model.addAttribute("codeList", MenuLoader.codeList);

		model.addAttribute("contentList", blogService.getInterviewQuestions());

		return "interviewQuestions";
	}

	private Set<AddFormModel> getRelatedContentIds(String tag, int contentId) {

		Set<AddFormModel> relatedTagContentIds = new HashSet<AddFormModel>();

		Set<Map.Entry<String, List<AddFormModel>>> hm = MenuLoader.hashRelatedTopics.entrySet();

		String splitArr[] = tag.split(";");
		int length = splitArr.length;
		for (int j = 0; j < length; j++) {

			Iterator<Map.Entry<String, List<AddFormModel>>> it1 = hm.iterator();

			while (it1.hasNext()) {
				Map.Entry<String, List<AddFormModel>> s = it1.next();
				if (s.getKey().equalsIgnoreCase(splitArr[j])) {
					int size = s.getValue().size();
					for (int i = 0; i < size; i++) {
						if (s.getValue().get(i).getContentId() != contentId) {
							relatedTagContentIds.add(s.getValue().get(i));
						}
					}
				}
			}
		}

		return relatedTagContentIds;

	}

	/*@RequestMapping(value = "viewTopics", method = { RequestMethod.GET, RequestMethod.POST })
	public String viewTopics(Model model, HttpServletRequest request) {

		String searchKeyword = request.getParameter("searchKeyword");
		int menuId = Integer.parseInt(request.getParameter("menuId") != null ? request.getParameter("menuId") : "0");
		String menuName = request.getParameter("menuName");
		String awsEndPoint = env.getProperty("awsEndPoint");
		List<AddFormModel> contentList = blogService.getContentByMenuIdWithLimit(menuId, 1, searchKeyword);
		model.addAttribute("contentList", contentList);
		model.addAttribute("awsEndPoint", awsEndPoint);
		model.addAttribute("menuId", menuId);
		model.addAttribute("menuName", menuName);
		model.addAttribute("searchKeyword", searchKeyword);

		model.addAttribute("selectorId", "RequestNotFromHomePage");

		int rowCount = blogService.rowCount(menuId, searchKeyword);

		model.addAttribute("rowCount", rowCount);
		model.addAttribute("newTopicContentList", MenuLoader.newTopicContentList);
		model.addAttribute("tagsSet", MenuLoader.tagsSet);
		model.addAttribute("codeList", MenuLoader.codeList);

		return "topics";
	}
*/
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "viewTopics/{parent}/{menuName}", method = { RequestMethod.GET, RequestMethod.POST })
	public String viewTopics1(@PathVariable("menuName") String menuName,@PathVariable("parent") String parent, Model model, HttpServletRequest request) {

		String searchKeyword = request.getParameter("searchKeyword");
		
		Map<String, Integer> menuTitleMap = (Map<String, Integer>)context.getAttribute("menuTitleMap");
		
		Integer menuId = menuTitleMap.get(menuName);
		
		if(menuId == null) {
			return "redirect:/homePage.html";
		}
		
		String awsEndPoint = env.getProperty("awsEndPoint");
		List<AddFormModel> contentList = blogService.getContentByMenuIdWithLimit(menuId, 1, searchKeyword);
		model.addAttribute("contentList", contentList);
		model.addAttribute("awsEndPoint", awsEndPoint);
		model.addAttribute("menuId", menuId);
		model.addAttribute("menuName", menuName);
		model.addAttribute("searchKeyword", searchKeyword);

		model.addAttribute("selectorId", "RequestNotFromHomePage");

		int rowCount = blogService.rowCount(menuId, searchKeyword);

		model.addAttribute("rowCount", rowCount);
		model.addAttribute("newTopicContentList", MenuLoader.newTopicContentList);
		model.addAttribute("tagsSet", MenuLoader.tagsSet);
		model.addAttribute("codeList", MenuLoader.codeList);

		return "topics";
	}
	
	@RequestMapping(value = "viewTopicsByPageNo", method = { RequestMethod.GET, RequestMethod.POST })
	public String viewTopicsByPageNo(@RequestParam int pageNo, Model model, HttpServletRequest request) {

		int menuId = Integer
				.parseInt(request.getParameter("menuId") != null && !"".equals(request.getParameter("menuId"))
						? request.getParameter("menuId")
						: "0");

		String menuName = request.getParameter("menuName");
		model.addAttribute("menuName", menuName);

		String awsEndPoint = env.getProperty("awsEndPoint");
		String searchKeywork = request.getParameter("searchKeyword");

		List<AddFormModel> contentList = blogService.getContentByMenuIdWithLimit(menuId, pageNo, searchKeywork);
		model.addAttribute("contentList", contentList);
		model.addAttribute("awsEndPoint", awsEndPoint);

		int rowCount = blogService.rowCount(menuId, searchKeywork);
		model.addAttribute("rowCount", rowCount);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("menuId", menuId);
		model.addAttribute("newTopicContentList", MenuLoader.newTopicContentList);
		model.addAttribute("tagsSet", MenuLoader.tagsSet);
		model.addAttribute("codeList", MenuLoader.codeList);

		model.addAttribute("searchKeyword", searchKeywork);

		return "topics";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "contentDetails/{contentTitle}", method = { RequestMethod.GET, RequestMethod.POST })
	public String readTopic(@PathVariable("contentTitle") String contentTitle, Model model, HttpServletRequest request) {
		String awsEndPoint = "https://" + env.getProperty("awsEndPoint");

		Map<String, Integer> contentTitleIdMap = (Map<String, Integer>)context.getAttribute("contentTitleMap");
		
		Integer id = contentTitleIdMap.get(contentTitle);
		
		if(id == null) {
			return "redirect:/homePage.html";
		}
		
		AddFormModel cBean = blogService.getTopicDetailByTopicId(id);

		model.addAttribute("content", cBean);
		model.addAttribute("awsEndPoint", awsEndPoint);
		model.addAttribute("newTopicContentList", MenuLoader.newTopicContentList);
		model.addAttribute("tagsSet", MenuLoader.tagsSet);
		model.addAttribute("codeList", MenuLoader.codeList);

		Set<AddFormModel> relatedTagContentIds = getRelatedContentIds(cBean.getTags(), id);
		model.addAttribute("relatedTagContentIds", relatedTagContentIds);

		// Get Updated Result
		List<YouTubeModel> youtubeList = blogService.getYouTubeList(id);
		model.addAttribute("youtubeList", youtubeList);

		return "topicDetail";
	}

	@RequestMapping(value = "addInterviewQuestion", method = { RequestMethod.GET, RequestMethod.POST })
	public String addInterviewQuestion(Model model, HttpServletRequest request) {
		if (request.getSession().getAttribute("loggerInUserId") == null) {
			request.setAttribute("error", "Please login to perform this action.");
			return "login";
		}

		// model.addAttribute("parent", blogService.getParentMenues());

		return "addInterviewQuestion";
	}

	@RequestMapping(value = "addInterviewQuestionSubmit", method = { RequestMethod.GET, RequestMethod.POST })
	public String addInterviewQuestionSubmit(HttpServletRequest request, @RequestParam("file") List<MultipartFile> fileList, Model model) {
		if (request.getSession().getAttribute("loggerInUserId") == null) {
			request.setAttribute("error", "Please login to perform this action.");
			return "login";
		}

		InterviewQuestionsBean addFormModel = null;
		List<AddFormDetailModel> addFormDetailModelList = null;
		try {

			addFormModel = new InterviewQuestionsBean();
			addFormDetailModelList = new ArrayList<AddFormDetailModel>();

			addFormModel.setCompanyName(request.getParameter("company_name"));
			addFormModel.setInterviewQuestion(request.getParameter("interview_question"));
			addFormModel.setShortAnswer(request.getParameter("shortDescId"));
			
			String[] arrLongDescId = request.getParameterValues("longDescId");
			String[] arrMoreTypeId = request.getParameterValues("moreTypeId");
			String[] arrOrderId = request.getParameterValues("orderId");

			int count = 0;
			int length = arrMoreTypeId.length;
			for (int i = 0; i < length; i++) {
				AddFormDetailModel arr = new AddFormDetailModel();

				if (!("Text".equalsIgnoreCase(arrMoreTypeId[i])) && !("Code".equalsIgnoreCase(arrMoreTypeId[i]))) {

					MultipartFile file = fileList.get(count);
					String filePath = s3Service.uploadS3File(file, arrMoreTypeId[i]);
					count++;
					arr.setLongDescId(filePath);
				} else {
					arr.setLongDescId(arrLongDescId[i]);
				}
				arr.setAddMoreType(arrMoreTypeId[i]);
				arr.setOrderId(arrOrderId[i]);
				addFormDetailModelList.add(arr);
			}
			addFormModel.setAnswerDetails(addFormDetailModelList);

			blogService.insertInterviewQuestionDetails(addFormModel);
		} catch (Exception e) {
			LOGGER.error("addBlog Function:", e);
		}
		model.addAttribute("parent", blogService.getParentMenues());
		return "addBlogForm";

	}

	@RequestMapping(value = "addBlogForm", method = { RequestMethod.GET, RequestMethod.POST })
	public String addBlogForm(Model model, HttpServletRequest request) {
		if (request.getSession().getAttribute("loggerInUserId") == null) {
			request.setAttribute("error", "Please login to perform this action.");
			return "login";
		}

		model.addAttribute("parent", blogService.getParentMenues());

		return "addBlogForm";
	}

	@RequestMapping(value = "deleteBlogForm", method = { RequestMethod.GET, RequestMethod.POST })
	public String deleteBlogForm(Model model, HttpServletRequest request) {
		if (request.getSession().getAttribute("loggerInUserId") == null) {
			request.setAttribute("error", "Please login to perform this action.");
			return "login";
		}
		List<AddFormModel> contentList = blogService.getLatestContent(30000, -1);
		model.addAttribute("contentList", contentList);
		model.addAttribute("parent", blogService.getParentMenues());

		return "deleteBlogForm";
	}

	@RequestMapping(value = "deleteBlog", method = { RequestMethod.GET, RequestMethod.POST })
	public String deleteBlog(Model model, HttpServletRequest request) {
		if (request.getSession().getAttribute("loggerInUserId") == null) {
			request.setAttribute("error", "Please login to perform this action.");
			return "login";
		}

		String[] delArr = request.getParameterValues("delCheck");
		blogService.deleteBlogContent(delArr, "ALL");

		return "redirect:deleteBlogForm.html";
	}

	@RequestMapping(value = "addMenuForm", method = { RequestMethod.GET, RequestMethod.POST })
	public String addMenuForm(HttpServletRequest request) {
		if (request.getSession().getAttribute("loggerInUserId") == null) {
			request.setAttribute("error", "Please login to perform this action.");
			return "login";
		}
		return "addMenuForm";
	}

	@RequestMapping(value = "addBlog", method = { RequestMethod.GET, RequestMethod.POST })
	public String addBlog(HttpServletRequest request, @RequestParam("file") List<MultipartFile> fileList, Model model) {

		if (request.getSession().getAttribute("loggerInUserId") == null) {
			request.setAttribute("error", "Please login to perform this action.");
			return "login";
		}

		AddFormModel addFormModel = null;
		List<AddFormDetailModel> addFormDetailModelList = null;
		try {

			addFormModel = new AddFormModel();
			addFormDetailModelList = new ArrayList<AddFormDetailModel>();

			addFormModel.setTitleId(request.getParameter("titleId"));
			addFormModel.setShortDescId(request.getParameter("shortDescId"));
			addFormModel.setMenuId(Integer.parseInt(request.getParameter("subMenuId")));
			addFormModel.setNextId(
					Integer.parseInt(request.getParameter("next") != null ? request.getParameter("next") : "-1"));
			addFormModel.setPrevId(
					Integer.parseInt(request.getParameter("prev") != null ? request.getParameter("prev") : "-1"));
			addFormModel.setTags(request.getParameter("tags"));

			String[] arrLongDescId = request.getParameterValues("longDescId");
			String[] arrMoreTypeId = request.getParameterValues("moreTypeId");
			String[] arrOrderId = request.getParameterValues("orderId");

			int count = 0;
			int length = arrMoreTypeId.length;
			for (int i = 0; i < length; i++) {
				AddFormDetailModel arr = new AddFormDetailModel();

				if (!("Text".equalsIgnoreCase(arrMoreTypeId[i])) && !("Code".equalsIgnoreCase(arrMoreTypeId[i]))) {

					MultipartFile file = fileList.get(count);
					String filePath = s3Service.uploadS3File(file, arrMoreTypeId[i]);
					count++;
					arr.setLongDescId(filePath);
				} else {
					arr.setLongDescId(arrLongDescId[i]);
				}
				arr.setAddMoreType(arrMoreTypeId[i]);
				arr.setOrderId(arrOrderId[i]);
				addFormDetailModelList.add(arr);
			}
			addFormModel.setAddFormDetailModel(addFormDetailModelList);

			blogService.insertAddForm(addFormModel);
		} catch (Exception e) {
			LOGGER.error("addBlog Function:", e);
		}
		model.addAttribute("parent", blogService.getParentMenues());
		return "addBlogForm";
	}

	@RequestMapping(value = "editBlog", method = { RequestMethod.GET, RequestMethod.POST })
	public String editBlog(HttpServletRequest request, Model model) {

		int contentId = Integer.parseInt(request.getParameter("id"));

		AddFormModel formModel = blogService.getTopicDetailByTopicId(contentId);
		List<AddFormDetailModel> formModelList = blogService.getTopicDetailForTopic(contentId);

		model.addAttribute("formModel", formModel);
		model.addAttribute("formModelList", formModelList);
		model.addAttribute("parent", blogService.getParentMenues());

		return "editBlogForm";
	}

	@RequestMapping(value = "editSaveBlog", method = { RequestMethod.GET, RequestMethod.POST })
	public String editSaveBlog(HttpServletRequest request, @RequestParam("file") List<MultipartFile> fileList,
			Model model) {

		AddFormModel addFormModel = null;
		List<AddFormDetailModel> addFormDetailModelList = null;
		try {

			addFormModel = new AddFormModel();
			addFormDetailModelList = new ArrayList<AddFormDetailModel>();

			addFormModel.setContentId(Integer.parseInt(request.getParameter("contentId")));
			addFormModel.setTitleId(request.getParameter("titleId"));
			addFormModel.setTags(request.getParameter("tags"));
			addFormModel.setShortDescId(request.getParameter("shortDescId"));
			addFormModel.setMenuId(Integer.parseInt(request.getParameter("subMenuId")));
			addFormModel.setNextId(
					Integer.parseInt(request.getParameter("next") != null ? request.getParameter("next") : "-1"));
			addFormModel.setPrevId(
					Integer.parseInt(request.getParameter("prev") != null ? request.getParameter("prev") : "-1"));

			String[] arrLongDescId = request.getParameterValues("longDescId");
			String[] arrMoreTypeId = request.getParameterValues("moreTypeId");
			String[] arrOrderId = request.getParameterValues("orderId");

			String[] filePathTxt = request.getParameterValues("filePathTxt");

			LOGGER.info("Method : editSaveBlog arrLongDescId:" + arrLongDescId);
			LOGGER.info("Method : editSaveBlog arrMoreTypeId:" + arrMoreTypeId);
			LOGGER.info("Method : editSaveBlog arrOrderId:" + arrOrderId);
			LOGGER.info("Method : editSaveBlog filePathTxt:" + filePathTxt);

			int count = 0;
			int length = arrMoreTypeId.length;
			for (int i = 0; i < length; i++) {
				AddFormDetailModel arr = new AddFormDetailModel();

				if (!("Text".equalsIgnoreCase(arrMoreTypeId[i])) && !("Code".equalsIgnoreCase(arrMoreTypeId[i]))) {
					String filePath = "";
					if (filePathTxt != null && count < filePathTxt.length) {
						filePath = filePathTxt[count];
						count++;
					} else {
						MultipartFile file = fileList.get(count - (filePathTxt != null ? filePathTxt.length : 0));
						if (file != null && !file.isEmpty()) {
							filePath = s3Service.uploadS3File(file, arrMoreTypeId[i]);
						}
						count++;
					}
					arr.setLongDescId(filePath);
				} else {
					arr.setLongDescId(arrLongDescId[i - count]);
				}
				arr.setAddMoreType(arrMoreTypeId[i]);
				arr.setOrderId(arrOrderId[i]);
				addFormDetailModelList.add(arr);
			}
			addFormModel.setAddFormDetailModel(addFormDetailModelList);
			blogService.updateContentDesc(addFormModel);
			LOGGER.info("Method : editSaveBlog :" + "Updated Completed");
		} catch (Exception e) {
			LOGGER.error("Exception in editSaveBlog ", e);
		}
		model.addAttribute("parent", blogService.getParentMenues());
		return "redirect:deleteBlogForm.html";
	}

	@RequestMapping(value = "addMenu", method = { RequestMethod.GET, RequestMethod.POST })
	public String addMenu(Model model, HttpServletRequest request) {

		if (request.getSession().getAttribute("loggerInUserId") == null) {
			request.setAttribute("error", "Please login to perform this action.");
			return "login";
		}

		String parent = request.getParameter("parent");
		String subMenu = request.getParameter("subMenu");
		String target = request.getParameter("target");
		String isVisible = request.getParameter("isVisible");

		Map<String, String> parameters = new HashMap<String, String>();

		parameters.put("MENU_DESC", subMenu);
		parameters.put("PARENT", parent);
		parameters.put("TARGET", target);
		parameters.put("IS_VISIBLE", isVisible);

		blogService.insertMenuForm(parameters);

		model.addAttribute("parent", blogService.getParentMenues());

		return "addMenuForm";
	}

	@RequestMapping(value = "contact", method = { RequestMethod.GET, RequestMethod.POST })
	public String contact() {
		return "contact";
	}

	@ResponseBody
	@RequestMapping(value = "getSubMenu", method = { RequestMethod.GET, RequestMethod.POST })
	public String getSubMenu(@RequestParam String menuId, HttpServletRequest request) {

		if (request.getSession().getAttribute("loggerInUserId") == null) {
			request.setAttribute("error", "Please login to perform this action.");
			return "login";
		}

		Map<String, String> data = blogService.getSubMenues(menuId);

		JSONObject json = new JSONObject(data);
		return json.toString();
	}

	@RequestMapping(value = "loadMenu", method = { RequestMethod.GET, RequestMethod.POST })
	public String loadMenu() {

		menuLoader.loadMenu();
		return "addMenuForm";
	}

	@RequestMapping(value = "datadaalde", method = { RequestMethod.GET, RequestMethod.POST })
	public String loginForm(Model model) {

		model.addAttribute("from", "admin");

		return "login";
	}

	@RequestMapping(value = "goToLogin", method = { RequestMethod.GET, RequestMethod.POST })
	public String goToLogin(Model model) {

		model.addAttribute("from", "user");

		return "login";
	}

	@RequestMapping(value = "login", method = { RequestMethod.GET, RequestMethod.POST })
	public String login(HttpServletRequest request, Model model) {

		String redirectPath = "";

		String from = request.getParameter("from");

		String userId = request.getParameter("userId");
		String password = request.getParameter("password");

		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", userId);
		params.put("password", password);

		if (from != null && from.equalsIgnoreCase("admin")) {
			redirectPath = "redirect:addBlogForm.html";
			params.put("admin", "Y");
		} else {
			redirectPath = "redirect:/";
			params.put("admin", "N");
		}

		boolean flag = blogService.userLogin(params, request);
		if (!flag) {
			request.setAttribute("error", "Email Id or password entered is incorrect.");
			model.addAttribute("from", request.getParameter("from"));
			return "login";
		} else {
			return redirectPath;
		}
	}

	@RequestMapping(value = "logout", method = { RequestMethod.GET, RequestMethod.POST })
	public String logout(HttpServletRequest request) {

		request.getSession().removeAttribute("loginName");
		request.getSession().invalidate();

		return "redirect:datadaalde.html";
	}

	@ResponseBody
	@RequestMapping(value = "subscribe", method = { RequestMethod.GET, RequestMethod.POST })
	public String subscribe(@RequestParam String emailId) {

		boolean flag = blogService.isExistsEmail(emailId);
		if (flag) {
			return "Already subscribed.";
		} else {
			blogService.subscribe(emailId);
			return "Subscribed successfully.";
		}
	}

	@RequestMapping(value = "search", method = { RequestMethod.GET, RequestMethod.POST })
	public String searchContent(HttpServletRequest request,
			@RequestParam(value = "searchKeyword", required = true) String searchKeyword, Model model) {

		List<AddFormModel> contentList = blogService.getSearchContent(searchKeyword);
		model.addAttribute("contentList", contentList);
		model.addAttribute("newTopicContentList", MenuLoader.newTopicContentList);
		request.setAttribute("hotTopic", "Y");
		model.addAttribute("tagsSet", MenuLoader.tagsSet);
		model.addAttribute("codeList", MenuLoader.codeList);

		return "topics";
	}

	@ResponseBody
	@RequestMapping(value = "getBlogComment", method = { RequestMethod.GET, RequestMethod.POST })
	public List<BlogCommentBean> getBlogComment(@RequestParam int topicId, HttpServletRequest request) {
		List<BlogCommentBean> data = blogService.getBlogCommentList(topicId);

		return data;
	}

	@ResponseBody
	@RequestMapping(value = "checkLogin", method = { RequestMethod.GET, RequestMethod.POST })
	public String checkLogin(HttpSession session) {
		String msg = "notLoggedIn";
		if (session != null && session.getAttribute("loggerInUserId") != null) {
			msg = "loggedIn";
		}
		return msg;
	}

	@ResponseBody
	@RequestMapping(value = "postComment", method = { RequestMethod.GET, RequestMethod.POST })
	public String postComment(HttpSession session, HttpServletRequest request) {

		String msg = "success";

		String comment = request.getParameter("comment");
		int topicId = Integer.parseInt(request.getParameter("topicId"));
		String loggerInUserId = (String) session.getAttribute("loggerInUserId");

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("comment", comment);
		params.put("topicId", topicId);
		params.put("loggerInUserId", loggerInUserId != null ? loggerInUserId : "vivek");

		blogService.submitComment(params);

		return msg;
	}

	@ResponseBody
	@RequestMapping(value = "postReply", method = { RequestMethod.GET, RequestMethod.POST })
	public String postReply(HttpSession session, HttpServletRequest request) {

		String msg = "success";

		String reply = request.getParameter("reply");
		int commentId = Integer.parseInt(request.getParameter("commentId"));
		String loggerInUserId = (String) session.getAttribute("loggerInUserId");

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("reply", reply);
		params.put("commentId", commentId);
		params.put("loggerInUserId", loggerInUserId != null ? loggerInUserId : "vivek");

		blogService.submitReply(params);

		return msg;
	}

	@ResponseBody
	@RequestMapping(value = "UserLogin", method = { RequestMethod.GET, RequestMethod.POST })
	public String UserLogin(HttpServletRequest request) {
		String message = "error";
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");

		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", userId);
		params.put("password", password);
		params.put("admin", "N");

		System.out.println("params : " + params);

		boolean flag = blogService.userLogin(params, request);
		if (flag) {
			message = "success";
		}

		return message;
	}

	@RequestMapping(value = "Logout", method = { RequestMethod.GET, RequestMethod.POST })
	public String Logout(HttpSession session, HttpServletRequest request, Model model) {
		session.removeAttribute("loggerInUserId");
		session.invalidate();

		return "redirect:/";
	}

	@ResponseBody
	@RequestMapping(value = "submitLike", method = { RequestMethod.GET, RequestMethod.POST })
	public String submitLike(HttpSession session, HttpServletRequest request) {

		int contentId = Integer.parseInt(request.getParameter("contentId"));
		String loggerInUserId = (String) session.getAttribute("loggerInUserId");

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("contentId", contentId);
		params.put("loggerInUserId", loggerInUserId != null ? loggerInUserId : "vivek");

		int count = blogService.submitLike(params);

		return "" + count;
	}

	@ResponseBody
	@RequestMapping(value = "checkUserIdExists", method = { RequestMethod.GET, RequestMethod.POST })
	public String checkUserIdExists(@RequestParam String userId) {

		String message = "noExists";
		boolean returnValue = blogService.checkUserIdExists(userId);
		if (returnValue) {
			message = "Exists";
		}

		return message;
	}

	@RequestMapping(value = "getYouTube.html", method = { RequestMethod.GET, RequestMethod.POST })
	public String getYouTube(HttpServletRequest request, Model model) {

		int contentId = Integer.parseInt(request.getParameter("contentId"));

		List<YouTubeModel> youtubeList = blogService.getYouTubeList(contentId);

		model.addAttribute("youtubeList", youtubeList);
		model.addAttribute("contentId", contentId);

		return "addYouTube";
	}

	@ResponseBody
	@RequestMapping(value = "RegisterUser", method = { RequestMethod.GET, RequestMethod.POST })
	public String RegisterUser(HttpServletRequest request, HttpSession session) {

		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");

		Map<String, String> params = new HashMap<String, String>();
		params.put("firstName", firstName);
		params.put("lastName", lastName);
		params.put("userName", userName);
		params.put("password", password);

		blogService.registerUser(params);

		session.setAttribute("loggerInUserName", firstName + " " + lastName);
		session.setAttribute("loggerInUserId", userName);

		return "success";
	}

	@ResponseBody
	@RequestMapping(value = "getBlogCount", method = { RequestMethod.GET, RequestMethod.POST })
	public String getBlogCount() {

		return String.valueOf(blogService.getBlogCount());
	}

	@ResponseBody
	@RequestMapping(value = "getTotalViews", method = { RequestMethod.GET, RequestMethod.POST })
	public String getTotalViews() {

		return String.valueOf(blogService.getVisitCount());
	}

	@RequestMapping(value = "addYouTube.html", method = { RequestMethod.GET, RequestMethod.POST })
	public String addYouTube(HttpServletRequest request, Model model) {

		List<String> youtubeArrList = new ArrayList<String>();
		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			String[] files = request.getParameterValues(paramName);
			if (files != null) {
				int length = files.length;
				for (int j = 0; j < length; j++) {
					if (paramName.equals("txtYoutubecode")) {
						youtubeArrList.add(files[j]);
					}
				}
			}
		}

		int contentId = Integer.parseInt(request.getParameter("txtContentId"));

		// Delete and Insert

		blogService.addYouTubeList(contentId, youtubeArrList);

		// Get Updated Result
		List<YouTubeModel> youtubeList = blogService.getYouTubeList(contentId);

		model.addAttribute("youtubeList", youtubeList);
		model.addAttribute("contentId", contentId);

		return "addYouTube";
	}

	@ResponseBody
	@RequestMapping(value = "getQuestionOfTheDay", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView getQuestionOfTheDay() {

		ModelMap map = new ModelMap();

		QuestionOfTheDayBean bean = blogService.getQuestionOfTheDay();
		map.put("questionOfTheDay", bean);

		return new ModelAndView("questionOfTheDay", map);
	}

	@RequestMapping(value="readInterviewQuestion", method= {RequestMethod.GET, RequestMethod.POST})
	public String readInterviewQuestion(Model model, @RequestParam int id) {
		String awsEndPoint = "https://" + env.getProperty("awsEndPoint");

		InterviewQuestionsBean cBean = blogService.getInterViewQuestionDetailById(id);

		model.addAttribute("content", cBean);
		model.addAttribute("awsEndPoint", awsEndPoint);
		model.addAttribute("newTopicContentList", MenuLoader.newTopicContentList);
		model.addAttribute("tagsSet", MenuLoader.tagsSet);
		model.addAttribute("codeList", MenuLoader.codeList);

		/*// Get Updated Result
		List<YouTubeModel> youtubeList = blogService.getYouTubeList(id);
		model.addAttribute("youtubeList", youtubeList);
		*/
		return "readInterviewQuestion";
	}
	
}
