package jp.takawitter.s3j.test.model;

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
