package jp.takawitter.s3j.jsonic;

public class ReferenceEncoder {
	public static String encode(TestModel model){
		StringBuilder b = new StringBuilder("{");
		b.append("\"boolProp\":").append(model.isBoolProp());
		b.append(",\"doubleProp\":").append(model.getDoubleProp());
		b.append(",\"intProp\":").append(model.getIntProp());
		b.append("}");
		return b.toString();
	}
}
