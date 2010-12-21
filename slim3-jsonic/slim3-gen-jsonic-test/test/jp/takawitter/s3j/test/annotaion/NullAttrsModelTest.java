package jp.takawitter.s3j.test.annotaion;

import jp.takawitter.s3j.test.annotation.NullAttrsModel;
import jp.takawitter.s3j.test.annotation.NullAttrsModelMeta;
import junit.framework.Assert;

import org.junit.Test;

public class NullAttrsModelTest {
	@Test
	public void modelToJson() throws Exception{
		Assert.assertEquals(
				"{\"nullBlob\":null,\"nullBytesBlob\":null,\"nullString\":\"null\"," +
				"\"nullText\":null,\"nullValueText\":null}",
				meta.modelToJson(new NullAttrsModel())
				);
	}
	
	private NullAttrsModelMeta meta = NullAttrsModelMeta.get();
}
