package cesar.poem.Service;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import cesar.poem.DAO.UserDAO;
import cesar.poem.bean.User;
import cesar.poem.util.CommonUtil;

@Path("User")
public class UserService {

	private UserDAO userDAO = BSSingleton.getUserDAO();
	private CommonUtil commonUtil = new CommonUtil();

	/**
	 * 通过email和password登陆
	 * user为用户名或email，密码为md5码
	 * @param user
	 * @param password
	 * @return 成功返回200,否则返回300
	 */
	@POST
	@Path("/login")
	@Produces("text/plain;charset=UTF-8")
	public String userLogin(
			@FormParam("user") String userInfo,
			@FormParam("password") String password) {
		List<User> users = new ArrayList<User>();
		if(userInfo.contains("@")){
			users = userDAO.findByUserEmail(userInfo);
		}else {
			users = userDAO.findByUserName(userInfo);
		}
		
		if (users.size() == 1 && users.get(0).getPassWord().equals(password)) {
			return "200";
		}
		return "300";
	}
	
	
	/**
	 * 通过email和password注册，用户名可选 且email和用户名不唯一
	 * user为用户名或email，密码为md5码
	 * @param user
	 * @param password
	 * @return 成功返回200,否则返回300
	 */
	@POST
	@Path("/register")
	@Produces("text/plain;charset=UTF-8")
	public String userRegister(
			@FormParam("userEmail") String userEmail,
			@FormParam("userName") String userName,
			@FormParam("password") String password) {
		String result = "";
		if(userDAO.findByUserEmail(userEmail).size()>0){
			result = result + "该邮箱已经被注册 +\n";
		}
		else if(userName!=null&&!"".equals(userName)&&userDAO.findByUserName(userName).size()>0) {
			result = result + "该用户名已被注册";
		}
		else {
			result = result + "注册成功";
			User user = new User();
			user.setUserEmail(userEmail);
			user.setUserName(userName);
			user.setPassWord(password);
			user.setCreatetime(commonUtil.getCurrentDateTime());
			user.setLastModifyTime(commonUtil.getCurrentDateTime());
			userDAO.save(user);
		}
		return result;
	}
}
