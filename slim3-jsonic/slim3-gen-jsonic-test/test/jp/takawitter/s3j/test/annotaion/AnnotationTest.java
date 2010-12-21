package jp.takawitter.s3j.test.annotaion;

import jp.takawitter.s3j.test.annotation.AnnotatedModel;
import jp.takawitter.s3j.test.annotation.AnnotatedModelMeta;

import org.junit.Test;

public class AnnotationTest {
	@Test
	public void modelToJson() throws Exception{
		AnnotatedModel m = new AnnotatedModel();
		m.setStringAttr("hello");
		
		System.out.println(AnnotatedModelMeta.get().modelToJson(m));
	}
}
