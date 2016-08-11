package menu.ui;

import jeu.CSG;
import jeu.Physic;
import jeu.Stats;
import jeu.mode.EndlessMode;
import menu.tuto.OnClick;
import assets.AssetMan;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Button extends AbstractButton {

	private static final float PADDING_BARRE = Stats.U;
	private BitmapFont font;
	private String texte;
	private boolean clicked = false;
	public Sprite sprite;
	public OnClick click;
	private float clickTime = 0, height, width;
	private final float originalWidth, originalHeight;
	
	public Button(String s, BitmapFont font, int x, int y, int width, int height, OnClick onClick) {
		this(s, font, x, y, width, height);
		click = onClick;
	}
	
	public Button(String s, BitmapFont font, float x, float y, float width, float height) {
		sprite = new Sprite();
		sprite.setBounds(x, y, width, height);
		this.originalHeight = height;
		this.originalWidth = width;
		this.font = font;
		texte = s;
		updateBorders(font, x, y, originalWidth, originalHeight);
	}

	private void updateBorders(BitmapFont font, float x, float y, float originalWidth, float originalHeight) {
		height = CSG.fontsDimensions.getHeight(font, texte);
		width = CSG.fontsDimensions.getWidth(font, texte);
		y = ((y + (originalHeight/2)) - (height/2)) - PADDING_BARRE;
		x = ((x + (originalWidth/2)) - (width/2)) - PADDING_BARRE;
		width += PADDING_BARRE * 2;
		height += PADDING_BARRE * 2;
		setBarres(x, y);
	}

	private void setBarres(float x, float y) {
		barres.clear();
		// top 
		horizontalBarre(x, y + height - Barre.HEIGHT, width + Barre.HEIGHT);
		// bottom
		horizontalBarre(x, y, width + Barre.HEIGHT);
		// left
		verticalBarre(x - Barre.HALF_HEIGHT, y, height);
		verticalBarre(x + width + Barre.HEIGHT, y, height);
		Barre.nbr = 0;
		this.x = x - Barre.HALF_HEIGHT;
		this.y = y;
	}

	public void setClick(OnClick click) {
		this.click = click;
	}

	public void draw(SpriteBatch batch) {
		drawBackground(batch);

		font.draw(batch, texte, getX(),	getY());
		
		for (Barre b : barres)
			b.draw(batch);
		
		if (Gdx.input.justTouched() && Physic.pointIn(sprite)) {
			impulse(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
			if (click != null)
				click.onClick();
		}
		act();
	}

	private void impulse(int x, int y) {
		tmpTouched.x = x;
		tmpTouched.y = y;
		for (Barre b : barres)
			b.impulse(tmpTouched);
	}

	private static final Vector2 tmpTouched = new Vector2();

	private float getY() {
		return sprite.getY() + CSG.fontsDimensions.getHeight(font, texte) + sprite.getHeight()/2 - CSG.fontsDimensions.getHeight(font, texte) / 2;
	}

	private float getX() {
		return (sprite.getX() + (sprite.getWidth()/2)) - CSG.fontsDimensions.getWidth(font, texte) / 2;
	}

	private void drawBackground(SpriteBatch batch) {
		batch.setColor(CSG.gm.palette().black);
		batch.draw(AssetMan.debris, x, y, width + PADDING_BARRE, height);
		batch.setColor(CSG.gm.palette().white);
	}

	public void act() {
		if (clicked)
			clickTime += EndlessMode.delta;
		if (clickTime > .2f)
			click.onClick();
	}
	
	public void setTexte(String texte) {
		// center
		this.texte = texte;
		width = CSG.fontsDimensions.getWidth(font, texte);
		width += PADDING_BARRE * 2;
		setBarres(getX() - PADDING_BARRE, y);
	}

	public static void testClick(Button b, float xOffset) {
		if (b != null && Physic.isPointInRect(Gdx.input.getX() + xOffset, CSG.height - Gdx.input.getY(), 0, b.sprite.getY() - Stats.U, CSG.halfWidth, b.sprite.getHeight() + Stats.U2) && b.click != null) 
			b.click.onClick();
	}
}