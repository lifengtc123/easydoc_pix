package controllers;

import java.io.*;
import java.util.List;

import models.Comment;
import play.data.validation.Valid;
import utils.PagedList;

public class Comments extends Application{

	public static void list(String orderBy,String order) {
		PagedList<Comment> pagedList = new PagedList<Comment>();
		Comment.findPage(pagedList,params.get("search"), params.get("searchField"), orderBy, order, params.get("condition"),null);
		render(pagedList,orderBy,order);
	}
	
	public static void blank() {
		Comment comment= new Comment();
		render(comment);
	}
	
	public static void create() {
		Comment comment= new Comment();
		validation.valid(comment.edit("comment", params.all()));
		if(validation.hasErrors()){
			render("@blank",comment);
		}
		comment.save();
		flash.success("add.success");
		list(null,null);
	//	result("保存成功","保存成功!","/comments/blank",true);
	}
	
	public static void show(Long id) {
		Comment comment= Comment.findById(id);
		notFoundIfNull(comment);
		render(comment);
	}
	
	public static void detail(Long id) {
		Comment comment= Comment.findById(id);
		notFoundIfNull(comment);
		render(comment);
	}
	
	public static void save(Long id) {
		Comment comment= Comment.findById(id);
		notFoundIfNull(comment);
		validation.valid(comment.edit("comment", params.all()));
		if(validation.hasErrors()){
			render("@show",comment);
		}
		comment.save();
		flash.success("edit.success");
		list(null,null);
	//	result("保存成功","保存成功!","/comments/blank",true);
	}
	
	public static void delete(Long id,Long[] arrayid) {
		if(arrayid!=null && arrayid.length > 0){
			for(long _id : arrayid){
				Comment comment = Comment.findById(_id);
				if(comment!=null) comment.delete();
			}
		}else if(id!=null){
			Comment comment = Comment.findById(id);
			if(comment==null){ flash.error("记录不存在，删除数据失败!"); list(null,null);}
			comment.delete();
		}
		flash.success("删除数据成功!");
		list(null,null);
	}
	
	//ajax 提交评论
	public static void addUp(){
		Comment object=new Comment();
		validation.valid(object.edit("comment", params.all()));
		if(validation.hasErrors()){
			renderJSON(false);
		}
		object.save();
		renderJSON(true);
	}
}