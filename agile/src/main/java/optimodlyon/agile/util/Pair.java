package optimodlyon.agile.util;

public interface Pair<key,value> {
	public key getKey();
	public value getValue();
	public void setKey(key k);
	public void setValue(value v);
}
