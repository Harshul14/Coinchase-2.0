// package com.coinchase.game;

// import com.badlogic.gdx.ApplicationAdapter;
// import com.badlogic.gdx.Gdx;
// import com.badlogic.gdx.graphics.Color;
// import com.badlogic.gdx.graphics.Texture;
// import com.badlogic.gdx.graphics.g2d.BitmapFont;
// import com.badlogic.gdx.graphics.g2d.SpriteBatch;
// import com.badlogic.gdx.math.Intersector;
// import com.badlogic.gdx.math.Rectangle;
// import com.badlogic.gdx.utils.ScreenUtils;

// import java.util.ArrayList;
// import java.util.Random;

// public class CoinChase extends ApplicationAdapter {
// 	SpriteBatch batch;
// 	Texture background;
// 	Texture[] man;
// 	int manState = 0;
// 	int pause = 0;
// 	float gravity = 0.2f;
// 	float velocity = 0;
// 	int manY = 0;
// 	Rectangle manRectangle;
// 	BitmapFont font;
// 	Texture dizzy;
// 	int score = 0;
// 	int gameState = 0;

// 	Random random;

// 	ArrayList<Integer> coinXs = new ArrayList<Integer>();
// 	ArrayList<Integer> coinYs = new ArrayList<Integer>();
// 	ArrayList<Rectangle> coinRectangles =  new ArrayList<Rectangle>();
// 	Texture coin;
// 	int coinCount;

// 	ArrayList<Integer> bombXs = new ArrayList<Integer>();
// 	ArrayList<Integer> bombYs = new ArrayList<Integer>();
// 	ArrayList<Rectangle> bombRectangles =  new ArrayList<Rectangle>();
// 	Texture bomb;
// 	int bombCount;

// 	@Override
// 	public void create () {

// 		SoundManager.create();
// 		batch = new SpriteBatch();
// 		background = new Texture("bg2.png");
// 		man = new Texture[6];
// 		man[0] = new Texture("f1.png");
// 		man[1] = new Texture("f2.png");
// 		man[2] = new Texture("f3.png");
// 		man[3] = new Texture("f4.png");
// 		man[4] = new Texture("f5.png");
// 		man[5] = new Texture("f6.png");

// 		manY = Gdx.graphics.getHeight() / 2;

// 		coin = new Texture("coin.png");
// 		bomb = new Texture("bomb.png");
// 		random = new Random();

// 		dizzy = new Texture("dizzy.png");

// 		font = new BitmapFont();
// 		font.setColor(Color.PURPLE);
// 		font.getData().setScale(10);
// 	}

// 	public void makeCoin() {
// 		float height = random.nextFloat() * Gdx.graphics.getHeight();
// 		coinYs.add((int)height);
// 		coinXs.add(Gdx.graphics.getWidth());
// 	}

// 	public void makeBomb() {
// 		float height = random.nextFloat() * Gdx.graphics.getHeight();
// 		bombYs.add((int)height);
// 		bombXs.add(Gdx.graphics.getWidth());
// 	}

// 	@Override
// 	public void render () {
// 		batch.begin();
// 		batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());


// 		if (gameState == 1) {
// 			// GAME IS LIVE
// 			SoundManager.background.setLooping(true);
// 			SoundManager.background.setVolume(5.0f);
// 			SoundManager.background.play();
// 			// BOMB
// 			if (bombCount < 250) {
// 				bombCount++;
// 			} else {
// 				bombCount = 0;
// 				makeBomb();
// 			}

// 			bombRectangles.clear();
// 			for (int i=0;i < bombXs.size();i++) {
// 				batch.draw(bomb, bombXs.get(i), bombYs.get(i));
// 				bombXs.set(i, bombXs.get(i) - 8);
// 				bombRectangles.add(new Rectangle(bombXs.get(i), bombYs.get(i), bomb.getWidth(), bomb.getHeight()));
// 			}

// 			// COINS
// 			if (coinCount < 100) {
// 				coinCount++;
// 			} else {
// 				coinCount = 0;
// 				makeCoin();
// 			}

// 			coinRectangles.clear();
// 			for (int i=0;i < coinXs.size();i++) {
// 				batch.draw(coin, coinXs.get(i), coinYs.get(i));
// 				coinXs.set(i, coinXs.get(i) - 4);
// 				coinRectangles.add(new Rectangle(coinXs.get(i), coinYs.get(i), coin.getWidth(), coin.getHeight()));
// 			}

// 			if (Gdx.input.justTouched()) {
// 				SoundManager.background.play();
// 				SoundManager.background.setLooping(true);
// 				SoundManager.background.setVolume(5.0f);

