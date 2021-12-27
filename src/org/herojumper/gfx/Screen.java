package org.herojumper.gfx;

import java.util.Random;

import org.herojumper.game.Game;

public class Screen extends Bitmap {
	
	public Random random = new Random();
	
	public Bitmap test;
	public Bitmap3D render3D;
	
	public Screen(int width, int height) {
		super(width, height);
		
		test = new Bitmap(50, 50);
		
		for (int i = 0; i < test.pixels.length; i++) {
			test.pixels[i] = random.nextInt();
		}
		
		render3D = new Bitmap3D(width, height);
	}
	
	public void render(Game game) {
		//int ox = (int) (Math.sin(t / 1000.0) * width / 2);
		//int oy = (int) (Math.cos(t / 1000.0) * height / 2);
		
		clear();
		//render(test, (width - 50) / 2 + ox, (height - 50) / 2 + oy);
		render3D.render(game);
		render3D.renderFog();
		render(render3D, 0, 0);
	}
	
	public void update() {
		
	}
}
