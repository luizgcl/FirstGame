package zeldaminiclone;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class Player extends Rectangle {

    public boolean up, down, left, right;
    public double speed = 3.85669815;

    public int curAnimation = 0;

    public int curFrames = 0, targetFrames = 15;

    public static MoveDirection lastMove = MoveDirection.NONE;
    public static MoveDirection actualMove = MoveDirection.NONE;

    public static List<Bullet> bullets = new ArrayList<Bullet>();
    public boolean shoot;

    public int life = 15;

    public int dir = 1;
    boolean moved = false;

    public static boolean attacked = false;

    public Player(int x, int y) {
        super(x, y, 32, 32);
    }

    public void tick() {
        if (life == 0) {
            die();
            return;
        }

        if (right && World.isFree((int) (x+speed), y)) {
            x+=speed;
            moved = true;
            dir = 1;
        } else if (left && World.isFree((int) (x-speed), y)) {
            x-=speed;
            moved = true;
            dir = -1;
        }

        if (up && World.isFree(x, (int) (y-speed))) {
            y-=speed;
            moved = true;
            dir = -1;
        } else if (down && World.isFree(x, (int) (y+speed))) {
            y+=speed;
            moved = true;
            dir = 1;
        }

        if (moved) {
            move();
        }

        if (Game.inimigos.size() > 0) {
            if (shoot) {
                shoot = false;
                bullets.add(new Bullet(x, y, dir));
            }

            for (int i = 0; i < bullets.size(); i++) {
                bullets.get(i).tick();
            }
        }

        if (Game.inimigos.size() == 0) {
            win();
        }
    }

    public void move() {
        curFrames++;
        if (curFrames == targetFrames) {
            curFrames = 0;
            curAnimation++;
            if (curAnimation == actualMove.value.length) {
                curAnimation = 0;
            }
        }

        for (int i = 0; i < Game.inimigos.size(); i++) {
            Inimigo inimigo = Game.inimigos.get(i);
            if (inimigo.intersects(this)) {
                if (!attacked) {
                    attacked = true;
                    if (life > 0)
                        life -= 1;
                }
            }
        }

        if (attacked) {
            attackedTime++;
            if (attackedTime == cooldownAttack) {
                attacked = false;
                attackedTime = 0;
            }
        }
    }

    public void render(Graphics g) {
        g.drawImage(Spritesheet.player_front[actualMove.value[curAnimation]], x, y, 32, 32, null);
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).render(g);
        }

        if (Game.inimigos.size() == 0) {
            g.drawImage(Spritesheet.winImage, 179, y-16, 279, 110, null);
        }

        hit(g);

        if (died) {
            g.drawImage(Spritesheet.diePlayer, x, y, 32, 32, null);
            lose(g);
        }
    }

    public static int attackedTime = 0, cooldownAttack = 3;

    public void hit(Graphics g) {
        if (attacked) {
            g.drawImage(Spritesheet.player_hit, x, y, 32, 32, null);
            g.drawImage(Spritesheet.classicHit, x-1, y+1, 20, 20, null);
            attacked = false;
        }
    }

    boolean died = false;

    public void die() {
        died = true;
    }

    public void win() {
        x = Game.WIDTH/2;
        y = Game.HEIGHT/2;
    }

    public void lose(Graphics g) {
        g.drawImage(Spritesheet.loseImage, x, y, 279, 110, null);
    }

}
