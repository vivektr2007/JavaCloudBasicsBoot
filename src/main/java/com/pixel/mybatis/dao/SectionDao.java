package com.pixel.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.pixel.model.AddFormDetailModel;
import com.pixel.model.AddFormModel;
import com.pixel.model.MasterSectionModel;
import com.pixel.model.SubSectionModel;
import com.pixel.model.ViewSectionModel;

@Mapper
public interface SectionDao {

	@Select("select * from tblmastersection")
	public List<MasterSectionModel> getMasterSection();

	@Select("select * from tblsubsection where masterid=#{masterid} order by orderid")
	public List<SubSectionModel> getSubSectionById(@Param("masterid") int masterid);

	@Select("SELECT MAX(content_id)+1 FROM tblsubsectiondata")
	public int maxContentId();

	@Insert("insert into tblsubsectiondata(content_id,subsectionid,title,tags,prev, next, created_date) values("
			+ "#{countent_id}, #{bean.menuId}, #{bean.titleId},#{bean.tags}, "
			+ "#{bean.prevId}, #{bean.nextId}, CURRENT_TIMESTAMP)")
	public void saveSubSectionData(@Param("bean") AddFormModel addFormModel, @Param("countent_id") int countent_id);

	@Insert("insert into tblsubsectiondetail(content_id, long_desc, content_type, order_id, creation_date) "
			+ "values( #{countent_id}, #{bean.longDescId}, #{bean.addMoreType},"
			+ "#{bean.orderId(), current_timestamp)")
	public void saveSubSectionDetailsData(@Param("bean") AddFormDetailModel addFormModel,
			@Param("countent_id") int countent_id);

	@Select("SELECT c.submasterdesc,a.content_id, a.long_desc as long_desc_id,a.content_type as add_more_type,a.order_id,a.creation_date,b.subsectionid,b.title,b.tags,"
			+ " b.prev,b.next FROM tblsubsectiondetail a  LEFT OUTER JOIN tblsubsectiondata b ON a.content_id=b.content_id LEFT OUTER JOIN tblsubsection c ON c.submasterid=b.subsectionid WHERE c.submasterid in (select d.masterid from tblmastersection d "
			+ " where c.masterid=d.masterid and d.masterid= #{masterid}) order by a.content_id,a.creation_date")
	public List<ViewSectionModel> getMasterSectionDetails(@Param("masterid") int masterid);
}
