package com.ayman.jengen.Graphics;

/**
 * A class used to be delegated the animation of the sprite
 */
public class AnimatedSprite implements Cloneable {


    private int frame = 0; //the current frame of the animation


    private Sprite[] sprites;
    private int rate = 5; //the rate at which the frames change
    private int time = 0; //the time since the last frame change

    public AnimatedSprite(SpriteSheet sheet, int numSprites) {
        if (sheet.WIDTH > sheet.HEIGHT) {
            loadHorizontal(sheet, numSprites);
        } else {
            loadVertical(sheet, numSprites);
        }
    }

    public AnimatedSprite(Sprite[] sprites) {
        this.sprites = sprites;
    }

    private void loadHorizontal(SpriteSheet sheet, int numSprites) {
        sprites = new Sprite[numSprites];

        for (int i = 0; i < numSprites; i++) {
            sprites[i] = new Sprite(sheet.HEIGHT, 0, i * (sheet.HEIGHT), sheet);
        }
    }

    private void loadVertical(SpriteSheet sheet, int numSprites) {
        sprites = new Sprite[numSprites];

        for (int i = 0; i < numSprites; i++) {
            sprites[i] = new Sprite(sheet.WIDTH, i * (sheet.WIDTH), 0, sheet);
        }
    }


    public void restart() {
        frame = 0;

    }

    public void update() {
        time++;
        if (time % rate == 0) {
            if (frame >= sprites.length - 1) frame = 0;
            else frame++;

        }
    }

    public Sprite getCurrentSPrite() {
        return this.sprites[frame];
    }

    public void setFrameRate(int frames) {
        rate = frames;
    }

    public AnimatedSprite clone() throws CloneNotSupportedException {
        AnimatedSprite clone = new AnimatedSprite(this);
        return clone;
    }

    public AnimatedSprite(AnimatedSprite animatedSprite) { // copy constructor to avoid copying the Shared Sprite array
        this.sprites = animatedSprite.sprites.clone();
        this.rate = animatedSprite.rate;
        this.time = animatedSprite.time;
        this.frame = animatedSprite.frame;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}