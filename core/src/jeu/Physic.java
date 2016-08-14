package jeu;

import jeu.mode.EndlessMode;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import elements.generic.enemies.Enemy;
import elements.generic.weapons.Weapon;
import elements.generic.weapons.player.PlayerWeapon;
import elements.particular.players.Player;

public class Physic {
	
	public static boolean pointIn(final Sprite s) {
		final int x = Gdx.input.getX();
		final int y = CSG.height - Gdx.input.getY();
        return s.getX() <= x && s.getX() + s.getWidth() >= x && s.getY() <= y && s.getY() + s.getHeight() >= y;
	}
	
	public static boolean isOnScreen(final Vector2 position, final float hauteur, final float largeur) {
		if (position.y + hauteur < -CSG.heightDiv10 || position.x + largeur < 0 ||
				position.x > CSG.screenWidth  || position.y > CSG.heightPlus4 + hauteur)
			return false;
		return true;
	}
	
	public static boolean isOnScreenWithTolerance(final Vector2 position, final float hauteur, final float largeur) {
		if (position.y + hauteur < -CSG.heightDiv10 || position.x + largeur < -Stats.WIDTH_DIV_10 || position.x > Stats.WIDTH_PLUS_MARGIN  || position.y > CSG.heightPlus4 + hauteur)
			return false;
		return true;
	}
	
	public static boolean isOnScreen(final float x, final float y, final float width) {
		if (y + width < 0 || x + width < 0 || x > CSG.screenWidth || y > CSG.height + width)
			return false;
		return true;
	}
	
	/**
	 * @return way
	 */
	public static boolean goToZigZagCentre(final Vector2 pos, final Vector2 dir, final int halfWidth, boolean way, final float amplitude, final float height, final int width){
		if (pos.x + halfWidth < CSG.halfWidth)		way = false;
		else										way = true;
		
		if (way)									dir.x -= amplitude * EndlessMode.delta;
		else										dir.x += amplitude * EndlessMode.delta;
		
		mvtNoCheck(pos, dir);
		return way;
	}

	public static void collisionsTest(Player player) {
		for (final Enemy enemy : Enemy.LIST) {
			if (enemy.pos.y + CSG.heightDiv100 > CSG.height)	continue;
			if (enemy.isOnPlayer())								player.touchedEnnemy(enemy);
			collisionPlayerWeaponToEnemy(enemy, Weapon.PLAYER_LIST);
			collisionPlayerWeaponToEnemy(enemy, Weapon.ADDS);
		}
	}

	private static void collisionPlayerWeaponToEnemy(final Enemy enemy, Array<PlayerWeapon> playerList) {
		for (final PlayerWeapon a : playerList) 
			if (!enemy.dead && enemy.isTouched(a) && enemy.stillAlive(a)) {
				a.free();
				playerList.removeValue(a, true);
			}
	}

	public static boolean isPointInRect(final float x, final float y, final float rectX, final float rectY, final float rectWidth, final float rectHeight) {
		 return rectX <= x && rectX + rectWidth >= x && rectY <= y && rectY + rectHeight >= y;
	}
	public static boolean isPointInRect(final float x, final float y, final Vector2 pos, final float rectWidth, final float rectHeight) {
		return pos.x <= x && pos.x + rectWidth >= x && pos.y <= y && pos.y + rectHeight >= y;
	}
	
	public static boolean isShipInRect(final float rectX, final float rectY, final float rectLarg, final float rectHaut) {
		return rectX <= Player.xCenter && rectX + rectLarg >= Player.xCenter && rectY <= Player.yCenter && rectY + rectHaut >= Player.yCenter;
	}
	
	public static boolean isPointInSquare(final float x, final float y, final float rectX, final float rectY, final float rectWidth) {
		 return rectX <= x && rectX + rectWidth >= x && rectY <= y && rectY + rectWidth >= y;
	}

	public static boolean isPlayerInRect(final Vector2 pos, final int rectWidth, final int rectHeight, Player player) {
		if (pos.x <= Player.xCenter && pos.x + rectWidth >= Player.xCenter && pos.y <= Player.yCenter && pos.y + rectHeight >= Player.yCenter) {
			player.touched();
			return true;
		}
		return false;
	}
	
	private static final Vector2 DESIRED = new Vector2(), STEER = new Vector2();

	public static float setDirToPlayer(final Vector2 dir, final Vector2 pos, final float maxSpeed, final float width, final float halfWidth, float rotation) {
		mvtToPlayer(dir, pos, maxSpeed, width, halfWidth, rotation);
		return dir.angle();
	}
	
