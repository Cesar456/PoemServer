package cesar.poem.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.google.gson.Gson;

import cesar.poem.DAO.AuthorDAO;
import cesar.poem.DAO.PoemDAO;
import cesar.poem.DAO.PoemTagDetailDAO;
import cesar.poem.bean.Author;
import cesar.poem.bean.Poem;
import cesar.poem.bean.PoemTagDetail;
import cesar.poem.util.CommonSource;
import cesar.poem.util.JsonUtil;
import cesar.poem.util.RandomUtil;

@Path("Poem")
public class PoemService {
	
	private PoemDAO poemDAO = BSSingleton.getPoemDAO();
	private PoemTagDetailDAO poemTagDetailDAO = BSSingleton.getPoemTagDetailDAO();
	private AuthorDAO authorDAO = BSSingleton.getAuthorDAO();
	
	private RandomUtil randomUtil = new RandomUtil();
	private JsonUtil jsonUtil = new JsonUtil();
	private Gson gson = new Gson();
	
	/**
	 * @author Cesar
	 * @return 获得随机的1首诗歌
	 */
	@GET
	@Path("getOneRandomPoem")
	@Produces("text/plain;charset=UTF-8")
	public String getOneRandomPoem(){
		try {
			Poem poem = poemDAO.findById(randomUtil.getOneRandom(CommonSource.PAGEFROM, CommonSource.PAGETO));
			return jsonUtil.formatObjectToJson(gson.toJson(poem), true);
		} catch (Exception e) {
			return jsonUtil.formatObjectToJson("", true);
		}
	}
	
	/**
	 * @author Cesar
	 * @return 获得随机的20首诗歌
	 */
	@GET
	@Path("getRandomPoem")
	@Produces("text/plain;charset=UTF-8")
	public String getRandomPoem(){
		List<Integer> ids = randomUtil.getRandomNum(CommonSource.PAGEFROM, CommonSource.PAGETO, CommonSource.PAGESIZE);
		DetachedCriteria dc = DetachedCriteria.forClass(Poem.class);
		dc.add(Restrictions.in("id", ids));
		List<Poem> poems = new ArrayList<Poem>();
		try {
			poems = (List<Poem>) poemDAO.getListByCriteria(dc);
			return jsonUtil.formatListToJson(poems, true);
		} catch (Exception e) {
			return jsonUtil.formatObjectToJson("", true);
		}
	}
	
	
	/**
	 * @author Cesar
	 * @param poemId
	 * @return 通过id得到诗歌内容
	 */
	@GET
	@Path("getPoemByPoemId")
	@Produces("text/plain;charset=UTF-8")
	public String getPoemByPoemId(@QueryParam("poemId") int poemId){
		try {
			Poem poem = poemDAO.findById(poemId);
			return jsonUtil.formatObjectToJson(gson.toJson(poem), true);
		} catch (Exception e) {
			return jsonUtil.formatObjectToJson("", false);
		}
	}
	
	/**
	 * @author Cesar
	 * @param authorName 诗人姓名
	 * @return 返回该诗人所有诗歌数量，用于分页
	 */
	@GET
	@Path("getPoemCountByAuthorName")
	@Produces("text/plain;charset=UTF-8")
	public String getPoemCountByAuthorName(@QueryParam("authorName") String authorName){
		try {
			DetachedCriteria dc = DetachedCriteria.forClass(Poem.class);
			dc.add(Restrictions.eq("author", authorName));
			return jsonUtil.formatObjectToJson(poemDAO.getCountByCriteria(dc), true);
		} catch (Exception e) {
		    return jsonUtil.formatObjectToJson("", false);
		}
	}
	
