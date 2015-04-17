package net.xenotoad.ld48.ld32;

import java.util.ArrayList;
import java.util.List;

public class Pool<T> {
	private List<T> available;
	private Factory<T> factory;
	
	public Pool(Factory<T> factory) {
		this.available = new ArrayList<T>();
		this.factory = factory;
	}
	
	public T get() {
		if(available.size() > 0) {
			return available.remove(available.size()-1);
		} else {
			T inst = factory.construct();
				
			return inst;
		}
	}
	
	public interface Factory<U> {
		public U construct();
	}

	public void free(T i) {
		available.add(i);
	}
}
