package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Menu;
import models.Role;
import models.RoleControl;
import models.User;
import play.data.validation.Valid;
import utils.PagedList;

public class Roles extends Application{

//	public static void list(String orderBy,String order) {
//		PagedList<Role> pagedList = new PagedList<Role>();
//		Role.findPage(pagedList,params.get("search"), params.get("searchField"), orderBy,order, params.get("condition"),null);
//		render(pagedList,orderBy,order);
//	}
	public static void list(){
		PagedList<Role> pagedList= new PagedList<Role>();
		Role.findPage(pagedList, null, null, null, null, null, null);
		render(pagedList);
	}
	
	public static void rolelist(Integer sEcho,String sSearch,String sColumns,String sSortDir_0,Integer iSortCol_0,Integer iDisplayLength,Integer iDisplayStart){
		String sql="1=1 ";
		List<Object> paramArray = new ArrayList<Object>();
		String[] Columns=sColumns.split(",");
		if(sSearch!=null){
			sql+="and name like ? ";
			sSearch = "%" + sSearch + "%";
			paramArray.add(sSearch);
		}
		sql+="order by "+Columns[iSortCol_0]+ " "+sSortDir_0;
		List<Role> role=Role.find(sql,paramArray.toArray()).fetch(iDisplayStart/iDisplayLength, iDisplayLength);
		Long count=Role.count(sql,paramArray.toArray());
		Object[][] roles=new Object[role.size()][Columns.length];
		int i=0;
		for(Role rr:role){
			for(int j=0;j<Columns.length-1;j++){
				if(!Columns[j].equals("option")){
				try {
					roles[i][j]=rr.getClass().getField(Columns[j]).get(rr);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchFieldException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			}
			i++;
		}
		Map<String,Object> result=new HashMap<String,Object>();
		result.put("sEcho", sEcho);
		result.put("iTotalRecords", count);
		result.put("iTotalDisplayRecords",count);
		result.put("aaData", roles);
		result.put("sColumns", sColumns);
		renderJSON(result);
	}
	
	public static void blank() {
		List<Menu> menu_p = Menu.find("pid = '0' and flag = 1 order by sort").fetch();
		List<Menu> menu_c = Menu.find("pid <> '0' and flag = 1 order by sort").fetch();
		List<RoleControl> role_c = RoleControl.find("order by sort asc").fetch();
		Role object = new Role();
		render(object,menu_p,menu_c,role_c);
	}
	
	public static void create(String[] menus,String[] ipads) {
		String value=params.get("value");
		Role object = new Role();
		validation.valid(object.edit("object",params.all()));
		if(validation.hasErrors()){
			List<Menu> menu_p = Menu.find("pid = '0' and flag = 1 order by sort").fetch();
			List<Menu> menu_c = Menu.find("pid <> '0' and flag = 1 order by sort").fetch();
			List<RoleControl> role_c = RoleControl.find("order by sort asc").fetch();
			render("@blank",menu_p,menu_c,object,role_c);
		}
		String menu = "";
		if(menus!=null&&menus.length>0){
			for(String str : menus){
				if(menu.length()>0){
					menu = menu + ",";
				}
				menu = menu + str;
			}
		}
		String ipad = "";
		if(ipads!=null&&ipads.length>0){
			for(String str : ipads){
					ipad += str + ",";
			}
		}
		object.ipad=ipad;
		object.value=value;
		object.menu = menu;
		object.save();
		list();
	}
	
	public static void show(long id) {
		Role object = Role.findById(id);
		notFoundIfNull(object);
		List<Menu> menu_p = Menu.find("pid = '0' and flag = 1 order by sort").fetch();
		List<Menu> menu_c = Menu.find("pid <> '0' and flag = 1 order by sort").fetch();
		List<RoleControl> role_c = RoleControl.find("order by sort asc").fetch();
		RoleControl role=new RoleControl();
		String rolevalue=object.value;
		render(object,menu_p,menu_c,role_c,role,rolevalue);
	}
	
//	public static void detail1(long id) {
//		Role object = Role.findById(id);
//		notFoundIfNull(object);
//		List<Menu> menu_p = Menu.find("pid = '0' and flag = 1 order by sort").fetch();
//		List<Menu> menu_c = Menu.find("pid <> '0' and flag = 1 order by sort").fetch();
//		List<RoleControl> role_c = RoleControl.find("order by sort asc").fetch();
//		RoleControl role=new RoleControl();
//		String rolevalue=object.value;
//		render(object,menu_p,menu_c,role_c,role,rolevalue);
//	}
	
	public static void detail(long id) {
		Role object = Role.findById(id);
		notFoundIfNull(object);
		List<Menu> menu_p = Menu.find("pid = '0' and flag = 1 order by sort").fetch();
		List<Menu> menu_c = Menu.find("pid <> '0' and flag = 1 order by sort").fetch();
		List<RoleControl> role_c = RoleControl.find("order by sort asc").fetch();
		RoleControl role=new RoleControl();
		String rolevalue=object.value;
		render(object,menu_p,menu_c,role_c,role,rolevalue);
	}
	
//	public static void show1(long id) {
//		Role object = Role.findById(id);
//		notFoundIfNull(object);
//		List<Menu> menu_p = Menu.find("pid = '0' and flag = 1 order by sort").fetch();
//		List<Menu> menu_c = Menu.find("pid <> '0' and flag = 1 order by sort").fetch();
//		List<RoleControl> role_c = RoleControl.find("order by sort asc").fetch();
//		RoleControl role=new RoleControl();
//		String rolevalue=object.value;
//		render(object,menu_p,menu_c,role_c,role,rolevalue);
//	}
	
	public static void save(Long id ,String[] menus,String[] ipads) {
		String value=params.get("value");
		Role object = Role.findById(id);
		notFoundIfNull(object);
		validation.valid(object.edit("object",params.all()));
		if(validation.hasErrors()){
			List<Menu> menu_p = Menu.find("pid = '0' and flag = 1 order by sort").fetch();
			List<Menu> menu_c = Menu.find("pid <> '0' and flag = 1 order by sort").fetch();
			render("@blank",menu_p,menu_c,object);
		}
		String menu = "";
		if(menus!=null&&menus.length>0){
			for(String str : menus){
				if(menu.length()>0){
					menu = menu + ",";
				}
				menu = menu + str;
			}
		}
		String ipad = "";
		if(ipads!=null&&ipads.length>0){
			for(String str : ipads){
					ipad += str + ",";
			}
		}
		object.ipad=ipad;
		object.value=value;
		object.menu = menu;
		object.save();
		list();
	}
	
	public static void delete(Long id,Long[] arrayid) {
		if(arrayid!=null && arrayid.length > 0){
			for(long _id : arrayid){
				Role role = Role.findById(_id);
				if(role!=null) role.delete();
			}
		}else if(id!=null){
			Role role = Role.findById(id);
			if(role==null){ renderJSON(false);}
			role.delete();
		}
		renderJSON(true);
	}
}