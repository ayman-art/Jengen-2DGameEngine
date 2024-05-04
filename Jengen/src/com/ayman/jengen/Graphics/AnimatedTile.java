package com.ayman.jengen.Graphics;

public class AnimatedTile {
    int time = 0;
    Sprite[] sprites;

    static int duration = 20;
    public AnimatedTile(Sprite sprite) {

        this.sprites = new Sprite[4];
        for(int x = 0; x < 2; x++) {
            for(int y = 0; y < 2; y++) {
                sprites[x + y * 2] = new Sprite(sprite, x * 8, y * 8, 8, 8);
                sprites[x + y * 2].x = x * 8;
                sprites[x + y * 2].y = y * 8;
            }
        }
    }
    public void render(int x, int y, Screen screen) {
        for(Sprite sprite : sprites) {
            screen.renderSprite(x + sprite.x, y + sprite.y, sprite, false);
        }
    }

}
