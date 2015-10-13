package elements.generic.components.positionning;

import jeu.CSG;

import elements.generic.enemies.Enemy;

public class Middle implements Pos{

	@Override
	public void set(Enemy e) {
		e.pos.x = CSG.halfWidth - e.getDimensions().halfWidth;
		e.pos.y = CSG.height;
	}

}
