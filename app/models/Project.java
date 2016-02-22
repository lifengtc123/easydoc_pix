package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import utils.ModelUtils;
import utils.PagedList;

@Entity
public class Project extends BaseModel{

	@ManyToOne public User user;	//客户ID
	public String name;	//项目名
	public Integer state;	//项目状态
	public String number;	//项目编号

	
	public static void findPage(PagedList<Project> pagedList,String search, String searchField, String orderBy, String order,String condition,String where){
		long count = ModelUtils.count(Project.class.getName(), "['user','name','state','number']", search, searchField, condition,where);
		List<Project> list = ModelUtils.findPage(pagedList.getFirstRowInThisPage(), pagedList.getPageSize(), Project.class.getName(), "['user','name','state','number']", search, searchField, orderBy, order,condition,where);
		pagedList.setRowCount(count);
		pagedList.setList(list);
	}
	
	public String toString(){
		return name;
	}
}