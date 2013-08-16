package vaisseaux.ennemis.particuliers.nv3;

import jeu.Stats;
import vaisseaux.ennemis.CoutsEnnemis;
import vaisseaux.ennemis.particuliers.nv1.QuiTir2;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;


public class EnnemiQuiTir2Nv3 extends QuiTir2 {
	
	public static Pool<EnnemiQuiTir2Nv3> pool = Pools.get(EnnemiQuiTir2Nv3.class);
	
	@Override
	protected void free() {
		pool.free(this);
	}
	
	@Override
	public void invoquer() {
		liste.add(pool.obtain());	
	}
	
	public static TextureRegion getTexture(int pv) {
		if (pv < Stats.DEMI_PV_BASE_QUI_TIR2)
			return QuiTir2.mauvaisEtat;
		return QuiTir2.bonEtat;
	}
	
	@Override
	protected int getPvMax() {
		return Stats.PVMAX_DE_BASE_QUI_TIR2;
	}
	
	@Override
	public int getXp() {
		return CoutsEnnemis.EnnemiQuiTir2Nv3.COUT;
	}
	
}
