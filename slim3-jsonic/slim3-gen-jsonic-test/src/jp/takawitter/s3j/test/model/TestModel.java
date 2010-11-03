package jp.takawitter.s3j.test.model;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.Model;

import com.google.appengine.api.datastore.Key;

@Model
public class TestModel {
	

	public Key getKey() {
		return key;
	}
	public void setKey(Key key) {
		this.key = key;
	}
	public boolean isBoolProp() {
		return boolProp;
	}
	public void setBoolProp(boolean boolProp) {
		this.boolProp = boolProp;
	}
	public short getShortProp() {
		return shortProp;
	}
	public void setShortProp(short shortProp) {
		this.shortProp = shortProp;
	}
	public int getIntProp() {
		return intProp;
	}
	public void setIntProp(int intProp) {
		this.intProp = intProp;
	}
	public long getLongProp() {
		return longProp;
	}
	public void setLongProp(long longProp) {
		this.longProp = longProp;
	}
	public float getFloatProp() {
		return floatProp;
	}
	public void setFloatProp(float floatProp) {
		this.floatProp = floatProp;
	}
	public double getDoubleProp() {
		return doubleProp;
	}
	public void setDoubleProp(double doubleProp) {
		this.doubleProp = doubleProp;
	}
	public String getStringProp() {
		return stringProp;
	}
	public void setStringProp(String stringProp) {
		this.stringProp = stringProp;
	}
	public String getEncryptedStringProp() {
		return encryptedStringProp;
	}
	public void setEncryptedStringProp(String encryptedStringProp) {
		this.encryptedStringProp = encryptedStringProp;
	}
	@Attribute(primaryKey=true)
	private Key key;
	private boolean boolProp = true;
	private short shortProp = 10;
	private int intProp = 100;
	private long longProp = 10000;
	private float floatProp = 10;
	private double doubleProp = 123.45;
	private String stringProp = "hello";
	@Attribute(cipher=true)
	private String encryptedStringProp = "hello";
}
