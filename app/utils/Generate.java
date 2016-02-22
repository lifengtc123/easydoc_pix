package utils;

import groovy.lang.Writable;
import groovy.text.SimpleTemplateEngine;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


//import models.Datebasecum;
//import models.Datebasetable;
//import models.Former;
//import models.Lmnews;
//import models.Mould;
//import models.Stencil;

import org.apache.commons.lang.StringUtils;
import org.codehaus.groovy.control.CompilationFailedException;

import play.Logger;
import play.Play;
import play.libs.IO;
//import utils.Zipper.FileEntry;

//import develop.generate.*;

public class Generate {

	private Map<String,String> map_param = new LinkedHashMap<String,String>();
	private Map<String,Object> args = new HashMap<String,Object>();
	private boolean result = false;
	private List<String> message = new ArrayList<String>();
//	private Mould mould;
//	private Datebasetable datebasetable;
//	private List<Datebasecum> datebasecums;
//	private List<FileEntry> fileList = new ArrayList<FileEntry>();
	private String model;
	private String models;
//	private Former former;
	
//	public Generate2(Datebasetable datebasetable,List<Datebasecum> datebasecums,Former former) {
//		this.datebasetable=datebasetable;
//		this.datebasecums=datebasecums;
//		this.model=datebasetable.name.replaceFirst(datebasetable.name.substring(0, 1),datebasetable.name.substring(0, 1).toUpperCase());
//		this.models=this.model+"s";
//		this.former=former;
//	}
	

	//属性替换
	private String render(String content){
		try{
			SimpleTemplateEngine engine = new SimpleTemplateEngine();
			groovy.text.Template _template = engine.createTemplate(content);
			Writable result = _template.make(map_param);
			return result.toString();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	private String replace_map(String str,Map<String,String> map_param){
		for(Iterator<String> it  = map_param.keySet().iterator();it.hasNext();){
			String key = it.next();
			str = StringUtils.replace(str,key, map_param.get(key));
		}
		return str;
	}
	
	
	public Boolean get_result(){
		return result;
	}
	
	
	//栏目替换
	//属性添加
	private String th(Map<String,Object> args,String template) throws IOException, CompilationFailedException, ClassNotFoundException{
		SimpleTemplateEngine engine = new SimpleTemplateEngine();
		File file=new File(Play.applicationPath,template);
		String content="";
		String line= "";   
        if(file.exists()){         
            InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "UTF-8");   
            BufferedReader bufferedreader = new BufferedReader(isr);   
            while((line = bufferedreader.readLine())!=null){   
                    //line = new String(line.getBytes(), "UTF-8");   
                    content+=line+"\n";
                }   
        } 
			String output = engine.createTemplate(content).make(args).toString();
			return output;
	}
	
//	public Boolean sc(String username){
//		args.put("mould", mould);
//		String[] template=mould.template.split(",");         //获取模板
//		for(String tt:template){
//			try {
//				String cp="";
//				cp = th(args,tt);
//				String zz=render(cp);
//				String tr=tt.replace("/public/mould", "");
//				String filepath="/public/"+username+"/app"+tr.substring(0,tr.lastIndexOf("/"));     //设置保存路径
//				File file=new File(Play.applicationPath,filepath);
//				if(!file.exists()){         //判断路径是否有 没有就创建
//					file.mkdirs();
//				}
//				BufferedWriter buf;
//				buf = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
//				buf.write(zz);             //写入保存
//				buf.flush();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				return false;
//			}
//		}
//		return true;
//	}
	
//	public static Boolean run(Mould mould,Map<String,String> params,String lmnewid,String username) {
//		Generate generate=new Generate(mould,params,lmnewid);
//		Boolean turnoff=generate.sc(username);
//		return turnoff;
//	}
	
//	private void create_file(String filename,String filetype,String template){
//		try {
//			//Logger.info("filename:%s filetype:%s", filename,filetype);
//			File root = new File(Play.applicationPath,"/public/shengcheng");
//			File file = null;
//			if(filetype.equals("controller")){ 
//				file = new File(root,"/app/controllers/"+filename);
//				fileList.add(new FileEntry("app/controllers",file));
//			}
//			if(filetype.equals("entity")){ 
//				file = new File(root,"/app/models/"+filename);
//				fileList.add(new FileEntry("app/models",file));
//			}
//			if(filetype.equals("page")){ 
//				file = new File(root,"/app/views/"+models);
//				if(!file.exists()) file.mkdirs();
//				file = new File(file,filename);
//				fileList.add(new FileEntry("app/views/"+models,file));
//			}
//			if(!file.exists()) file.createNewFile();
//			IO.write(template.getBytes("utf-8"), file);
//			//Logger.info("filepath:%s", file.getAbsoluteFile());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	
	private void zipper(){
		try {
			String filename = model + "_" + System.currentTimeMillis() + ".rar";
			File dir = new File(Play.applicationPath,"/public/zippers/"+model);
			if(!dir.exists()) dir.mkdirs();
			OutputStream out = new FileOutputStream(new File(dir,filename));
//			Zipper.zip(out, fileList);
	//		new models.Zipper("/public/zippers/"+project.folder+"/"+filename,entityObject).save();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

//	public void buildTable() {
//		args.put("model", model);
//		args.put("title", "");
//		args.put("models", models);
//		args.put("datebasetable", datebasetable);
//		args.put("datebasecums", datebasecums);
//		for(Stencil stencil:former.stencil){
//			SimpleTemplateEngine engine = new SimpleTemplateEngine();
//			File file=new File(Play.applicationPath,stencil.path);
//			String content="";
//			String line= "";   
//			try {
//		        if(file.exists()){    
//		            InputStreamReader isr;
//					isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
//		            BufferedReader bufferedreader = new BufferedReader(isr);   
//		            while((line = bufferedreader.readLine())!=null){   
//		                    //line = new String(line.getBytes(), "UTF-8");   
//		                    content+=line+"\n";
//		                }   
//		        }
//				String output = engine.createTemplate(content).make(args).toString();
//				String outpath=stencil.name.replace("model", model);
//				create_file(outpath,stencil.type,output);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		zipper();
//	}
}
