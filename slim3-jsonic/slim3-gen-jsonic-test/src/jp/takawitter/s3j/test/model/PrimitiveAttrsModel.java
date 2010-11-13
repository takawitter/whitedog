package jp.takawitter.s3j.test.model;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.Model;

import com.google.appengine.api.datastore.Key;

@Model
public class PrimitiveAttrsModel {
	public Key getKey() {
		return key;
	}
	public void setKey(Key key) {
		this.key = key;
	}
	public boolean isBooleanAttr() {
		return booleanAttr;
	}
	public void setBooleanAttr(boolean booleanAttr) {
		this.booleanAttr = booleanAttr;
	}
	public short getShortAttr() {
		return shortAttr;
	}
	public void setShortAttr(short shortAttr) {
		this.shortAttr = shortAttr;
	}
	public int getIntAttr() {
		return intAttr;
	}
	public void setIntAttr(int intAttr) {
		this.intAttr = intAttr;
	}
	public long getLongAttr() {
		return longAttr;
	}
	public void setLongAttr(long longAttr) {
		this.longAttr = longAttr;
	}
	public float getFloatAttr() {
		return floatAttr;
	}
	public void setFloatAttr(float floatAttr) {
		this.floatAttr = floatAttr;
	}
	public double getDoubleAttr() {
		return doubleAttr;
	}
	public void setDoubleAttr(double doubleAttr) {
		this.doubleAttr = doubleAttr;
	}

	@Attribute(primaryKey=true)
	private Key key;
	private boolean booleanAttr = true;
	private short shortAttr = 10;
	private int intAttr = 100;
	private long longAttr = 10000;
	private float floatAttr = 10.2f;
	private double doubleAttr = 123.45;
}
