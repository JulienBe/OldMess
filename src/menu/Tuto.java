package menu;

import jeu.Stats;
import vaisseaux.bonus.BonusBombe;
import vaisseaux.bonus.BonusStop;
import vaisseaux.bonus.BonusTemps;
import vaisseaux.bonus.XP;
import vaisseaux.ennemis.particuliers.nv1.DeBase;
import assets.AssetMan;
import assets.animation.AnimationEnnemiDeBase;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Tuto implements Screen {
	
	private final Game game;
	private final GL20 gl = Gdx.graphics.getGL20();
	private final float ligne = (CSG.DIXIEME_HAUTEUR / 3) * 2;
	private final float colonne = CSG.DIXIEME_LARGEUR / 2;
	private final String stay = "STAY A WHILE AND LISTEN";
	private final String enemy = "That's an enemy";
	private final String xp = "You can collect points to upgrade your ship";
	private final String bomb = "Use this bomb... to kill them all";
	private final String slow = "That's a nice bonus, it will freeze the time";
	private final String stop = "That's even better... Try it !";
	private final BitmapFont font;

	public Tuto(Game game) {
		this.game = game;
		font = new BitmapFont(Gdx.files.internal("default.fnt"), false);
		font.setScale(Stats.U / 20);
		font.setColor(.82f, .82f, 0.1f, 1);
	}

	@Override
	public void render(float delta) {
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		CSG.batch.begin();
		CSG.renderBackground(CSG.batch);
		// SHIP
		ligne(AnimationEnnemiDeBase.getTexture(0), 1, DeBase.LARGEUR, DeBase.HAUTEUR, enemy);
		// XP
		ligne(AssetMan.XP, 3, XP.LARGEUR, XP.LARGEUR, xp);
		// BOMB
		ligne(AssetMan.bombe, 5, BonusBombe.LARGEUR, BonusBombe.LARGEUR, bomb);

		ligne(AssetMan.temps, 7, BonusTemps.LARGEUR, BonusTemps.LARGEUR, stop);

		ligne(AssetMan.bonusetoile, 9, BonusStop.LARGEUR, BonusStop.LARGEUR, slow);
		                           
		// TITRE
		font.draw(CSG.batch, stay, CSG.DEMI_LARGEUR_ECRAN - (font.getBounds(stay).width/2) , CSG.HAUTEUR_ECRAN - 5);
		CSG.batch.end();
		
		if (Gdx.input.isKeyPressed(Keys.BACK) || Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			Menu menu = new Menu(game);
			game.setScreen(menu);
		}
	}

	private void ligne(TextureRegion r, int l, int largeur, int hauteur, String text) {
		CSG.batch.draw(r, colonne, ligne * l, largeur, hauteur);
		font.draw(CSG.batch, text, CSG.LARGEUR_ECRAN - (colonne + font.getBounds(text).width) , (ligne*l) + (hauteur/2));
	}

	@Override
	public void resize(int width, int height) {	}
	@Override
	public void show() {	}
	@Override
	public void hide() {	}
	@Override
	public void pause() {	}
	@Override
	public void resume() {	}
	@Override
	public void dispose() {	}

}