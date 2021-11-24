package zeldaminiclone;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

import java.util.ArrayList;
import java.util.List;

public class Game extends Canvas implements Runnable, KeyListener {

    public static int WIDTH = 640, HEIGHT = 480;
    public static int SCALE = 3;
    public static Player player;

    public World world;

    public static boolean start;
	
	public static List<Inimigo> inimigos = new ArrayList<Inimigo>();

    public Game() {
        this.addKeyListener(this);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        new Spritesheet();
        player = new Player(32, 32);
        world = new World();

		inimigos.add(new Inimigo(400, 400));
    }

    public void tick() {
        player.tick();
		for (int i = 0; i < inimigos.size(); i++) {
			inimigos.get(i).tick();
		}
    }

    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.GREEN);
        g.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);

        player.render(g);
        world.render(g);
		
		for (int i = 0; i < inimigos.size(); i++) {
			inimigos.get(i).render(g);
		}

        bs.show();
    }

    public static void main(String[] args) {
        Game game = new Game();
        JFrame frame = new JFrame();

        frame.add(game);
        frame.setTitle("Mini Zelda");
        frame.pack();
        frame.setLocationRelativeTo(null);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        new Thread(game).start();
    }

    @Override
    public void run() {
        while (true) {
            tick();
            render();
            try {
                Thread.sleep(1000/60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            player.right = true;
            player.actualMove = MoveDirection.RIGHT;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            player.left = true;
            player.actualMove = MoveDirection.LEFT;
        }

        if (e.getKeyCode() == KeyEvent.VK_X) {
            player.shoot = true;
        }

        if(e.getKeyCode() == KeyEvent.VK_UP) {
            player.up = true;
            player.actualMove = MoveDirection.UP;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            player.down = true;
            player.actualMove = MoveDirection.DOWN;
        }

        if (!start)
            start = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            player.right = false;
            player.lastMove = MoveDirection.RIGHT;
            player.actualMove = MoveDirection.NONE;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            player.left = false;
            player.lastMove = MoveDirection.LEFT;
            player.actualMove = MoveDirection.NONE;
        }

        if(e.getKeyCode() == KeyEvent.VK_UP) {
            player.up = false;
            player.lastMove = MoveDirection.UP;
            player.actualMove = MoveDirection.NONE;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            player.down = false;
            player.lastMove = MoveDirection.DOWN;
            player.actualMove = MoveDirection.NONE;
        }
    }

}
