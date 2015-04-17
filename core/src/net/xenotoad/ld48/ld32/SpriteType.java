package net.xenotoad.ld48.ld32;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SpriteType {
	public Map<String, Anim> animMap = new HashMap<String, Anim>();
	
	public SpriteType(BufferedReader reader) {
		System.out.println("loading sprtyp");
		
		String line;
		try {
			while((line = reader.readLine()) != null) {
				String parts[] = line.split(": ?");
				System.out.println("got anim " + parts[0]);
				animMap.put(parts[0], new Anim(parts[1].split(", ?")));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
