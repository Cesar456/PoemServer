package cesar.poem.DAO.test;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cesar.poem.DAO.AuthorDAO;
import cesar.poem.bean.Author;


public class AuthorDAOTest {
	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		AuthorDAO authorDAO = (AuthorDAO) applicationContext.getBean("AuthorDAO");
		List<Author> authors = (List<Author>) authorDAO.findByAuthorName("���");
		System.out.println(authors.get(0).getId());
		System.out.println(authors.size());
	}
}
