package jp.takawitter.s3j.test;

import org.junit.Assert;
import org.junit.Test;

import com.google.appengine.repackaged.org.json.JSONArray;
import com.google.appengine.repackaged.org.json.JSONException;
import com.google.appengine.repackaged.org.json.JSONObject;

public class JSONObjectTest {
	@Test
	public void has() throws Exception{
		JSONObject j = new JSONObject("{}");
		Assert.assertFalse(j.has("attr"));
	}

	@Test
	public void nullTest() throws Exception{
		JSONObject j = new JSONObject("{}");
		try{
			Assert.assertNull(j.get("attr"));
			Assert.fail();
		} catch(JSONException e){
		}
	}

	@Test
	public void optInt() throws Exception{
		JSONObject j = new JSONObject("{str: \"hello\", int: 3}");
		Assert.assertEquals(0, j.optInt("v"));
		Assert.assertEquals(0, j.optInt("str"));
		Assert.assertEquals("hello", j.optString("str"));
		Assert.assertEquals("3", j.optString("int"));
	}

	@Test
	public void wrapperIntValueTest() throws Exception{
		JSONObject j = new JSONObject("{value: 1}");
		Assert.assertEquals((Integer)1, j.get("value"));
		try{
			j.getBoolean("value");
			Assert.fail();
		} catch(JSONException e){
		}
		Assert.assertEquals(1, j.getDouble("value"), 0.1);
		Assert.assertEquals(1, j.getInt("value"));
		Assert.assertEquals(1, j.getLong("value"));
		Assert.assertEquals("1", j.getString("value"));
	}

	@Test
	public void wrapperFloatValueTest() throws Exception{
		JSONObject j = new JSONObject("{value: 1.1}");
		Assert.assertEquals((Double)1.1, j.get("value"));
		try{
			j.getBoolean("value");
			Assert.fail();
		} catch(JSONException e){
		}
		Assert.assertEquals(1.1, j.getDouble("value"), 0.1);
		Assert.assertEquals(1, j.getInt("value"));
		Assert.assertEquals(1, j.getLong("value"));
		Assert.assertEquals("1.1", j.getString("value"));
	}

	@Test
	public void wrapperListArrayTest() throws Exception{
		JSONObject j = new JSONObject("{\"values\":[1,2,3]}");
		JSONArray array = j.getJSONArray("values");
		Assert.assertEquals(3, array.length());
		Assert.assertEquals("1", array.getString(0));
		Assert.assertEquals("2", array.getString(1));
		Assert.assertEquals("3", array.getString(2));
	}
}
