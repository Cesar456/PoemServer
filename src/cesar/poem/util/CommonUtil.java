package cesar.poem.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;

public class CommonUtil {

	public  String getProperty(String filename,String key){
		InputStream is = getClass().getResourceAsStream(filename);
		Properties properties=new Properties();//读取项目配置文件
		try {
			properties.load(is);//配置文件关键字
		} catch (Exception e) {
			e.printStackTrace();
		}
		String keyValue=properties.get(key).toString();
		return keyValue;
	}
	public void setProperty(String filename,String key,String value){
		try {
			File file=new File(getPropertyPath()+filename);
			InputStream is=new FileInputStream(file);
			Properties properties=new Properties();
			properties.load(is);
			is.close();
			properties.put(key, value);
			OutputStream out = new FileOutputStream(file);
		    properties.store(out, "by Cesar");
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public String getCurrentDate(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String currentDate = sdf.format(new Date());		
		return currentDate;
	}
	public String getCurrentHour(){
		SimpleDateFormat sdf = new SimpleDateFormat("HH");
		String currentHour = sdf.format(new Date());		
		return currentHour;
	}
	
	public String getCurrentDateTime(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentDateTime = sdf.format(new Date());		
		return currentDateTime;
	}
	
	public String getFullCurrentDateTime(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		String currentDateTime = sdf.format(new Date());		
		return currentDateTime;
	}
	public String  getPropertyPath() throws URISyntaxException{
		return(getClass().getClassLoader().getResource("").toURI().getPath());
	}
	public String  getTomcatPath() throws URISyntaxException{
		return(System.getProperty("user.dir").replace("bin", "webapps"));
	}
	public String getWebRootPath(){
		URL url = ClassLoader.getSystemClassLoader().getResource("./");  
 	    File file = new File(url.getPath());  
 	    File parentFile = new File(file.getParent());  
		return (parentFile.getParent());
	}
	public String getspecificDateTime(String compareDateTime,int count){
		GregorianCalendar   calendar   =   new   GregorianCalendar();
	    Date   date   =  Timestamp.valueOf(compareDateTime);
	    calendar.setTime(date);
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    calendar.set (Calendar.DATE,calendar.get(Calendar.DATE)+count);
	    date   =   calendar.getTime(); 
	    String currentDate=sdf.format(date);
	    return currentDate;
	}
	public String getspecificDate(String compareDateTime,int count){
		GregorianCalendar   calendar   =   new   GregorianCalendar();
	    Date   date   =  Timestamp.valueOf(compareDateTime);
	    calendar.setTime(date);
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    calendar.set (Calendar.DATE,calendar.get(Calendar.DATE)+count);
	    date   =   calendar.getTime(); 
	    String currentDate=sdf.format(date);
	    return currentDate;
	}
	
	public String getspecificDateNew(int count){
		GregorianCalendar   calendar   =   new   GregorianCalendar();
		Date date=new Date();
	    calendar.setTime(date);
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    calendar.set (Calendar.DATE,calendar.get(Calendar.DATE)+count);
	    date   =   calendar.getTime(); 
	    String currentDate=sdf.format(date);
	    return currentDate;
	}
	
	public Integer getCountDays(String fromDateTime,String toDateTime){
		int count=0;
        try {
        	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date d1 = df.parse(fromDateTime);
		    Date d2 = df.parse(toDateTime);
		    long diff = d2.getTime() - d1.getTime();
		    long days = diff / (1000 * 60 * 60 * 24);
		    count=Integer.valueOf(String.valueOf(days));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public String MD5(String s) {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};       
        try {
            byte[] btInput = s.getBytes("utf-8");
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	/**
	 * 替换SQL中的特殊字符
	 * @param input
	 * @return
	 */
	public String sqlReplace(String input){
		try{
			return input.replaceAll("'", "''").replaceAll("\\\\", "\\\\\\\\");
		}catch(Exception e){
			return input;
		}
	}
	public String getTextOfFile(String filePath){
		File file=new File(filePath);
		if(!file.exists()){
			return null;
		}
		try {
			InputStreamReader read = new InputStreamReader(new FileInputStream(file));
			BufferedReader bufferedReader = new BufferedReader(read);
			String lineTxt = null;
			String content = "";
			while((lineTxt = bufferedReader.readLine()) != null){
			content = content + lineTxt + "\n";
			}
			read.close();
			return content;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	} 
	public void exportToTxt(String content, String filePath) {
		File file1 = new File(filePath);
		if (!file1.exists()) {
			try {
				file1.createNewFile();
			} catch (IOException e) {
				System.out.println("文件" + filePath + "创建失败");
			}
		}
		FileWriter fw;
		try {
			fw = new FileWriter(file1, true);
			BufferedWriter bf = new BufferedWriter(fw);
			bf.append(content);
			bf.newLine();
			bf.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
