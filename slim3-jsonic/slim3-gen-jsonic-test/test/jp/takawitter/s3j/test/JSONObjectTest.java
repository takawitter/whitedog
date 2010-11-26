package jp.takawitter.s3j.test;

import org.junit.Assert;
import org.junit.Test;

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
		Assert.assertNull(j.get("attr"));
	}
}
