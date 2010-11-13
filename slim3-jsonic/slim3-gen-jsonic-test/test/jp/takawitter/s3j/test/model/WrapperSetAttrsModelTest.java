package jp.takawitter.s3j.test.model;

import java.util.Arrays;
import java.util.HashSet;

import jp.takawitter.s3j.test.meta.WrapperSetAttrsModelMeta;
import junit.framework.Assert;
import net.arnx.jsonic.JSON;

import org.junit.Test;

public class WrapperSetAttrsModelTest {
	@Test
	public void gen(){
		WrapperSetAttrsModelMeta m = WrapperSetAttrsModelMeta.get();
		WrapperSetAttrsModel model = new WrapperSetAttrsModel();
		model.setBooleanSetAttr(new HashSet<Boolean>(Arrays.asList(true,false,true)));
		model.setShortSetAttr(new HashSet<Short>(Arrays.asList((short)9, (short)8, (short)7)));
		model.setIntegerSetAttr(new HashSet<Integer>(Arrays.asList(9, 8, 7)));
		model.setLongSetAttr(new HashSet<Long>(Arrays.asList(9L, 8L, 7L)));
		model.setFloatSetAttr(new HashSet<Float>(Arrays.asList(9.9f, 8.8f, 7.7f)));
		model.setDoubleSetAttr(new HashSet<Double>(Arrays.asList(9.9, 8.8, 7.7)));

		String json = m.modelToJson(model);
		System.out.println(json);
		JSON j = new JSON();
		j.setSuppressNull(true);
		System.out.println(j.format(model));

		Assert.assertEquals(
				"{\"booleanSetAttr\":[false,true],\"doubleSetAttr\":[9.9,8.8,7.7]" +
				",\"floatSetAttr\":[8.8,7.7,9.9],\"integerSetAttr\":[7,8,9]" +
				",\"longSetAttr\":[7,8,9],\"shortSetAttr\":[7,8,9]}"
				, json);
	}

	@Test
	public void gen_null() throws Exception{
		WrapperSetAttrsModel m = new WrapperSetAttrsModel();
		String json = WrapperSetAttrsModelMeta.get().modelToJson(m);
		Assert.assertEquals("{}", json);
	}
}
