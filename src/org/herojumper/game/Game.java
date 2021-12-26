package org.herojumper.game;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {
	
	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 280;
	public static final int HEIGHT = WIDTH * 3 / 4;//230;
	public static final int SCALE = 4;
	public static final String TITLE = "Hero Jumper Pre-Release 0.1";
	
	public Game() {
		
	}
	
	public void start() {
		
	}
	
	public void run() {
		
	}
	
	public void render() {
		
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
