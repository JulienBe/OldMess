package elements.generic.enemies.individual.lvl4;

import com.badlogic.gdx.utils.Pool;

import elements.generic.enemies.individual.lvl3.Group3;

public class Group4 extends Group3 {
	
	public static final Pool<Group4> POOL = new Pool<Group4>() {		protected Group4 newObject() {			return new Group4();		}	};
	
	@Override	public void free() {								POOL.free(this);			}
	@Override	public int getNumberOfShots() {						return 5;					}
	@Override
	public int getXp() {
		return super.getXp() * 3;
	}
	
}
