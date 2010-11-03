package jp.takawitter.s3j.test.model;

import jp.takawitter.s3j.test.meta.TestModelMeta;
import junit.framework.Assert;

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
		Assert.assertEquals(model.isBoolProp(), model2.isBoolProp());
		Assert.assertEquals(model.getShortProp(), model2.getShortProp());
		Assert.assertEquals(model.getIntProp(), model2.getIntProp());
		Assert.assertEquals(model.getLongProp(), model2.getLongProp());
		Assert.assertEquals(model.getFloatProp(), model2.getFloatProp());
		Assert.assertEquals(model.getDoubleProp(), model2.getDoubleProp());
		Assert.assertEquals(model.getStringProp(), model2.getStringProp());
		Assert.assertEquals(model.getEncryptedStringProp(), model2.getEncryptedStringProp());
	}
}
