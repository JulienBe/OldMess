package menu.screens;

import jeu.CSG;
import jeu.Strings;
import menu.tuto.OnClick;
import menu.ui.Button;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class Menu extends AbstractScreen {

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

    public Menu(Game game) {
        super(game);
        setUpScreenElements();
    }

    public void setUpScreenElements() {
        time = 0;
        Gdx.input.setCatchBackKey(false);

        add(new Button(Strings.BUTTON_PLAY, CSG.menuFont, BUTTON_WIDTH, BUTTON_HEIGHT, CSG.screenWidth / PADDING, (int) (CSG.height - (CSG.heightDiv10 * PLAY_Y)), new OnClick() {
            public void onClick() {
                changeMenu(new ChoixDifficulte(game));
            }
        }));
        add(new Button(Strings.BUTTON_SHIP, CSG.menuFont, BUTTON_WIDTH, BUTTON_HEIGHT, CSG.screenWidth / PADDING, (int) (CSG.height - (CSG.heightDiv10 * SHIP_Y)), new OnClick() {
            public void onClick() {
                changeMenu(new MenuXP(game));
            }
        }));
        add(new Button(Strings.BUTTON_OPTION, CSG.menuFont, BUTTON_WIDTH, BUTTON_HEIGHT, CSG.screenWidth / PADDING, (int) (CSG.height - (CSG.heightDiv10 * OPTION_Y)), new OnClick() {
            public void onClick() {
                changeMenu(new MenuOptions(game));
            }
        }));

        highscores = new Button(Strings.BUTTON_HIGHSCORE, false, CSG.menuFont, BUTTON_WIDTH, BUTTON_HEIGHT, CSG.screenWidth / PADDING, (int) (CSG.height - (CSG.heightDiv10 * HIGHSCORES_Y)));
        add(highscores);

        achievements = new Button(Strings.BUTTON_ACHIEVEMENT, false, CSG.menuFont, BUTTON_WIDTH, BUTTON_HEIGHT, CSG.screenWidth / PADDING, (int) (CSG.height - (CSG.heightDiv10 * ACHIEVEMENTS_Y)));
        add(achievements);

        add(new Button(Strings.BUTTON_SUPPORT_US, CSG.menuFont, BUTTON_WIDTH, BUTTON_HEIGHT, CSG.screenWidth / PADDING, (int) (CSG.height - (CSG.heightDiv10 * SUPPORT_Y)), new OnClick() {
            public void onClick() {
                CSG.talkToTheWorld.buyUsABeer();
            }
        }));

        add(new Button("Other games", CSG.menuFont, BUTTON_WIDTH, BUTTON_HEIGHT, CSG.screenWidth / PADDING, (int) (CSG.height - (CSG.heightDiv10 * BUILD_SHIPS_Y)), new OnClick() {
            public void onClick() {
                CSG.talkToTheWorld.otherGames();
            }
        }));

        add(new Button(Strings.BUTTON_EXIT, CSG.menuFont, BUTTON_WIDTH, BUTTON_HEIGHT, CSG.screenWidth / PADDING, (int) (CSG.height - (CSG.heightDiv10 * EXIT_Y)), new OnClick() {
            public void onClick() {
                Gdx.app.exit();
            }
        }));

        if (Gdx.app.getVersion() != 0)
            CSG.talkToTheWorld.showAds(true);
    }

    @Override
    public void keyBackPressed() {
        super.keyBackPressed();
        Gdx.app.exit();
    }

    @Override
    public void render(float delta) {
        cam.update();
        CSG.batch.setProjectionMatrix(cam.combined);
        if (Gdx.input.isTouched() && CSG.height - Gdx.input.getY() > highscores.sprite.getY() && CSG.height - Gdx.input.getY() < highscores.sprite.getY() + highscores.sprite.getHeight())
            CSG.talkToTheWorld.getScores();

        if (Gdx.input.isTouched() && CSG.height - Gdx.input.getY() > achievements.sprite.getY() && CSG.height - Gdx.input.getY() < achievements.sprite.getY() + achievements.sprite.getHeight()) {
            if (CSG.talkToTheWorld.getSignedIn()) CSG.talkToTheWorld.getAchievements();
            else CSG.talkToTheWorld.login();
        }
        time += delta;
        super.render(delta);
        if (Gdx.input.isKeyPressed(Keys.BACK) && time > TIME_MIN_BEFORE_EXIT)
            Gdx.app.exit();
    }

}