	public static float setDirTo(final Vector2 dir, final Vector2 pos, final float maxSpeed, final float width, final float halfWidth, float rotation, Vector2 target) {
		mvtToTarget(dir, pos, maxSpeed, width, halfWidth, rotation, target);
		return dir.angle();
	}
	
	public static void mvtToPlayer(final Vector2 dir, final Vector2 pos, final float maxSpeed, final float width, final float halfWidth, float rotation) {
		DESIRED.set(pos.x - Player.xCenter, pos.y - Player.yCenter).nor();
		dir.nor();
		STEER.set(dir.x - DESIRED.x, dir.y - DESIRED.y).nor().scl(rotation);
		dir.add(STEER).scl(maxSpeed);
	}
	
	public static void mvtToTarget(final Vector2 dir, final Vector2 pos, final float maxSpeed, final float width, final float halfWidth, float rotation, Vector2 target) {
		DESIRED.set(pos.x - target.x, pos.y - target.y).nor();
		dir.nor();
		STEER.set(dir.x - DESIRED.x, dir.y - DESIRED.y).nor().scl(rotation);
		dir.add(STEER).scl(maxSpeed);
	}
	
	public static void dirToPlayer(final Vector2 dir, final Vector2 pos, final float width, final float halfWidth) {
		DESIRED.set((pos.x + halfWidth) - Player.xCenter,  (pos.y + halfWidth) - Player.yCenter).nor();
		dir.set(-DESIRED.x, -DESIRED.y);
	}
	
	private static boolean collision = false;
	
//	public static boolean isAddTouched(final Vector2 pos, final float height, final float height) {
//		collision = false;
//		if (Player.leftDrone && Physic.isPointInRect(Player.centerLeft1AddX, Player.centerAdd1Y, pos, height, height)) {
//			Player.removeLeftAdd1();
//			collision = true;
//		}
//		if (Player.rightDrone && Physic.isPointInRect(Player.centerRight1AddX, Player.centerAdd1Y, pos, height, height))	{
//			Player.enleverAddDroite1();
//			collision = true;
//		}
//		if (Player.leftDrone2 && Physic.isPointInRect(Player.centerLeft2AddX, Player.centerAdd2Y, pos, height, height)) {
//			Player.removeLeftAdd2();
//			collision = true;
//		}
//		if (Player.rightDrone2 && Physic.isPointInRect(Player.centerRight2AddX, Player.centerAdd2Y, pos, height, height)) {
//			Player.enleverAddDroite2();
//			collision = true;
//		}
//		return collision;
//	}
	
	public static void mvtNoCheck(final Vector2 pos, final Vector2 dir) {
		pos.add(dir.x * EndlessMode.delta, dir.y * EndlessMode.delta);
	}
	
	public static boolean mvt(final Vector2 dir, final Vector2 pos, final float width) {
		pos.add(dir.x * EndlessMode.delta, dir.y * EndlessMode.delta);
		return isOnScreen(pos, width, width);
	}
	
	public static boolean mvt(final float height, final float width, final Vector2 dir, final Vector2 pos) {
		pos.add(dir.x * EndlessMode.delta, dir.y * EndlessMode.delta);
		return isOnScreen(pos, width, height);
	}
	
	public static float mvtOmbrelle(final Enemy e, final Vector2 dir) {
		dir.x = -((CSG.halfWidth - Player.xCenter) - (CSG.halfWidth - e.pos.x - e.getDimensions().halfWidth));
		dir.y /= 1 + EndlessMode.delta;
		Physic.mvtNoCheck(e.pos, dir);
		return getAngleWithPlayer(e.pos, e.getDimensions().halfWidth, e.getDimensions().halfHeight);
	}
	
	
	public static float getAngleWithPlayer(final Vector2 pos, final float halfWidth, final float halfHeight) {
		CSG.tmpPos.set(Player.xCenter, Player.yCenter);
		return CSG.tmpPos.sub(pos.x + halfWidth, pos.y + halfHeight).angle();
	}

	public static float distanceFromPlayer(final Vector2 pos) {
		return CSG.tmpPos.set(pos.x - Player.xCenter, pos.y - Player.yCenter).len();
	}

	public static void stayOnScreen(Vector2 pos, float width) {
		if (pos.x < 0)							pos.x += EndlessMode.delta15;
		else if (pos.x + width > CSG.screenWidth)		pos.x -= EndlessMode.delta15;
	}

	public static boolean isNotDisplayed(Enemy enemy) {
		return enemy.pos.x + enemy.getDimensions().width < 0 || enemy.pos.x > CSG.screenWidth || enemy.pos.y > CSG.height || enemy.pos.y + enemy.getDimensions().height < 0;
	}

	public static boolean isLeft(Vector2 pos, float halfWidth) {
		return (pos.x + halfWidth < CSG.halfWidth); 
	}
}