package jp.takawitter.s3j.test.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

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
	public List<String> getStringListProp() {
		return stringListProp;
	}
	public void setStringListProp(List<String> stringListProp) {
		this.stringListProp = stringListProp;
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
	public Set<String> getStringSetProp() {
		return stringSetProp;
	}
	public void setStringSetProp(Set<String> stringSetProp) {
		this.stringSetProp = stringSetProp;
	}
	public Set<Short> getShortSetProp() {
		return shortSetProp;
	}
	public void setShortSetProp(Set<Short> shortSetProp) {
		this.shortSetProp = shortSetProp;
	}
	public Set<Integer> getIntegerSetProp() {
		return integerSetProp;
	}
	public void setIntegerSetProp(Set<Integer> integerSetProp) {
		this.integerSetProp = integerSetProp;
	}
	public Set<Long> getLongSetProp() {
		return longSetProp;
	}
	public void setLongSetProp(Set<Long> longSetProp) {
		this.longSetProp = longSetProp;
	}
	public Set<Float> getFloatSetProp() {
		return floatSetProp;
	}
	public void setFloatSetProp(Set<Float> floatSetProp) {
		this.floatSetProp = floatSetProp;
	}
	public Set<Double> getDoubleSetProp() {
		return doubleSetProp;
	}
	public void setDoubleSetProp(Set<Double> doubleSetProp) {
		this.doubleSetProp = doubleSetProp;
	}
	public SortedSet<String> getStringSortedSetProp() {
		return stringSortedSetProp;
	}
	public void setStringSortedSetProp(SortedSet<String> stringSortedSetProp) {
		this.stringSortedSetProp = stringSortedSetProp;
	}
	public SortedSet<Short> getShortSortedSetProp() {
		return shortSortedSetProp;
	}
	public void setShortSortedSetProp(SortedSet<Short> shortSortedSetProp) {
		this.shortSortedSetProp = shortSortedSetProp;
	}
	public SortedSet<Integer> getIntegerSortedSetProp() {
		return integerSortedSetProp;
	}
	public void setIntegerSortedSetProp(SortedSet<Integer> integerSortedSetProp) {
		this.integerSortedSetProp = integerSortedSetProp;
	}
	public SortedSet<Long> getLongSortedSetProp() {
		return longSortedSetProp;
	}
	public void setLongSortedSetProp(SortedSet<Long> longSortedSetProp) {
		this.longSortedSetProp = longSortedSetProp;
	}
	public SortedSet<Float> getFloatSortedSetProp() {
		return floatSortedSetProp;
	}
	public void setFloatSortedSetProp(SortedSet<Float> floatSortedSetProp) {
		this.floatSortedSetProp = floatSortedSetProp;
	}
	public SortedSet<Double> getDoubleSortedSetProp() {
		return doubleSortedSetProp;
	}
	public void setDoubleSortedSetProp(SortedSet<Double> doubleSortedSetProp) {
		this.doubleSortedSetProp = doubleSortedSetProp;
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
	private List<String> stringListProp = new ArrayList<String>();
	private List<Short> shortListProp = new ArrayList<Short>();
	private List<Integer> integerListProp = new ArrayList<Integer>();
	private List<Long> longListProp = new ArrayList<Long>();
	private List<Float> floatListProp = new ArrayList<Float>();
	private List<Double> doubleListProp = new ArrayList<Double>();
	private Set<String> stringSetProp = new HashSet<String>();
	private Set<Short> shortSetProp = new HashSet<Short>();
	private Set<Integer> integerSetProp = new HashSet<Integer>();
	private Set<Long> longSetProp = new HashSet<Long>();
	private Set<Float> floatSetProp = new HashSet<Float>();
	private Set<Double> doubleSetProp = new HashSet<Double>();
	private SortedSet<String> stringSortedSetProp = new TreeSet<String>();
	private SortedSet<Short> shortSortedSetProp = new TreeSet<Short>();
	private SortedSet<Integer> integerSortedSetProp = new TreeSet<Integer>();
	private SortedSet<Long> longSortedSetProp = new TreeSet<Long>();
	private SortedSet<Float> floatSortedSetProp = new TreeSet<Float>();
	private SortedSet<Double> doubleSortedSetProp = new TreeSet<Double>();
}
