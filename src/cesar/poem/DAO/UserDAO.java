package cesar.poem.DAO;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cesar.poem.bean.User;

public class UserDAO extends HibernateDaoSupport {

	public static final String USERID = "userId";
	public static final String USEREmail = "userEmail";
	public static final String STATU = "statu";
	public static final String USERNAME = "userName";
	public static final String CREATEMAIL = "creatTime";
	public static final String LASTMODIFYTIME = "lastModifyTime";

	public void save(User user) {
		try {
			getHibernateTemplate().save(user);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	/**
	 * 根据主键删除User,只需要设置user的userid即可
	 * @author Cesar
	 * @param user
	 */
	public void delete(User user) {
		try {
			getHibernateTemplate().delete(user);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public User findById(int id) {
		try {
			User user = (User) getHibernateTemplate().get(
					"cesar.poem.bean.User", id);
			return user;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	/**
	 * 1.不支持主键  2.不支持关联  3.不支持NULL 通过除了id以外的所有属性来查询一个user
	 * @author Cesar 
	 * @param user
	 * @return
	 */
	public List findByExample(User user) {
		try {
			List results = getHibernateTemplate().findByExample(user);
			return results;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		try {
			String queryString = "from User as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public List findByUserId(Object userId) {
		return findByProperty(USERID, userId);
	}

	public List findByUserName(Object userName) {
		return findByProperty(USERNAME, userName);
	}
	public List findByUserEmail(Object userEmail) {
		return findByProperty(USEREmail, userEmail);
	}

	public List findAll() {
		try {
			String queryString = "from User";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			throw re;
		}
	}
	/**
	 * @author Cesar
	 * 根据用户id更新其他字段，email，密码等均不能为空
	 * @param user
	 */
	public void update(User user) {
		try {
			getHibernateTemplate().update(user);
		} catch (RuntimeException re) {
			throw re;
		}
	}
	public int getCountByCriteria(DetachedCriteria dc){
		int total=0;
		try {
			 Session session = this.getSession();   
			 Criteria criteria=dc.getExecutableCriteria(session);
			 total=((Integer)criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();		
			 criteria.setProjection(null);
	         releaseSession(session);
		} catch (Exception e) {
			 e.printStackTrace();
		}		 
		return total;
	 }
	public List getListByCriteria(DetachedCriteria dc) {
		List list = null;
		try {
			Session session = this.getSession();
			Criteria criteria = dc.getExecutableCriteria(session);
			list = criteria.list();
			releaseSession(session);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
