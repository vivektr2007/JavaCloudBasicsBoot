package com.pixel.blog.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pixel.blog.service.BlogService;
import com.pixel.model.AddFormModel;
import com.pixel.model.CodeModel;
import com.pixel.model.MenuBean;
import com.pixel.mybatis.dao.BlogDao;
import com.pixel.utils.CommonIFace;

@Component
public class MenuLoader {

	@Autowired
	BlogDao blogDao;

	@Autowired
	BlogService blogService;

	public static Map<String, List<MenuBean>> menus;

	public static List<AddFormModel> newTopicContentList;

	public static Set<String> tagsSet;

	public static List<CodeModel> codeList;

	public static List<AddFormModel> relatedTopicList;

	public static HashMap<String, List<AddFormModel>> hashRelatedTopics;

	@PostConstruct
	public void loadMenu() {

		menus = new LinkedHashMap<String, List<MenuBean>>();
		List<MenuBean> list = blogDao.getMenuDetail();
		Iterator<MenuBean> itr = list.iterator();
		while (itr.hasNext()) {
			MenuBean mBean = itr.next();
			if (menus.containsKey(mBean.getParent())) {
				menus.get(mBean.getParent()).add(mBean);
			} else {
				List<MenuBean> l = new ArrayList<MenuBean>();
				l.add(mBean);
				menus.put(mBean.getParent(), l);
			}
		}

		newTopicContentList = blogService.getLatestContent(10, -1);

		loadTags();

		loadCodeList();

		createRelatedTopics();
	}

	public void loadTags() {

		List<String> list = blogDao.getTagList();

		tagsSet = new HashSet<String>();

		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				String splitArr[] = list.get(i).split(";");
				int length = splitArr.length;
				for (int j = 0; j < length; j++) {
					tagsSet.add(splitArr[j].toLowerCase());
				}
			}
		}
	}

	public void loadCodeList() {
		if (codeList == null) {
			codeList = blogService.getCodeList();
		}
	}

	public void createRelatedTopics() {

		try {
			if (relatedTopicList == null) {

				Set<String> temptagsSet = new HashSet<String>(tagsSet);

				hashRelatedTopics = new HashMap<String, List<AddFormModel>>();

				relatedTopicList = blogService.getLatestContent(CommonIFace.MAX_CONTENT_LIMIT, -1);

				ArrayList<String> commonTags = new ArrayList<String>();

				commonTags.add("java");
				commonTags.add("cloud");
				commonTags.add("aws");
				commonTags.add("core java");

				temptagsSet.removeAll(commonTags);

				Iterator<String> it = temptagsSet.iterator();

				while (it.hasNext()) {

					String tag = it.next();

					ArrayList<AddFormModel> temp = new ArrayList<AddFormModel>();
					int size = relatedTopicList.size();
					for (int j = 0; j < size; j++) {

						String splitArr[] = relatedTopicList.get(j).getTags().split(";");
						int length = splitArr.length;
						for (int k = 0; k < length; k++) {
							if (tag.equalsIgnoreCase(splitArr[k])) {
								temp.add(relatedTopicList.get(j));
							}
						}
					}
					hashRelatedTopics.put(tag, temp);
				}
			}
		} catch (Exception e) {
			System.out.println("Exception While Creating related Topics::");
			e.printStackTrace();
		}
	}

}