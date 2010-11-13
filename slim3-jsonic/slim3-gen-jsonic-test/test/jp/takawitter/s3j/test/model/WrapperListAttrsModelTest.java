package jp.takawitter.s3j.test.model;

import java.util.Arrays;

import jp.takawitter.s3j.test.meta.WrapperListAttrsModelMeta;
import junit.framework.Assert;
import net.arnx.jsonic.JSON;

import org.junit.Test;

public class WrapperListAttrsModelTest {
	@Test
	public void gen(){
		WrapperListAttrsModelMeta m = WrapperListAttrsModelMeta.get();
		WrapperListAttrsModel model = new WrapperListAttrsModel();
		model.setBooleanListAttr(Arrays.asList(true, false, true));
		model.setShortListAttr(Arrays.asList((short)9, (short)8, (short)7));
		model.setIntegerListAttr(Arrays.asList(9, 8, 7));
		model.setLongListAttr(Arrays.asList(9L, 8L, 7L));
		model.setFloatListAttr(Arrays.asList(9.9f, 8.8f, 7.7f));
		model.setDoubleListAttr(Arrays.asList(9.9, 8.8, 7.7));
		String json = m.modelToJson(model);
		System.out.println(json);
		JSON j = new JSON();
		j.setSuppressNull(true);
		System.out.println(j.format(model));
		Assert.assertEquals(
				"{\"booleanListAttr\":[true,false,true],\"doubleListAttr\":[9.9,8.8,7.7],\"floatListAttr\":[9.9,8.8,7.7]" +
				",\"integerListAttr\":[9,8,7],\"longListAttr\":[9,8,7]" +
				",\"shortListAttr\":[9,8,7]}"
				, json);
	}

	@Test
	public void gen_null() throws Exception{
		WrapperListAttrsModel m = new WrapperListAttrsModel();
		String json = WrapperListAttrsModelMeta.get().modelToJson(m);
		Assert.assertEquals("{}", json);
	}
}
