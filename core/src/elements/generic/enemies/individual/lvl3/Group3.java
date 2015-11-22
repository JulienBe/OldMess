package elements.generic.enemies.individual.lvl3;

import com.badlogic.gdx.utils.Pool;

import elements.generic.enemies.individual.lvl1.Group;

public class Group3 extends Group {
	
	public static final Pool<Group3> POOL = new Pool<Group3>() {
		protected Group3 newObject() {
			return new Group3();
		}
	};
	
	@Override	public void free() {									POOL.free(this);			}
	@Override	public int getNumberOfShots() {							return 4;					}
	@Override
	public int getXp() {
		return super.getXp() * 2;
	}
}
