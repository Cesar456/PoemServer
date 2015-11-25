package cesar.poem.DAO;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cesar.poem.bean.Poem;

public class PoemDAO extends HibernateDaoSupport {

	private static final String ID = "id";
	private static final String TITLE = "title";
	private static final String TIME = "time";
	private static final String AUTHOR = "author";
	private static final String CONTENT = "content";
	private static final String AUTHORID = "authorId";
	public void save(Poem poem) {
		try {
			getHibernateTemplate().save(poem);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public void delete(Poem poem) {
		try {
			getHibernateTemplate().delete(poem);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public Poem findById(int id) {
		try {
			Poem poem = (Poem) getHibernateTemplate().get(
					"cesar.poem.bean.Poem", id);
			return poem;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public List<?> findByExample(Poem poem) {
		try {
			List<?> results = getHibernateTemplate().findByExample(poem);
			return results;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public List<?> findByProperty(String propertyName, Object value) {
		try {
			String queryString = "from Poem as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public List<?> findByPoemId(Object poemId) {
		return findByProperty(ID, poemId);
	}

	public List<?> findByTitle(Object title) {
		return findByProperty(TITLE, title);
	}

	public List<?> findByTime(Object time) {
		return findByProperty(TIME, time);
	}

	public List<?> findByAuthor(Object author) {
		return findByProperty(AUTHOR, author);
	}

	public List<?> findByContent(Object content) {
		return findByProperty(CONTENT, content);
	}

	public List<?> findByAuthorId(Object authorId) {
		return findByProperty(AUTHORID, authorId);
	}

	public List<?> findAll() {
		try {
			String queryString = "from Poem";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public static PoemDAO getFromApplicationContext(ApplicationContext ctx) {
		return (PoemDAO) ctx.getBean("PoemDAO");
	}

	public void update(Poem poem) {
		try {
			getHibernateTemplate().update(poem);
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
