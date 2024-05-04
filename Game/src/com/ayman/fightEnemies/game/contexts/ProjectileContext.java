package com.ayman.fightEnemies.game.contexts;

/**
 * ProjectileContext is a class that holds the context of the Projectile.
 * For now the engine only supports one type of projectile.
 */
public class ProjectileContext extends Context {

        private final int speed;
        private final int damage;
        private final int range;
        private final int fireInterval;

        private ProjectileContext(Builder builder){
            this.speed = builder.speed;
            this.damage = builder.damage;
            this.range = builder.range;
            this.fireInterval = builder.fireInterval;
        }

        public int getSpeed() {
            return speed;
        }

        public int getDamage() {
            return damage;
        }

        public int getRange() {
            return range;
        }
        public int getFireInterval() {
            return fireInterval;
        }



        public static class Builder {
            private int speed = 2;
            private int damage = 1;
            private int range = 1000;
            private int fireInterval = 10;

            public Builder setSpeed(int speed) {
                this.speed = speed;
                return this;
            }

            public Builder setDamage(int damage) {
                this.damage = damage;
                return this;
            }

            public Builder setRange(int range) {
                this.range = range;
                return this;
            }

            public Builder setFireInterval(int fireInterval) {
                this.fireInterval = fireInterval;
                return this;
            }

            public ProjectileContext build(){
                return new ProjectileContext(this);
            }
        }
}
