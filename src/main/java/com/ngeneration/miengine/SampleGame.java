package com.ngeneration.miengine;

import java.util.ArrayList;

import com.ngeneration.miengine.graphics.Color;
import com.ngeneration.miengine.graphics.FitViewport;
import com.ngeneration.miengine.graphics.Sprite;
import com.ngeneration.miengine.graphics.SpriteBatch;
import com.ngeneration.miengine.graphics.Texture;
import com.ngeneration.miengine.math.MathUtils;
import com.ngeneration.miengine.math.Rectangle;
import com.ngeneration.miengine.math.Vector2;

public class SampleGame implements ApplicationListener {

	Texture backgroundTexture;
	Texture bucketTexture;
	Texture dropTexture;
//    Sound dropSound;
//    Music music;
	SpriteBatch spriteBatch;
	FitViewport viewport;
	Sprite bucketSprite;
	Vector2 touchPos;
	ArrayList<Sprite> dropSprites;
	float dropTimer;
	Rectangle bucketRectangle;
	Rectangle dropRectangle;

	@Override
	public void create() {
		System.out.println("xd");
		backgroundTexture = new Texture("assets/background.png");
		bucketTexture = new Texture("assets/bucket.png");
		dropTexture = new Texture("assets/drop.png");
//        dropSound = MyEngine.audio.newSound(MyEngine.files.internal("drop.mp3"));
//        music = MyEngine.audio.newMusic(MyEngine.files.internal("music.mp3"));
		spriteBatch = new SpriteBatch();
		viewport = new FitViewport(8, 5);
		bucketSprite = new Sprite(bucketTexture);
		bucketSprite.setSize(1, 1);
		touchPos = new Vector2();
		dropSprites = new ArrayList<>();
		bucketRectangle = new Rectangle();
		dropRectangle = new Rectangle();
//        music.setLooping(true);
//        music.setVolume(.5f);
//        music.play();
		System.out.println("xd3");
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
	}

	@Override
	public void render() {
		input();
		logic();
		draw();
	}

	private void input() {
		float speed = 4f;
		float delta = Engine.graphics.getDeltaTime();
		if (Engine.input.isKeyPressed(Input.Keys.VK_RIGHT)) {
			bucketSprite.translateX(speed * delta);
		} else if (Engine.input.isKeyPressed(Input.Keys.VK_LEFT)) {
			bucketSprite.translateX(-speed * delta);
		}

		if (Engine.input.isTouched()) {
			touchPos.set(Engine.input.getX(), Engine.input.getY());
			viewport.unproject(touchPos);
			bucketSprite.setCenterX(touchPos.x);
		}
	}

	private void logic() {
		float worldWidth = viewport.getWorldWidth();
		float worldHeight = viewport.getWorldHeight();
		float bucketWidth = bucketSprite.getWidth();
		float bucketHeight = bucketSprite.getHeight();

		bucketSprite.setX(MathUtils.clamp(bucketSprite.getX(), 0, worldWidth - bucketWidth));

		float delta = Engine.graphics.getDeltaTime();
		bucketRectangle.set(bucketSprite.getX(), bucketSprite.getY(), bucketWidth, bucketHeight);

		for (int i = dropSprites.size() - 1; i >= 0; i--) {
			Sprite dropSprite = dropSprites.get(i);
			float dropWidth = dropSprite.getWidth();
			float dropHeight = dropSprite.getHeight();

			dropSprite.translateY(-2f * delta);
			dropRectangle.set(dropSprite.getX(), dropSprite.getY(), dropWidth, dropHeight);

			if (dropSprite.getY() < -dropHeight)
				dropSprites.remove(i);
			else if (bucketRectangle.overlaps(dropRectangle)) {
				dropSprites.remove(i);
//                dropSound.play();
			}
		}

		dropTimer += delta;
		if (dropTimer > 1f) {
			dropTimer = 0;
			createDroplet();
		}
	}

	private void draw() {
		ScreenUtils.clear(Color.BLACK);
		viewport.apply();
		spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
		spriteBatch.begin();

		float worldWidth = viewport.getWorldWidth();
		float worldHeight = viewport.getWorldHeight();

		spriteBatch.drawTexture(backgroundTexture, 0, 0, worldWidth, worldHeight);
		bucketSprite.draw(spriteBatch);

		for (Sprite dropSprite : dropSprites) {
			dropSprite.draw(spriteBatch);
		}

		spriteBatch.end();
	}

	private void createDroplet() {
		float dropWidth = 1;
		float dropHeight = 1;
		float worldWidth = viewport.getWorldWidth();
		float worldHeight = viewport.getWorldHeight();

		Sprite dropSprite = new Sprite(dropTexture);
		dropSprite.setSize(dropWidth, dropHeight);
		dropSprite.setX(MathUtils.random(0f, worldWidth - dropWidth));
		dropSprite.setY(worldHeight);
		dropSprites.add(dropSprite);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}

}
