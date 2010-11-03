package jp.takawitter.s3j.jsonic;

public class TestModel {
	
	public int getIntProp() {
		return intProp;
	}
	public void setIntProp(int intProp) {
		this.intProp = intProp;
	}
	public double getDoubleProp() {
		return doubleProp;
	}
	public void setDoubleProp(double doubleProp) {
		this.doubleProp = doubleProp;
	}
	public boolean isBoolProp() {
		return boolProp;
	}
	public void setBoolProp(boolean boolProp) {
		this.boolProp = boolProp;
	}
	public String getStringProp() {
		return stringProp;
	}
	public void setStringProp(String strProp) {
		this.stringProp = strProp;
	}

	private int intProp = 100;
	private double doubleProp = 123.45;
	private boolean boolProp = true;
	private String stringProp = "hello";
}
