package com.pixel.blog.serviceImpl;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pixel.blog.service.SectionService;
import com.pixel.model.AddFormDetailModel;
import com.pixel.model.AddFormModel;
import com.pixel.model.MasterSectionModel;
import com.pixel.model.SubSectionModel;
import com.pixel.model.ViewSectionModel;
import com.pixel.mybatis.dao.SectionDao;

@Service
public class SectionServiceImpl implements SectionService {

	@Autowired
	SectionDao sectionDao;

	public List<MasterSectionModel> getMasterSection() {
		return sectionDao.getMasterSection();
	}

	public List<SubSectionModel> getSubSectionById(String masterid) {
		return sectionDao.getSubSectionById(Integer.parseInt(masterid));
	}

	public void saveSubSectionData(AddFormModel addFormModel) {

		Integer countent_id = selectMaxContentId();
		sectionDao.saveSubSectionData(addFormModel, countent_id);

		List<AddFormDetailModel> addFormDetailModelList = addFormModel.getAddFormDetailModel();
		insertAddDetail(addFormDetailModelList, countent_id);
	}

	public int selectMaxContentId() {
		return sectionDao.maxContentId();
	}

	private void insertAddDetail(List<AddFormDetailModel> addFormDetailModelList, Integer countent_id) {
		Iterator<AddFormDetailModel> itr = addFormDetailModelList.iterator();

		while (itr.hasNext()) {
			AddFormDetailModel model = itr.next();
			sectionDao.saveSubSectionDetailsData(model, countent_id);

		}
	}

	public List<ViewSectionModel> getMasterSectionDetails(String masterid) {
		List<ViewSectionModel> list = null;
		try {
			sectionDao.getMasterSectionDetails(Integer.parseInt(masterid));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
