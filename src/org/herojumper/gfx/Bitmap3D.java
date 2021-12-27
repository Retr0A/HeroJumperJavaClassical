package org.herojumper.gfx;

public class Bitmap3D extends Bitmap {
	
	private double fov = height;
	private double[] depthBuffer;
	
	public Bitmap3D(int width, int height) {
		super(width, height);
		
		depthBuffer = new double[width * height];
	}
	
	int t;
	
	double xCam = 0;
	double yCam = 0;
	double zCam = 4;
	
	double rot = 0;
	
	public void render() {
		t++;
		
		//xCam = t / 100.0;
		//yCam = t / 100.0;
		//zCam = Math.sin(t / 300.0);
		
		//rot = t / 1000.0;
		
		double rSin = Math.sin(rot);
		double rCos = Math.cos(rot);
		
		for (int y = 0; y < height; y++) {
			double yd = (y - (height / 2)) / fov;
			double zd = (6 + zCam) / yd;
			
			if (yd < 0)
				zd = (6 - zCam) / -yd;
			
			for (int x = 0; x < width; x++) {
				double xd = (x - (width / 2)) / fov;
				xd *= zd;
				
				int xPix = (int) (xd * rCos - zd * rSin + xCam);
				int yPix = (int) (xd * rSin + zd * rCos + yCam);
				
				depthBuffer[x + y * width] = zd;
				pixels[x + y * width] = Textures.tilemap.pixels[(xPix & 15) | (yPix & 15) * Textures.tilemap.width];
				
				drawFloor(xd, zd, yd, x, y, xPix, yPix, 0, 2);
				drawFloor(xd, zd, yd, x, y, xPix, yPix, 0, 3);
				drawFloor(xd, zd, yd, x, y, xPix, yPix, 1, 2);
				drawFloor(xd, zd, yd, x, y, xPix, yPix, 1, 3);
			}
		}
	}
	
	public void drawFloor(double xd, double zd, double yd, int x, int y, int xPix, int yPix, int pX, int pY) {
		if (yd >=0 && xd >= pX * 16 && xd < pX * 16 + 16 && zd >= pY * 16 && zd < pY * 16 + 16)
			pixels[x + y * width] = Textures.tilemap.pixels[(xPix & 15) + 16 | (yPix & 15) * Textures.tilemap.width];
	}
	
	public void drawCeiling(double xd, double zd, double yd, int x, int y, int xPix, int yPix, int pX, int pY) {
		if (yd <=0 && xd >= pX * 16 && xd < pX * 16 + 16 && zd >= pY * 16 && zd < pY * 16 + 16)
			pixels[x + y * width] = Textures.tilemap.pixels[(xPix & 15) + 16 | (yPix & 15) * Textures.tilemap.width];
	}
	
	public void renderFog() {
		for (int i = 0; i < depthBuffer.length; i++) {
			int color = pixels[i];
			int r = (color >> 16) & 0xff;
			int g = (color >> 8) & 0xff;
			int b = (color) & 0xff;
			
			double brightness = 255 - depthBuffer[i] * 2;
			
			r = (int) (r / 255.0 * brightness);
			g = (int) (g / 255.0 * brightness);
			b = (int) (b / 255.0 * brightness);
			
			pixels[i] = r << 16 | g << 8 | b;
		}
	}

}
