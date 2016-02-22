package controllers;

import java.io.*;
import java.util.List;

import models.Version;
import play.data.validation.Valid;
import utils.PagedList;
//import utils.ExcelUtils;
//import static utils.ExcelUtils.renderExcel;

public class Versions extends Application{

	public static void list(String orderBy,String order) {
		PagedList<Version> pagedList = new PagedList<Version>();
		Version.findPage(pagedList,params.get("search"), params.get("searchField"), orderBy, order, params.get("condition"),null);
		render(pagedList,orderBy,order);
	}
	
	public static void blank() {
		Version version= new Version();
		render(version);
	}
	
	public static void create() {
		Version version= new Version();
		validation.valid(version.edit("version", params.all()));
		if(validation.hasErrors()){
			render("@blank",version);
		}
		version.save();
//		result("保存成功","保存成功!","/versions/blank",true);
	}
	
	public static void show(Long id) {
		Version object= Version.findById(id);
		notFoundIfNull(object);
		render(object);
	}
	
	public static void detail(Long id) {
		Version version= Version.findById(id);
		notFoundIfNull(version);
		render(version);
	}
	
	public static void save(Long id) {
		Version version= Version.findById(id);
		notFoundIfNull(version);
		validation.valid(version.edit("version", params.all()));
		if(validation.hasErrors()){
			render("@show",version);
		}
		version.save();
//		result("保存成功","保存成功!","/versions/blank",true);
	}
	
	public static void delete(Long id,Long[] arrayid,Long projectid) {
		if(arrayid!=null && arrayid.length > 0){
			for(long _id : arrayid){
				Version version = Version.findById(_id);
				if(version!=null) version.delete();
			}
		}else if(id!=null){
			Version version = Version.findById(id);
			if(version==null){ flash.error("记录不存在，删除数据失败!"); list(null,null);}
			version.delete();
		}
		flash.success("delete.success");
		Scenes.list(projectid,null,null);
	}

}