// 				velocity = -10;
// 				SoundManager.jump.play(0.2f);
// 				SoundManager.background.setLooping(true);
// 			}


// 			if (pause < 5) {
// 				pause++;
// 			} else {
// 				pause = 0;
// 				if (manState < 3) {
// 					manState++;
// 				} else {
// 					manState = 0;
// 				}
// 			}

// 			velocity += gravity;
// 			manY -= velocity;

// 			if (manY <= 0) {
// 				manY = 0;
// 			}
// 		} else if (gameState == 0) {
// 			// Waitng to start
// 			if (Gdx.input.justTouched()) {
// 				gameState = 1;
// 			}
// 		} else if (gameState == 2) {
// 			// GAME OVER
// 			if (Gdx.input.justTouched()) {
// 				gameState = 1;
// 				SoundManager.background.play();
// 				SoundManager.background.setLooping(true);
// 				SoundManager.background.setVolume(5.0f);
// 				manY = Gdx.graphics.getHeight() / 2;
// 				score = 0;
// 				velocity = 0;
// 				coinXs.clear();
// 				coinYs.clear();
// 				coinRectangles.clear();
// 				coinCount = 0;
// 				bombXs.clear();
// 				bombYs.clear();
// 				bombRectangles.clear();
// 				bombCount = 0;
// 			}
// 		}

// 		if (gameState == 2) {
// 			SoundManager.background.dispose();
// 			batch.draw(dizzy, Gdx.graphics.getWidth() / 2 - man[manState].getWidth() / 2, manY);
// 		} else {
// 			batch.draw(man[manState], Gdx.graphics.getWidth() / 2 - man[manState].getWidth() / 2, manY);
// 		}
// 		manRectangle = new Rectangle(Gdx.graphics.getWidth() / 2 - man[manState].getWidth() / 2, manY, man[manState].getWidth(), man[manState].getHeight());

// 		for (int i=0; i < coinRectangles.size();i++) {
// 			if (Intersector.overlaps(manRectangle, coinRectangles.get(i))){
// 				score++;

// 				coinRectangles.remove(i);
// 				coinXs.remove(i);
// 				coinYs.remove(i);
// 				break;
// 			}
// 		}

// 		for (int i=0; i < bombRectangles.size();i++) {
// 			if (Intersector.overlaps(manRectangle, bombRectangles.get(i)))
// 			{
// 				Gdx.app.log("Bomb!", "Collision!");
// 				gameState = 2;
// 			}
// 		}
// 		font.draw(batch, String.valueOf(score),100,200);

// 		batch.end();
// 	}

// 	@Override
// 	public void dispose () {
// 		SoundManager.dispose();
// 		SoundManager.background.dispose();
// 		batch.dispose();
// 	}
// }


//updated the Y-index for the Object

package com.coinchase.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.Random;

public class CoinChase extends ApplicationAdapter {
    SpriteBatch batch;
    Texture background;
    Texture[] man;
    int manState = 0;
    int pause = 0;
    float gravity = 0.2f;
    float velocity = 0;
    int manY = 0;
    Rectangle manRectangle;
    BitmapFont font;
    Texture dizzy;
    int score = 0;
    int gameState = 0;

    Random random;

    ArrayList<Integer> coinXs = new ArrayList<Integer>();
    ArrayList<Integer> coinYs = new ArrayList<Integer>();
    ArrayList<Rectangle> coinRectangles = new ArrayList<Rectangle>();
    Texture coin;
    int coinCount;

    ArrayList<Integer> bombXs = new ArrayList<Integer>();
    ArrayList<Integer> bombYs = new ArrayList<Integer>();
    ArrayList<Rectangle> bombRectangles = new ArrayList<Rectangle>();
    Texture bomb;
    int bombCount;

    @Override
    public void create() {

        SoundManager.create();
        batch = new SpriteBatch();
        background = new Texture("bg2.png");
        man = new Texture[6];
        man[0] = new Texture("f1.png");
        man[1] = new Texture("f2.png");
        man[2] = new Texture("f3.png");
        man[3] = new Texture("f4.png");
        man[4] = new Texture("f5.png");
        man[5] = new Texture("f6.png");

        manY = Gdx.graphics.getHeight() / 2;

        coin = new Texture("coin.png");
        bomb = new Texture("bomb.png");
        random = new Random();

        dizzy = new Texture("dizzy.png");

        font = new BitmapFont();
        font.setColor(Color.PURPLE);
        font.getData().setScale(10);
//        plays in the creation time only
//        SoundManager.background.setLooping(true);
//        SoundManager.background.setVolume(5.0f);
//        SoundManager.background.play();
    }

