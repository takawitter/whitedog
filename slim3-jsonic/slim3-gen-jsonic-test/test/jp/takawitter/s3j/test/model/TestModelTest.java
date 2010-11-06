package jp.takawitter.s3j.test.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.TreeSet;

import jp.takawitter.s3j.test.meta.TestModelMeta;

import org.junit.Assert;
import org.junit.Test;
import org.slim3.datastore.Datastore;

public class TestModelTest {
	@Test
	public void test(){
		TestModelMeta m = TestModelMeta.get();
		Datastore.setGlobalCipherKey("0654813216578941");
		TestModel model = new TestModel();
		model.setBooleanProp(true);
		model.setShortProp((short)9);
		model.setIntProp(99);
		model.setLongProp(999);
		model.setFloatProp(9.9f);
		model.setDoubleProp(9.99);
		model.setWrapperBooleanProp(true);
		model.setWrapperShortProp((short)9);
		model.setWrapperIntegerProp(99);
		model.setWrapperLongProp(999L);
		model.setWrapperFloatProp(9.9f);
		model.setWrapperDoubleProp(9.99);
		model.setShortListProp(Arrays.asList((short)9, (short)8, (short)7));
		model.setIntegerListProp(Arrays.asList(9, 8, 7));
		model.setLongListProp(Arrays.asList(9L, 8L, 7L));
		model.setFloatListProp(Arrays.asList(9.9f, 8.8f, 7.7f));
		model.setDoubleListProp(Arrays.asList(9.9, 8.8, 7.7));
		model.setShortSetProp(new HashSet<Short>(Arrays.asList((short)9, (short)8, (short)7)));
		model.setIntegerSetProp(new HashSet<Integer>(Arrays.asList(9, 8, 7)));
		model.setLongSetProp(new HashSet<Long>(Arrays.asList(9L, 8L, 7L)));
		model.setFloatSetProp(new HashSet<Float>(Arrays.asList(9.9f, 8.8f, 7.7f)));
		model.setDoubleSetProp(new HashSet<Double>(Arrays.asList(9.9, 8.8, 7.7)));
		model.setShortSortedSetProp(new TreeSet<Short>(Arrays.asList((short)9, (short)8, (short)7)));
		model.setIntegerSortedSetProp(new TreeSet<Integer>(Arrays.asList(9, 8, 7)));
		model.setLongSortedSetProp(new TreeSet<Long>(Arrays.asList(9L, 8L, 7L)));
		model.setFloatSortedSetProp(new TreeSet<Float>(Arrays.asList(9.9f, 8.8f, 7.7f)));
		model.setDoubleSortedSetProp(new TreeSet<Double>(Arrays.asList(9.9, 8.8, 7.7)));
//		model.setKey(Datastore.createKey(m, 100));
		String json = m.modelToJson(model);
		System.out.println(json);
		System.out.println(net.arnx.jsonic.JSON.encode(model));
		TestModel model2 = m.jsonToModel(json);
		Assert.assertEquals(model.isBooleanProp(), model2.isBooleanProp());
		Assert.assertEquals(model.getShortProp(), model2.getShortProp());
		Assert.assertEquals(model.getIntProp(), model2.getIntProp());
		Assert.assertArrayEquals(
				model.getShortListProp().toArray()
				, model2.getShortListProp().toArray()
				);
		Assert.assertArrayEquals(
				model.getIntegerListProp().toArray()
				, model2.getIntegerListProp().toArray()
				);
		Assert.assertArrayEquals(
				model.getLongListProp().toArray()
				, model2.getLongListProp().toArray()
				);
		Assert.assertArrayEquals(
				model.getFloatListProp().toArray()
				, model2.getFloatListProp().toArray()
				);
		Assert.assertArrayEquals(
				model.getDoubleListProp().toArray()
				, model2.getDoubleListProp().toArray()
				);
		Assert.assertArrayEquals(
				new TreeSet<Short>(model.getShortSetProp()).toArray()
				, new TreeSet<Short>(model2.getShortSetProp()).toArray()
				);
		Assert.assertArrayEquals(
				new TreeSet<Integer>(model.getIntegerSetProp()).toArray()
				, new TreeSet<Integer>(model2.getIntegerSetProp()).toArray()
				);
		Assert.assertArrayEquals(
				new TreeSet<Long>(model.getLongSetProp()).toArray()
				, new TreeSet<Long>(model2.getLongSetProp()).toArray()
				);
		Assert.assertArrayEquals(
				new TreeSet<Float>(model.getFloatSetProp()).toArray()
				, new TreeSet<Float>(model2.getFloatSetProp()).toArray()
				);
		Assert.assertArrayEquals(
				new TreeSet<Double>(model.getDoubleSetProp()).toArray()
				, new TreeSet<Double>(model2.getDoubleSetProp()).toArray()
				);
		Assert.assertArrayEquals(
				new TreeSet<Short>(model.getShortSortedSetProp()).toArray()
				, new TreeSet<Short>(model2.getShortSortedSetProp()).toArray()
				);
		Assert.assertArrayEquals(
				new TreeSet<Integer>(model.getIntegerSortedSetProp()).toArray()
				, new TreeSet<Integer>(model2.getIntegerSortedSetProp()).toArray()
				);
		Assert.assertArrayEquals(
				new TreeSet<Long>(model.getLongSortedSetProp()).toArray()
				, new TreeSet<Long>(model2.getLongSortedSetProp()).toArray()
				);
		Assert.assertArrayEquals(
				new TreeSet<Float>(model.getFloatSortedSetProp()).toArray()
				, new TreeSet<Float>(model2.getFloatSortedSetProp()).toArray()
				);
		Assert.assertArrayEquals(
				new TreeSet<Double>(model.getDoubleSortedSetProp()).toArray()
				, new TreeSet<Double>(model2.getDoubleSortedSetProp()).toArray()
				);
		Assert.assertEquals(model.getLongProp(), model2.getLongProp());
		Assert.assertEquals(model.getFloatProp(), model2.getFloatProp(), 0);
		Assert.assertEquals(model.getDoubleProp(), model2.getDoubleProp(), 0);
		Assert.assertEquals(model.getStringProp(), model2.getStringProp());
		Assert.assertEquals(model.getEncryptedStringProp(), model2.getEncryptedStringProp());
		Assert.assertEquals(model.getWrapperBooleanProp(), model2.getWrapperBooleanProp());
		Assert.assertEquals(model.getWrapperShortProp(), model2.getWrapperShortProp());
		Assert.assertEquals(model.getWrapperIntegerProp(), model2.getWrapperIntegerProp());
		Assert.assertEquals(model.getWrapperLongProp(), model2.getWrapperLongProp());
		Assert.assertEquals(model.getWrapperFloatProp(), model2.getWrapperFloatProp());
		Assert.assertEquals(model.getWrapperDoubleProp(), model2.getWrapperDoubleProp());
	}
}
