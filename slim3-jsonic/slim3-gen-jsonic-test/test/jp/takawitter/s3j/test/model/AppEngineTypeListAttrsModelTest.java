package jp.takawitter.s3j.test.model;

import java.net.URL;
import java.util.Arrays;

import jp.takawitter.s3j.test.meta.AppEngineTypeListAttrsModelMeta;
import net.arnx.jsonic.JSON;

import org.junit.Assert;
import org.junit.Test;
import org.slim3.tester.TestEnvironment;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Category;
import com.google.appengine.api.datastore.Email;
import com.google.appengine.api.datastore.GeoPt;
import com.google.appengine.api.datastore.IMHandle;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Link;
import com.google.appengine.api.datastore.PhoneNumber;
import com.google.appengine.api.datastore.PostalAddress;
import com.google.appengine.api.datastore.Rating;
import com.google.appengine.api.datastore.ShortBlob;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.users.User;
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
		m.setBlobKeyListAttr(Arrays.asList(new BlobKey("lkwejl2k3jrksl"), new BlobKey("kaekl23joij")));
		m.setBlobListAttr(Arrays.asList(new Blob("hello".getBytes()), new Blob("world".getBytes())));
		m.setCategoryListAttr(Arrays.asList(new Category("partOfSpeech"), new Category("kind")));
		m.setGeoPtListAttr(Arrays.asList(new GeoPt(1.0f, 2.0f), new GeoPt(3.0f, 4.0f)));
		m.setImHandleListAttr(Arrays.asList(
				new IMHandle(IMHandle.Scheme.xmpp, "address")
				, new IMHandle(new URL("http://test"), "address")
				));
		m.setKeyListAttr(Arrays.asList(
				KeyFactory.createKey("kind", "name")
				, KeyFactory.createKey("kind", 1)
				));
		m.setLinkListAttr(Arrays.asList(new Link("link"), new Link("external")));
		m.setPhoneNumberListAttr(Arrays.asList(
				new PhoneNumber("000-0000-0000")
				, new PhoneNumber("111-1111-1111")
				));
		m.setPostalAddressListAttr(Arrays.asList(
				new PostalAddress("111-1111")
				, new PostalAddress("222-2222")
				));
		m.setRatingListAttr(Arrays.asList(
				new Rating(80), new Rating(90)
				));
		m.setShortBlobListAttr(Arrays.asList(
				new ShortBlob("hello".getBytes()), new ShortBlob("world".getBytes())
				));
		m.setTextListAttr(Arrays.asList(
				new Text("hello"), new Text("world")
				));
		m.setUserListAttr(Arrays.asList(
				new User("email", "authDomain"), new User("email", "authDomain", "userId")
				, new User("email", "authDomain", "userId", "federatedId")
				));

		String json = AppEngineTypeListAttrsModelMeta.get().modelToJson(m);
		System.out.println(json);
		JSON j = new JSON();
		j.setSuppressNull(true);
		System.out.println(j.format(m));
		JSON.decode(json);

		Assert.assertEquals(
				"{\"blobKeyListAttr\":[\"lkwejl2k3jrksl\",\"kaekl23joij\"]" +
				",\"blobListAttr\":[\"aGVsbG8=\",\"d29ybGQ=\"]" +
				",\"categoryListAttr\":[\"partOfSpeech\",\"kind\"]" +
				",\"emailListAttr\":[\"a@b.com\",\"d@e.com\"]" +
				",\"geoPtListAttr\":[" +
				"{\"latitude\":1.0,\"longitude\":2.0}" +
				",{\"latitude\":3.0,\"longitude\":4.0}" +
				"]" +
				",\"imHandleListAttr\":[" +
				"{\"address\":\"address\",\"protocol\":\"xmpp\"}" +
				",{\"address\":\"address\",\"protocol\":\"http://test\"}" +
				"]" +
				",\"linkListAttr\":[\"link\",\"external\"]" +
				",\"phoneNumberListAttr\":[\"000-0000-0000\",\"111-1111-1111\"]" +
				",\"postalAddressListAttr\":[\"111-1111\",\"222-2222\"]" +
				",\"ratingListAttr\":[80,90]" +
				",\"shortBlobListAttr\":[\"aGVsbG8=\",\"d29ybGQ=\"]" +
				",\"textListAttr\":[\"hello\",\"world\"]" +
				",\"userListAttr\":[{\"authDomain\":\"authDomain\",\"email\":\"email\"}" +
					",{\"authDomain\":\"authDomain\",\"email\":\"email\",\"userId\":\"userId\"}" +
					",{\"authDomain\":\"authDomain\",\"email\":\"email\"" +
						",\"federatedIdentity\":\"federatedId\",\"userId\":\"userId\"}]" +
				"}"
				, json);
	}

	@Test
	public void gen_null(){
		AppEngineTypeListAttrsModel t = new AppEngineTypeListAttrsModel();
		String json = AppEngineTypeListAttrsModelMeta.get().modelToJson(t);
		Assert.assertEquals("{}", json);
	}
}
