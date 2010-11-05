package jp.takawitter.s3j.test.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
	public boolean isBooleanProp() {
		return booleanProp;
	}
	public void setBooleanProp(boolean booleanProp) {
		this.booleanProp = booleanProp;
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
	public Boolean getWrapperBooleanProp() {
		return wrapperBooleanProp;
	}
	public void setWrapperBooleanProp(Boolean wrapperBooleanProp) {
		this.wrapperBooleanProp = wrapperBooleanProp;
	}
	public Short getWrapperShortProp() {
		return wrapperShortProp;
	}
	public void setWrapperShortProp(Short wrapperShortProp) {
		this.wrapperShortProp = wrapperShortProp;
	}
	public Integer getWrapperIntegerProp() {
		return wrapperIntegerProp;
	}
	public void setWrapperIntegerProp(Integer wrapperIntegerProp) {
		this.wrapperIntegerProp = wrapperIntegerProp;
	}
	public Long getWrapperLongProp() {
		return wrapperLongProp;
	}
	public void setWrapperLongProp(Long wrapperLongProp) {
		this.wrapperLongProp = wrapperLongProp;
	}
	public Float getWrapperFloatProp() {
		return wrapperFloatProp;
	}
	public void setWrapperFloatProp(Float wrapperFloatProp) {
		this.wrapperFloatProp = wrapperFloatProp;
	}
	public Double getWrapperDoubleProp() {
		return wrapperDoubleProp;
	}
	public void setWrapperDoubleProp(Double wrapperDoubleProp) {
		this.wrapperDoubleProp = wrapperDoubleProp;
	}
	public List<Short> getShortListProp() {
		return shortListProp;
	}
	public void setShortListProp(List<Short> shortListProp) {
		this.shortListProp = shortListProp;
	}
	public List<Integer> getIntegerListProp() {
		return integerListProp;
	}
	public void setIntegerListProp(List<Integer> integerListProp) {
		this.integerListProp = integerListProp;
	}
	public List<Long> getLongListProp() {
		return longListProp;
	}
	public void setLongListProp(List<Long> longListProp) {
		this.longListProp = longListProp;
	}
	public List<Float> getFloatListProp() {
		return floatListProp;
	}
	public void setFloatListProp(List<Float> floatListProp) {
		this.floatListProp = floatListProp;
	}
	public List<Double> getDoubleListProp() {
		return doubleListProp;
	}
	public void setDoubleListProp(List<Double> doubleListProp) {
		this.doubleListProp = doubleListProp;
	}


	@Attribute(primaryKey=true)
	private Key key;
	private boolean booleanProp = true;
	private short shortProp = 10;
	private int intProp = 100;
	private long longProp = 10000;
	private float floatProp = 10.2f;
	private double doubleProp = 123.45;
	private String stringProp = "hello";
	@Attribute(cipher=true)
	private String encryptedStringProp = "hello";
	private Boolean wrapperBooleanProp = true;
	private Short wrapperShortProp = 20;
	private Integer wrapperIntegerProp = 200;
	private Long wrapperLongProp = 20000L;
	private Float wrapperFloatProp = 1.0f;
	private Double wrapperDoubleProp = 234.56;
	private List<Short> shortListProp = new ArrayList<Short>(Arrays.asList((short)1, (short)2, (short)3, (short)4));
	private List<Integer> integerListProp = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4));
	private List<Long> longListProp = new ArrayList<Long>(Arrays.asList(1L, 2L, 3L, 4L));
	private List<Float> floatListProp = new ArrayList<Float>(Arrays.asList(1.1f, 2.2f, 3.3f, 4.4f));
	private List<Double> doubleListProp = new ArrayList<Double>(Arrays.asList(1.1, 2.2, 3.3, 4.4));
}
