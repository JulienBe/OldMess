package elements.generic.enemies.individual.lvl4;

import jeu.CSG;
import com.badlogic.gdx.utils.Pool;

import elements.generic.enemies.individual.lvl1.Group;
import elements.generic.enemies.individual.lvl3.Group3;

public class Group4 extends Group3 {
	
	public static final Pool<Group4> POOL = new Pool<Group4>() {		protected Group4 newObject() {			return new Group4();		}	};
	private static final float FIRERATE = Group.FIRERATE * 0.8f * MOD_FIRERATE;
	
	public static Group4 initAll() {
		Group4 e = POOL.obtain();
		Group4 f = POOL.obtain();
		Group4 g = POOL.obtain();
		e.init(CSG.width - DIMENSIONS.width);
		f.init(CSG.width - DIMENSIONS.width * 2.1f);
		g.init(CSG.width - DIMENSIONS.width * 3.2f);
		return e;
	}
	@Override	public float getFirerate() {						return FIRERATE;			}
	@Override	public void free() {								POOL.free(this);			}
	@Override	public int getNumberOfShots() {						return 5;					}
	
}
