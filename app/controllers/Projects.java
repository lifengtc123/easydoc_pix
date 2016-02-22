package controllers;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Official;
import models.Project;
import models.User;
import play.data.validation.Valid;
import utils.PagedList;
//import utils.ExcelUtils;
//import static utils.ExcelUtils.renderExcel;

public class Projects extends Application{

	public static void list(String orderBy,String order) {
		PagedList<Project> pagedList = new PagedList<Project>();
		String where="user.id="+session.get("userid");
		Project.findPage(pagedList,params.get("search"), params.get("searchField"), orderBy, order, params.get("condition"),where);
		render(pagedList,orderBy,order);
	}
	
	public static void list2(String orderBy,String order) {
		PagedList<Official> pagedList = new PagedList<Official>();
		String where="user.id="+session.get("userid");
		Official.findPage(pagedList,params.get("search"), params.get("searchField"), orderBy, order, params.get("condition"),where);
		render(pagedList,orderBy,order);
	}
	
	public static void blank() {
		Project object= new Project();
		render(object);
	}
	
	public static void blank2() {
		Project object= new Project();
		List<User> user=User.find("role=2").fetch();
		Map<Long,String> users=new HashMap<Long,String>();
		for(User uu:user){
			users.put(uu.id,uu.truename);
		}
		render(object,users);
	}
	
	public static void create() {
		Project object= new Project();
		validation.valid(object.edit("object", params.all()));
		if(validation.hasErrors()){
			render("@blank",object);
		}
		object.save();
		User user=connect();
		if(user.role.id==1l){
			Official official=new Official();
			official.project=object;
			official.user=user;
			official.save();
			flash.success("add.success");
			list2(null,null);
		}
		flash.success("add.success");
		list(null,null);
//		result("保存成功","保存成功!","/projects/blank",true);
	}
	
	public static void show(Long id) {
		Project object= Project.findById(id);
		notFoundIfNull(object);
		render(object);
	}
	
	public static void detail(Long id) {
		Project object= Project.findById(id);
		notFoundIfNull(object);
		render(object);
	}
	
	public static void save(Long id) {
		Project object= Project.findById(id);
		notFoundIfNull(object);
		validation.valid(object.edit("object", params.all()));
		if(validation.hasErrors()){
			render("@show",object);
		}
		object.save();
		User user=connect();
		if(user.role.id==1l){
    		flash.success("edit.success");
    		list2(null,null);
    	}
		flash.success("edit.success");
		list(null,null);
//		result("保存成功","保存成功!","/projects/blank",true);
	}
	
	public static void delete(Long id,Long[] arrayid) {
		if(arrayid!=null && arrayid.length > 0){
			for(long _id : arrayid){
				Project project = Project.findById(_id);
				if(project!=null) project.delete();
			}
		}else if(id!=null){
			Project project = Project.findById(id);
			if(project==null){ flash.error("记录不存在，删除数据失败!"); list(null,null);}
			project.delete();
		}
		flash.success("删除数据成功!");
		list(null,null);
	}
	
	//添加负责人
	public static void addOfficials(Long id){
		List<Official> official=Official.find("project.id", id).fetch();
		if(official.isEmpty()){
			official=new ArrayList<Official>();
		}
		List<User> user=User.find("role.id=1").fetch();
		for(Official oo:official){
			user.remove(oo.user);
		}
		render(official,user,id);
	}
	
	public static void addOff(Long[] selectuser){
		String sql="delete from official where project_id="+params.get("project.id");
		play.db.DB.execute(sql);
		if(selectuser!=null){
			for(Long uu:selectuser){
				Official official=new Official();
				User user=new User();
				user.id=uu;
				official.user=user;
				Project project=new Project();
				project.id=Long.parseLong(params.get("project.id"));
				official.project=project;
				official.save();
			}
		}
		User user=connect();
		if(user.role.id==1l){
    		flash.success("edit.success");
    		list2(null,null);
    	}
		flash.success("edit.success");
		list(null,null);
	}

}