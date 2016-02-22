package controllers;

import play.*;
import play.i18n.Lang;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends Controller {
	
	//访问验证(判断当前用户有无session)
	@Before
	static void checkAccess() {
		if (!session.contains("username")) {
			Secure.login();
		}
	}
	
	@Before
	public static void checkRole(){
		if(!"/".equals(request.url)){
			String controller=request.controller;
			String action=request.actionMethod;
			String value= connect().role.value;
			renderArgs.put("header", new RoleControl());
			if(value!=null){
				int userPurview = 0;
				String[] roles = value.split("\\|");
				Map<String,Integer> map = new HashMap<String,Integer>();
				for(String _role : roles){
					if(_role.length()==1){
						break;
					}
					String[] purview =_role.split(":");
					
					if(purview[0].equalsIgnoreCase(controller)){
						userPurview = Integer.parseInt(purview[1]);
						break;
					}
				}
				List<RoleControl> list=RoleControl.find("controller = ?",controller).fetch();
				//if(list.isEmpty()) renderArgs.put("hasRole", true);
				//System.out.println("-----------------userPurview:"+userPurview);
				for (RoleControl role : list) {
					if(action.equalsIgnoreCase(role.action)){
						role.parentmenu=Menu.find("cid=?", role.menu.pid).first();
						renderArgs.put("header", role);
					}
					if(RoleControl.checkPower(userPurview,role.value)){
				//		System.out.println("key you:"+ controller +" "+role.action);
						map.put(role.action, 1);
					}else{
						if(action.equalsIgnoreCase(role.action))//忽略大小写
							result();
						//System.out.println("key mei:"+ controller +" "+role.action);
						map.put(role.action, 0);
					}
				}
				renderArgs.put("roleMap", map);
				RoleControl role2=RoleControl.find("controller=? and action=?", controller,action).first();
			}
		}
	}

    public static void index() {
    	User user=connect();
    	if(user.role.id==1l){
    		Projects.list2(null, null);
    	}else if(user.role.id==2l){
    		Projects.list(null, null);
    	}else{
    		Projects.list(null, null);
    	}
    }
    
    public static void result() {
        render();
    }
    
    
    
	protected static User connect() {
		return User.findById(Long.parseLong(session.get("userid")));
	}
	
	public static void menu(){
		//request.
	}
	
	//语言切换
	public static void loug(String laug){
		Lang.change(laug);
		renderJSON(true);
	}

}