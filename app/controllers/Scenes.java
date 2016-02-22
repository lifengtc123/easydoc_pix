package controllers;

import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.MultimediaInfo;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.servlet.ServletFileUpload;

import models.Scene;
import models.User;
import models.Version;
import play.Play;
import play.data.validation.Valid;
import play.libs.Files;
import utils.DateUtils;
import utils.Filscopy;
import utils.PagedList;
import utils.UploadUtils;
//import utils.ExcelUtils;
//import static utils.ExcelUtils.renderExcel;
import utils.ParamUtils;

public class Scenes extends Application{

	public static void list(Long id,String orderBy,String order) {
		PagedList<Scene> pagedList = new PagedList<Scene>();
		String where="project.id="+id;
		Scene.findPage(pagedList,params.get("search"), params.get("searchField"), orderBy, order, params.get("condition"),where);
		render(pagedList,orderBy,order,id);
	}
	
	public static void blank(Long id) {
		Scene object= new Scene();
		render(object,id);
	}
	
	public static void create() {
		Scene object= new Scene();
		validation.valid(object.edit("object", params.all()));
		if(validation.hasErrors()){
			render("@blank",object);
		}
		object.save();
		List<Version> versions = ParamUtils.getAllList(Version.class, "version", params);
		for (Version version:versions) {
			version.scene = object;
			File file=new File(Play.applicationPath,version.path);
			Encoder encoder = new Encoder();
			try {
				version.image=version.path.substring(0,version.path.indexOf("."))+".jpg";
				File image=new File(Play.applicationPath,version.image);
				encoder.getImage(file,image,24);
				MultimediaInfo info=encoder.getInfo(file);
				version.height=info.getVideo().getSize().getHeight();
				version.width=info.getVideo().getSize().getWidth();
				version.decoder=info.getVideo().getDecoder();
				version.duration=info.getDuration();
				version.frameRate=info.getVideo().getFrameRate();
		//		version.minImage=UploadUtils.resizeImage(image, version.path.substring(version.path.lastIndexOf("/"),version.path.indexOf("."))+"min", 192, 108);
//				info.
			} catch (EncoderException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			version.save();
		}
		flash.success("add.success");
		list(object.project.id,null,null);
//		result("保存成功","保存成功!","/scenes/blank",true);
	}
	
	public static void show(Long id) {
		Scene object= Scene.findById(id);
		notFoundIfNull(object);
		render(object);
	}
	
	public static void detail(Long id) {
		Scene object= Scene.findById(id);
		notFoundIfNull(object);
		render(object);
	}
	
	public static void save(Long id) {
		Scene object= Scene.findById(id);
		notFoundIfNull(object);
		validation.valid(object.edit("object", params.all()));
		if(validation.hasErrors()){
			render("@show",object);
		}
		object.save();
		List<Version> versions = ParamUtils.getAllList(Version.class, "version", params);
		for (Version version:versions) {
			version.scene = object;
			File file=new File(Play.applicationPath,version.path);
			Encoder encoder = new Encoder();
			try {
				version.image=version.path.substring(0,version.path.indexOf("."))+".jpg";
				File image=new File(Play.applicationPath,version.image);
				encoder.getImage(file,image,24);
				MultimediaInfo info=encoder.getInfo(file);
				version.height=info.getVideo().getSize().getHeight();
				version.width=info.getVideo().getSize().getWidth();
				version.decoder=info.getVideo().getDecoder();
				version.duration=info.getDuration();
				version.frameRate=info.getVideo().getFrameRate();
	//			version.minImage=UploadUtils.resizeImage(image, version.path.substring(version.path.lastIndexOf("\\"),version.path.indexOf("."))+"min", 192, 108);
//				info.
			} catch (EncoderException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			version.save();
		}
		flash.success("edit.success");
		list(object.project.id,null,null);
//		result("保存成功","保存成功!","/scenes/blank",true);
	}
	
	public static void delete(Long id,Long[] arrayid,Long projectid) {
		if(arrayid!=null && arrayid.length > 0){
			for(long _id : arrayid){
				Scene scene = Scene.findById(_id);
				if(scene!=null) scene.delete();
			}
		}else if(id!=null){
			Scene scene = Scene.findById(id);
			if(scene==null){ flash.error("记录不存在，删除数据失败!"); list(projectid,null,null);}
			scene.delete();
		}
		flash.success("delete.success");
		list(projectid,null,null);
	}
	
	//上传视频
	public static void plupload(Integer chunks,File file,Integer chunk,String name){
		String filepath = "/public/uploads/" + DateUtils.format(new Date(), "yyyy-MM")+"/"+DateUtils.format(new Date(), "dd");
		filepath = filepath + "/";
		File to = new File(Play.applicationPath,filepath);
		if(!to.exists()){
			to.mkdirs();
		}
		to = new File(to,name);
		try {
			Filscopy.saveUploadFile(file,to);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		file.delete();
//		if(chunks==chunk+1){
//			Encoder encoder = new Encoder();
//			try {
//				encoder.getImage(to, new File(Play.applicationPath,filepath+name.substring(0,name.indexOf("."))+".jpg"),0);
//				MultimediaInfo info=encoder.getInfo(to);
////				info.
//			} catch (EncoderException e) {
//				e.printStackTrace();
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			}
//		}
		renderJSON(filepath+name);
	}
}