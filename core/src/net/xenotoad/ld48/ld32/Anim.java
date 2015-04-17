package net.xenotoad.ld48.ld32;

public class Anim {
	public Anim(String[] frames) {
		this.frames = new int[frames.length];
		for(int i = 0; i < frames.length; i++) {
			this.frames[i] = Integer.parseInt(frames[i]);
		}
	}

	public int frames[];
}