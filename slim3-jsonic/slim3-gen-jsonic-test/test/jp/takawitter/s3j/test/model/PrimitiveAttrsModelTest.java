package jp.takawitter.s3j.test.model;

import jp.takawitter.s3j.test.meta.PrimitiveAttrsModelMeta;
import junit.framework.Assert;
import net.arnx.jsonic.JSON;

import org.junit.Test;

public class PrimitiveAttrsModelTest {
	@Test
	public void gen() throws Exception{
		PrimitiveAttrsModel m = new PrimitiveAttrsModel();
		m.setBooleanAttr(true);
		m.setShortAttr((short)100);
		m.setIntAttr(1000);
		m.setLongAttr(10000);
		m.setFloatAttr(1.1f);
		m.setDoubleAttr(11.1);
		String json = PrimitiveAttrsModelMeta.get().modelToJson(m);
		System.out.println(json);
		JSON j = new JSON();
		j.setSuppressNull(true);
		System.out.println(j.format(m));
		Assert.assertEquals(
				"{\"booleanAttr\":true,\"doubleAttr\":11.1" +
				",\"floatAttr\":1.1,\"intAttr\":1000" +
				",\"longAttr\":10000,\"shortAttr\":100}"
				, json
				);
	}
}
