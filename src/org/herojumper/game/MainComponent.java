package org.herojumper.game;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import org.herojumper.gfx.Screen;
import org.herojumper.input.InputHandler;

public class MainComponent extends Canvas implements Runnable {
	
	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 280;
	public static final int HEIGHT = WIDTH * 3 / 4;
	public static final int SCALE = 4;
	public static final String TITLE = "Hero Jumper Pre-Release 0.1";
	public static final double FRAME_LIMIT = 40.0;
	
	private boolean isRunning = false;
	
	public BufferedImage image;
	public final int[] pixels;
	
	private Game game;
	private Screen screen;
	private InputHandler inputHandler;
	
	public MainComponent() {
		
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		
		Dimension dimension = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
		setMinimumSize(dimension);
		setMaximumSize(dimension);
		setPreferredSize(dimension);
		
		inputHandler = new InputHandler();
		
		addKeyListener(inputHandler);
		addMouseListener(inputHandler);
		addMouseMotionListener(inputHandler);
		addMouseWheelListener(inputHandler);
	}
	
	public void start() {
		if (isRunning)
			return;
		
		isRunning = true;
		
		init();
		new Thread(this).start();
	}
	
	public void init() {
		game = new Game();
		screen = new Screen(WIDTH, HEIGHT);
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
		
		screen.render(game);
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}
		g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		//
		
		g.dispose();
		bs.show();
	}
	
	public void update() {
		game.update(inputHandler.keys);
		screen.update();
	}
	
	public void stop() {
		if (!isRunning)
			return;
		
		isRunning = false;
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		MainComponent game = new MainComponent();
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
