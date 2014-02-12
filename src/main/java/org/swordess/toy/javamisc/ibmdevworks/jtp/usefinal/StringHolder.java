package org.swordess.toy.javamisc.ibmdevworks.jtp.usefinal;

import java.util.HashSet;

public class StringHolder {

	private String string;

	public StringHolder(String string) {
		this.string = string;
	}

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

	@Override
	public int hashCode() {
		return (string != null) ? string.hashCode() : 0;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof StringHolder)) {
			return false;
		}
		StringHolder other = (StringHolder) obj;
		if (null == string) {
			return other.string == null;
		}
		return string.equals(other.string);
	}
	
	@Override
	public String toString() {
		return string;
	}
	
	public static void main(String[] args) {
		StringHolder sh = new StringHolder("blert");
		HashSet<StringHolder> set = new HashSet<>();
		set.add(sh);
		sh.setString("moo");
		System.out.println(set.contains(sh));
		System.out.println(set.size());
		System.out.println(set.iterator().next());
	}
	
}
