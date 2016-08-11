/*******************************************************************************
 * Copyright 2012-Present, MoribitoTech
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package menu.screens;

import jeu.CSG;
import jeu.Physic;
import jeu.Strings;
import jeu.mode.EndlessMode;
import menu.Credits;
import menu.BasicMenu;
import menu.tuto.OnClick;
import menu.ui.Button;
import assets.sprites.Animations;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Array;

import elements.particular.particles.Particles;
import elements.particular.particles.ParticuleBundles;

public abstract class AbstractScreen implements Screen {

	protected Game game;
	protected Array<Button> buttons = new Array<Button>();
	private Credits credits;
	public final static int PADDING = 11, BUTTON_WIDTH = (CSG.screenWidth / PADDING) * 9, BUTTON_HEIGHT = CSG.height / 25;
	public final static int BUTTON_HALF_WIDTH = BUTTON_WIDTH / 2, BUTTON_HALF_HEIGHT = BUTTON_HEIGHT / 2;
	public final static int SMALL_BUTTON_WIDTH = (CSG.screenWidth / PADDING) * 4, SMALL_BUTTON_HEIGHT = CSG.height / 18;
	public final static int MINI_BOUTON_WIDTH = SMALL_BUTTON_WIDTH/2, MINI_BOUTON_HEIGHT = SMALL_BUTTON_HEIGHT/2, yOffset = CSG.height/10;
	public static OrthographicCamera cam = new OrthographicCamera(CSG.screenWidth, CSG.height);
	protected Button buttonBack;
	public boolean renderBackground = true;
	private final Array<BasicMenu> enemies = new Array<BasicMenu>();
	private final float speed = CSG.height / 6, offset = speed/10;
	private float trigger = 0;
	
	public AbstractScreen(final Game game) {
		super();
		this.game = game;
		credits = new Credits();
		
		CSG.initBloom();
		cam.position.set(CSG.screenWidth /2, CSG.height/2, 0);
		
		buttonBack = new Button(Strings.BUTTON_BACK, CSG.menuFontSmall, CSG.screenWidth / PADDING, BUTTON_HEIGHT, SMALL_BUTTON_WIDTH, SMALL_BUTTON_HEIGHT, new OnClick() {
			public void onClick() {
				changeMenu(new Menu(game));
				CSG.profilManager.persist();
			}
		});
		CSG.reset();
		CSG.batch.setShader(EndlessMode.original);
	}

	public void setRenderBackground(boolean renderBackground) {
		this.renderBackground = renderBackground;
	}
	
	protected void add(Button bouton) {
		buttons.add(bouton);
	}
	
	
	@Override
	public void render(float delta) {
		CSG.begin(delta);
		
		if (Gdx.input.isKeyPressed(Keys.BACK)) {
			keyBackPressed();
		}
		CSG.batch.begin();
		if (renderBackground) {
			Particles.background(CSG.batch);
		}
		Particles.draw(CSG.batch);
		for (BasicMenu e : enemies) {
			CSG.batch.draw(Animations.BASIC_ENEMY_RED.anim.getTexture(e), e.pos.x, e.pos.y, e.getDimensions().halfWidth, e.getDimensions().halfHeight, e.getDimensions().width, e.getDimensions().height, 1, 1, e.dir.angle() + 90);
			Particles.smoke(e.pos.x + BasicMenu.DIMENSIONS.halfWidth - e.dir.x/offset, e.pos.y + BasicMenu.DIMENSIONS.halfHeight - e.dir.y/offset, true, ParticuleBundles.SMOKE.colors);
			Physic.mvt(e.dir, e.pos, e.getDimensions().height);
			if (e.pos.x + BasicMenu.DIMENSIONS.halfWidth < CSG.widthDiv10 * 2) {
				e.desired.x = speed;
				e.desired.y = e.dir.y;
				e.steerScl = ((CSG.widthDiv10 * 2) - (e.pos.x + BasicMenu.DIMENSIONS.halfWidth)) / (CSG.widthDiv10/3);
			} else if (e.pos.x + BasicMenu.DIMENSIONS.halfWidth > CSG.screenWidth - CSG.widthDiv10 * 2) {
				e.desired.x = -speed;
				e.desired.y = e.dir.y;
				e.steerScl = Math.abs(((e.pos.x + BasicMenu.DIMENSIONS.halfWidth) - (CSG.screenWidth - CSG.widthDiv10 * 2))) / (CSG.widthDiv10/3);
			} else if (e.pos.y + BasicMenu.DIMENSIONS.halfHeight < CSG.heightDiv10 * 2.5f) {
				e.desired.x = e.dir.x;
				e.desired.y = speed;
				e.steerScl = ((CSG.heightDiv10*2.5f) - (e.pos.y + BasicMenu.DIMENSIONS.halfHeight)) / (CSG.heightDiv10/6);
			} else if (e.pos.y + BasicMenu.DIMENSIONS.halfHeight > CSG.height - CSG.heightDiv10*2.5f) {
				e.desired.x = e.dir.x;
				e.desired.y = -speed;
				e.steerScl = Math.abs(((e.pos.y + BasicMenu.DIMENSIONS.halfHeight) - (CSG.height - CSG.heightDiv10*2.5f))) / (CSG.heightDiv10/6);
			}
			steer(e);
		}
		for (int i = 0; i < buttons.size; i++) {
			if (buttons.get(i) != null) buttons.get(i).draw(CSG.batch);
		}
		if (credits != null)
			credits.render(CSG.batch, delta);
		CSG.end();
		trigger += delta;
		if (trigger > 4) {
			BasicMenu e = BasicMenu.POOL.obtain();
			e.dir.x = 0;
			e.dir.y = -speed;
			e.dir.rotate(CSG.R.nextInt(360));
			enemies.add(e);
			trigger = -enemies.size;
		}
	}

	private void steer(BasicMenu e) {
		e.steer.x = e.desired.x - e.dir.x;
		e.steer.y = e.desired.y - e.dir.y;
		e.steer.nor();
		e.steer.scl(e.steerScl*2);
		e.dir.x += e.steer.x;
		e.dir.y += e.steer.y;
		e.dir.nor();
		e.dir.scl(speed);
	}

	public void changeMenu (Screen s) {
		BasicMenu.POOL.freeAll(enemies);
		enemies.clear();
		game.setScreen(s);
	}
	
	protected int detectKonamiCode(int etapeCode) {
		switch (etapeCode) {
		case 0:
		case 1:
			if (Gdx.input.justTouched() && Gdx.input.getY() < CSG.halfHeight)
				etapeCode++;
			break;
		case 2:
		case 3:
			if (Gdx.input.justTouched() && Gdx.input.getY() > CSG.halfHeight)
				etapeCode++;
			break;
		case 4:
		case 6:
			if (Gdx.input.justTouched() && Gdx.input.getX() < CSG.halfWidth)
				etapeCode++;
			break;
		case 5:
		case 7:
			if (Gdx.input.justTouched() && Gdx.input.getX() > CSG.halfWidth)
				etapeCode++;
			break;
		}
		return etapeCode;
	}
	


	public void setBackButtonActive(boolean isBackButtonActive) {		Gdx.input.setCatchBackKey(true);	}
	public void keyBackPressed() {	}
	public void setGame(Game game) {		this.game = game;	}
	@Override	public void resize(int width, int height) {		cam.position.set(width /2, height/2, 0);	}
	@Override	public void show() {    }
	@Override	public void hide() {	}
	@Override	public void pause() {	}
	@Override	public void resume() {		CSG.assetMan.reload();	}
	@Override	public void dispose() {	}

}
