package controllers;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Depart;
import models.Role;
import models.User;
import utils.MD5;
import utils.PagedList;
import utils.SelectTree;
import utils.SelectTreeUtils;

public class Users extends Application{

	
	public static void list(String orderBy,String order,Long role) {
		PagedList<User> pagedList = new PagedList<User>();
		if (order == null){
			order = "DESC";
		}
		if (orderBy == null){
			orderBy = "created";
		}
		if(params.get("pid")==null){
			String where = "role.id = " + role;
			User.findPage(pagedList,params.get("search"), params.get("field"), orderBy, order, params.get("condition"),where);
		}else{
			String where = "depart.id = " + params.get("pid");
			User.findPage(pagedList,params.get("search"), params.get("searchField"), orderBy, order, params.get("condition"),where);
		}
		render(pagedList,orderBy,order);
	}
	
	public static void blank() {
		User object = new User();
		List<Role> role = Role.findAll();
	//	List<Depart> departs = Depart.find("flag=1 order by id").fetch();
		Map<Object,Object> roles=new HashMap<Object,Object>();
		for(Role rr:role){
			roles.put(rr.id, rr.name);
		}
		render(object,roles);
	}
	
	public static void blank1() {
		User object = new User();
		List<Role> role = Role.findAll();
		List<Depart> departs = Depart.find("flag=1 order by id").fetch();
		render(object,role,departs);
	}
	
	public static void create() {
		User object = new User();
		validation.valid(object.edit("object", params.all()));
		if(validation.hasErrors()){
			List<Role> role = Role.findAll();
			render("@blank",object,role);
		}
		object.password=MD5.hash(object.password);
		object.login=0;
		object.save();
		flash.success("add.success");
		list(null,null,object.role.id);
//		result("保存用户成功","保存用户成功!","/users/blank",true);
	
	}
	
	public static void show(long id) {
		User object = User.findById(id);
		notFoundIfNull(object);
		List<Role> role = Role.findAll();
		Map<Object,Object> roles=new HashMap<Object,Object>();
		for(Role rr:role){
			roles.put(rr.id, rr.name);
		}
		render(object,roles);
	}
	
	public static void detail1(long id) {
		User object = User.findById(id);
		notFoundIfNull(object);
		List<Role> role = Role.findAll();
		render(object,role);
	}
	
	public static void userInformSet(long id) {
		User object = User.findById(id);
		notFoundIfNull(object);
		render(object);
	}
	
	public static void detail(long id) {
		User object = User.findById(id);
		notFoundIfNull(object);
		List<Role> role = Role.findAll();
		render(object,role);
	}
	
	public static void save(Long id) {
//		object.password=MD5.hash(object.password);
		User object = User.findById(id);
		validation.valid(object.edit("object", params.all()));
		if(params.get("object.password2")!=null&&!params.get("object.password2").trim().equals("")&&!object.password.equals(MD5.hash(params.get("object.password2")))){
			object.password=MD5.hash(params.get("object.password2"));
		}
		if(validation.hasErrors()){
			render("@detail1",object);
		}
		object.save();
		flash.success("edit.success");
		list(null,null,object.role.id);
	}
	
	public static void userInformSetSave(Long id) {
		User object = User.findById(id);
		validation.valid(object.edit("object", params.all()));
		if(params.get("object.password2")!=null&&!params.get("object.password2").trim().equals("")&&!object.password.equals(MD5.hash(params.get("object.password2")))){
			object.password=MD5.hash(params.get("object.password2"));
		}
		if(validation.hasErrors()){
			render("@show",object);
		}
		
		object.save();
	//	result2("保存用户成功","保存用户成功!","/users/userInformSet?id="+id,false);
		
	}
	

	
	public static void delete(Long id,Long[] arrayid) {
		if(arrayid!=null && arrayid.length > 0){
			for(long _id : arrayid){
				User user = User.findById(_id);
				if(user!=null) user.delete();
			}
		}else if(id!=null){
			User user= User.findById(id);
			if(user==null){ renderJSON(false);}
			user.delete();
		}
		renderJSON(true);
	}
	
	public static void select() {
		List<Depart> list = Depart.find("flag = 1 order by sort").fetch();
		List<SelectTree> departs = SelectTreeUtils.setTree(list);
		render(departs);
	}
	
	//填出选择用户界面
	public static void select1(){
		List<Depart> list = Depart.find("flag = 1 and sort!=4 order by sort").fetch();
		List<SelectTree> departs = SelectTreeUtils.setTree(list);
		render("@select",departs);
	}
	
	public static void user_edit(){
		User object = User.findById(Long.parseLong(session.get("userid")));
		render(object);
	}

	public static void user_save(Long id, String password){
		User user = User.findById(id);
		user.edit("user", params.all());
		if(!"".equals(password) && password != null) {
			user.password = MD5.hash(password);
		}else {
			user.password = user.password;
		}
		user.save();
	//	new_result("修改个人信息成功","修改个人信息成功!","/users/list","/users/user_edit",true);
	}
	
	public static void listphone(){
		List<Depart> departList = Depart.find("flag=1 order by sort").fetch();
		List<User> userList = User.find("status=1 order by depart_id").fetch();
		render(departList,userList);
	}
	
	//ip绑定页面
	public static void ipedit(Long id) {
		User user = User.findById(id);
		if (user != null) {
			render(user);
		}
	}
	
	//ip绑定保存
	public static void doIpEdit(Long id, String useIp, String ip_address) {
		User user = User.findById(id);
		if (user != null) {
			if (useIp != null && useIp.trim().equals("1")) {
				user.useIp = true;
				user.ip_address = ip_address;
				user.save();
			} else {
				user.useIp = false;
				user.ip_address = ip_address;
				user.save();
			}
//			result("保存成功!", "保存成功!", "/Users/ipedit", false);
		} else {
//			result("不存在此用户!", "不存在此用户!", "/Users/ipedit", false);
		}
	}
	
}

