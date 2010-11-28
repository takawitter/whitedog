package jp.takawitter.s3j.test;

import org.junit.Assert;
import org.junit.Test;

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
}
