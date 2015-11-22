package jeu;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.JsonValue;

import elements.generic.weapons.player.BlueSweepWeapon;
import elements.generic.weapons.player.BlueSweepWeaponManager;
import elements.generic.weapons.player.Fireball;
import elements.generic.weapons.player.FireballManager;
import elements.generic.weapons.player.PinkWeapon;
import elements.generic.weapons.player.PinkWeaponManager;
import elements.generic.weapons.player.SpaceInvaderManager;
import elements.generic.weapons.player.SpaceInvaderWeapon;
import elements.generic.weapons.player.SunManager;
import elements.generic.weapons.player.SunWeapon;
import elements.generic.weapons.player.TWeapon;
import elements.generic.weapons.player.TWeaponManager;
import elements.generic.weapons.player.WeaponManager;

public class Profil implements Serializable{
	
	// -- KEYS
	private static final String STR_FIRERATE_ADD = "vitesse", STR_ARME_SELECT = "kjhuk", STR_ARME_DE_BASE_NV = "adbnv",
		STR_ARME_BALAYAGE_NV = "abnv", STR_ARME_TROIS_NV = "tricheur", STR_ARME_HANTEE_NV = "trichur",
		STR_XP = "XP", STR_VOLUME_ARME = "sjciuendk", STR_VOLUME_MUSIQUE = "sjciuend",
		STR_VOLUME_BRUITAGES = "sjciuen", STR_TYPE_CONTROLE = "sfdsfiuen", STR_BLOOM = "bloom",
		STR_MANUAL_BONUS = "particules", STR_INTENSITE_BLOOM = "intensitebloom", BFG = "bfg", STR_ARME_SUN = "pointculture", STR_SPACE_INVADER_WEAPON = "spaceInvader", STR_SCREENSHAKE = "screens";
	private static final float STEP_VOL = .1f;
	public int dronesFirerate = 1, controls = CSG.CONTROLE_TOUCH_RELATIVE, lvlFireball = 1, lvlSweepWeapon = 1, lvlPinkWeapon = 1, lvlTWeapon = 1, lvlSunWeapon = 1, lvlSpaceInvader = 1, xp;
	public float weaponVolume = 0.5f, musicVolume = 0.5f, effectsVolume = 0.5f, bloomIntensity = 2.1f, sensitivity = 1.5f;
	public String selectedWeapon;
	public boolean manualBonus, bfg, screenshake;
	// -- -- string d'affichage
	public String xpDisplay = " XP : " + xp;
	private static boolean firstTime = false;
	public static final int LVL_MAX = 8;
	public static final int LVL_UNLOCK = 6;
	
	/**
	 * Valeurs par defaut si pas de profil
	 */
	public Profil() {
		lvlSweepWeapon = 1;
		lvlFireball = 1;
		lvlPinkWeapon = 1;
		lvlTWeapon = 1;
		lvlSunWeapon = 1;
		dronesFirerate = 1;
		lvlSpaceInvader = 1;
		xp = (int) (200 * CSG.mulSCORE);
		effectsVolume = 1;
		weaponVolume = effectsVolume / 3;
		musicVolume = 1;
		selectedWeapon = Fireball.LABEL;
		controls = CSG.CONTROLE_TOUCH_RELATIVE;
		bloomIntensity = 2.4f;
		firstTime = true;
		manualBonus = false;
		sensitivity = 1.5f;
		bfg = false;
		screenshake = true;
	}

	/**
	 * Sauvegarde
	 */
	@Override
	public void write(Json json) {
		json.writeValue(STR_SPACE_INVADER_WEAPON, lvlSpaceInvader);
		json.writeValue(STR_ARME_SUN, lvlSunWeapon);
		json.writeValue(STR_ARME_BALAYAGE_NV, lvlSweepWeapon);
		json.writeValue(STR_ARME_DE_BASE_NV, lvlFireball);
		json.writeValue(STR_ARME_TROIS_NV, lvlPinkWeapon);
		json.writeValue(STR_ARME_HANTEE_NV, lvlTWeapon);
		json.writeValue(STR_FIRERATE_ADD, dronesFirerate);
		json.writeValue(STR_XP, xp);
		json.writeValue(STR_ARME_SELECT, selectedWeapon);
		json.writeValue(STR_VOLUME_ARME, sensitivity);
		
		json.writeValue(STR_VOLUME_BRUITAGES, effectsVolume);
		json.writeValue(STR_VOLUME_MUSIQUE, musicVolume);
		json.writeValue(STR_TYPE_CONTROLE, controls);
		json.writeValue(STR_MANUAL_BONUS, manualBonus);
		json.writeValue(STR_SCREENSHAKE, screenshake);
		json.writeValue(STR_INTENSITE_BLOOM, bloomIntensity);
		json.writeValue(BFG, bfg);
	}

