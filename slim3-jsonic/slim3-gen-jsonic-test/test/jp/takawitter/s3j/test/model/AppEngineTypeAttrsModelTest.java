package jp.takawitter.s3j.test.model;

import jp.takawitter.s3j.test.meta.AppEngineTypeAttrsModelMeta;
import net.arnx.jsonic.JSON;

import org.junit.Assert;
import org.junit.Test;
import org.slim3.tester.TestEnvironment;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.Category;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.apphosting.api.ApiProxy;

public class AppEngineTypeAttrsModelTest {
	@Test
	public void gen() throws Exception{
		ApiProxy.setEnvironmentForCurrentThread(new TestEnvironment(){
			@Override
			public String getAppId() {
				return "slim3-gen";
			}
		});
		AppEngineTypeAttrsModel t = new AppEngineTypeAttrsModel();
		t.setKey(KeyFactory.createKey("test", 1000));
		t.setBlobKeyAttr(new BlobKey("AMIfv95A29oaShnIqBEzpYPCwuPThrxXPpyj2snlR-SU8-tmqKSCjvXv8XOh0oMWXzJLbMig81GVIdVjvMnLENdgY1nqMyicj3XPMPrk8HOZxugHzsu64C85sbR0Cq0-C3flit6jl59SzSYQ3PqkweYlb4iWpp0BVw"));
		t.setCategoryAttr(new Category("partOfSpeech"));
/*
		t.setEmailAttr(new Email("takawitter@test.com"));
		t.setBlobAttr(new Blob("hello".getBytes()));
		t.setGeoPtAttr(new GeoPt(10, 10));
		t.setImHandleAttr(new IMHandle(IMHandle.Scheme.xmpp, "handle"));
		t.setImHandleAttr(new IMHandle(new URL("http://aim.com"), "network"));
*/
//		System.out.println(Base64.encode("hello".getBytes()));
//		t.setShortBlobAttr(new ShortBlob("hello".getBytes()));

		String json = AppEngineTypeAttrsModelMeta.get().modelToJson(t);
		System.out.println(json);
		JSON j = new JSON();
		j.setSuppressNull(true);
		System.out.println(j.format(t));

		Assert.assertEquals(
				"{" +
				"\"blobKeyAttr\":\"AMIfv95A29oaShnIqBEzpYPCwuPThrxXPpyj2snlR-SU8-tmqKSCjvXv8XOh0oMWXzJLbMig81GVIdVjvMnLENdgY1nqMyicj3XPMPrk8HOZxugHzsu64C85sbR0Cq0-C3flit6jl59SzSYQ3PqkweYlb4iWpp0BVw\"" +
				",\"categoryAttr\":\"partOfSpeech\"" +
				",\"key\":\"aglzbGltMy1nZW5yCwsSBHRlc3QY6AcM\"" +
				"}"
				, json
				);
	}

	@Test
	public void gen_null(){
		AppEngineTypeAttrsModel t = new AppEngineTypeAttrsModel();
		String json = AppEngineTypeAttrsModelMeta.get().modelToJson(t);
		Assert.assertEquals("{}", json);
	}

	@Test
	public void keyTest(){
		Key key = KeyFactory.stringToKey("aglzbGltMy1nZW5yCwsSBHRlc3QY6AcM");
		Assert.assertEquals("test", key.getKind());
		Assert.assertEquals(1000, key.getId());
	}
}
