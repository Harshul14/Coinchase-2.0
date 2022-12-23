package com.coinchase.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {
    public static Sound jump;
    public static Music background;
    public static void create()
    {
        jump = Gdx.audio.newSound(Gdx.files.internal("sounds/jumpfinal.wav"));
        background = Gdx.audio.newMusic(Gdx.files.internal("sounds/coinchasebgv2.mp3"));
//        background = Gdx.audio.newMusic(Gdx.files.internal("sounds/bg.mp3"));
    }

    public static void dispose()
    {
        jump.dispose();
        background.dispose();
    }
}
