package utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import play.db.DB;

public class typeSearch {
	
	//出库期初数量
	@SuppressWarnings("deprecation")
	public static Double getkcShuliang(Long materialID,Long id,String cid) {
		Double total=0.00;
		String sql = null;
		if("1".equals(cid)) {
			sql = "select sum(totalAmount) from initialinventory where material_id = '"+materialID+"'";
		}else{
			sql = "SELECT SUM(totalAmount) FROM initialinventory where cid like '"+cid+"%'";
			if(id != null && !"".equals(id)){
				sql += " and material_id = '"+id+"'";
			}
		}
//		System.out.println("库存期初++++++++++："+sql);
		try {
			ResultSet rs;
			rs = DB.executeQuery(sql);
			if(rs.next()){
				total = rs.getDouble(1);
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace(System.out);
		}
		//total = NumberUtils.formateDouble(total, 2);
		return total; 
	}
	
	//出库期初(不含税金额)
	@SuppressWarnings("deprecation")
	public static Double getInitialInventoryB(Long materialID,Long id,String cid) {
		Double total=0.00;
		String sql = null;
		if("1".equals(cid)) {
			sql = "select sum(totalMoney) from initialinventory where material_id = '"+materialID+"'";
		}else {
			sql = "SELECT SUM(totalMoney) FROM initialinventory where cid like '"+cid+"%'";
			if(id != null && !("").equals(id)){
				sql += " and material_id = '"+id+"'";
			}
		}
//		System.out.println("库存期初++++++++++："+sql);
		try {
			ResultSet rs;
			rs = DB.executeQuery(sql);
			if(rs.next()){
				total = rs.getDouble(1);
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace(System.out);
		}
		//total = NumberUtils.formateDouble(total, 2);
		return total; 
	}
	
	//出库期初(含税金额)
	@SuppressWarnings("deprecation")
	public static Double getInitialInventoryH(Long materialID,Long id,String cid) {
		Double total=0.00;
		String sql = null;
		if("1".equals(cid)) {
			sql = "select sum(totalMoney_tax) from initialinventory where material_id = '"+materialID+"'";
		}else {
			sql = "SELECT SUM(totalMoney_tax) FROM initialinventory where cid like '"+cid+"%'";
			if(id != null && !"".equals(id)){
				sql += " and material_id = '"+id+"'";
			}
		}
//		System.out.println("库存期初++++++++++："+sql);
		try {
			ResultSet rs;
			rs = DB.executeQuery(sql);
			if(rs.next()){
				total = rs.getDouble(1);
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace(System.out);
		}
		//total = NumberUtils.formateDouble(total, 2);
		return total; 
	}
	
	//期初数量
	@SuppressWarnings("deprecation")
	public static Double getqichuShuliang(String year,String month,String startTime,String endTime,Long materialID,Long id,String cid) {
//		System.out.println("期初：" + "year:"+year+":month:"+month+":startTime:"+startTime+":endTime:"+endTime);
//		System.out.println("期初：" + "cid:"+cid+":prodName:"+prodName+":specifications:"+specifications+":unit:"+unit);
		Double total = null;
		Double total1 = null;
		try {
			//入库查询
			String sql = "";
			//出库查询
			String sql1 = "";
				if("1".equals(cid)) {
					sql="select SUM(amount) from forminfo where confirmDate is not null and intoForm_id is not null and material_id = '"+materialID+"'";
					sql1="select SUM(amount) from forminfo where confirmDate is not null and outForm_id is not null and material_id = '"+materialID+"'";
					if(startTime!=null && !("").equals(startTime)){
						startTime = startTime+" 00:00:000";
						sql+=" and createDate < '"+startTime+"'";
						sql1+=" and createDate < '"+startTime+"'";
					}
					if(startTime==null || ("").equals(startTime)){
						sql+=" and createDate < '"+year+"-"+month+"-00'";
						sql1+=" and createDate < '"+year+"-"+month+"-00'";
					}
				}else{
					sql="select SUM(i.amount) from forminfo as i left join material as m on i.material_id = m.id left join materialstype as mt on mt.id = m.materialstype_id where i.confirmDate is not null and i.intoForm_id is not null and mt.cid like '"+cid+"%'";
					sql1="select SUM(i.amount) from forminfo as i left join material as m on i.material_id = m.id left join materialstype as mt on mt.id = m.materialstype_id where i.confirmDate is not null and i.outForm_id is not null and mt.cid like '"+cid+"%'";
					if(id != null && !"".equals(id)){
						sql += " and i.material_id = '"+id+"'";
						sql1 += " and i.material_id = '"+id+"'";
					}
					if(startTime!=null && !("").equals(startTime)){
						startTime = startTime+" 00:00:000";
						sql+=" and i.createDate < '"+startTime+"'";
						sql1+=" and i.createDate < '"+startTime+"'";
					}
					if(startTime==null || ("").equals(startTime)){
						sql+=" and i.createDate < '"+year+"-"+month+"-00'";
						sql1+=" and i.createDate < '"+year+"-"+month+"-00'";
					}
				}
			
//			System.out.println("期初入库+++++++++："+sql);
//			System.out.println("期初出库+++++++++："+sql1);
			ResultSet rs;
			rs = DB.executeQuery(sql);
			if(rs.next()){
				total = rs.getDouble(1);
			}
			ResultSet rs1;
			rs1 = DB.executeQuery(sql1);
			if(rs1.next()){
				total1 = rs1.getDouble(1);
			}
//			System.out.println("入库发生:"+total+":出库发生:"+total1);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace(System.out);
		}
		return total-total1;
	}
	
	//期初(不含税金额)
	@SuppressWarnings("deprecation")
	public static Double getBeginningB(String year,String month,String startTime,String endTime,Long materialID,Long id,String cid) {
//		System.out.println("期初：" + "year:"+year+":month:"+month+":startTime:"+startTime+":endTime:"+endTime);
//		System.out.println("期初：" + "cid:"+cid+":prodName:"+prodName+":specifications:"+specifications+":unit:"+unit);
		Double total = null;
		Double total1 = null;
		try {
			//入库查询
			String sql = "";
			//出库查询
			String sql1 = "";
			if("1".equals(cid)) {
				sql="select sum(amount*price) from forminfo where confirmDate is not null and intoForm_id is not null and material_id = '"+materialID+"'";
				sql1="select sum(amount*price) from forminfo where confirmDate is not null and outForm_id is not null and material_id = '"+materialID+"'";
				if(startTime!=null && !("").equals(startTime)){
					startTime = startTime+" 00:00:000";
					sql+=" and createDate < '"+startTime+"'";
					sql1+=" and createDate < '"+startTime+"'";
				}
				if(startTime==null || ("").equals(startTime)){
					sql+=" and createDate < '"+year+"-"+month+"-00'";
					sql1+=" and createDate < '"+year+"-"+month+"-00'";
				}
			} else{
				sql="select SUM(i.amount*i.price) from forminfo as i left join material as m on i.material_id = m.id left join materialstype as mt on mt.id = m.materialstype_id where i.confirmDate is not null and i.intoForm_id is not null and mt.cid like '"+cid+"%'";
				sql1="select SUM(i.amount*i.price) from forminfo as i left join material as m on i.material_id = m.id left join materialstype as mt on mt.id = m.materialstype_id where i.confirmDate is not null and i.outForm_id is not null and mt.cid like '"+cid+"%'";
				if(id != null && !"".equals(id)){
					sql += " and i.material_id = '"+id+"'";
					sql1 += " and i.material_id = '"+id+"'";
				}
				if(startTime!=null && !("").equals(startTime)){
					startTime = startTime+" 00:00:000";
					sql+=" and i.createDate < '"+startTime+"'";
					sql1+=" and i.createDate < '"+startTime+"'";
				}
				if(startTime==null || ("").equals(startTime)){
					sql+=" and i.createDate < '"+year+"-"+month+"-00'";
					sql1+=" and i.createDate < '"+year+"-"+month+"-00'";
				}
			}
			
//			System.out.println("期初入库+++++++++："+sql);
//			System.out.println("期初出库+++++++++："+sql1);
			
			ResultSet rs;
			rs = DB.executeQuery(sql);
			if(rs.next()){
				total = rs.getDouble(1);
			}
			ResultSet rs1;
			rs1 = DB.executeQuery(sql1);
			if(rs1.next()){
				total1 = rs1.getDouble(1);
			}
//			System.out.println("入库发生:"+total+":出库发生:"+total1);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace(System.out);
		}
		return total-total1;
	}
	
	//期初(含税金额)
	@SuppressWarnings("deprecation")
	public static Double getBeginningH(String year,String month,String startTime,String endTime,Long materialID,Long id,String cid) {
//		System.out.println("期初：" + "year:"+year+":month:"+month+":startTime:"+startTime+":endTime:"+endTime);
//		System.out.println("期初：" + "cid:"+cid+":prodName:"+prodName+":specifications:"+specifications+":unit:"+unit);
		Double total = null;
		Double total1 = null;
		try {
			//入库查询
			String sql = "";
			//出库查询
			String sql1 = "";
			if("1".equals(cid)) {
				sql="select sum(amount*price) from forminfo where confirmDate is not null and intoForm_id is not null and material_id = '"+materialID+"'";
				sql1="select sum(amount*price) from forminfo where confirmDate is not null and outForm_id is not null and material_id = '"+materialID+"'";
				if(startTime!=null && !("").equals(startTime)){
					startTime = startTime+" 00:00:000";
					sql+=" and createDate < '"+startTime+"'";
					sql1+=" and createDate < '"+startTime+"'";
				}
				if(startTime==null || ("").equals(startTime)){
					sql+=" and createDate < '"+year+"-"+month+"-00'";
					sql1+=" and createDate < '"+year+"-"+month+"-00'";
				}
			} else{
				sql="select SUM(i.amount*i.price_tax) from forminfo as i left join material as m on i.material_id = m.id left join materialstype as mt on mt.id = m.materialstype_id where i.confirmDate is not null and i.intoForm_id is not null and mt.cid like '"+cid+"%'";
				sql1="select SUM(i.amount*i.price_tax) from forminfo as i left join material as m on i.material_id = m.id left join materialstype as mt on mt.id = m.materialstype_id where i.confirmDate is not null and i.outForm_id is not null and mt.cid like '"+cid+"%'";
				if(id != null && !"".equals(id)){
					sql += " and i.material_id = '"+id+"'";
					sql1 += " and i.material_id = '"+id+"'";
				}
				if(startTime!=null && !("").equals(startTime)){
					startTime = startTime+" 00:00:000";
					sql+=" and i.createDate < '"+startTime+"'";
					sql1+=" and i.createDate < '"+startTime+"'";
				}
				if(startTime==null || ("").equals(startTime)){
					sql+=" and i.createDate < '"+year+"-"+month+"-00'";
					sql1+=" and i.createDate < '"+year+"-"+month+"-00'";
				}
			}
			
//			System.out.println("期初入库+++++++++："+sql);
//			System.out.println("期初出库+++++++++："+sql1);
			
			ResultSet rs;
			rs = DB.executeQuery(sql);
			if(rs.next()){
				total = rs.getDouble(1);
			}
			ResultSet rs1;
			rs1 = DB.executeQuery(sql1);
			if(rs1.next()){
				total1 = rs1.getDouble(1);
			}
//			System.out.println("入库发生:"+total+":出库发生:"+total1);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace(System.out);
		}
		return total-total1;
	}
	
	//入库发生数量
	@SuppressWarnings("deprecation")
	public static Double getInputS(String year,String month,String startTime,String endTime,Long materialID,Long id,String cid) {
		Double total = 0.00;
		try {
			//本月入库查询
			String sql = "";
			if("1".equals(cid)) {
				sql="select sum(amount) from forminfo where confirmDate is not null and intoForm_id is not null and material_id = '"+materialID+"'";
				if(startTime!=null && !("").equals(startTime)){
					startTime = startTime+" 00:00:000";
					sql+=" and createDate >= '"+startTime+"'";
				}
				if(endTime!=null && !("").equals(endTime)){
					endTime=endTime+" 24:00:000";
					sql+=" and createDate <= '"+endTime+"'";
				}
				if(startTime==null || ("").equals(startTime) && (endTime==null || ("").equals(endTime))){
					sql+=" and createDate >= '"+year+"-"+month+"-00'" + " and createDate <= '"+year+"-"+month+"-31'";
				}
			} else{
				sql="select SUM(i.amount) from forminfo as i left join material as m on i.material_id = m.id left join materialstype as mt on mt.id = m.materialstype_id where i.confirmDate is not null and i.intoForm_id is not null and mt.cid like '"+cid+"%'";
				if(id != null && !"".equals(id)){
					sql += " and i.material_id = '"+id+"'";
				}
				if(startTime!=null && !("").equals(startTime)){
					startTime = startTime+" 00:00:000";
					sql+=" and i.createDate >= '"+startTime+"'";
				}
				if(endTime!=null && !("").equals(endTime)){
					endTime=endTime+" 24:00:000";
					sql+=" and i.createDate <= '"+endTime+"'";
				}
				if(startTime==null || ("").equals(startTime) && (endTime==null || ("").equals(endTime))){
					sql+=" and i.createDate >= '"+year+"-"+month+"-00'" + " and i.createDate <= '"+year+"-"+month+"-31'";
				}
			}
			
			ResultSet rs;
			rs = DB.executeQuery(sql);
			if(rs.next()){
				total = total+rs.getDouble(1);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace(System.out);
		}
		return total;
	}

	//入库发生(不含税金额)
	@SuppressWarnings("deprecation")
	public static Double getInputB(String year,String month,String startTime,String endTime,Long materialID,Long id,String cid) {
		Double total = 0.00;
		try {
			//本月入库查询
			String sql = "";
			if("1".equals(cid)) {
				sql="select sum(amount*price) from forminfo where confirmDate is not null and intoForm_id is not null and material_id = '"+materialID+"'";
				if(startTime!=null && !("").equals(startTime)){
					startTime = startTime+" 00:00:000";
					sql+=" and createDate >= '"+startTime+"'";
				}
				if(endTime!=null && !("").equals(endTime)){
					endTime=endTime+" 24:00:000";
					sql+=" and createDate <= '"+endTime+"'";
				}
				if(startTime==null || ("").equals(startTime) && (endTime==null || ("").equals(endTime))){
					sql+=" and createDate >= '"+year+"-"+month+"-00'" + " and createDate <= '"+year+"-"+month+"-31'";
				}
			} else{
				sql="select SUM(i.amount*i.price) from forminfo as i left join material as m on i.material_id = m.id left join materialstype as mt on mt.id = m.materialstype_id where i.confirmDate is not null and i.intoForm_id is not null and mt.cid like '"+cid+"%'";
				if(id != null && !"".equals(id)){
					sql += " and i.material_id = '"+id+"'";
				}
				if(startTime!=null && !("").equals(startTime)){
					startTime = startTime+" 00:00:000";
					sql+=" and i.createDate >= '"+startTime+"'";
				}
				if(endTime!=null && !("").equals(endTime)){
					endTime=endTime+" 24:00:000";
					sql+=" and i.createDate <= '"+endTime+"'";
				}
				if(startTime==null || ("").equals(startTime) && (endTime==null || ("").equals(endTime))){
					sql+=" and i.createDate >= '"+year+"-"+month+"-00'" + " and i.createDate <= '"+year+"-"+month+"-31'";
				}
			}
			ResultSet rs;
			rs = DB.executeQuery(sql);
			if(rs.next()){
				total = total+rs.getDouble(1);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace(System.out);
		}
		return total;
	}
	
	//入库发生(含税金额)
	@SuppressWarnings("deprecation")
	public static Double getInputH(String year,String month,String startTime,String endTime,Long materialID,Long id,String cid) {
		Double total = 0.00;
		try {
			//本月入库查询
			String sql = "";
			if("1".equals(cid)) {
				sql="select sum(amount*price_tax) from forminfo where confirmDate is not null and intoForm_id is not null and material_id = '"+materialID+"'";
				if(startTime!=null && !("").equals(startTime)){
					startTime = startTime+" 00:00:000";
					sql+=" and createDate >= '"+startTime+"'";
				}
				if(endTime!=null && !("").equals(endTime)){
					endTime=endTime+" 24:00:000";
					sql+=" and createDate <= '"+endTime+"'";
				}
				if(startTime==null || ("").equals(startTime) && (endTime==null || ("").equals(endTime))){
					sql+=" and createDate >= '"+year+"-"+month+"-00'" + " and createDate <= '"+year+"-"+month+"-31'";
				}
			} else{
				sql="select SUM(i.amount*i.price_tax) from forminfo as i left join material as m on i.material_id = m.id left join materialstype as mt on mt.id = m.materialstype_id where i.confirmDate is not null and i.intoForm_id is not null and mt.cid like '"+cid+"%'";
				if(id != null && !"".equals(id)){
					sql += " and i.material_id = '"+id+"'";
				}
				if(startTime!=null && !("").equals(startTime)){
					startTime = startTime+" 00:00:000";
					sql+=" and i.createDate >= '"+startTime+"'";
				}
				if(endTime!=null && !("").equals(endTime)){
					endTime=endTime+" 24:00:000";
					sql+=" and i.createDate <= '"+endTime+"'";
				}
				if(startTime==null || ("").equals(startTime) && (endTime==null || ("").equals(endTime))){
					sql+=" and i.createDate >= '"+year+"-"+month+"-00'" + " and i.createDate <= '"+year+"-"+month+"-31'";
				}
			}
			ResultSet rs;
			rs = DB.executeQuery(sql);
			if(rs.next()){
				total = total+rs.getDouble(1);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace(System.out);
		}
		return total;
	}
	
	//出库发生数量
	@SuppressWarnings("deprecation")
	public static Double getOutputS(String year,String month,String startTime,String endTime,Long materialID,Long id,String cid) {
		Double total = 0.00;
		try {
			//本月出库查询
			String sql = "";
			if("1".equals(cid)) {
				sql="select sum(amount) from forminfo where confirmDate is not null and outForm_id is not null and material_id = '"+materialID+"'";
				if(startTime!=null && !("").equals(startTime)){
					startTime = startTime+" 00:00:000";
					sql+=" and createDate >= '"+startTime+"'";
				}
				if(endTime!=null && !("").equals(endTime)){
					endTime=endTime+" 24:00:000";
					sql+=" and createDate <= '"+endTime+"'";
				}
				if(startTime==null || ("").equals(startTime) && (endTime==null || ("").equals(endTime))){
					sql+=" and createDate >= '"+year+"-"+month+"-00'" + " and createDate <= '"+year+"-"+month+"-31'";
				}
			} else{
				sql="select SUM(i.amount) from forminfo as i left join material as m on i.material_id = m.id left join materialstype as mt on mt.id = m.materialstype_id where i.confirmDate is not null and i.outForm_id is not null and mt.cid like '"+cid+"%'";
				if(id != null && !"".equals(id)){
					sql += " and i.material_id = '"+id+"'";
				}
				if(startTime!=null && !("").equals(startTime)){
					startTime = startTime+" 00:00:000";
					sql+=" and i.createDate >= '"+startTime+"'";
				}
				if(endTime!=null && !("").equals(endTime)){
					endTime=endTime+" 24:00:000";
					sql+=" and i.createDate <= '"+endTime+"'";
				}
				if(startTime==null || ("").equals(startTime) && (endTime==null || ("").equals(endTime))){
					sql+=" and i.createDate >= '"+year+"-"+month+"-00'" + " and i.createDate <= '"+year+"-"+month+"-31'";
				}
			}
			
			ResultSet rs;
			rs = DB.executeQuery(sql);
			if(rs.next()){
				total = total+rs.getDouble(1);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace(System.out);
		}
		return total;
	}
	
	//出库发生(不含税金额)
	@SuppressWarnings("deprecation")
	public static Double getOutputB(String year,String month,String startTime,String endTime,Long materialID,Long id,String cid) {
		Double total = 0.00;
		try {
			//本月出库查询
			String sql = "";
			if("1".equals(cid)) {
				sql="select sum(amount*price) from forminfo where confirmDate is not null and outForm_id is not null and material_id = '"+materialID+"'";
				if(startTime!=null && !("").equals(startTime)){
					startTime = startTime+" 00:00:000";
					sql+=" and createDate >= '"+startTime+"'";
				}
				if(endTime!=null && !("").equals(endTime)){
					endTime=endTime+" 24:00:000";
					sql+=" and createDate <= '"+endTime+"'";
				}
				if(startTime==null || ("").equals(startTime) && (endTime==null || ("").equals(endTime))){
					sql+=" and createDate >= '"+year+"-"+month+"-00'" + " and createDate <= '"+year+"-"+month+"-31'";
				}
			} else{
				sql="select SUM(i.amount*i.price) from forminfo as i left join material as m on i.material_id = m.id left join materialstype as mt on mt.id = m.materialstype_id where i.confirmDate is not null and i.outForm_id is not null and mt.cid like '"+cid+"%'";
				if(id != null && !"".equals(id)){
					sql += " and i.material_id = '"+id+"'";
				}
				if(startTime!=null && !("").equals(startTime)){
					startTime = startTime+" 00:00:000";
					sql+=" and i.createDate >= '"+startTime+"'";
				}
				if(endTime!=null && !("").equals(endTime)){
					endTime=endTime+" 24:00:000";
					sql+=" and i.createDate <= '"+endTime+"'";
				}
				if(startTime==null || ("").equals(startTime) && (endTime==null || ("").equals(endTime))){
					sql+=" and i.createDate >= '"+year+"-"+month+"-00'" + " and i.createDate <= '"+year+"-"+month+"-31'";
				}
			}
			
			ResultSet rs;
			rs = DB.executeQuery(sql);
			if(rs.next()){
				total = total+rs.getDouble(1);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace(System.out);
		}
		return total;
	}
	
	//出库发生(含税金额)
	@SuppressWarnings("deprecation")
	public static Double getOutputH(String year,String month,String startTime,String endTime,Long materialID,Long id,String cid) {
		Double total = 0.00;
		try {
			//本月出库查询
			String sql = "";
			if("1".equals(cid)) {
				sql="select sum(amount*price_tax) from forminfo where confirmDate is not null and outForm_id is not null and material_id = '"+materialID+"'";
				if(startTime!=null && !("").equals(startTime)){
					startTime = startTime+" 00:00:000";
					sql+=" and createDate >= '"+startTime+"'";
				}
				if(endTime!=null && !("").equals(endTime)){
					endTime=endTime+" 24:00:000";
					sql+=" and createDate <= '"+endTime+"'";
				}
				if(startTime==null || ("").equals(startTime) && (endTime==null || ("").equals(endTime))){
					sql+=" and createDate >= '"+year+"-"+month+"-00'" + " and createDate <= '"+year+"-"+month+"-31'";
				}
			} else{
				sql="select SUM(i.amount*i.price_tax) from forminfo as i left join material as m on i.material_id = m.id left join materialstype as mt on mt.id = m.materialstype_id where i.confirmDate is not null and i.outForm_id is not null and mt.cid like '"+cid+"%'";
				if(id != null && !"".equals(id)){
					sql += " and i.material_id = '"+id+"'";
				}
				if(startTime!=null && !("").equals(startTime)){
					startTime = startTime+" 00:00:000";
					sql+=" and i.createDate >= '"+startTime+"'";
				}
				if(endTime!=null && !("").equals(endTime)){
					endTime=endTime+" 24:00:000";
					sql+=" and i.createDate <= '"+endTime+"'";
				}
				if(startTime==null || ("").equals(startTime) && (endTime==null || ("").equals(endTime))){
					sql+=" and i.createDate >= '"+year+"-"+month+"-00'" + " and i.createDate <= '"+year+"-"+month+"-31'";
				}
			}
			
			ResultSet rs;
			rs = DB.executeQuery(sql);
			if(rs.next()){
				total = total+rs.getDouble(1);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace(System.out);
		}
		return total;
	}
	
	//入库本年度累计数量
	@SuppressWarnings("deprecation")
	public static Double getInputCumulativeS(String year,String month,String startTime,String endTime,Long materialID,Long id,String cid) {
		Double total = 0.00;
		try {
			//本月入库查询
			String sql = "";
			if("1".equals(cid)) {
				sql="select sum(amount) from forminfo where confirmDate is not null and intoForm_id is not null and material_id = '"+materialID+"'";
				if(endTime==null || ("").equals(endTime)){
					endTime=endTime+" 24:00:000";
					sql+=" and createDate <= '"+endTime+"'";
				}
				if(startTime==null || ("").equals(startTime) && (endTime==null || ("").equals(endTime))){
					sql+=" and createDate <= '"+year+"-"+month+"-31'";
				}
				sql += " and createDate like '"+year+"%'";
			} else{
				sql="select SUM(i.amount) from forminfo as i left join material as m on i.material_id = m.id left join materialstype as mt on mt.id = m.materialstype_id where i.confirmDate is not null and i.intoForm_id is not null and mt.cid like '"+cid+"%'";
				if(id != null && !"".equals(id)){
					sql += " and i.material_id = '"+id+"'";
				}
				if(endTime==null || ("").equals(endTime)){
					endTime=endTime+" 24:00:000";
					sql+=" and i.createDate <= '"+endTime+"'";
				}
				if(startTime==null || ("").equals(startTime) && (endTime==null || ("").equals(endTime))){
					sql+=" and i.createDate <= '"+year+"-"+month+"-31'";
				}
				sql += " and i.createDate like '"+year+"%'";
			}
			
			ResultSet rs;
			rs = DB.executeQuery(sql);
			if(rs.next()){
				total = total+rs.getDouble(1);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace(System.out);
		}
		return total;
	}
	
	//入库本年度累计(不含税金额)
	@SuppressWarnings("deprecation")
	public static Double getInputCumulativeB(String year,String month,String startTime,String endTime,Long materialID,Long id,String cid) {
		Double total = 0.00;
		try {
			//本月入库查询
			String sql = "";
			if("1".equals(cid)) {
				sql="select sum(amount*price) from forminfo where confirmDate is not null and intoForm_id is not null and material_id = '"+materialID+"'";
				if(endTime==null || ("").equals(endTime)){
					endTime=endTime+" 24:00:000";
					sql+=" and createDate <= '"+endTime+"'";
				}
				if(startTime==null || ("").equals(startTime) && (endTime==null || ("").equals(endTime))){
					sql+=" and createDate <= '"+year+"-"+month+"-31'";
				}
				sql += " and createDate like '"+year+"%'";
			} else{
				sql="select SUM(i.amount*i.price) from forminfo as i left join material as m on i.material_id = m.id left join materialstype as mt on mt.id = m.materialstype_id where i.confirmDate is not null and i.intoForm_id is not null and mt.cid like '"+cid+"%'";
				if(id != null && !"".equals(id)){
					sql += " and i.material_id = '"+id+"'";
				}
				if(endTime==null || ("").equals(endTime)){
					endTime=endTime+" 24:00:000";
					sql+=" and i.createDate <= '"+endTime+"'";
				}
				if(startTime==null || ("").equals(startTime) && (endTime==null || ("").equals(endTime))){
					sql+=" and i.createDate <= '"+year+"-"+month+"-31'";
				}
				sql += " and i.createDate like '"+year+"%'";
			}
				
			ResultSet rs;
			rs = DB.executeQuery(sql);
			if(rs.next()){
				total = total+rs.getDouble(1);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace(System.out);
		}
		return total;
	}
	
	//入库本年度累计(含税金额)
	@SuppressWarnings("deprecation")
	public static Double getInputCumulativeH(String year,String month,String startTime,String endTime,Long materialID,Long id,String cid) {
		Double total = 0.00;
		try {
			//本月入库查询
			String sql = "";
			if("1".equals(cid)) {
				sql="select sum(amount*price_tax) from forminfo where confirmDate is not null and intoForm_id is not null and material_id = '"+materialID+"'";
				if(endTime==null || ("").equals(endTime)){
					endTime=endTime+" 24:00:000";
					sql+=" and createDate <= '"+endTime+"'";
				}
				if(startTime==null || ("").equals(startTime) && (endTime==null || ("").equals(endTime))){
					sql+=" and createDate <= '"+year+"-"+month+"-31'";
				}
				sql += " and createDate like '"+year+"%'";
			} else{
				sql="select SUM(i.amount*i.price_tax) from forminfo as i left join material as m on i.material_id = m.id left join materialstype as mt on mt.id = m.materialstype_id where i.confirmDate is not null and i.intoForm_id is not null and mt.cid like '"+cid+"%'";
				if(id != null && !"".equals(id)){
					sql += " and i.material_id = '"+id+"'";
				}
				if(endTime==null || ("").equals(endTime)){
					endTime=endTime+" 24:00:000";
					sql+=" and i.createDate <= '"+endTime+"'";
				}
				if(startTime==null || ("").equals(startTime) && (endTime==null || ("").equals(endTime))){
					sql+=" and i.createDate <= '"+year+"-"+month+"-31'";
				}
				sql += " and i.createDate like '"+year+"%'";
			}
				
			ResultSet rs;
			rs = DB.executeQuery(sql);
			if(rs.next()){
				total = total+rs.getDouble(1);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace(System.out);
		}
		return total;
	}
	
	//出库本年度累计数量
	@SuppressWarnings("deprecation")
	public static Double getOutCumulativeS(String year,String month,String startTime,String endTime,Long materialID,Long id,String cid) {
		Double total = 0.00;
		try {
			//本月入库查询
			String sql = "";
			if("1".equals(cid)) {
				sql="select sum(amount) from forminfo where confirmDate is not null and outForm_id is not null and material_id = '"+materialID+"'";
				if(endTime==null || ("").equals(endTime)){
					endTime=endTime+" 24:00:000";
					sql+=" and createDate <= '"+endTime+"'";
				}
				if(startTime==null || ("").equals(startTime) && (endTime==null || ("").equals(endTime))){
					sql+=" and createDate <= '"+year+"-"+month+"-31'";
				}
				sql += " and createDate like '"+year+"%'";
			} else{
				sql="select SUM(i.amount) from forminfo as i left join material as m on i.material_id = m.id left join materialstype as mt on mt.id = m.materialstype_id where i.confirmDate is not null and i.outForm_id is not null and mt.cid like '"+cid+"%'";
				if(id != null && !"".equals(id)){
					sql += " and i.material_id = '"+id+"'";
				}
				if(endTime==null || ("").equals(endTime)){
					endTime=endTime+" 24:00:000";
					sql+=" and i.createDate <= '"+endTime+"'";
				}
				if(startTime==null || ("").equals(startTime) && (endTime==null || ("").equals(endTime))){
					sql+=" and i.createDate <= '"+year+"-"+month+"-31'";
				}
				sql += " and i.createDate like '"+year+"%'";
			}
				
			ResultSet rs;
			rs = DB.executeQuery(sql);
			if(rs.next()){
				total = total+rs.getDouble(1);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace(System.out);
		}
		return total;
	}
	
	//出库本年度累计(不含税金额)
	@SuppressWarnings("deprecation")
	public static Double getOutCumulativeB(String year,String month,String startTime,String endTime,Long materialID,Long id,String cid) {
		Double total = 0.00;
		try {
			//本月入库查询
			String sql = "";
			if("1".equals(cid)) {
				sql="select sum(amount*price) from forminfo where confirmDate is not null and outForm_id is not null and material_id = '"+materialID+"'";
				if(endTime==null || ("").equals(endTime)){
					endTime=endTime+" 24:00:000";
					sql+=" and createDate <= '"+endTime+"'";
				}
				if(startTime==null || ("").equals(startTime) && (endTime==null || ("").equals(endTime))){
					sql+=" and createDate <= '"+year+"-"+month+"-31'";
				}
				sql += " and createDate like '"+year+"%'";
			} else{
				sql="select SUM(i.amount*i.price) from forminfo as i left join material as m on i.material_id = m.id left join materialstype as mt on mt.id = m.materialstype_id where i.confirmDate is not null and i.outForm_id is not null and mt.cid like '"+cid+"%'";
				if(id != null && !"".equals(id)){
					sql += " and i.material_id = '"+id+"'";
				}
				if(endTime==null || ("").equals(endTime)){
					endTime=endTime+" 24:00:000";
					sql+=" and i.createDate <= '"+endTime+"'";
				}
				if(startTime==null || ("").equals(startTime) && (endTime==null || ("").equals(endTime))){
					sql+=" and i.createDate <= '"+year+"-"+month+"-31'";
				}
				sql += " and i.createDate like '"+year+"%'";
			}
				
			ResultSet rs;
			rs = DB.executeQuery(sql);
			if(rs.next()){
				total = total+rs.getDouble(1);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace(System.out);
		}
		return total;
	}
	
	//出库本年度累计(含税金额)
	@SuppressWarnings("deprecation")
	public static Double getOutCumulativeH(String year,String month,String startTime,String endTime,Long materialID,Long id,String cid) {
		Double total = 0.00;
		try {
			//本月入库查询
			String sql = "";
			if("1".equals(cid)) {
				sql="select sum(amount*price_tax) from forminfo where confirmDate is not null and outForm_id is not null and material_id = '"+materialID+"'";
				if(endTime==null || ("").equals(endTime)){
					endTime=endTime+" 24:00:000";
					sql+=" and createDate <= '"+endTime+"'";
				}
				if(startTime==null || ("").equals(startTime) && (endTime==null || ("").equals(endTime))){
					sql+=" and createDate <= '"+year+"-"+month+"-31'";
				}
				sql += " and createDate like '"+year+"%'";
			} else{
				sql="select SUM(i.amount*i.price_tax) from forminfo as i left join material as m on i.material_id = m.id left join materialstype as mt on mt.id = m.materialstype_id where i.confirmDate is not null and i.outForm_id is not null and mt.cid like '"+cid+"%'";
				if(id != null && !"".equals(id)){
					sql += " and i.material_id = '"+id+"'";
				}
				if(endTime==null || ("").equals(endTime)){
					endTime=endTime+" 24:00:000";
					sql+=" and i.createDate <= '"+endTime+"'";
				}
				if(startTime==null || ("").equals(startTime) && (endTime==null || ("").equals(endTime))){
					sql+=" and i.createDate <= '"+year+"-"+month+"-31'";
				}
				sql += " and i.createDate like '"+year+"%'";
			}
				
			ResultSet rs;
			rs = DB.executeQuery(sql);
			if(rs.next()){
				total = total+rs.getDouble(1);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace(System.out);
		}
		return total;
	}
	
	//入库年度累计数量
	@SuppressWarnings("deprecation")
	public static Double getInputYearCumulativeS(String year,String month,String startTime,String endTime,Long materialID,Long id,String cid) {
		Double total = 0.00;
		try {
			//本月入库查询
			String sql = "";
			if("1".equals(cid)) {
				sql="select sum(amount) from forminfo where confirmDate is not null and intoForm_id is not null and material_id = '"+materialID+"'";
				if(endTime==null || ("").equals(endTime)){
					endTime=endTime+" 24:00:000";
					sql+=" and createDate <= '"+endTime+"'";
				}
				if(startTime==null || ("").equals(startTime) && (endTime==null || ("").equals(endTime))){
					sql+=" and createDate <= '"+year+"-"+month+"-31'";
				}
			} else{
				sql="select SUM(i.amount) from forminfo as i left join material as m on i.material_id = m.id left join materialstype as mt on mt.id = m.materialstype_id where i.confirmDate is not null and i.intoForm_id is not null and mt.cid like '"+cid+"%'";
				if(id != null && !"".equals(id)){
					sql += " and i.material_id = '"+id+"'";
				}
				if(endTime==null || ("").equals(endTime)){
					endTime=endTime+" 24:00:000";
					sql+=" and i.createDate <= '"+endTime+"'";
				}
				if(startTime==null || ("").equals(startTime) && (endTime==null || ("").equals(endTime))){
					sql+=" and i.createDate <= '"+year+"-"+month+"-31'";
				}
			}
			
			ResultSet rs;
			rs = DB.executeQuery(sql);
			if(rs.next()){
				total = total+rs.getDouble(1);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace(System.out);
		}
		return total;
	}
	
	//入库年度累计(不含税金额)
	@SuppressWarnings("deprecation")
	public static Double getInputYearCumulativeB(String year,String month,String startTime,String endTime,Long materialID,Long id,String cid) {
		Double total = 0.00;
		try {
			//本月入库查询
			String sql = "";
			if("1".equals(cid)) {
				sql="select sum(amount*price) from forminfo where confirmDate is not null and intoForm_id is not null and material_id = '"+materialID+"'";
				if(endTime==null || ("").equals(endTime)){
					endTime=endTime+" 24:00:000";
					sql+=" and createDate <= '"+endTime+"'";
				}
				if(startTime==null || ("").equals(startTime) && (endTime==null || ("").equals(endTime))){
					sql+=" and createDate <= '"+year+"-"+month+"-31'";
				}
			} else{
				sql="select SUM(i.amount*i.price) from forminfo as i left join material as m on i.material_id = m.id left join materialstype as mt on mt.id = m.materialstype_id where i.confirmDate is not null and i.intoForm_id is not null and mt.cid like '"+cid+"%'";
				if(id != null && !"".equals(id)){
					sql += " and i.material_id = '"+id+"'";
				}
				if(endTime==null || ("").equals(endTime)){
					endTime=endTime+" 24:00:000";
					sql+=" and i.createDate <= '"+endTime+"'";
				}
				if(startTime==null || ("").equals(startTime) && (endTime==null || ("").equals(endTime))){
					sql+=" and i.createDate <= '"+year+"-"+month+"-31'";
				}
			}
			
			ResultSet rs;
			rs = DB.executeQuery(sql);
			if(rs.next()){
				total = total+rs.getDouble(1);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace(System.out);
		}
		return total;
	}
	
	//入库年度累计(含税金额)
	@SuppressWarnings("deprecation")
	public static Double getInputYearCumulativeH(String year,String month,String startTime,String endTime,Long materialID,Long id,String cid) {
		Double total = 0.00;
		try {
			//本月入库查询
			String sql = "";
			if("1".equals(cid)) {
				sql="select sum(amount*price_tax) from forminfo where confirmDate is not null and intoForm_id is not null and material_id = '"+materialID+"'";
				if(endTime==null || ("").equals(endTime)){
					endTime=endTime+" 24:00:000";
					sql+=" and createDate <= '"+endTime+"'";
				}
				if(startTime==null || ("").equals(startTime) && (endTime==null || ("").equals(endTime))){
					sql+=" and createDate <= '"+year+"-"+month+"-31'";
				}
			} else{
				sql="select SUM(i.amount*i.price_tax) from forminfo as i left join material as m on i.material_id = m.id left join materialstype as mt on mt.id = m.materialstype_id where i.confirmDate is not null and i.intoForm_id is not null and mt.cid like '"+cid+"%'";
				if(id != null && !"".equals(id)){
					sql += " and i.material_id = '"+id+"'";
				}
				if(endTime==null || ("").equals(endTime)){
					endTime=endTime+" 24:00:000";
					sql+=" and i.createDate <= '"+endTime+"'";
				}
				if(startTime==null || ("").equals(startTime) && (endTime==null || ("").equals(endTime))){
					sql+=" and i.createDate <= '"+year+"-"+month+"-31'";
				}
			}
			
			ResultSet rs;
			rs = DB.executeQuery(sql);
			if(rs.next()){
				total = total+rs.getDouble(1);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace(System.out);
		}
		return total;
	}
	
	//出库年度累计数量
	@SuppressWarnings("deprecation")
	public static Double getOutYearCumulativeS(String year,String month,String startTime,String endTime,Long materialID,Long id,String cid) {
		Double total = 0.00;
		try {
			//本月入库查询
			String sql = "";
			if("1".equals(cid)) {
				sql="select sum(amount) from forminfo where confirmDate is not null and outForm_id is not null and material_id = '"+materialID+"'";
				if(endTime==null || ("").equals(endTime)){
					endTime=endTime+" 24:00:000";
					sql+=" and createDate <= '"+endTime+"'";
				}
				if(startTime==null || ("").equals(startTime) && (endTime==null || ("").equals(endTime))){
					sql+=" and createDate <= '"+year+"-"+month+"-31'";
				}
			} else{
				sql="select SUM(i.amount) from forminfo as i left join material as m on i.material_id = m.id left join materialstype as mt on mt.id = m.materialstype_id where i.confirmDate is not null and i.outForm_id is not null and mt.cid like '"+cid+"%'";
				if(id != null && !"".equals(id)){
					sql += " and i.material_id = '"+id+"'";
				}
				if(endTime==null || ("").equals(endTime)){
					endTime=endTime+" 24:00:000";
					sql+=" and i.createDate <= '"+endTime+"'";
				}
				if(startTime==null || ("").equals(startTime) && (endTime==null || ("").equals(endTime))){
					sql+=" and i.createDate <= '"+year+"-"+month+"-31'";
				}
			}
			
			ResultSet rs;
			rs = DB.executeQuery(sql);
			if(rs.next()){
				total = total+rs.getDouble(1);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace(System.out);
		}
		return total;
	}
	
	//出库年度累计(不含税金额)
	@SuppressWarnings("deprecation")
	public static Double getOutYearCumulativeB(String year,String month,String startTime,String endTime,Long materialID,Long id,String cid) {
		Double total = 0.00;
		try {
			//本月入库查询
			String sql = "";
			if("1".equals(cid)) {
				sql="select sum(amount*price) from forminfo where confirmDate is not null and outForm_id is not null and material_id = '"+materialID+"'";
				if(endTime==null || ("").equals(endTime)){
					endTime=endTime+" 24:00:000";
					sql+=" and createDate <= '"+endTime+"'";
				}
				if(startTime==null || ("").equals(startTime) && (endTime==null || ("").equals(endTime))){
					sql+=" and createDate <= '"+year+"-"+month+"-31'";
				}
			} else{
				sql="select SUM(i.amount*i.price) from forminfo as i left join material as m on i.material_id = m.id left join materialstype as mt on mt.id = m.materialstype_id where i.confirmDate is not null and i.outForm_id is not null and mt.cid like '"+cid+"%'";
				if(id != null && !"".equals(id)){
					sql += " and i.material_id = '"+id+"'";
				}
				if(endTime==null || ("").equals(endTime)){
					endTime=endTime+" 24:00:000";
					sql+=" and i.createDate <= '"+endTime+"'";
				}
				if(startTime==null || ("").equals(startTime) && (endTime==null || ("").equals(endTime))){
					sql+=" and i.createDate <= '"+year+"-"+month+"-31'";
				}
			}
			
			ResultSet rs;
			rs = DB.executeQuery(sql);
			if(rs.next()){
				total = total+rs.getDouble(1);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace(System.out);
		}
		return total;
	}
	
	//出库年度累计(含税金额)
	@SuppressWarnings("deprecation")
	public static Double getOutYearCumulativeH(String year,String month,String startTime,String endTime,Long materialID,Long id,String cid) {
		Double total = 0.00;
		try {
			//本月入库查询
			String sql = "";
			if("1".equals(cid)) {
				sql="select sum(amount*price_tax) from forminfo where confirmDate is not null and outForm_id is not null and material_id = '"+materialID+"'";
				if(endTime==null || ("").equals(endTime)){
					endTime=endTime+" 24:00:000";
					sql+=" and createDate <= '"+endTime+"'";
				}
				if(startTime==null || ("").equals(startTime) && (endTime==null || ("").equals(endTime))){
					sql+=" and createDate <= '"+year+"-"+month+"-31'";
				}
			} else{
				sql="select SUM(i.amount*i.price_tax) from forminfo as i left join material as m on i.material_id = m.id left join materialstype as mt on mt.id = m.materialstype_id where i.confirmDate is not null and i.outForm_id is not null and mt.cid like '"+cid+"%'";
				if(id != null && !"".equals(id)){
					sql += " and i.material_id = '"+id+"'";
				}
				if(endTime==null || ("").equals(endTime)){
					endTime=endTime+" 24:00:000";
					sql+=" and i.createDate <= '"+endTime+"'";
				}
				if(startTime==null || ("").equals(startTime) && (endTime==null || ("").equals(endTime))){
					sql+=" and i.createDate <= '"+year+"-"+month+"-31'";
				}
			}
			
			ResultSet rs;
			rs = DB.executeQuery(sql);
			if(rs.next()){
				total = total+rs.getDouble(1);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace(System.out);
		}
		return total;
	}
	
	//期初-listMaterial
	@SuppressWarnings("deprecation")
	public static Double[] getInitialInventoryAndAmount(Long materialID, Long id, String cid) {
		Connection conn = null;
		Statement stmt = null;
		Double[] total = new Double[2];
//		System.out.println("库存期初++++++++++："+sql);
		try {
			String sql ="";
			if("1".equals(cid)){
				sql += "SELECT SUM(totalMoney),SUM(totalAmount) FROM initialinventory where material_id = '"+materialID+"'";
			}else {
				sql += "SELECT SUM(totalMoney),SUM(totalAmount) FROM initialinventory where 1=1";
				if(id != null && !"".equals(id)){
					sql += " and material_id = '"+id+"'";
				}
			}
			ResultSet rs;
			rs = DB.executeQuery(sql);
			if(rs.next()){
				total[0] = rs.getDouble(1);
				total[1] = rs.getDouble(2);
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace(System.out);
		}
		//total = NumberUtils.formateDouble(total, 2);
		return total; 
	}
	
	//listMaterial
	@SuppressWarnings("deprecation")
	public static Double[] getBeginningAndQuantity(String year,String month,String startTime,String endTime,String cid,Long materialID, Long id) {
		Connection conn = null;
		Statement stmt = null;
		Double[] total = new Double[2];
		Double[] total1 = new Double[2];
		try {
			//入库查询
			String sql = "";
			//出库查询
			String sql1 = "";
			if("1".equals(cid)){
				sql = "SELECT SUM(amount*price),SUM(amount) FROM forminfo where confirmDate is not null and intoForm_id is not null and material_id = '"+materialID+"'";
				sql1 = "SELECT SUM(amount*price),SUM(amount) FROM forminfo where confirmDate is not null and outForm_id is not null and material_id = '"+materialID+"'";
				if(startTime!=null && !("").equals(startTime)){
					startTime = startTime+" 00:00:000";
					sql+=" and createDate < '"+startTime+"'";
					sql1+=" and createDate < '"+startTime+"'";
				}
				if(startTime==null || ("").equals(startTime)){
					sql+=" and createDate < '"+year+"-"+month+"-00'";
					sql1+=" and createDate < '"+year+"-"+month+"-00'";
				}
			}else{
				sql="select sum(i.price*i.amount),sum(i.amount) from forminfo as i left join material as m on i.material_id = m.id left join materialstype as mt on mt.id = m.materialstype_id where i.confirmDate is not null and i.intoForm_id is not null and mt.cid like '"+cid+"%'";
				sql1="select sum(i.price*i.amount),sum(i.amount) from forminfo as i left join material as m on i.material_id = m.id left join materialstype as mt on mt.id = m.materialstype_id where i.confirmDate is not null and i.outForm_id is not null and mt.cid like '"+cid+"%'";
				if(id != null && !"".equals(id)){
					sql += " and i.material_id = '"+id+"'";
					sql1 += " and i.material_id = '"+id+"'";
				}
				if(startTime!=null && !("").equals(startTime)){
					startTime = startTime+" 00:00:000";
					sql+=" and i.createDate < '"+startTime+"'";
					sql1+=" and i.createDate < '"+startTime+"'";
				}
				if(startTime==null || ("").equals(startTime)){
					sql+=" and i.createDate < '"+year+"-"+month+"-00'";
					sql1+=" and i.createDate < '"+year+"-"+month+"-00'";
				}
			}
			
			
//			System.out.println("入库+++++++++："+sql);
//			System.out.println("出库+++++++++："+sql1);
			
			ResultSet rs;
			rs = DB.executeQuery(sql);
			if(rs.next()){
				total[0] = rs.getDouble(1);
				total[1] = rs.getDouble(2);
			}
			ResultSet rs2;
			rs2 = DB.executeQuery(sql1);
			if(rs2.next()){
				total1[0] = rs2.getDouble(1);
				total1[1] = rs2.getDouble(2);
			}
			total[0] = total[0] - total1[0];
			total[1] = total[1] - total1[1];
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace(System.out);
		}
		return total;
	}
	
	//出库本年累计 和 出库本年累计 数量--listMaterial
	@SuppressWarnings("deprecation")
	public static Double[] getInputAndOutCumulative(String year,String month,String startTime,String endTime,String cid,Long materialID,Long id) {
		Connection conn=null;
		Statement stmt=null;
		Double total[] = new Double[4];
		try {
			String sql = "";
			String sql1 = "";
			if("1".equals(cid)) {
				sql="SELECT sum(a.amount),sum(a.price*a.amount) FROM forminfo as a where a.intoForm_id is not null and a.confirmDate is not null and material_id = '"+materialID+"'";
				sql1="SELECT sum(a.amount),sum(a.price*a.amount) FROM forminfo as a where a.outForm_id is not null and a.confirmDate is not null and material_id = '"+materialID+"'";
				
				if(endTime!=null && !("").equals(endTime)){
					endTime=endTime+" 24:00:000";
					sql+=" and a.createDate <= '"+endTime+"'";
					sql1+=" and a.createDate <= '"+endTime+"'";
				}
				if(startTime==null || ("").equals(startTime) && (endTime==null || ("").equals(endTime))){
					sql+=" and a.createDate >= '"+year+"-"+month+"-00'" + " and a.createDate <= '"+year+"-"+month+"-31'";
					sql1+=" and a.createDate >= '"+year+"-"+month+"-00'" + " and a.createDate <= '"+year+"-"+month+"-31'";
				}
//				sql += " and a.createDate like '"+year+"%'"; 
//				sql1 += " and a.createDate like '"+year+"%'";
			}else {
				sql="SELECT sum(a.amount),sum(a.price*a.amount) FROM forminfo as a where a.intoForm_id is not null and a.confirmDate is not null";
				sql1="SELECT sum(a.amount),sum(a.price*a.amount) FROM forminfo as a where a.outForm_id is not null and a.confirmDate is not null";
				
				if(id != null && !"".equals(id)){
					sql += " and a.material_id = '"+id+"'";
					sql1 += " and a.material_id = '"+id+"'";
				}
				if(endTime!=null && !("").equals(endTime)){
					endTime=endTime+" 24:00:000";
					sql+=" and a.createDate <= '"+endTime+"'";
					sql1+=" and a.createDate <= '"+endTime+"'";
				}
				if(startTime==null || ("").equals(startTime) && (endTime==null || ("").equals(endTime))){
					sql+=" and a.createDate >= '"+year+"-"+month+"-00'" + " and a.createDate <= '"+year+"-"+month+"-31'";
					sql1+=" and a.createDate >= '"+year+"-"+month+"-00'" + " and a.createDate <= '"+year+"-"+month+"-31'";
				}
				sql += " and a.createDate like '"+year+"%'"; 
				sql1 += " and a.createDate like '"+year+"%'";
			}
//			System.out.println("入库发生+++++++++++："+sql);
//			System.out.println("出库发生+++++++++++："+sql1);
			ResultSet rs;
			rs = DB.executeQuery(sql);
			if(rs.next()){
				total[0] = rs.getDouble(1);
				total[1] = rs.getDouble(2);
				
			}
			ResultSet rs2;
			rs2 = DB.executeQuery(sql1);
			if(rs2.next()){
				total[2] = rs2.getDouble(1);
				total[3] = rs2.getDouble(2);
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace(System.out);
		}
		return total;
	}
	
}
