package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import utils.ModelUtils;
import utils.PagedList;

@Entity
public class Official extends BaseModel{

	@ManyToOne public Project project;	//项目ID
	@ManyToOne public User user;	//负责人ID

	
	public static void findPage(PagedList<Official> pagedList,String search, String searchField, String orderBy, String order,String condition,String where){
		long count = ModelUtils.count(Official.class.getName(), "['project','user']", search, searchField, condition,where);
		List<Official> list = ModelUtils.findPage(pagedList.getFirstRowInThisPage(), pagedList.getPageSize(), Official.class.getName(), "['project','user']", search, searchField, orderBy, order,condition,where);
		pagedList.setRowCount(count);
		pagedList.setList(list);
	}
}