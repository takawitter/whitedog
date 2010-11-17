package jp.takawitter.s3j.test.model;

import java.net.URL;

import jp.takawitter.s3j.test.meta.AppEngineTypeAttrsModelMeta;
import net.arnx.jsonic.JSON;

import org.junit.Assert;
import org.junit.Test;
import org.slim3.datastore.Datastore;
import org.slim3.tester.TestEnvironment;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Category;
import com.google.appengine.api.datastore.Email;
import com.google.appengine.api.datastore.GeoPt;
import com.google.appengine.api.datastore.IMHandle;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Link;
import com.google.appengine.api.datastore.PhoneNumber;
import com.google.appengine.api.datastore.PostalAddress;
import com.google.appengine.api.datastore.Rating;
import com.google.appengine.api.datastore.ShortBlob;
import com.google.appengine.api.datastore.Text;
import com.google.apphosting.api.ApiProxy;

public class AppEngineTypeAttrsModelTest {
	@Test
	public void gen() throws Exception{
		Datastore.setGlobalCipherKey("0654813216578941");
		ApiProxy.setEnvironmentForCurrentThread(new TestEnvironment(){
			@Override
			public String getAppId() {
				return "slim3-gen";
			}
		});
		AppEngineTypeAttrsModel m = new AppEngineTypeAttrsModel();
		m.setKey(KeyFactory.createKey("test", 1000));
		m.setBlobKeyAttr(new BlobKey("Q3PqkweYlb4iWpp0BVw"));
		m.setCategoryAttr(new Category("partOfSpeech"));
		m.setEmailAttr(new Email("takawitter@test.com"));
		m.setBlobAttr(new Blob("hello".getBytes()));
		m.setGeoPtAttr(new GeoPt(10, 10));
		m.setImHandleAttr1(new IMHandle(IMHandle.Scheme.xmpp, "handle"));
		m.setImHandleAttr2(new IMHandle(new URL("http://aim.com"), "network"));
		m.setLinkAttr(new Link("link"));
		m.setPhoneNumberAttr(new PhoneNumber("000-0000-0000"));
		m.setPostalAddressAttr(new PostalAddress("address"));
		m.setRatingAttr(new Rating(70));
		m.setShortBlobAttr(new ShortBlob("hello".getBytes()));
		m.setTextAttr(new Text("hello"));
		m.setEncryptedTextAttr(new Text("hello"));

		IMHandle h1 = new IMHandle(IMHandle.Scheme.xmpp, "handle");
		IMHandle h2 = new IMHandle(new URL("http://aim.com"), "network");
		IMHandle[] hs = {h1, h2};
		for(IMHandle h : hs){
			if(h.getProtocol() != null){
				System.out.println("{\"protocol\":\"" + h.getProtocol() + "\",\"address\":\"" + h.getAddress() + "\"}");
			} else{
				System.out.println("address: " + h.getAddress());
			}
		}
		String json = AppEngineTypeAttrsModelMeta.get().modelToJson(m);
		System.out.println(json);
		JSON j = new JSON();
		j.setSuppressNull(true);
		System.out.println(j.format(m));

		Assert.assertEquals(
				"{" +
				"\"blobAttr\":\"aGVsbG8=\"" +
				",\"blobKeyAttr\":\"Q3PqkweYlb4iWpp0BVw\",\"categoryAttr\":\"partOfSpeech\"" +
				",\"emailAttr\":\"takawitter@test.com\"" +
				",\"encryptedTextAttr\":\"eeRXmeJQOo8HbwTHJ+R+WQ==\"" +
				",\"geoPtAttr\":{\"latitude\":10.0,\"longitude\":10.0}" +
				",\"imHandleAttr1\":{\"address\":\"handle\",\"protocol\":\"xmpp\"}" +
				",\"imHandleAttr2\":{\"address\":\"network\",\"protocol\":\"http://aim.com\"}" +
				",\"key\":\"aglzbGltMy1nZW5yCwsSBHRlc3QY6AcM\"" +
				",\"linkAttr\":\"link\",\"phoneNumberAttr\":\"000-0000-0000\"" +
				",\"postalAddressAttr\":\"address\",\"ratingAttr\":70" +
				",\"shortBlobAttr\":\"aGVsbG8=\"" +
				",\"textAttr\":\"hello\"" +
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
