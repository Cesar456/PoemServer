package cesar.poem.DAO.test;

import java.util.List;

import cesar.poem.DAO.PoemDAO;
import cesar.poem.DAO.PoemTagDetailDAO;
import cesar.poem.Service.BSSingleton;
import cesar.poem.bean.Poem;
import cesar.poem.bean.PoemTagDetail;
import cesar.poem.util.CommonUtil;

public class PoemTagDetailTtest {

	private static PoemTagDetailDAO poemTagDetailDAO = BSSingleton
			.getPoemTagDetailDAO();
	private static PoemDAO poemDAO = BSSingleton.getPoemDAO();

	public static void main(String[] args) {
		List<Poem> poems = (List<Poem>) poemDAO.findAll();
		int poemid;
		int i = 0;
		String s;
		for (Poem poem : poems) {
			System.out.println(i++);
			s = poem.getLable().trim();
			poemid = poem.getId();
			if (!"".equals(s) && s != null) {
				String[] tags = s.split("\n");
				for (String tag : tags) {
					PoemTagDetail poemTagDetail = new PoemTagDetail();
					poemTagDetail.setPoemID(poemid);
					poemTagDetail.setTag(tag);
					poemTagDetailDAO.save(poemTagDetail);
				}
			}
		}
	}
}
