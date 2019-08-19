package pers.dafagame.utils.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Reporter;

import java.text.SimpleDateFormat;
import java.util.Date;

//import org.apache.log4j.Level;
//import org.apache.log4j.Logger;


public class Log {
	
//	private static Logger Log = Logger.getLogger(Log.class.getName());
	private final static Logger Log = LoggerFactory.getLogger(Log.class);
//	static {
//		//System.setProperty("log.info.file", "lcBet01.log");
//		Log.setLevel(Level.INFO);
//		Log.fatal("the effective level is : "+Log.getEffectiveLevel());
//	}
	
	/**info的日志*/
	public static void info(String message) {
		String IMessage="["+time()+"] "+message;
		Reporter.log(IMessage);
		Log.info(IMessage);		
	}	
	
	/**失败
	 *<p> 参数WebDriver driver,String message 错误日志信息,
	 */
	public static void infoError(String message) {
		String eMessage="[error]["+time()+"] "+message;
		//Reporter.log(eMessage);
		Log.info(eMessage);
		throw new RuntimeException(eMessage.replaceAll("\"", ""));// 解决java.net.SocketException: Software caused connection abort: socket write error
	}
	
	public static void warn(String message) {
		Log.warn(message);
	}

	public static void error(String message) {
		Log.error(message);
	}

//	public static void fatal(String message) {
//		Log.fatal(message);
//	}

	public static void debug(String message) {
		Log.debug(message);
	}

	/**返回系统时间精确到时分秒20180101000000，用于截图等*/
	public static String time() {
		Date day=new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String time=df.format(day);
		return time;
	}
	
	/**测试*/
	public static void main(String[] args) {
		
	}
	
}
