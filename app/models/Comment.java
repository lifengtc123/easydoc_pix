package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import utils.ModelUtils;
import utils.PagedList;

@Entity
public class Comment extends BaseModel{

	@ManyToOne public User user;	//评论人ID
	@ManyToOne public Version version;	//版本ID
	public Integer frameNumber;	//帧数
	public String note;	//评论

	
	public static void findPage(PagedList<Comment> pagedList,String search, String searchField, String orderBy, String order,String condition,String where){
		long count = ModelUtils.count(Comment.class.getName(), "['user','comment','frameNumber ','note']", search, searchField, condition,where);
		List<Comment> list = ModelUtils.findPage(pagedList.getFirstRowInThisPage(), pagedList.getPageSize(), Comment.class.getName(), "['user','comment','frameNumber ','note']", search, searchField, orderBy, order,condition,where);
		pagedList.setRowCount(count);
		pagedList.setList(list);
	}
}