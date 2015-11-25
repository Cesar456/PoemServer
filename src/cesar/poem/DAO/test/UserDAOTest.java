package cesar.poem.DAO.test;


import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import cesar.poem.DAO.UserDAO;
import cesar.poem.Service.BSSingleton;
import cesar.poem.bean.User;
import cesar.poem.util.CommonUtil;

public class UserDAOTest {
	
	public static UserDAO userDAO = BSSingleton.getUserDAO();
	public static CommonUtil commonUtil = new CommonUtil();
	
	public static void main(String[] args) {
		TestgetListByCriteria();
	}
	
	public static void testSave(){
		User user = new User();
		user.setCreatetime(commonUtil.getCurrentDateTime());
		user.setLastModifyTime(commonUtil.getCurrentDateTime());
		user.setPassWord("chen72");
		user.setUserEmail("fubai@qq.com");
		user.setStatu(1);
		user.setUserName("Cesar");
		userDAO.save(user);
		System.out.println(user.getUserId());
	}
	public static void testDelete() {
		User user = new User();
		user.setCreatetime(commonUtil.getCurrentDateTime());
		user.setLastModifyTime(commonUtil.getCurrentDateTime());
		user.setPassWord("chen72");
		user.setUserEmail("fubai@qq.com");
		user.setStatu(2);
		user.setUserId(6);
		user.setUserName("Cesar");
		try {
			userDAO.delete(user);
			System.out.println("删除成功");
		} catch (Exception e) {
			System.out.println("删除失败");
		}
	}
	public static void testFindById() {
		User user = userDAO.findById(5);
		System.out.println(user.toString());
	}
	public static void testFindByExample() {
		User user = new User();
//		user.setUserId(4);
		user.setCreatetime("2015-08-26 10:59:19");
		user.setLastModifyTime("2015-08-26 10:59:19");
		user.setPassWord("chen72");
		user.setUserEmail("fubai@qq.com");
		user.setStatu(1);
		user.setUserName("Cesar");
		int i = userDAO.findByExample(user).size();
		System.out.println(i);
	}
	public static void TestFindByProperty(){
		List<User> users = userDAO.findByProperty("userName", "Cesar");
		for(User user:users){
			System.out.println(user.toString());
		}
	}
	public static void testUpdate(){
		User user = new User();
		user.setCreatetime(commonUtil.getCurrentDateTime());
		user.setLastModifyTime(commonUtil.getCurrentDateTime());
		user.setPassWord("chen724467");
		user.setUserEmail("fubaidelong@qq.com");
		user.setStatu(1);
		user.setUserName("Cesar");
		user.setUserId(5);
		userDAO.update(user);
	}
	public static void TestgetListByCriteria(){
		DetachedCriteria dc = DetachedCriteria.forClass(User.class);
		dc.add(Restrictions.eq("userId", 1));
		List<User> users = userDAO.getListByCriteria(dc);
		System.out.println(users.size());
	}
	
}
