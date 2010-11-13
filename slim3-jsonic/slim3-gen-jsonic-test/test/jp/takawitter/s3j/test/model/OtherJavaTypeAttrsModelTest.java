package jp.takawitter.s3j.test.model;

import java.text.SimpleDateFormat;

import jp.takawitter.s3j.test.meta.OtherJavaTypeAttrsModelMeta;
import jp.takawitter.s3j.test.model.OtherJavaTypeAttrsModel.WeekDay;
import net.arnx.jsonic.JSON;

import org.junit.Assert;
import org.junit.Test;
import org.slim3.datastore.Datastore;

public class OtherJavaTypeAttrsModelTest {
	@Test
	public void gen() throws Exception{
		Datastore.setGlobalCipherKey("0654813216578941");
		OtherJavaTypeAttrsModel m = new OtherJavaTypeAttrsModel();
		m.setStringAttr("hello");
		m.setEncryptedStringAttr("world");
		m.setDateAttr(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2010-11-11 11:11:11"));
		m.setEnumAttr(WeekDay.Sun);

		String json = OtherJavaTypeAttrsModelMeta.get().modelToJson(m);
		System.out.println(json);
		JSON j = new JSON();
		j.setSuppressNull(true);
		System.out.println(j.format(m));

		Assert.assertEquals(
				"{\"dateAttr\":1289441471000,\"encryptedStringAttr\":\"mMB4qZAgtBKJq0d1LBGTCA==\"" +
				",\"enumAttr\":\"Sun\",\"stringAttr\":\"hello\"}"
				, json
				);
	}

	@Test
	public void gen_null(){
		OtherJavaTypeAttrsModel t = new OtherJavaTypeAttrsModel();
		String json = OtherJavaTypeAttrsModelMeta.get().modelToJson(t);
		Assert.assertEquals("{}", json);
	}
}
