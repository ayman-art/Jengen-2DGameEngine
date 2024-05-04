package com.ayman.jengen.util;

import com.ayman.jengen.entity.IEntity;
import com.ayman.jengen.entity.mob.Chaser;
import com.ayman.jengen.entity.mob.Dummy;
import com.ayman.jengen.entity.mob.Player;
import com.ayman.jengen.level.effects.CoinEffect;
import com.ayman.jengen.level.effects.HealthEffect;
import com.ayman.jengen.level.effects.WinningEffect;
import com.ayman.jengen.level.effects.decorationEffects.BreakTilesEffect;
import com.ayman.jengen.level.effects.decorationEffects.HelperFighterEffect;
import com.ayman.jengen.level.effects.decorationEffects.InvisibilityEffect;
import com.ayman.jengen.level.effects.decorationEffects.SpeedEffect;

/**
 * This class is used to create entities based on their type.
 The goal of this class is to provide a way to create entities while parsing the level file.
 */
public class EntityFactory {

    static IEntity createEntity(String type, int x, int y) {
        switch (type) {
            case "Chaser" -> {
                return new Chaser(x, y);
            }
            case "Dummy" -> {
                return new Dummy(x, y);
            }
            case "Player" -> {
                return new Player(x, y, null, null);
            }
            case "BreakTilesEffect" -> {
                return new BreakTilesEffect(new Vector2i(x, y));
            }
            case "HelperFighterEffect" -> {
                return new HelperFighterEffect(new Vector2i(x, y));
            }
            case "InvisibilityEffect" -> {
                return new InvisibilityEffect(new Vector2i(x, y));
            }
            case "SpeedEffect" -> {
                return new SpeedEffect(new Vector2i(x, y));
            }
            case "CoinEffect" -> {
                return new CoinEffect(new Vector2i(x, y));
            }
            case "HealthEffect" -> {
                return new HealthEffect(new Vector2i(x, y), 10);
            }
            case "WinningEffect" -> {
                return new WinningEffect(new Vector2i(x, y));
            }
            default -> throw new IllegalArgumentException("Invalid entity type: " + type);
        }
    }
}
