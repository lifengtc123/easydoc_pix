package controllers;


import java.util.ArrayList;
import java.util.List;

import models.Menu;
import models.User;
import play.data.validation.Valid;
import utils.PagedList;

public class Menus extends Application{
	
	public static void list(String orderBy,String order) {
		List<Menu> object=Menu.find("order by sort").fetch();
		List<Menu> object2=new ArrayList<Menu>();
		for(Menu mm:object){
			if(mm.pid.equals("0")){
				object2.add(mm);
			}
		}
		for(Menu mm:object2){
			for(Menu m:object){
				if(m.pid.equals(mm.cid)){
					mm.menus.add(m);
				}
			}
		}
		render(object2,orderBy,order);
	}
	
	public static void blank() {
		List<Menu> list = Menu.find("pid='0' order by sort").fetch();
		Menu object = new Menu();
		render(object,list);
	}
	
	public static void create() {
		Menu object = new Menu();
		validation.valid(object.edit("object", params.all()));
		if(validation.hasErrors()){
			List<Menu> list = Menu.find("pid=0 order by sort").fetch();
			render("@blank",object,list);
		}
		object.save();
		flash.success("add.success");
		list(null,null);
//		result("保存菜单成功","保存菜单成功!","/menus/blank",true);
	}
	
	public static void save(Long id) {
		Menu object = Menu.findById(id);
		validation.valid(object.edit("object", params.all()));
		if(validation.hasErrors()){
			List<Menu> list = Menu.find("pid=0 order by sort").fetch();
			render("@show",object,list);
		}
		object.save();
		flash.success("edit.success");
		list(null,null);
	//	result("保存菜单成功","保存菜单成功!","/menus/blank",true);
	}
	
	public static void show(long id) {
		Menu object = Menu.findById(id);
		notFoundIfNull(object);
		List<Menu> list = Menu.find("pid=0 order by sort").fetch();
		render(object,list);
	}
	
	public static void detail(long id) {
		Menu object = Menu.findById(id);
		notFoundIfNull(object);
		List<Menu> list = Menu.find("pid=0 order by sort").fetch();
		render(object,list);
	}
	
	public static void delete(Long id,Long[] arrayid) {
		if(arrayid!=null && arrayid.length > 0){
			for(long _id : arrayid){
				Menu menu = Menu.findById(_id);
				if(menu!=null) menu.delete();
			}
		}else if(id!=null){
			Menu menu = Menu.findById(id);
			if(menu==null){ flash.error("记录不存在，删除数据失败!"); list(null,null);}
			menu.delete();
		}
		flash.success("删除数据成功!");
		list(null,null);
	}
}
