package pers.dafacloud.beting;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//import org.apache.commons.dbutils.QueryRunner;

import pers.dafacloud.common.Log;
//import dataBaseConn.MysqlConns;

public class BodyInsertIntoMysql {
//	private static Connection conn=MysqlConns.getConnection();
	private static String[] syx5= {"1211","1212","1228","1214"};
	private static String[] k3= {"2231","2233","2246"};
	
	public static void main(String[] args) {
		String body="action=ok&lotId=2231&termNo=181214042&multiple=1&betCount=3&betAmount=6&termCount=1&betContent=07,10,17;&betTypes=HZ;&termNos=&Multiples=&winStop=FALSE&stopWinCount=1&CouponId=0";
		//bodyIntoMysql(body);
	}
	
	
	/*public static void bodyIntoMysql(String body) {
		
		String[] bodyArray = body.split("&");
		List<String> list=new ArrayList<>();
		String Lotid="";
		for(String bodys: bodyArray) {
			String[] params=bodys.split("=",-1);
			String key=params[0];
			String value=params[1];
			list.add(value);
			if(key.equals("lotId")) {
				Lotid=value;
				if(value.equals("1211")) {
					//list.add(Params.sd11x5Lotid);
					list.add(Params.sd11x5LotName);}
				else if(value.equals("1212")) {
					//list.add(Params.gd11x5Lotid);
					list.add(Params.gd11x5LotName);}
				else if(value.equals("1228")) {
					//list.add(Params.sx11x5Lotid);
					list.add(Params.sx11x5LotName);}
				else if(value.equals("1214")) {
					//list.add(Params.zj11x5Lotid);
					list.add(Params.zj11x5LotName);}
				else if(value.equals("2231")) {
					//list.add(Params.jsk3Lotid);
					list.add(Params.jsk3LotName);}
				else if(value.equals("2233")) {
					//list.add(Params.ahk3Lotid);
					list.add(Params.ahk3LotName);}
				else if(value.equals("2246")) {
					//list.add(Params.jxk3Lotid);
					list.add(Params.jxk3LotName);}else {
						Log.infoError("结果写入数据库失败，lotId没有找到");
					}
			}
			
			if(key.equals("betTypes")) {
				if(Arrays.asList(syx5).contains(Lotid)) {
					//value=value.replace(";", "");
					if(value.contains("R2")) {
						list.add("任二");}
					else if(value.contains("R3")) {
						list.add("任三");}
					else if(value.contains("R4")) {
						list.add("任四");}
					else if(value.contains("R5")) {
						list.add("任五");}
					else if(value.contains("R6")) {
						list.add("任六");}
					else if(value.contains("R7")) {
						list.add("任七");}
					else if(value.contains("R8")) {
						list.add("任八");}
					else if(value.contains("Q1")) {
						list.add("前一直选");}
					else if(value.contains("Q2")) {
						list.add("前二直选");}
					else if(value.contains("Z2")) {
						list.add("前二组选");}
					else if(value.contains("Q3")) {
						list.add("前三直选");}
					else if(value.contains("Z3")) {
						list.add("前三组选");}
					
					else if(value.contains("L3")) {
						list.add("乐三");}
					else if(value.contains("L4")) {
						list.add("乐四");}
					else if(value.contains("L5")) {
						list.add("乐五");}
					else {
							Log.infoError("结果写入数据库失败，快3的betTypes没有找到");
						}
				}
				else if(Arrays.asList(k3).contains(Lotid)) {
					//System.out.println(value);
					if(value.contains("D2")) {
						list.add("二不同号");}
					else if(value.contains("F2")) {
						list.add("二同号复选");}
					else if(value.contains("S2")) {
						list.add("二同号单选");}
					else if(value.contains("HZ")) {
						list.add("和值");}
					else if(value.contains("S3")) {
						list.add("三同号单选");}
					else if(value.contains("T3")) {
						list.add("三同号通选");}
					else if(value.contains("D3")) {
						list.add("三不同号");}
					else if(value.contains("L3")) {
						list.add("三连号通选");}
					else {
						Log.infoError("结果写入数据库失败，快3的betTypes没有找到");
					}
				}
				
			}
		}
		if(list.size()>0) {
			System.out.println(list);
//			QueryRunner queryRunner = new QueryRunner(true);
			String sql =
			 "INSERT IGNORE INTO bet_success(action,lotId,lotname,termNo,multiple,betCount,betAmount,termCount,betContent,betTypes,TypesName,termNos,Multiples,winStop,stopWinCount,CouponId,Value,user) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//			try {
////				queryRunner.update(conn, sql, list.toArray()); }
//			catch (SQLException e) {
//				e.printStackTrace();
//			}
		}else {
			System.out.println("collist数据空");
		}
		
	}*/
}
