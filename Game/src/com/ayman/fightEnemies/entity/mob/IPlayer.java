package com.ayman.fightEnemies.entity.mob;

import com.ayman.fightEnemies.Game;
import com.ayman.fightEnemies.Graphics.Screen;
import com.ayman.fightEnemies.Input.Mouse;
import com.ayman.fightEnemies.entity.projectile.Projectile;
import com.ayman.fightEnemies.entity.projectile.WizardProjectile;

public interface IPlayer extends IMob{
    public void update();

    public void render(Screen screen);
    public String getName();
    public boolean isVisible();
    public void setVisible(boolean visible);
}
