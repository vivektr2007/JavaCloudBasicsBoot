package com.pixel.blog.service;

import java.util.List;

import com.pixel.model.AddFormModel;
import com.pixel.model.MasterSectionModel;
import com.pixel.model.SubSectionModel;
import com.pixel.model.ViewSectionModel;

public interface SectionService {

	public List<MasterSectionModel> getMasterSection();
	
	public List<SubSectionModel> getSubSectionById(String masterid);

	public void saveSubSectionData(AddFormModel addFormModel);
	
	public List<ViewSectionModel> getMasterSectionDetails(String masterid);
	
}
