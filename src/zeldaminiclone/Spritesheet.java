package zeldaminiclone;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Spritesheet {

    public static BufferedImage spritesheet;
    public static BufferedImage winSpritesheet;
    public static BufferedImage loseSpritesheet;

    public static BufferedImage player_front[];
    public static BufferedImage inimigo_front[];

    public static BufferedImage player_hit;
    public static BufferedImage inimigo_hit;
    public static BufferedImage classicHit;

    public static BufferedImage diePlayer;

    public static BufferedImage tileWall;

    public static BufferedImage winImage;
    public static BufferedImage loseImage;

    public Spritesheet() {
        try {
            spritesheet = ImageIO.read(getClass().getResource("res/spritesheet.png"));
            winSpritesheet = ImageIO.read(getClass().getResource("res/win.png"));
            loseSpritesheet = ImageIO.read(getClass().getResource("res/lose.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        player_front = new BufferedImage[6];

        player_front[0] = Spritesheet.getSprite(0,11,16,16); //frente
        player_front[1] = Spritesheet.getSprite(16,11,16,16);

        player_front[2] = Spritesheet.getSprite(34,11,16,16); //direita
        player_front[3] = Spritesheet.getSprite(34+16,11,16,16);

        player_front[4] = Spritesheet.getSprite(34,28,16,16); //esquerda
        player_front[5] = Spritesheet.getSprite(34+16,28,16,16);


        inimigo_front = new BufferedImage[6];

        inimigo_front[0] = Spritesheet.getSprite(314,240,16,16); //frente
        inimigo_front[1] = Spritesheet.getSprite(314+16,240,16,16);

        inimigo_front[2] = Spritesheet.getSprite(284,240+16 ,16,16); //direita
        inimigo_front[3] = Spritesheet.getSprite(284+16,240+16 ,16,16);

        inimigo_front[4] = Spritesheet.getSprite(284,240,16,16); //esquerda
        inimigo_front[5] = Spritesheet.getSprite(284+16,240,16,16);

        player_hit = Spritesheet.getSprite(127, 258, 16, 16);
        inimigo_hit = Spritesheet.getSprite(314,240+16,16,16);
        classicHit = Spritesheet.getSprite(330, 216, 16, 16);

        diePlayer = Spritesheet.getSprite(1, 47, 16, 16);

        tileWall = Spritesheet.getSprite(257, 241, 16, 16);

        winImage = Spritesheet.getWinSprite(45, 88, 279, 110);
        loseImage = Spritesheet.getLoseSprite(33, 115, 279, 110);
    }

    public static BufferedImage getSprite(int x, int y, int width, int height) {
        return spritesheet.getSubimage(x, y, width, height);
    }

    public static BufferedImage getWinSprite(int x, int y, int width, int height) {
        return winSpritesheet.getSubimage(x, y, width, height);
    }

    public static BufferedImage getLoseSprite(int x, int y, int width, int height) {
        return loseSpritesheet.getSubimage(x, y, width, height);
    }

}
