package org.herojumper.game;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.nio.Buffer;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {
	
	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 280;
	public static final int HEIGHT = WIDTH * 3 / 4;//230;
	public static final int SCALE = 4;
	public static final String TITLE = "Hero Jumper Pre-Release 0.1";
	
	private boolean isRunning = false;
	
	public static BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	public static final int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	public Game() {
		
	}
	
	public void start() {
		if (isRunning)
			return;
		
		isRunning = true;
		
		new Thread(this).start();
	}
	
	public void run() {
		while(isRunning) {
			render();
			update();
		}
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		//Draw here.
		Graphics g = bs.getDrawGraphics();
		
		g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		//
		
		g.dispose();
		bs.show();
	}
	
	public void update() {
		
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		Game game = new Game();
		Dimension dimension = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
		game.setMinimumSize(dimension);
		game.setMaximumSize(dimension);
		game.setPreferredSize(dimension);
		frame.setTitle(TITLE);
		frame.setResizable(false);
		frame.add(game);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		game.start();
	}
}
