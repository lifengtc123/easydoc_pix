package controllers;

import java.io.*;
import java.util.List;

import models.Official;
import play.data.validation.Valid;
import utils.PagedList;

public class Officials extends Application{

	public static void list(String orderBy,String order) {
		PagedList<Official> pagedList = new PagedList<Official>();
		Official.findPage(pagedList,params.get("search"), params.get("searchField"), orderBy, order, params.get("condition"),null);
		render(pagedList,orderBy,order);
	}
	
	public static void blank() {
		Official official= new Official();
		render(official);
	}
	
	public static void create() {
		Official official= new Official();
		validation.valid(official.edit("official", params.all()));
		if(validation.hasErrors()){
			render("@blank",official);
		}
		official.save();
	//	result("保存成功","保存成功!","/officials/blank",true);
	}
	
	public static void show(Long id) {
		Official official= Official.findById(id);
		notFoundIfNull(official);
		render(official);
	}
	
	public static void detail(Long id) {
		Official official= Official.findById(id);
		notFoundIfNull(official);
		render(official);
	}
	
	public static void save(Long id) {
		Official official= Official.findById(id);
		notFoundIfNull(official);
		validation.valid(official.edit("official", params.all()));
		if(validation.hasErrors()){
			render("@show",official);
		}
		official.save();
	//	result("保存成功","保存成功!","/officials/blank",true);
	}
	
	public static void delete(Long id,Long[] arrayid) {
		if(arrayid!=null && arrayid.length > 0){
			for(long _id : arrayid){
				Official official = Official.findById(_id);
				if(official!=null) official.delete();
			}
		}else if(id!=null){
			Official official = Official.findById(id);
			if(official==null){ flash.error("记录不存在，删除数据失败!"); list(null,null);}
			official.delete();
		}
		flash.success("删除数据成功!");
		list(null,null);
	}

}