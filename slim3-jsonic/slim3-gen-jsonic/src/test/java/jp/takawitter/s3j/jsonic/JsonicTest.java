package jp.takawitter.s3j.jsonic;

import java.util.Map;

import net.arnx.jsonic.JSON;
import junit.framework.Assert;

import org.junit.Test;

public class JsonicTest {
	@Test
	public void test_encode() throws Exception{
		TestModel model = new TestModel();
		String actual = ReferenceEncoder.encode(model);
		String expected = JSON.encode(model);
		System.out.println(actual);
		System.out.println(expected);
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void test_decode() throws Exception{
		Map<?, ?> map = JSON.decode("{\"name\":\"hello\"}");
		if(map.containsKey("name")){
			String name = (String)map.get("name");
			System.out.println(name);
		}
	}
}
