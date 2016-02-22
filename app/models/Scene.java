package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import utils.ModelUtils;
import utils.PagedList;

@Entity
public class Scene extends BaseModel{

	@ManyToOne public Project project;	//项目ID
	public String name;	//镜头名
	public String number;	//镜头编号
	public Integer state;	//镜头状态
	@ManyToOne public User user;	//客户ID
	@OneToMany(mappedBy="scene",cascade=CascadeType.ALL) public List<Version> versions;  //表信息


	
	public static void findPage(PagedList<Scene> pagedList,String search, String searchField, String orderBy, String order,String condition,String where){
		long count = ModelUtils.count(Scene.class.getName(), "['project','name','number','state','user']", search, searchField, condition,where);
		List<Scene> list = ModelUtils.findPage(pagedList.getFirstRowInThisPage(), pagedList.getPageSize(), Scene.class.getName(), "['project','name','number','state','user']", search, searchField, orderBy, order,condition,where);
		pagedList.setRowCount(count);
		pagedList.setList(list);
	}
	
	public String toString(){
		return name;
	}
}