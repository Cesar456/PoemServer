package cesar.poem.DAO;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cesar.poem.bean.PoemTagDetail;

public class PoemTagDetailDAO extends HibernateDaoSupport{
	
	private static final String ID = "id";
	private static final String POEMID = "poemID";
	private static final String TAG = "tag";
	public void save(PoemTagDetail poemTagDetail) {
		try {
			getHibernateTemplate().save(poemTagDetail);
		} catch (RuntimeException re) {
			throw re;
		}
	}
	public void delete(PoemTagDetail poemTagDetail) {
		try {
			getHibernateTemplate().delete(poemTagDetail);
		} catch (RuntimeException re) {
			throw re;
		}
	}
	public PoemTagDetail findById(int id) {
		try {
			PoemTagDetail poemTagDetail = (PoemTagDetail) getHibernateTemplate().get(
					"cesar.poem.bean.PoemTagDetail", id);
			return poemTagDetail;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public List findByExample(PoemTagDetail poemTagDetail) {
		try {
			List results = getHibernateTemplate().findByExample(poemTagDetail);
			return results;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		try {
			String queryString = "from PoemTagDetail as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public List findByPoemTagDetailId(Object poemTagDetailId) {
		return findByProperty(ID, poemTagDetailId);
	}

	public List findByPoemID(Object poemID) {
		return findByProperty(POEMID, poemID);
	}

	public List findByTag(Object tag) {
		return findByProperty(TAG, tag);
	}

	public List findAll() {
		try {
			String queryString = "from PoemTagDetail";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			throw re;
		}
	}
	public void update(PoemTagDetail poemTagDetail) {
		try {
			getHibernateTemplate().update(poemTagDetail);
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
