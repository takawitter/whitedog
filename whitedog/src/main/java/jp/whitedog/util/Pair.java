package jp.whitedog.util;

public class Pair<T, U> {
	public Pair(T first, U second){
		this.first = first;
		this.second = second;
	}

	public boolean equls(Object value){
		if(!(value instanceof Pair)) return false;
		Pair<?, ?> v = (Pair<?, ?>)value;
		if(!first.equals(v.first)) return false;
		if(!second.equals(v.second)) return false;
		return true;
	}

	public int hashCode(){
		return first.hashCode() * 31 + second.hashCode(); 
	}

	public String toString(){
		StringBuilder b = new StringBuilder();
		b.append(getClass().getName());
		b.append("[first:");
		b.append(first.toString());
		b.append(", second:");
		b.append(second.toString());
		b.append("]");
		return b.toString();
	}

	public T getFirst(){
		return first;
	}

	public U getSecond(){
		return second;
	}

	public static <V, W> Pair<V, W> create(V first, W second){
		return new Pair<V, W>(first, second);
	}

	private T first;
	private U second;
}
