package com.ayman.jengen.audio;

import com.ayman.jengen.entity.projectile.WizardProjectile;

import javax.sound.sampled.*;
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
        FloatControl gainControl = (FloatControl) gunClip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(-80.0f); // -80.0f is the minimum volume (in decibels)
        // Start playing the clip
        gunClip.setFramePosition(0); // Start from the beginning
        gunClip.start();
        gainControl.setValue(0.0f); // Set the volume to 0.0f (maximum volume)
        break_tileClip = loadSound("/sounds/break_tile.wav");
        winningClip = loadSound("/sounds/winning.wav");
        losingClip = loadSound("/sounds/losing.wav");
        coinClip = loadSound("/sounds/coin.wav");
        healthClip = loadSound("/sounds/health.wav");


    }

    public static Clip loadSound(String resourcePath) {
        Clip clip;
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
        try {
            clip.open(AudioSystem.getAudioInputStream(Objects.requireNonNull(WizardProjectile.class.getResource(resourcePath))));
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            throw new RuntimeException(e);
        }
        return clip;
    }
}