	@Override
	public void read(Json json, JsonValue jsonData) {
		firstTime = false;
		xp = json.readValue(STR_XP, Integer.class, jsonData);
		dronesFirerate = json.readValue(STR_FIRERATE_ADD, Integer.class, jsonData);
		lvlPinkWeapon = json.readValue(STR_ARME_TROIS_NV, Integer.class, jsonData);
		lvlTWeapon = json.readValue(STR_ARME_HANTEE_NV, Integer.class, jsonData);
		lvlFireball = json.readValue(STR_ARME_DE_BASE_NV, Integer.class, jsonData);
		lvlSweepWeapon = json.readValue(STR_ARME_BALAYAGE_NV, Integer.class, jsonData);
		selectedWeapon = json.readValue(STR_ARME_SELECT, String.class, jsonData);
		sensitivity = json.readValue(STR_VOLUME_ARME, Float.class, jsonData);
		effectsVolume = json.readValue(STR_VOLUME_BRUITAGES, Float.class, jsonData);
		musicVolume = json.readValue(STR_VOLUME_MUSIQUE, Float.class, jsonData);
		weaponVolume = effectsVolume / 3;
		xpDisplay = "XP : " + xp;
		controls = json.readValue(STR_TYPE_CONTROLE, Integer.class, jsonData).shortValue();
		manualBonus = json.readValue(STR_MANUAL_BONUS, Boolean.class, jsonData);
		bloomIntensity = json.readValue(STR_INTENSITE_BLOOM, Float.class, jsonData);
		if (sensitivity < 1f)
			sensitivity = 1f;
		if (json.readValue(BFG, Boolean.class, jsonData) != null)
			bfg = json.readValue(BFG, Boolean.class, jsonData);
		else
			bfg = false;
		if (json.readValue(STR_ARME_SUN, Integer.class, jsonData) != null) 	lvlSunWeapon = json.readValue(STR_ARME_SUN, Integer.class, jsonData).shortValue();
		else 																lvlSunWeapon = 1;
		if (json.readValue(STR_SPACE_INVADER_WEAPON, Integer.class, jsonData) != null) 	lvlSpaceInvader = json.readValue(STR_SPACE_INVADER_WEAPON, Integer.class, jsonData).shortValue();
		else 																			lvlSpaceInvader = 1;
		controls = CSG.CONTROLE_TOUCH_RELATIVE;
		if (json.readValue(STR_SCREENSHAKE, Boolean.class, jsonData) != null)
			screenshake = json.readValue(STR_SCREENSHAKE, Boolean.class, jsonData);
		else
			screenshake = true;
		checkAchievementWeapons();
	}

	/**
	 * decremente l'xp disponible. Attention on doit verifier avant si on peut faire le up
	 */
	public void upCadenceAdd() {
		xp -= getCoutCadenceAdd();
		dronesFirerate++;
		xpDisplay = "XP : " + xp;
	}
	
	public int getCoutCadenceAdd() {
		return (int) (((dronesFirerate+dronesFirerate) * dronesFirerate * dronesFirerate * 120) * CSG.mulSCORE);
	}
	
	/**
	 * Renvoie l'arme actuellement selectionnee.
	 */
	public WeaponManager getArmeSelectionnee() {
		return convertArme(selectedWeapon);
	}

	public void upArme() {
		lvlSweepWeapon = tryUpdateWeapon(BlueSweepWeapon.LABEL, lvlSweepWeapon);
		lvlFireball = tryUpdateWeapon(Fireball.LABEL, lvlFireball);
		lvlTWeapon = tryUpdateWeapon(TWeapon.LABEL, lvlTWeapon);
		lvlPinkWeapon = tryUpdateWeapon(PinkWeapon.LABEL, lvlPinkWeapon);
		lvlSunWeapon = tryUpdateWeapon(SunWeapon.LABEL, lvlSunWeapon);
		lvlSpaceInvader = tryUpdateWeapon(SpaceInvaderWeapon.LABEL, lvlSpaceInvader);
	}

	protected int tryUpdateWeapon(String label, int lvl) {
		if (selectedWeapon.equals(label) && lvl < LVL_MAX) {
			xp -= getCoutUpArme();
			xpDisplay = "XP : " + xp;
			return ++lvl;
		}
		return lvl;
	}

