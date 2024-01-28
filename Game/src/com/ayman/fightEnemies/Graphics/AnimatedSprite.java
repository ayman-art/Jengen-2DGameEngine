package com.ayman.fightEnemies.Graphics;

public class AnimatedSprite {


    private int frame = 0; //the current frame of the animation


    private Sprite[] sprites;
    private int rate = 5; //the rate at which the frames change
    private int time = 0; //the time since the last frame change

    public AnimatedSprite(SpriteSheet sheet, int numSprites) {
            if(sheet.WIDTH > sheet.HEIGHT) {
                loadHorizontal(sheet, numSprites);
            } else {
                loadVertical(sheet, numSprites);
            }
    }

   private void loadHorizontal(SpriteSheet sheet, int numSprites) {
        sprites = new Sprite[numSprites];

        for(int i = 0; i < numSprites; i++) {
            sprites[i] = new Sprite(sheet.HEIGHT, 0, i * (sheet.HEIGHT), sheet);
        }
   }
   private void loadVertical(SpriteSheet sheet, int numSprites) {
        sprites = new Sprite[numSprites];

        for(int i = 0; i < numSprites; i++) {
            sprites[i] = new Sprite(sheet.WIDTH, i * (sheet.WIDTH), 0, sheet);
        }
   }


    public void restart() {
        frame = 0;

    }

    public void update() {
        time++;
        if(time % rate == 0) {
            if(frame >= sprites.length - 1) frame = 0;
            else frame++;

        }
    }

    public Sprite getCurrentSPrite() {
        return this.sprites[frame];
    }

    public void setFrameRate(int frames) {
        rate = frames;
    }
}
