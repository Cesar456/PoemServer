package cesar.poem.DAO.test;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cesar.poem.DAO.PoemDAO;
import cesar.poem.bean.Poem;

public class PoemDAOTest {
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		PoemDAO poemDAO = (PoemDAO) context.getBean("PoemDAO");
		Poem poem = poemDAO.findById(1454);
		System.out.println(poem.getTitle());
		List<Poem> poems = (List<Poem>) poemDAO.findAll();
		System.out.println(poems.size());
	}

}
