package cesar.poem.test;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cesar.poem.DAO.PoemDAO;
import cesar.poem.DAO.PoemTagDetailDAO;
import cesar.poem.Service.BSSingleton;
import cesar.poem.bean.Poem;
import cesar.poem.bean.PoemTagDetail;
import cesar.poem.util.CommonUtil;

public class tset {
	private static String errPath = "F:\\标签1.txt";
	public static void main(String[] args) {
		PoemTagDetailDAO poemTagDetailDAO = BSSingleton.getPoemTagDetailDAO();
		CommonUtil commonUtil = new CommonUtil();
		List<PoemTagDetail> poemTagDetails = poemTagDetailDAO.findAll();
		Set<String> set = new HashSet<String>();
		for(PoemTagDetail poemTagDetail:poemTagDetails){
			set.add(poemTagDetail.getTag());
		}
		for(String s:set){
			commonUtil.exportToTxt(s, errPath);
		}
		
	}
}
