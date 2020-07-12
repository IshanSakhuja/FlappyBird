package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import java.util.Random;
import javax.xml.soap.Text;

public class MyGdxGame extends ApplicationAdapter {
	Texture gameOver;
	Circle circle;
	//ShapeRenderer shapeRenderer;
	SpriteBatch batch;
	Texture bg;
	Rectangle[] topTubeRectangles;
	Rectangle[] bottomTubeRectangles;
	int gameStart;
	Texture[] bird;
	int flapBirds;
	int numberOfTubes = 2;
	Texture[] topTube = new Texture[numberOfTubes];
	Texture[] bottomTube = new Texture[numberOfTubes];
	float gap;
	float gravity;
	float birdY;
	float velocity;
	float[] tubeX;
	float ishan;
	int i;
	float[] tubeOffesets;
	Random randomNumberGenerator;
	@Override
	public void create () {
		topTubeRectangles = new Rectangle[numberOfTubes];
		bottomTubeRectangles = new Rectangle[numberOfTubes];
		gameOver = new Texture("gameover.png");
		circle = new Circle();
		//shapeRenderer = new ShapeRenderer();
		tubeOffesets = new float[numberOfTubes];
		tubeX = new float[numberOfTubes];
		randomNumberGenerator = new Random();
		velocity = 3;
		gravity = 5;
		gap = 200;
		batch = new SpriteBatch();
		flapBirds = 0;
		bg = new Texture("bg.png");
		gameStart = 0;
		bird = new Texture[2];
		bird[0] = new Texture("bird.png");
		bird[1] = new Texture("bird2.png");
		for (i = 0; i < numberOfTubes; i++){
			topTubeRectangles[i] = new Rectangle();
			bottomTubeRectangles[i] = new Rectangle();
			topTube[i] = new Texture("toptube.png");
			bottomTube[i] = new Texture("bottomtube.png");
			tubeX[i] = (Gdx.graphics.getWidth() + (0.5f  * i * Gdx.graphics.getWidth()));
			tubeOffesets[i] = (randomNumberGenerator.nextFloat() -  0.5f) * (Gdx.graphics.getHeight() - bottomTube[0].getHeight() - 200);
		}
		birdY = Gdx.graphics.getHeight()/2-bird[0].getHeight()/2;
		ishan = bottomTube[0].getHeight()-Gdx.graphics.getHeight()/2;
	}
	@Override
	public void render () {
		batch.begin();
		//shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		if(flapBirds == 0)
		{
			flapBirds = 1;
		}else
		{
			flapBirds = 0;
		}
		if(Gdx.input.isTouched() && gameStart != 2)
		{
			gameStart = 1;
		}
		if(gameStart == 1)
		{
			batch.draw(bg,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
			if(velocity < 10) {
				velocity += 0.005;
			}
			if(gravity <= 25)
			{
				gravity += 0.5;
			}
			if(Gdx.input.isTouched())
			{
				birdY = birdY + 10 + gravity;
			}
			birdY -= gravity;
			for(i = 0;i<numberOfTubes;i++) {
				if (Intersector.overlaps(circle, topTubeRectangles[i]) || Intersector.overlaps(circle, bottomTubeRectangles[i])) {

					gameStart = 2;

				}
				tubeX[i] -= velocity;
				batch.draw(topTube[i], tubeX[i], Gdx.graphics.getHeight() / 2 + gap+tubeOffesets[i]);
				batch.draw(bottomTube[i], tubeX[i], -(ishan + gap-tubeOffesets[i]));
				topTubeRectangles[i] = new Rectangle(tubeX[i], Gdx.graphics.getHeight() / 2 + gap+tubeOffesets[i], topTube[i].getWidth(), topTube[i].getHeight());
				bottomTubeRectangles[i] = new Rectangle(tubeX[i], -(ishan + gap-tubeOffesets[i]), bottomTube[i].getWidth(), bottomTube[i].getHeight());
				//shapeRenderer.rect(topTubeRectangles[i].x,topTubeRectangles[i].y,topTubeRectangles[i].width,topTubeRectangles[i].height);
				//shapeRenderer.rect(bottomTubeRectangles[i].x,bottomTubeRectangles[i].y,bottomTubeRectangles[i].width,bottomTubeRectangles[i].height);
				if(tubeX[i] < -(topTube[0].getWidth()) || tubeX[i] < -(bottomTube[0].getWidth()))
				{
					tubeX[i] += Gdx.graphics.getWidth() + topTube[0].getWidth()/2;
					tubeOffesets[i] = (randomNumberGenerator.nextFloat() -  0.5f) * (Gdx.graphics.getHeight() - bottomTube[0].getHeight() - 150);
				}
			}
			if(birdY < bird[0].getHeight()/2)
			{
				gameStart = 2;
			}
			batch.draw(bird[flapBirds], Gdx.graphics.getWidth() / 2 - bird[0].getWidth() / 2, birdY);
		}else if(gameStart == 0) {
			batch.draw(bg,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
			batch.draw(bird[flapBirds], Gdx.graphics.getWidth() / 2 - bird[0].getWidth() / 2, birdY);
		}
		else if(gameStart == 2)
		{
			batch.draw(gameOver,Gdx.graphics.getWidth()/2-gameOver.getWidth()/2,Gdx.graphics.getHeight()/2-gameOver.getHeight()/2);
		}
		//shapeRenderer.setColor(Color.RED);
		circle.set(Gdx.graphics.getWidth()/2-bird[0].getWidth()/2,birdY,bird[0].getWidth()/2);
		//shapeRenderer.circle(circle.x,circle.y,circle.radius);
		//shapeRenderer.end();
		batch.end();
	}
	@Override
	public void dispose () {
		batch.dispose();
		bg.dispose();
	}
}
