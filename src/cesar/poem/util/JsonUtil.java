package cesar.poem.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cesar.poem.bean.Poem;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtil {
	
	private Gson gson = new Gson();
	
	public String formatObjectToJson(Object data, boolean succ){
		Map<String,Object> map = new HashMap<String,Object>(3);
		if(succ){
			map.put("code", "200");
			map.put("msg", "success");
			map.put("data", gson.toJson(data));
		}
		else{
			map.put("code", "400");
			map.put("msg", "error ");
		}
		return gson.toJson(map);
	}
	public String formatListToJson(List list,boolean succ){
		Map<String,Object> map = new HashMap<String,Object>(3);
		if(succ){
			map.put("code", "200");
			map.put("msg", "success");
			map.put("data", gson.toJson(list).trim());
		}
		else{
			map.put("code", "400");
			map.put("msg", "error ");
		}
		return gson.toJson(map);
	}
}
