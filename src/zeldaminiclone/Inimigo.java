package zeldaminiclone;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Inimigo extends Rectangle {

    public boolean up = false, down = false, left = false, right = true;
    public double speed = 1.7000969999999;

    public int curAnimation = 0;

    public int curFrames = 0, targetFrames = 15;

    public static MoveDirection moveDirection = MoveDirection.NONE;

    public static List<Bullet> bullets = new ArrayList<Bullet>();
    public boolean shoot;

    int life = 10;

    public int dir = 1;

    boolean attacked = false;

    public Inimigo(int x, int y) {
        super(x, y, 32, 32);
    }

    boolean moved = false;

    public void tick() {
        if (life == 0) {
            die();
            return;
        }

        if (Game.start)
            perseguirPlayer();

        if (moved) {
            curFrames++;
            if (curFrames == targetFrames) {
                curFrames = 0;
                curAnimation++;
                if (curAnimation == moveDirection.value.length) {
                    curAnimation = 0;
                }
            }
        }

        if (shoot) {
            shoot = false;
            bullets.add(new Bullet(x, y, dir));
        }

        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).tick();
        }
    }

    public void perseguirPlayer() {
        Player player = Game.player;
        moved = true;

        if (x < player.x && World.isFree((int) (x+speed), y)) {
            x+=speed;
            moveDirection = MoveDirection.LEFT;
        } else if (x > player.x && World.isFree((int) (x-speed), y)) {
            x-=speed;
            moveDirection = MoveDirection.RIGHT;
        }

        if (y < player.y && World.isFree(x, (int) (y+speed))) {
            y+=speed;
            moveDirection = MoveDirection.UP;
        } else if (y > player.y && World.isFree(x, (int) (y-speed))) {
            y-=speed;
            moveDirection = MoveDirection.DOWN;
        }

        for (int i = 0; i < player.bullets.size(); i++) {
            Bullet bulletActual = player.bullets.get(i);
            if (bulletActual.intersects(this)) {
                attacked = true;
                player.bullets.remove(bulletActual);
                if (life > 0)
                    life--;
            }
        }
    }

    public void render(Graphics g) {
        g.drawImage(Spritesheet.inimigo_front[moveDirection.value[curAnimation]], x, y, 32, 32, null);
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).render(g);
        }
        hit(g);
    }

    public void hit(Graphics g) {
        if (attacked) {
            g.drawImage(Spritesheet.inimigo_hit, x, y, 32, 32, null);
            g.drawImage(Spritesheet.classicHit, x-1, y+1, 20, 20, null);
            attacked = false;
        }
    }

    public void die() {
        Game.inimigos.remove(this);
    }

}
