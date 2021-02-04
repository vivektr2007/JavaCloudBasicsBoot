package com.pixel.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.pixel.aws.service.S3Service;
import com.pixel.blog.service.SectionService;
import com.pixel.model.AddFormDetailModel;
import com.pixel.model.AddFormModel;
import com.pixel.model.MasterSectionModel;
import com.pixel.model.SubSectionModel;
import com.pixel.model.ViewSectionModel;

@Controller
@PropertySource("classpath:/log4j.properties")
public class SectionController {

	@Autowired
	private S3Service s3Service;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SectionController.class);

	@Autowired
	SectionService sectionService;

	@Autowired
	Environment env;
	
	@ResponseBody
	@RequestMapping(value="getSubSection", method={RequestMethod.GET, RequestMethod.POST})
	public String getSubSection(@RequestParam String masterid){
		List<SubSectionModel> list = sectionService.getSubSectionById(masterid);
		Gson gson = new Gson();
		String json = gson.toJson(list); 
		 
		return json;
	}
		
	@RequestMapping(value="addSubSectionData")
	public String addSubSectionData(Model model){
		List<MasterSectionModel> MasterSection = sectionService.getMasterSection();
		model.addAttribute("MasterSection", MasterSection);
		
		return "addSubSectionData";
	}
	
	@RequestMapping(value="viewSubSectionData")
	public String viewSubSectionData(){
		return "viewSubSectionData";
	}

	@RequestMapping(value = "saveSubSectionData", method = { RequestMethod.GET, RequestMethod.POST })
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

			sectionService.saveSubSectionData(addFormModel);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("addBlog Function:", e);
		}
		return "redirect:addSubSectionData";
	}
	
	@RequestMapping(value="getInterviewQuestion")
	public String getInterviewQuestion(Model model){
		
		List<ViewSectionModel> MasterSection = sectionService.getMasterSectionDetails("1");
		System.out.println(MasterSection.get(0).getSubmasterdesc());
		//model.addAttribute("MasterSection", MasterSection);
		
		return "addSubSectionData";
	}
}
