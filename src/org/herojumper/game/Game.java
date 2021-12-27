package org.herojumper.game;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import org.herojumper.gfx.Screen;

public class Game extends Canvas implements Runnable {
	
	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 280;
	public static final int HEIGHT = WIDTH * 3 / 4;
	public static final int SCALE = 4;
	public static final String TITLE = "Hero Jumper Pre-Release 0.1";
	public static final double FRAME_LIMIT = 60.0;
	
	private boolean isRunning = false;
	
	public static BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	public static final int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	private Screen screen;
	
	public Game() {
		screen = new Screen(WIDTH, HEIGHT);
	}
	
	public void start() {
		if (isRunning)
			return;
		
		isRunning = true;
		
		new Thread(this).start();
	}
	
	@SuppressWarnings("unused")
	public void run() {
		final double nsPerUpdate = 100000000.0 / FRAME_LIMIT;

        long lastTime = System.nanoTime();
        double unprocessedTime = 0;

        int frames = 0;
        int updates = 0;

        long frameCounter = System.currentTimeMillis();

        while (isRunning)
        {
            long currentTime = System.nanoTime();
            long passedTime = currentTime - lastTime;
            lastTime = currentTime;
            unprocessedTime += passedTime;

            if (unprocessedTime >= nsPerUpdate)
            {
                unprocessedTime = 0;
                update();
                updates++;
            }

            render();
            frames++;

            if (System.currentTimeMillis() - frameCounter >= 1000)
            {
                /*System.out.println("FPS: " + frames +
                				   "\nUPDATES: " + updates);*/
                frames = 0;
                updates = 0;
                frameCounter += 1000;
            }
        }
		
		System.exit(0);
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		//Draw here.
		Graphics g = bs.getDrawGraphics();
		
		screen.render();
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}
		g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		//
		
		g.dispose();
		bs.show();
	}
	
	public void update() {
		screen.update();
	}
	
	public void stop() {
		if (!isRunning)
			return;
		
		isRunning = false;
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
