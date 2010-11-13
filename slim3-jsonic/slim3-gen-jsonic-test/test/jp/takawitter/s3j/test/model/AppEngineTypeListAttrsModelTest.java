package jp.takawitter.s3j.test.model;

import java.util.Arrays;

import jp.takawitter.s3j.test.meta.AppEngineTypeListAttrsModelMeta;
import net.arnx.jsonic.JSON;

import org.junit.Assert;
import org.junit.Test;
import org.slim3.tester.TestEnvironment;

import com.google.appengine.api.datastore.Email;
import com.google.apphosting.api.ApiProxy;

public class AppEngineTypeListAttrsModelTest {
	@Test
	public void gen() throws Exception{
		ApiProxy.setEnvironmentForCurrentThread(new TestEnvironment(){
			@Override
			public String getAppId() {
				return "slim3-gen";
			}
		});
		AppEngineTypeListAttrsModel m = new AppEngineTypeListAttrsModel();
		m.setEmailListAttr(Arrays.asList(new Email("a@b.com"), new Email("d@e.com")));
/*
		t.setEmailAttr(new Email("takawitter@test.com"));
		t.setBlobAttr(new Blob("hello".getBytes()));
		t.setCategoryAttr(new Category("noun"));
		t.setGeoPtAttr(new GeoPt(10, 10));
		t.setImHandleAttr(new IMHandle(IMHandle.Scheme.xmpp, "handle"));
		t.setImHandleAttr(new IMHandle(new URL("http://aim.com"), "network"));
*/
//		System.out.println(Base64.encode("hello".getBytes()));
//		t.setShortBlobAttr(new ShortBlob("hello".getBytes()));

		String json = AppEngineTypeListAttrsModelMeta.get().modelToJson(m);
		System.out.println(json);
		JSON j = new JSON();
		j.setSuppressNull(true);
		System.out.println(j.format(m));

		Assert.assertEquals("{}", json);
	}

	@Test
	public void gen_null(){
		AppEngineTypeListAttrsModel t = new AppEngineTypeListAttrsModel();
		String json = AppEngineTypeListAttrsModelMeta.get().modelToJson(t);
		Assert.assertEquals("{}", json);
	}
}
