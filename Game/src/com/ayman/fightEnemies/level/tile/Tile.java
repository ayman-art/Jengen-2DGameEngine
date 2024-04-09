package com.ayman.fightEnemies.level.tile;

import com.ayman.fightEnemies.Graphics.Screen;
import com.ayman.fightEnemies.Graphics.Sprite;

public class Tile {


        public int x, y;
        public Sprite sprite;

        public static Tile sky = new SkyTile(Sprite.sky);
        public static Tile bird = new BirdTile(Sprite.bird);
        public static Tile grass = new grassTile(Sprite.grass);
        public static Tile rock = new rockTile(Sprite.rock.rotatedSPrite(Math.PI));
        public static Tile flower = new flowerTile(Sprite.flower);
        public static Tile water = new WaterTile(Sprite.water);
        public static Tile brick = new BrickTile(Sprite.brick);
        public static Tile wood = new WoodTile(Sprite.wood);


        public static Tile voidTile = new VoidTile(Sprite.voidSprite);


        public static final int grassColor = 0xff00ff00;
        public static final int rockColor = 0xff000000;
        public static final int flowerColor = 0xffffff00;
        public static final int skyColor = 0xffADD8E6;
        public static final int birdColor = 0; // not used.
        public static final int brickColor = 0xffff0000;
        public static final int waterColor = 0xff00ffff;
        public static final int woodColor = 0xff7F0000;






        public Tile(Sprite sprite) {

            this.sprite = sprite;
        }

        public void render(int x, int y, Screen screen) {


        }

        public boolean isSolid() {

            return false;
        }


}
