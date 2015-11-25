package cesar.poem.util;

public class CommonSource {
	private static CommonUtil commonUtil = new CommonUtil();
	private static String filePath = "/poem_config.properties";
	
	public static int PAGESIZE = Integer.parseInt(commonUtil.getProperty(filePath, "page_size"));
	public static int PAGEFROM = Integer.parseInt(commonUtil.getProperty(filePath, "page_from"));
	public static int PAGETO = Integer.parseInt(commonUtil.getProperty(filePath, "page_to"));
}
