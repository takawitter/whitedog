package jp.takawitter.s3j.test;

import junit.framework.Assert;

import org.junit.Test;

public class EnumTest {
	enum TestEnum{
		ONE,TWO;
	}

	@Test
	public void test(){
		Assert.assertEquals(TestEnum.TWO, Enum.valueOf(TestEnum.class, "TWO"));
	}
}