	private void checkAchievementWeapons() {
		if (isOneWeaponsLvlOk(LVL_MAX))
			CSG.talkToTheWorld.unlockAchievementGPGS(Strings.ACH_LVL8);
		else if (isOneWeaponsLvlOk(LVL_UNLOCK)) {
			CSG.talkToTheWorld.unlockAchievementGPGS(Strings.ACH_LVL6);
			if (isAllWeaponsLvlOk(LVL_UNLOCK))
				CSG.talkToTheWorld.unlockAchievementGPGS(Strings.ACH_UNLOCK_SUN);
		}
	}

	public boolean isAllWeaponsLvlOk(int min) {
		return lvlSweepWeapon >= min && lvlFireball >= min && lvlTWeapon >= min && lvlSunWeapon >= min && lvlPinkWeapon >= min && lvlSpaceInvader >= min;
	}
	
	public boolean isOneWeaponsLvlOk(int min) {
		return lvlSweepWeapon >= min || lvlFireball >= min || lvlTWeapon >= min || lvlSunWeapon >= min || lvlPinkWeapon >= min || lvlSpaceInvader >= min;
	}
	
	public int getCoutUpArme() {
		int nv = 1;
		if (BlueSweepWeapon.LABEL.equals(selectedWeapon))				nv = lvlSweepWeapon;
		else if (Fireball.LABEL.equals(selectedWeapon))				nv = lvlFireball;
		else if (TWeapon.LABEL.equals(selectedWeapon))				nv = lvlTWeapon;
		else if (PinkWeapon.LABEL.equals(selectedWeapon))				nv = lvlPinkWeapon;
		else if (SunWeapon.LABEL.equals(selectedWeapon))				nv = lvlSunWeapon;
		else if (SpaceInvaderWeapon.LABEL.equals(selectedWeapon))		nv = lvlSpaceInvader;
		return (int) (((nv+nv) * nv * nv * 120) * CSG.mulSCORE);
	}

	public void addXp(int i) {
		xp += i;
		xpDisplay = "XP : " + xp;
	}

	public void setArmeSelectionnee(String s) {
		selectedWeapon = s;
	}
	
	/**
	 * Convertit la string en arme correspondante
	 * @param arme
	 * @return <code>TypesArmes</code>
	 */
	private static WeaponManager convertArme(String arme) {
		if (BlueSweepWeapon.LABEL.equals(arme))			return new BlueSweepWeaponManager();
		if (Fireball.LABEL.equals(arme))	   			return new FireballManager();
		if (PinkWeapon.LABEL.equals(arme))	   		 	return new PinkWeaponManager();
		if (SunWeapon.LABEL.equals(arme))				return new SunManager();
		if (SpaceInvaderWeapon.LABEL.equals(arme))		return new SpaceInvaderManager();
		return new TWeaponManager();
	}

	/**
	 * Diminue et persiste
	 */
	public void diminuerVolumeBruitage() {
		if (effectsVolume > 0) {
			effectsVolume -= STEP_VOL;
			weaponVolume = effectsVolume / 3;
		}
		CSG.profilManager.persist();
	}
	
	/**
	 * Augmente et persiste
	 */
	public void augmenterVolumeBruitage() {
		if (effectsVolume < 1) {
			effectsVolume += STEP_VOL;
			weaponVolume = effectsVolume / 3;
		}
		CSG.profilManager.persist();
	}

	/**
	 * Augmente et persiste
	 */
	public void augmenterVolumeMusique() {
		if (musicVolume < 1)
			musicVolume += STEP_VOL;
		CSG.profilManager.persist();
	}
	
	/**
	 * Augmente et persiste
	 */
	public void diminuerVolumeMusique() {
		if (musicVolume > 0)
			musicVolume -= STEP_VOL;
		CSG.profilManager.persist();
	}

	public void upSensitivity() {
		sensitivity += .1f;
	}
	
	public void downSensitivity() {
		sensitivity -= .1f;
		if (sensitivity < 1f)
			sensitivity = 1f;
	}
	public void upBloom() {
		bloomIntensity += .2f;
	}
	
	public void downBloom() {
		bloomIntensity -= .2f;
		if (bloomIntensity < .4f)
			bloomIntensity = .4f;
	}

	public String getSensitivityString() {
		return Strings.DF.format(sensitivity);
	}
	
	public String getBloomString() {
		return Strings.DF.format(bloomIntensity);
	}

	public boolean isFirstTime() {
		return firstTime;
	}
}
