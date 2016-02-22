package models;

import java.io.File;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;

import play.Play;

import utils.ModelUtils;
import utils.PagedList;

@Entity
public class Version extends BaseModel{

	@ManyToOne public Scene scene;	//镜头ID
	public String number;	//版本编号
	@ManyToOne public User user;	//客户ID
	public Integer state;	//版本状态
	public String path;   //视频地址
	public String downloadpath;  //視頻下載地址
	public Integer height;
	public Integer width;
	public Float frameRate;
	public Long duration;
	public String decoder;
	public String image;
	public String minImage;
	@OneToMany(mappedBy="version",cascade=CascadeType.ALL) public List<Comment> comments;  //评论信息


	
	public static void findPage(PagedList<Version> pagedList,String search, String searchField, String orderBy, String order,String condition,String where){
		long count = ModelUtils.count(Version.class.getName(), "['scene','number','user','state']", search, searchField, condition,where);
		List<Version> list = ModelUtils.findPage(pagedList.getFirstRowInThisPage(), pagedList.getPageSize(), Version.class.getName(), "['scene','number','user','state']", search, searchField, orderBy, order,condition,where);
		pagedList.setRowCount(count);
		pagedList.setList(list);
	}
	
	public String toString(){
		return number;
	}
	
	@PreRemove
	public void remove(){
		if(path!=null){
			File file=new File(Play.applicationPath,path);
			file.delete();
		}
		if(image!=null){
			File file=new File(Play.applicationPath,image);
			file.delete();
		}
		if(minImage!=null){
			File file=new File(Play.applicationPath,minImage);
			file.delete();
		}
	}
}