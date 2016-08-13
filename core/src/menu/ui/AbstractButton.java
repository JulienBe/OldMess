package menu.ui;

import com.badlogic.gdx.utils.Array;

public abstract class AbstractButton {
	
	protected float y, x;
	protected final Array<UiParticle> barres = new Array<UiParticle>();
	
	protected void verticalBarre(float x, float y, float heightToCover) {
		final int nbrBarre = (int) (((heightToCover * 1.1f) / UiParticle.HEIGHT));
		final float distanceCouverte = nbrBarre * UiParticle.HEIGHT;
		final float ecartTotal = heightToCover - distanceCouverte;
		final float ecart = ecartTotal / (nbrBarre-1);
		float tmpX = 0;
		for (int i = 0; i < nbrBarre; i++) {
			barres.add(UiParticle.POOL.obtain().init(x, y + tmpX));
			tmpX += (UiParticle.HEIGHT + ecart);
		}
	}
	
	protected void horizontalBarre(float x, float y, float widthToCover) {
		final int nbrBarre = (int) (((widthToCover * 1.15f) / UiParticle.HEIGHT));
		final float distanceCouverte = nbrBarre * UiParticle.HEIGHT;
		final float ecartTotal = widthToCover - distanceCouverte;
		final float ecart = ecartTotal / (nbrBarre-1);
		float tmpX = -UiParticle.HALF_HEIGHT;
		for (int i = 0; i <= nbrBarre; i++) {
			barres.add(UiParticle.POOL.obtain().init(x + tmpX, y));
			tmpX += (UiParticle.HEIGHT + ecart);
		}
	}

}
