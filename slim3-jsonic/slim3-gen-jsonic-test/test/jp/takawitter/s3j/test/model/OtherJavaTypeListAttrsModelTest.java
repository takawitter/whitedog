package jp.takawitter.s3j.test.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import jp.takawitter.s3j.test.meta.OtherJavaTypeAttrsModelMeta;
import jp.takawitter.s3j.test.meta.OtherJavaTypeListAttrsModelMeta;
import jp.takawitter.s3j.test.model.OtherJavaTypeListAttrsModel.WeekDay;
import net.arnx.jsonic.JSON;

import org.junit.Assert;
import org.junit.Test;
import org.slim3.datastore.Datastore;

public class OtherJavaTypeListAttrsModelTest {
	@Test
	public void gen() throws Exception{
		Datastore.setGlobalCipherKey("0654813216578941");
		OtherJavaTypeListAttrsModel m = new OtherJavaTypeListAttrsModel();
		m.setStringListAttr(Arrays.asList("hello", "world"));
		DateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		m.setDateListAttr(Arrays.asList(
				f.parse("2010-11-11 11:11:11")
				, f.parse("2010-12-12 12:12:12")
				));
		m.setEnumListAttr(Arrays.asList(WeekDay.Sun, WeekDay.Mon, WeekDay.Tue));

		String json = OtherJavaTypeListAttrsModelMeta.get().modelToJson(m);
		System.out.println(json);
		JSON j = new JSON();
		j.setSuppressNull(true);
		System.out.println(j.format(m));

		Assert.assertEquals(
				"{\"dateListAttr\":[1289441471000,1292080332000]" +
				",\"enumListAttr\":[\"Sun\",\"Mon\",\"Tue\"]" +
				",\"stringListAttr\":[\"hello\",\"world\"]}"
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