	/**
	 * 通过作者名搜索作者诗歌，每页大小在配置文件配置
	 * @author Cesar
	 * @param authorName 作者名
	 * @param pageNum 页数
	 * @return 返回该作者当前页的诗歌，通过title排序
	 */
	@GET
	@Path("getPoemByAuthorName")
	@Produces("text/plain;charset=UTF-8")
	public String getPoemByAuthorName(@QueryParam("authorName") String authorName,@QueryParam("pageNum") int pageNum){
		try {
			DetachedCriteria dc = DetachedCriteria.forClass(Poem.class);
			dc.add(Restrictions.eq("author", authorName));
			dc.addOrder(Order.asc("title"));
			List<Poem> poems = poemDAO.getHibernateTemplate().findByCriteria(dc, (pageNum-1)*CommonSource.PAGESIZE, CommonSource.PAGESIZE);
			return jsonUtil.formatListToJson(poems , true);
		} catch (Exception e) {
			return jsonUtil.formatObjectToJson("", false);
		}
	}
	
	
	/**
	 * @author Cesar
	 * @param authorId 诗人id
	 * @return 返回该诗人所有诗歌数量，用于分页
	 */
	@GET
	@Path("getPoemCountByAuthorId")
	@Produces("text/plain;charset=UTF-8")
	public String getPoemCountByAuthorId(@QueryParam("authorId") int authorId){
		try {
			DetachedCriteria dc = DetachedCriteria.forClass(Poem.class);
			dc.add(Restrictions.eq("authorId", authorId));
			return jsonUtil.formatObjectToJson(poemDAO.getCountByCriteria(dc), true);
		} catch (Exception e) {
			return jsonUtil.formatObjectToJson("", false);
		}
	}
	
	/**
	 * 通过作者名搜索作者诗歌，每页大小在配置文件配置
	 * @author Cesar
	 * @param authorId 作者id
	 * @param pageNum 页数
	 * @return 返回该作者当前页的诗歌,通过title排序
	 */
	@GET
	@Path("getPoemByAuthorId")
	@Produces("text/plain;charset=UTF-8")
	public String getPoemByAuthorId(@QueryParam("authorId") int authorId,@QueryParam("pageNum") int pageNum){
		try {
			DetachedCriteria dc = DetachedCriteria.forClass(Poem.class);
			dc.add(Restrictions.eq("authorId", authorId));
			dc.addOrder(Order.asc("title"));
			List<Poem> poems = poemDAO.getHibernateTemplate().findByCriteria(dc, (pageNum-1)*CommonSource.PAGESIZE, CommonSource.PAGESIZE);
			return jsonUtil.formatListToJson(poems, true);
		} catch (Exception e) {
			return jsonUtil.formatObjectToJson("", false);
		}
	}
	
	/**
	 * 搜索title，得到该title数量
	 * @param keyword
	 * @return 标题包含该关键词的所有诗歌数量（右包含）
	 */
	@GET
	@Path("getSearchTitleCount")
	@Produces("text/plain;charset=UTF-8")
	public String getSearchTitleCount(@QueryParam("keyword") String keyword){
		DetachedCriteria dc = DetachedCriteria.forClass(Poem.class);
		dc.add(Restrictions.like("title", keyword+"%"));
		try {
			int totle = poemDAO.getCountByCriteria(dc);
			return jsonUtil.formatObjectToJson(totle, true);
		} catch (Exception e) {
			return jsonUtil.formatObjectToJson("", false);
		}
	}
	/**
	 * 分页获取搜索的title
	 * @author Cesar
	 * @param keyword
	 * @param pageNum
	 * @return 标题包含该关键词的所有诗歌列表
	 */
	@GET
	@Path("searchTitle")
	@Produces("text/plain;charset=UTF-8")
	public String searchTitle(@QueryParam("keyword") String keyword,@QueryParam("pageNum") int pageNum){
		DetachedCriteria dc = DetachedCriteria.forClass(Poem.class);
		dc.add(Restrictions.like("title", keyword+"%"));
		List<Poem> poems = null;
		try {
			poems = poemDAO.getHibernateTemplate().findByCriteria(dc,(pageNum-1)*CommonSource.PAGESIZE, CommonSource.PAGESIZE);
			return jsonUtil.formatListToJson(poems, true);
		} catch (Exception e) {
			return jsonUtil.formatObjectToJson("", false);
		}
	}
	
