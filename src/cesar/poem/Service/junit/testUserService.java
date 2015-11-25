package cesar.poem.Service.junit;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import junit.framework.TestCase;

public class testUserService extends TestCase {

	public void testUserService() {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost post = new HttpPost("http://123.56.162.157:8080/PoemServer/User/register");
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("userEmail", "234"));
			nvps.add(new BasicNameValuePair("password", "123"));
			try {
				post.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
				HttpResponse response = httpClient.execute(post);
				HttpEntity entity = response.getEntity();
				System.out.println(EntityUtils.toString(entity));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
}
