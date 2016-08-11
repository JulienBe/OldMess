package menu.screens;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import jeu.CSG;
import jeu.Strings;
import menu.tuto.OnClick;
import menu.ui.Button;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class Menu extends AbstractScreen {

    private static final int WIDTH = BUTTON_WIDTH, HEIGHT = BUTTON_HEIGHT, SRC_X = CSG.screenWidth / PADDING;
    private static final float ECART = 0.9f, TIME_MIN_BEFORE_EXIT = 2;
    private static final float PLAY_Y = 1.9f,
            SHIP_Y = PLAY_Y + ECART,
            OPTION_Y = SHIP_Y + ECART,
            HIGHSCORES_Y = OPTION_Y + ECART,
            ACHIEVEMENTS_Y = HIGHSCORES_Y + ECART,
            SUPPORT_Y = ACHIEVEMENTS_Y + ECART,
            BUILD_SHIPS_Y = SUPPORT_Y + ECART,
            EXIT_Y = SUPPORT_Y + ECART * 3f;
    private float time = 0;
    private Button highscores, achievements;
    private BitmapFont font = CSG.menuFont;

    public Menu(Game game) {
        super(game);
        setUpScreenElements();
    }

    public void setUpScreenElements() {
        time = 0;
        Gdx.input.setCatchBackKey(false);

        add(new Button(Strings.BUTTON_PLAY,         font, WIDTH, HEIGHT, SRC_X, getSrcY(PLAY_Y), new OnClick() { public void onClick() {    changeMenu(new ChoixDifficulte(game)); }}));
        add(new Button(Strings.BUTTON_SHIP,         font, WIDTH, HEIGHT, SRC_X, getSrcY(SHIP_Y), new OnClick() {
            public void onClick() {                                     changeMenu(new MenuXP(game)); }
        }));
        add(new Button(Strings.BUTTON_OPTION,       font, WIDTH, HEIGHT, SRC_X, getSrcY(OPTION_Y), new OnClick() {
            public void onClick() {                                   changeMenu(new MenuOptions(game)); }
        }));
        add(new Button(Strings.BUTTON_SUPPORT_US,   font, WIDTH, HEIGHT, SRC_X, getSrcY(SUPPORT_Y), new OnClick() { public void onClick() {         CSG.talkToTheWorld.buyUsABeer(); } }));
        add(new Button(Strings.BUTTON_CREATE_ENEMIES, font, WIDTH, HEIGHT, SRC_X, getSrcY(BUILD_SHIPS_Y), new OnClick() { public void onClick() {   CSG.talkToTheWorld.otherGames(); } }));
        add(new Button(Strings.BUTTON_EXIT,           font, WIDTH, HEIGHT, SRC_X, getSrcY(EXIT_Y), new OnClick() { public void onClick() { Gdx.app.exit(); } }));
        highscores = new Button(Strings.BUTTON_HIGHSCORE, false, font, WIDTH, HEIGHT, SRC_X, getSrcY(HIGHSCORES_Y));
        achievements = new Button(Strings.BUTTON_ACHIEVEMENT, false, font, WIDTH, HEIGHT, SRC_X, getSrcY(ACHIEVEMENTS_Y));
        add(highscores);
        add(achievements);

        if (Gdx.app.getVersion() != 0)
            CSG.talkToTheWorld.showAds(true);
    }

    private int getSrcY(float line) {
        return (int) (CSG.height - (CSG.heightDiv10 * line));
    }

    @Override
    public void keyBackPressed() {
        super.keyBackPressed();
        Gdx.app.exit();
    }

    @Override
    public void render(float delta) {
        time += delta;
        cam.update();
        CSG.batch.setProjectionMatrix(cam.combined);
        if (Gdx.input.isTouched() && CSG.height - Gdx.input.getY() > highscores.sprite.getY() && CSG.height - Gdx.input.getY() < highscores.sprite.getY() + highscores.sprite.getHeight())
            CSG.talkToTheWorld.getScores();

        if (Gdx.input.isTouched() && CSG.height - Gdx.input.getY() > achievements.sprite.getY() && CSG.height - Gdx.input.getY() < achievements.sprite.getY() + achievements.sprite.getHeight())
            if (CSG.talkToTheWorld.getSignedIn()) CSG.talkToTheWorld.getAchievements();
            else CSG.talkToTheWorld.login();

        super.render(delta);
        if (Gdx.input.isKeyPressed(Keys.BACK) && time > TIME_MIN_BEFORE_EXIT)
            Gdx.app.exit();
    }

}
