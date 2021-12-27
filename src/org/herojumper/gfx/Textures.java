package org.herojumper.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Textures {
	public static Bitmap tilemap = Textures.loadTexture("/textures/tilemap.png");
	
	public static Bitmap loadTexture(String path) {
		try {
			BufferedImage image = ImageIO.read(Textures.class.getResourceAsStream(path));
			Bitmap res = new Bitmap(image.getWidth(), image.getHeight());
			image.getRGB(0, 0, res.width, res.height, res.pixels, 0, res.width);
			
			for (int i = 0; i < res.pixels.length; i++) {
				res.pixels[i] = res.pixels[i] & 0xffffff;
			}
			
			return res;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
