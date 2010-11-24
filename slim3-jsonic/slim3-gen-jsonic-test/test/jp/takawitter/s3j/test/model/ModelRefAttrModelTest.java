package jp.takawitter.s3j.test.model;

import static org.junit.Assert.assertEquals;
import jp.takawitter.s3j.test.meta.ModelRefAttrModelMeta;
import net.arnx.jsonic.JSON;

import org.junit.Test;
import org.slim3.datastore.Datastore;
import org.slim3.tester.TestEnvironment;

import com.google.apphosting.api.ApiProxy;

public class ModelRefAttrModelTest {
	@Test
	public void gen() throws Exception{
		Datastore.setGlobalCipherKey("0654813216578941");
		ApiProxy.setEnvironmentForCurrentThread(new TestEnvironment(){
			@Override
			public String getAppId() {
				return "slim3-gen";
			}
		});
		ModelRefAttrModel m = new ModelRefAttrModel("parent");
		ModelRefAttrModel child = new ModelRefAttrModel("child");
		ModelRefAttrModel gchild = new ModelRefAttrModel("ground child");
		ModelRefAttrModel ggchild = new ModelRefAttrModel("child of ground child");
		m.getRef().setModel(child);
		child.getRef().setModel(gchild);
		gchild.getRef().setModel(ggchild);

		String json = ModelRefAttrModelMeta.get().modelToJson(m);
		System.out.println(json);
		JSON.decode(json);

		assertEquals(
				"{\"key\":\"aglzbGltMy1nZW5yHQsSEU1vZGVsUmVmQXR0ck1vZGVsIgZwYXJlbnQM\"" +
				",\"name\":\"parent\"" +
				",\"ref\":{" +
				  "\"key\":\"aglzbGltMy1nZW5yHAsSEU1vZGVsUmVmQXR0ck1vZGVsIgVjaGlsZAw\"" +
				  ",\"name\":\"child\"" +
				  ",\"ref\":{" +
				    "\"key\":\"aglzbGltMy1nZW5yIwsSEU1vZGVsUmVmQXR0ck1vZGVsIgxncm91bmQgY2hpbGQM\"" +
				    ",\"name\":\"ground child\"" +
				    "}" +
				  "}" +
				"}"
				, json
				);
	}

	@Test
	public void gen_null(){
		ModelRefAttrModel m = new ModelRefAttrModel();
		String json = ModelRefAttrModelMeta.get().modelToJson(m);
		assertEquals("{}", json);
	}
}
