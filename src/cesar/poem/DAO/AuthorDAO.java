package cesar.poem.DAO;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cesar.poem.bean.Author;


public class AuthorDAO extends HibernateDaoSupport{
	
	private static final String ID="id";
	private static final String AUTHORNAME = "authorName";
	private static final String TIME = "time";

	public void save(Author author) {
		try {
			getHibernateTemplate().save(author);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public void delete(Author author) {
		try {
			getHibernateTemplate().delete(author);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public Author findById(int id) {
		try {
			Author author = (Author) getHibernateTemplate().get(
					"cesar.poem.bean.Author", id);
			return author;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public List<?> findByExample(Author author) {
		try {
			List<?> results = getHibernateTemplate().findByExample(author);
			return results;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public List<?> findByProperty(String propertyName, Object value) {
		try {
			String queryString = "from Author as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public List<?> findById(Object authorId) {
		return findByProperty(ID, authorId);
	}

	public List<?> findByAuthorName(Object authorName) {
		return findByProperty(AUTHORNAME, authorName);
	}
	public List<?> findByTime(Object authorTime) {
		return findByProperty(TIME, authorTime);
	}

	public List<?> findAll() {
		try {
			String queryString = "from Author";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public static UserDAO getFromApplicationContext(ApplicationContext ctx) {
		return (UserDAO) ctx.getBean("AuthorDAO");
	}

	public void update(Author author) {
		try {
			getHibernateTemplate().update(author);
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
	public List<?> getListByCriteria(DetachedCriteria dc) {
		List<?> list = null;
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
