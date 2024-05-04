package com.ayman.fightEnemies.audio;

import com.ayman.fightEnemies.entity.projectile.WizardProjectile;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Objects;

/**
 * This class is responsible for loading and saving sound effects.
 */
public class Sound {
    public static Clip gunClip;
    public static Clip break_tileClip;

    public static Clip winningClip;
    public static Clip losingClip;
    public static Clip coinClip;
    public static Clip healthClip;


    /**
     * Load all the sound effects.
     */
    public static void init() {
        gunClip = loadSound("/sounds/gun01.wav");
        break_tileClip = loadSound("/sounds/break_tile.wav");
        winningClip = loadSound("/sounds/winning.wav");
        losingClip = loadSound("/sounds/losing.wav");
        coinClip = loadSound("/sounds/coin.wav");
        healthClip = loadSound("/sounds/health.wav");


    }

    public static Clip loadSound(String resourcePath) {
        Clip clip;
        try {
            System.out.println("Loading sound");
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
        try {
            clip.open(AudioSystem.getAudioInputStream(Objects.requireNonNull(WizardProjectile.class.getResource(resourcePath))));
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Done");
        return clip;
    }
}