	/**
	 * 通过诗歌内容搜索，返回包含该内容的诗歌总数
	 * @author Cesar
	 * @param keyword
	 * @return 包含该搜索词的诗歌总数
	 */
	@GET
	@Path("getSearchContentCount")
	@Produces("text/plain;charset=UTF-8")
	public String getSearchContentCount(@QueryParam("keyword") String keyword){
		DetachedCriteria dc = DetachedCriteria.forClass(Poem.class);
		dc.add(Restrictions.like("content", "%"+keyword+"%"));
		try {
			int totle = poemDAO.getCountByCriteria(dc);
			return jsonUtil.formatObjectToJson(totle, true);
		} catch (Exception e) {
			return jsonUtil.formatObjectToJson("", false);
		}
	}
	
	
	/**
	 * 通过诗歌内容搜索，返回包含该内容的诗歌
	 * @author Cesar
	 * @param keyword
	 * @param pageNum
	 * @return 包含该搜索词的诗歌
	 */
	@GET
	@Path("searchContent")
	@Produces("text/plain;charset=UTF-8")
	public String searchContent(@QueryParam("keyword") String keyword,@QueryParam("pageNum") int pageNum){
		DetachedCriteria dc = DetachedCriteria.forClass(Poem.class);
		dc.add(Restrictions.like("content", "%"+keyword+"%"));
		try {
			List<Poem> poems = poemDAO.getHibernateTemplate().findByCriteria(dc,(pageNum-1)*CommonSource.PAGESIZE, CommonSource.PAGESIZE);
			return jsonUtil.formatListToJson(poems, true);
		} catch (Exception e) {
			return jsonUtil.formatObjectToJson("", false);
		}
	}
	
	/**
	 * 获取为该标签的所有诗歌数量
	 * @author Cesar
	 * @param tag 标签名
	 * @return 
	 */
	@GET
	@Path("getPoemCountByTag")
	@Produces("text/plain;charset=UTF-8")
	public String getPoemCountByTag(@QueryParam("tag") String tag){
		try {
			DetachedCriteria dc = DetachedCriteria.forClass(PoemTagDetail.class);
			dc.add(Restrictions.eq("tag", tag));
			int t = poemTagDetailDAO.getCountByCriteria(dc);
			return jsonUtil.formatObjectToJson(t, true);
		} catch (Exception e) {
			return jsonUtil.formatObjectToJson("", false);
		}
	}
	/**
	 * 获取为该标签的所有诗歌
	 * @author Cesar
	 * @param tag 标签名
	 * @return 
	 */
	@GET
	@Path("getPoemsByTag")
	@Produces("text/plain;charset=UTF-8")
	public String getPoemsByTag(@QueryParam("tag") String tag,@QueryParam("pageNum") int pageNum){
		try {
			DetachedCriteria dc = DetachedCriteria.forClass(PoemTagDetail.class);
			dc.add(Restrictions.eq("tag", tag));
			List<PoemTagDetail> poemTagDetails = poemTagDetailDAO.getHibernateTemplate().findByCriteria(dc,(pageNum-1)*CommonSource.PAGESIZE, CommonSource.PAGESIZE);
			Set<Integer> poemIdSet = new HashSet<Integer>();
			for(PoemTagDetail poemTagDetail:poemTagDetails){
				poemIdSet.add(poemTagDetail.getPoemID());
			}
			DetachedCriteria dc1 = DetachedCriteria.forClass(Poem.class);
			dc1.add(Restrictions.in("id", poemIdSet));
			List<Poem> poems = (List<Poem>) poemDAO.getListByCriteria(dc1);
			return jsonUtil.formatListToJson(poems, true);
		} catch (Exception e) {
			return jsonUtil.formatObjectToJson("", false);
		}
	}
	
	@GET
	@Path("getAuthorByName")
	@Produces("text/plain;charset=UTF-8")
	public String getAuthorByName(@QueryParam("authorName") String authorName){
		
		try {
			Author author = (Author) authorDAO.findByAuthorName(authorName).get(0);
			return jsonUtil.formatObjectToJson(author, true);
		} catch (Exception e) {
			return jsonUtil.formatObjectToJson("", false);
		}
		
	}
}
