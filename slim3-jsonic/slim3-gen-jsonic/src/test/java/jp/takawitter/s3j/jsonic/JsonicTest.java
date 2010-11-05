package jp.takawitter.s3j.jsonic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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

	static class TestModel2{
		
		public Collection<Integer> getC() {
			return c;
		}

		public void setC(Collection<Integer> c) {
			this.c = c;
		}

		private Collection<Integer> c = new ArrayList<Integer>(){{
			add(1);
			add(2);
		}};
	}

	@Test
	public void test_encode_collection() throws Exception{
		TestModel2 model = new TestModel2();
		String expected = JSON.encode(model);
		Map<?, ?> map = JSON.decode(expected);
		System.out.println(expected);
		System.out.println(map.get("c").getClass().getName());
		System.out.println(((List<?>)map.get("c")).get(0).getClass().getName());
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
