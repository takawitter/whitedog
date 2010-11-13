package jp.takawitter.s3j.test.model;

import java.util.Arrays;
import java.util.TreeSet;

import jp.takawitter.s3j.test.meta.WrapperSortedSetAttrsModelMeta;
import junit.framework.Assert;
import net.arnx.jsonic.JSON;

import org.junit.Test;

public class WrapperSortedSetAttrsModelTest {
	@Test
	public void gen(){
		WrapperSortedSetAttrsModelMeta m = WrapperSortedSetAttrsModelMeta.get();
		WrapperSortedSetAttrsModel model = new WrapperSortedSetAttrsModel();
		model.setBooleanSortedSetAttr(new TreeSet<Boolean>(Arrays.asList(true,false,true)));
		model.setShortSortedSetAttr(new TreeSet<Short>(Arrays.asList((short)9, (short)8, (short)7)));
		model.setIntegerSortedSetAttr(new TreeSet<Integer>(Arrays.asList(9, 8, 7)));
		model.setLongSortedSetAttr(new TreeSet<Long>(Arrays.asList(9L, 8L, 7L)));
		model.setFloatSortedSetAttr(new TreeSet<Float>(Arrays.asList(9.9f, 8.8f, 7.7f)));
		model.setDoubleSortedSetAttr(new TreeSet<Double>(Arrays.asList(9.9, 8.8, 7.7)));

		String json = m.modelToJson(model);
		System.out.println(json);
		JSON j = new JSON();
		j.setSuppressNull(true);
		System.out.println(j.format(model));

		Assert.assertEquals(
				"{\"booleanSortedSetAttr\":[false,true],\"doubleSortedSetAttr\":[7.7,8.8,9.9]" +
				",\"floatSortedSetAttr\":[7.7,8.8,9.9],\"integerSortedSetAttr\":[7,8,9]" +
				",\"longSortedSetAttr\":[7,8,9],\"shortSortedSetAttr\":[7,8,9]}"
				, json);
	}

	@Test
	public void gen_null() throws Exception{
		WrapperSortedSetAttrsModel m = new WrapperSortedSetAttrsModel();
		String json = WrapperSortedSetAttrsModelMeta.get().modelToJson(m);
		Assert.assertEquals("{}", json);
	}
}