    public void makeCoin() {
        float height = random.nextFloat() * Gdx.graphics.getHeight();
        coinYs.add((int) height);
        coinXs.add(Gdx.graphics.getWidth());
    }

    public void makeBomb() {
        float height = random.nextFloat() * Gdx.graphics.getHeight();
        bombYs.add((int) height);
        bombXs.add(Gdx.graphics.getWidth());
    }

    @Override
    public void render() {
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        if (gameState == 1) {
            // GAME IS LIVE
//            SoundManager.background.setLooping(true);
//            SoundManager.background.setVolume(5.0f);
//            SoundManager.background.play();
            // BOMB
            if (bombCount < 250) {
                bombCount++;
            } else {
                bombCount = 0;
                makeBomb();
            }

            bombRectangles.clear();
            for (int i = 0; i < bombXs.size(); i++) {
                batch.draw(bomb, bombXs.get(i), bombYs.get(i));
                bombXs.set(i, bombXs.get(i) - 8);
                bombRectangles.add(new Rectangle(bombXs.get(i), bombYs.get(i), bomb.getWidth(), bomb.getHeight()));
            }

            // COINS
            if (coinCount < 100) {
                coinCount++;
            } else {
                coinCount = 0;
                makeCoin();
            }

            coinRectangles.clear();
            for (int i = 0; i < coinXs.size(); i++) {
                batch.draw(coin, coinXs.get(i), coinYs.get(i));
                coinXs.set(i, coinXs.get(i) - 4);
                coinRectangles.add(new Rectangle(coinXs.get(i), coinYs.get(i), coin.getWidth(), coin.getHeight()));
            }

            if (Gdx.input.justTouched()) {
                SoundManager.background.play();
                SoundManager.background.setLooping(true);
                SoundManager.background.setVolume(5.0f);

//                velocity = -10;
//                SoundManager.jump.play(0.2f);
//                SoundManager.background.setLooping(true);
                if (manY < Gdx.graphics.getHeight() - man[manState].getHeight()) {
                    velocity = -10;
                    SoundManager.jump.play(0.2f);
                    SoundManager.background.setLooping(true);
                }
            }


            if (pause < 5) {
                pause++;
            } else {
                pause = 0;
                if (manState < 3) {
                    manState++;
                } else {
                    manState = 0;
                }
            }

            velocity += gravity;
            manY -= velocity;

            if (manY <= 0) {
                manY = 0;
            }
            if (manY > Gdx.graphics.getHeight() - man[manState].getHeight()) {
                manY = Gdx.graphics.getHeight() - man[manState].getHeight();
            }
        } else if (gameState == 0) {
            // Waitng to start
            if (Gdx.input.justTouched()) {
                SoundManager.background.play();
                SoundManager.background.setLooping(true);
                SoundManager.background.setVolume(5.0f);
                gameState = 1;
            }
        } else if (gameState == 2) {
            // GAME OVER
            if (Gdx.input.justTouched()) {
                SoundManager.background.play();
                SoundManager.background.setLooping(true);
                SoundManager.background.setVolume(5.0f);
                gameState = 1;
                manY = Gdx.graphics.getHeight() / 2;
                score = 0;
                velocity = 0;
                coinXs.clear();
                coinYs.clear();
                coinRectangles.clear();
                coinCount = 0;
                bombXs.clear();
                bombYs.clear();
                bombRectangles.clear();
                bombCount = 0;
            }
        }

        if (gameState == 2) {
            SoundManager.background.dispose();
            batch.draw(dizzy, Gdx.graphics.getWidth() / 2 - man[manState].getWidth() / 2, manY);
        } else {
            batch.draw(man[manState], Gdx.graphics.getWidth() / 2 - man[manState].getWidth() / 2, manY);
        }
        manRectangle = new Rectangle(Gdx.graphics.getWidth() / 2 - man[manState].getWidth() / 2, manY, man[manState].getWidth(), man[manState].getHeight());

        for (int i = 0; i < coinRectangles.size(); i++) {
            if (Intersector.overlaps(manRectangle, coinRectangles.get(i))) {
                score++;

                coinRectangles.remove(i);
                coinXs.remove(i);
                coinYs.remove(i);
                break;
            }
        }

        for (int i = 0; i < bombRectangles.size(); i++) {
            if (Intersector.overlaps(manRectangle, bombRectangles.get(i))) {
                Gdx.app.log("Bomb!", "Collision!");
                gameState = 2;
            }
        }
        font.draw(batch, String.valueOf(score), 100, 200);

        batch.end();
    }

    @Override
    public void dispose() {
        SoundManager.dispose();
        SoundManager.background.dispose();
        batch.dispose();
    }
}
