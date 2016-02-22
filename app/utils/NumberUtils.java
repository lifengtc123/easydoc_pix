package utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import play.db.DB;

public class NumberUtils {
	//自动生成编号
	public static String makeSerialNumber(String tableName,String fields,String sendDate) {
		int num=1;
		String date = "";
		if(sendDate != null && !"".equals(sendDate)){
			date = sendDate;
		}else{
			date = DateUtils.format(new Date(), "yyyyMM");
		}
		Connection conn = DB.getConnection();
		try {
				conn.setAutoCommit(false);
				Statement st = conn.createStatement();
				String sql = "select * from "+tableName+" where "+fields+" like '"+date+"%' order by "+fields+" desc"; 
//System.out.println("sql:"+sql);
				st = conn.createStatement();
				ResultSet rs = st.executeQuery(sql);	//查找产品
				String dbnum="";
				if(rs.next()){
					dbnum=rs.getString(fields);
					if(dbnum!=null){
						if(dbnum.substring(0, 6).equals(date)){
							num=Integer.parseInt(dbnum.substring(6, 9))+1;
						}
					}
				}
				String strnum=num<10?("00"+num):((num<100)?("0"+num):(num+""));
				String serialnum=date+strnum;
				return serialnum;
			} catch (SQLException e) {
				e.printStackTrace();
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				return "";
			}
		}
	
	public static Double formateDoubleWithDig(Double number,Integer dig){
		return (double)(Math.round(number*dig))/dig; 
	}
}
