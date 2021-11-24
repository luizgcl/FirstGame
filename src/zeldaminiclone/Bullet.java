package zeldaminiclone;

import java.awt.*;

public class Bullet extends Rectangle {

    public int dir = 1;
    public double speed = 8.00236577;

    public int frames = 0;

    public Bullet(int x, int y, int dir) {
        super(x+16, y+16, 10, 12);
        this.dir = dir;
    }

    public void tick() {

        Player p = Game.player;

        switch (p.lastMove) {
            case LEFT:
            case RIGHT: {
                x+=speed*dir;
                break;
            }
            case UP:
            case DOWN: {
                y+=speed*dir;
                break;
            }
            default: {
                x+=speed*dir;
            }
        }

        frames++;
        if (frames == 60) {
            frames = 0;
            Player.bullets.remove(this);
            return;
        }

        if (Game.inimigos.size() == 0) {
            Player.bullets.remove(this);
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillOval(x, y, width, height);
        g.setColor(Color.black);
        g.fillOval(16, 16, width, height);
    }

}
