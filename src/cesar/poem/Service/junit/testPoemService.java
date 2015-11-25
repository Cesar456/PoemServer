package cesar.poem.Service.junit;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import cesar.poem.Service.PoemService;
import cesar.poem.bean.Poem;
import junit.framework.TestCase;

public class testPoemService extends TestCase {
	
	private String baseUrl = "http://localhost:8080/PoemServer/Poem/";

	private Gson gson = new Gson();
	private static DefaultHttpClient httpClient = new DefaultHttpClient();
	private static HttpResponse response;
	private static HttpGet get;
	private static HttpEntity entity;
	
	public void testmain() {
		t0901();
	}
	
	public void t0901(){
		getOneRandom();
	}
	
	public void t0831(){
		searchTitle();
		searchContent();
		getcountByTag();
	}
	
	public void t0829(){
		random();
		getById();
		getCountByAuthorId();
		getByAuthorIdAndPage();
	}
	
	public void random(){
	    get = new HttpGet(baseUrl+"getRandomPoem");
		try {
			response = httpClient.execute(get);
			entity = response.getEntity();
			Map<String, String> maps = gson.fromJson(EntityUtils.toString(entity), new TypeToken<Map<String, String>>(){}.getType());
			System.out.println(maps.get("code"));
			System.out.println(maps.get("msg"));
			System.out.println(maps.get("data").trim());
			List<Poem> poems = gson.fromJson(maps.get("data"), new TypeToken<List<Poem>>() {}.getType());
			System.out.println(poems.size());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void getById() {
		get = new HttpGet(baseUrl+"getPoemByPoemId?poemId=31483");
		try {
			response = httpClient.execute(get);
			entity = response.getEntity();
			Map<String, String> maps = gson.fromJson(EntityUtils.toString(entity), new TypeToken<Map<String, String>>(){}.getType());
			System.out.println(maps.get("code"));
			Poem poem = gson.fromJson(maps.get("data"), Poem.class);
			System.out.println(poem.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void getCountByAuthorId() {
		get = new HttpGet(baseUrl+"getPoemCountByAuthorId?authorId=293");
		try {
			response = httpClient.execute(get);
			entity = response.getEntity();
			Map<String, String> maps = gson.fromJson(EntityUtils.toString(entity), new TypeToken<Map<String, String>>(){}.getType());
			System.out.println(maps.get("code"));
			System.out.println(maps.get("data"));
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void getByAuthorIdAndPage() {
		get = new HttpGet(baseUrl+"getPoemByAuthorId?authorId=293&pageNum=1");
		try {
			response = httpClient.execute(get);
			entity = response.getEntity();
			Map<String, String> maps = gson.fromJson(EntityUtils.toString(entity), new TypeToken<Map<String, String>>(){}.getType());
			System.out.println(maps.get("code"));
			System.out.println(maps.get("data"));
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void searchTitle(){
		get = new HttpGet(baseUrl+"getSearchTitleCount?keyword=123123");
		getcontent(get);
		get = new HttpGet(baseUrl+"searchTitle?keyword=123123&pageNum=1");
		getcontentOfPoemList(get);
	}
	public void searchContent(){
		get = new HttpGet(baseUrl+"getSearchContentCount?keyword=123123123");
		getcontent(get);
		get = new HttpGet(baseUrl+"searchContent?keyword=123123123&pageNum=1");
		getcontentOfPoemList(get);
	}
	public void getcountByTag(){
		get = new HttpGet(baseUrl+"getPoemCountByTag?tag=伤感");
		getcontent(get);
		get = new HttpGet(baseUrl+"getPoemsByTag?tag=爱情");
		getcontentOfPoemList(get);
	}

	public void getOneRandom(){
		get = new HttpGet(baseUrl+"getOneRandomPoem");
		getOnePoem(get);
	}
	
	
	public Poem getOnePoem(HttpGet httpGet) {
		try {
			response = httpClient.execute(httpGet);
			entity = response.getEntity();
			Map<String, String> maps = gson.fromJson(EntityUtils.toString(entity), new TypeToken<Map<String, String>>(){}.getType());
			System.out.println(maps.get("code"));
			Poem poem = gson.fromJson(maps.get("data"), Poem.class);//new TypeToken<Poem>() {}.getType());
			System.out.println(poem.toString());
			return poem;
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Poem> getcontentOfPoemList(HttpGet httpGet){
		try {
			response = httpClient.execute(httpGet);
			entity = response.getEntity();
			Map<String, String> maps = gson.fromJson(EntityUtils.toString(entity), new TypeToken<Map<String, String>>(){}.getType());
			CharSequence charSequence = maps.get("data");
			System.out.println(maps.get("code"));
			List<Poem> poems = gson.fromJson(maps.get("data"), new TypeToken<List<Poem>>() {}.getType());
			System.out.println(poems.size());
			for(Poem poem:poems){
				Files.append(poem.toString()+"\r\n\r\n",new File("C:\\Users\\Cesar\\Desktop\\123.txt"), Charsets.UTF_8);
			}
			return poems;
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public void getcontent(HttpGet httpGet){
		try {
			response = httpClient.execute(httpGet);
			entity = response.getEntity();
			Map<String, String> maps = gson.fromJson(EntityUtils.toString(entity), new TypeToken<Map<String, String>>(){}.getType());
			CharSequence charSequence = maps.get("data");
			System.out.println(maps.get("code"));
			System.out.println(charSequence);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
