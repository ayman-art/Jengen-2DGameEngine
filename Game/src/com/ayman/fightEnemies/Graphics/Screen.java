package com.ayman.fightEnemies.Graphics;

import com.ayman.fightEnemies.entity.projectile.Projectile;
import com.ayman.fightEnemies.entity.projectile.WizardProjectile;
import com.ayman.fightEnemies.level.tile.Tile;

import java.util.Random;

public class Screen {

    public final int width;
    public final int height;
    public int[] pixels;

    private final int MAP_SIZE = 64;

    private final int MAP_SIZE_MASK = MAP_SIZE - 1;

    public int[] tiles = new int[MAP_SIZE * MAP_SIZE];
    private Random random = new Random();


    public int xOffset, yOffset;

    public Screen(int width, int height) {

        this.width = width;
        this.height = height;
        pixels = new int[width * height];

        for(int i = 0; i < MAP_SIZE * MAP_SIZE; i++) {
            tiles[i] = random.nextInt(0xffffff);
        }
    }


    public void clear() {

        for(int i = 0; i < pixels.length; i++) {
            pixels[i] = 0;
        }



    }





    public void renderTile(int xp, int yp, Tile tile) {

        xp -= xOffset;
        yp -= yOffset;


        for(int y = 0; y < tile.sprite.SIZE; y++) {
            int ya = yp + y;
            for(int x = 0; x < tile.sprite.SIZE; x++) {
                int xa = xp + x;

                if(xa < 0 || xa >= width || ya < 0 || ya >= height) continue;

                pixels[xa + ya * width] = tile.sprite.pixels[x + y * tile.sprite.SIZE];
            }
        }
    }

    public void renderSprite(int xp, int yp, Sprite sprite, boolean fixed) {

        if(!fixed) {
            xp -= xOffset;
            yp -= yOffset;
        }

        for(int y = 0; y < sprite.getHeight(); y++) {
            int ya = yp + y;
            for(int x = 0; x < sprite.getWidth(); x++) {
                int xa = xp + x;

                if(xa < 0 || xa >= width || ya < 0 || ya >= height) continue;

                int col = sprite.pixels[x + y * sprite.getWidth()];
                if(col != 0xffff00ff) {
                    pixels[xa + ya * width] = col;
                    //System.out.println("xa: " + xa + ", ya: " + ya  );
                }
            }
        }
    }
    public void renderSprite(int xp, int yp, Sprite sprite){
        renderSprite(xp, yp, sprite, false);
    }

    public void renderProjectile(int x, int y, Projectile projectile) {

            x -= xOffset;
            y -= yOffset;

            for(int y0 = 0; y0 < projectile.getSpriteSize(); y0++) {
                int ya = y + y0;
                for(int x0 = 0; x0 < projectile.getSpriteSize(); x0++) {
                    int xa = x + x0;

                    if(xa < -projectile.getSpriteSize() || xa >= width || ya < 0 || ya >= height) break;
                    if(xa < 0) xa = 0;

                    int col = projectile.getSprite().pixels[x0 + y0 * projectile.getSpriteSize()];
                    if(col != 0xffff00ff) pixels[xa + ya * width] = col;
                }
            }

    }

    public void renderSpriteSheet(int xp, int yp, SpriteSheet spriteSheet, boolean fixed) {
        if(!fixed) {
            xp -= xOffset;
            yp -= yOffset;
        }

        for(int y = 0; y < spriteSheet.HEIGHT; y++) {
            int ya = y + yp;
            for(int x = 0; x < spriteSheet.WIDTH; x++) {
                int xa = x + xp;
                if(xa < 0 || xa >= width || ya <  0 || ya >= height)
                    continue;
                pixels[xa + ya * width] = spriteSheet.pixels[x + y * spriteSheet.WIDTH];
            }

        }
    }

    public void renderPlayer(int xp, int yp, Sprite sprite, boolean flip) {

        xp -= xOffset;
        yp -= yOffset;

        for(int y = 0; y < 32; y++) {
            int ya = yp + y;
            for(int x = 0; x < 32; x++) {
                int xa = xp + (flip ? 31 - x : x);

                if(xa < -32 || xa >= width || ya < 0 || ya >= height) continue;

                int col = sprite.pixels[x + y * 32];
                if(col != 0xffff00ff) pixels[xa + ya * width] = col;
            }
        }
    }

    public void setOffset(int xOffset, int yOffset) {

        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }
}


