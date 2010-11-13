package jp.takawitter.s3j.test.model;

import jp.takawitter.s3j.test.meta.WrapperAttrsModelMeta;
import junit.framework.Assert;
import net.arnx.jsonic.JSON;

import org.junit.Test;

public class WrapperAttrsModelTest {
	@Test
	public void gen() throws Exception{
		WrapperAttrsModel m = new WrapperAttrsModel();
		m.setBooleanAttr(true);
		m.setShortAttr((short)100);
		m.setIntegerAttr(1000);
		m.setLongAttr(10000L);
		m.setFloatAttr(1.1f);
		m.setDoubleAttr(11.1);
		String json = WrapperAttrsModelMeta.get().modelToJson(m);
		System.out.println(json);
		JSON j = new JSON();
		j.setSuppressNull(true);
		System.out.println(j.format(m));
		Assert.assertEquals(
				"{\"booleanAttr\":true,\"doubleAttr\":11.1" +
				",\"floatAttr\":1.1,\"integerAttr\":1000" +
				",\"longAttr\":10000,\"shortAttr\":100}"
				, json
				);
	}

	@Test
	public void gen_null() throws Exception{
		WrapperAttrsModel m = new WrapperAttrsModel();
		String json = WrapperAttrsModelMeta.get().modelToJson(m);
		Assert.assertEquals("{}", json);
	}

	@Test
	public void gen_short() throws Exception{
		WrapperAttrsModel m = new WrapperAttrsModel();
		m.setShortAttr((short)1);
		String json = WrapperAttrsModelMeta.get().modelToJson(m);
		Assert.assertEquals("{\"shortAttr\":1}", json);
	}
}
