package com.ayman.jengen.Graphics;

import com.ayman.jengen.entity.mob.IMob;
import com.ayman.jengen.entity.mob.IPlayer;
import com.ayman.jengen.entity.projectile.Projectile;
import com.ayman.jengen.level.tile.Tile;

import java.awt.*;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

/**
 * Screen is the Renderer of the game.
  */
public class Screen {

    public final int width;
    public final int height;
    public int[] pixels;

    private final int MAP_SIZE = 64;

    private final int MAP_SIZE_MASK = MAP_SIZE - 1;

    public int[] tiles = new int[MAP_SIZE * MAP_SIZE];
    private Random random = new Random();


    public int xOffset, yOffset;

    static public Optional<Integer> MiniMapAlpha = Optional.empty();

    public Screen(int width, int height) {

        this.width = width;
        this.height = height;
        pixels = new int[width * height];

        for(int i = 0; i < MAP_SIZE * MAP_SIZE; i++) {
            tiles[i] = random.nextInt(0xffffff);
        }
    }


    public void clear() {
        Arrays.fill(pixels, 0);
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

        renderSprite(xp, yp, sprite, fixed, 0xff);
    }

    public void renderSprite(int xp, int yp, Sprite sprite, boolean fixed, int alpha) {

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
                    col = blendColors(col, pixels[xa + ya * width], alpha);
                    pixels[xa + ya * width] = col;
                }
            }
        }
    }
    public void renderSprite(int xp, int yp, Sprite sprite){
        renderSprite(xp, yp, sprite, false);
    }

    public void renderHealthBar(int xp, int yp, int health) {
        xp -= xOffset;
        yp -= yOffset;
        xp += 10;
        yp += 5;
        Sprite entireBar = new Sprite(25,3, Color.red.getRGB());
        renderSprite(xp, yp, entireBar, true);
        int healthWidth = (int) (health * 0.25);
        Sprite healthBar = new Sprite(healthWidth, 3, Color.green.getRGB());
        renderSprite(xp, yp, healthBar, true);
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

    public void renderMob(int xp, int yp, IMob mob, boolean flip) {

        xp -= xOffset;
        yp -= yOffset;

        for(int y = 0; y < Tile.TILE_SIZE; y++) {
            int ya = yp + y;
            for(int x = 0; x < Tile.TILE_SIZE; x++) {
                int xa = xp + (flip ? 31 - x : x);

                if(xa < 0 || xa >= width || ya < 0 || ya >= height) continue;

                int col = mob.getSprite().pixels[x + y * Tile.TILE_SIZE];
                if(mob instanceof IPlayer player && !player.isVisible()) {
                    col = blendColors(col, pixels[xa + ya * width], 75);
                }
                if(col != 0xffff00ff) pixels[xa + ya * width] = col;
            }
        }
    }

    public void setOffset(int xOffset, int yOffset) {

        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public void renderPixel(int xp, int yp, int color, int size, boolean fixed, int alpha) {
        color = blendColors(color, pixels[xp + yp * width], alpha);
        if(!fixed) {
            xp -= xOffset;
            yp -= yOffset;
        }
        for(int y = 0; y < size; y++) {
            int ya = yp + y;
            for(int x = 0; x < size; x++) {
                int xa = xp + x;
                if(xa < 0 || xa >= width || ya < 0 || ya >= height) continue;
                pixels[xa + ya * width] = color;
            }
        }
    }

    /**
     * Blends two colors together
     * @param foregroundColor the color of the sprite
     * @param backgroundColor the color of the background
     * @param alpha the alpha value of the sprite
     * @return the blended color
     */
    private int blendColors(int foregroundColor, int backgroundColor, int alpha) {
        if(backgroundColor == 0xffff00ff) return foregroundColor;
        if(foregroundColor == 0xffff00ff) return backgroundColor;
        int blendedRed = ((foregroundColor >> Tile.TILE_SIZE) & 0xFF) * alpha / 255 + ((backgroundColor >> Tile.TILE_SIZE) & 0xFF) * (255 - alpha) / 255;
        int blendedGreen = ((foregroundColor >> 8) & 0xFF) * alpha / 255 + ((backgroundColor >> 8) & 0xFF) * (255 - alpha) / 255;
        int blendedBlue = (foregroundColor & 0xFF) * alpha / 255 + (backgroundColor & 0xFF) * (255 - alpha) / 255;
        return (blendedRed << Tile.TILE_SIZE) | (blendedGreen << 8) | blendedBlue;
    }
}


