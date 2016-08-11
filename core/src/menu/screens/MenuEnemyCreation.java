package menu.screens;

import com.badlogic.gdx.Game;
import jeu.CSG;
import jeu.Strings;
import menu.tuto.OnClick;
import menu.ui.Button;

/**
 * Created by julein on 11/08/16.
 */
public class MenuEnemyCreation extends AbstractScreen {

    private static final int X_CREATE = (int) (CSG.height * 0.9f), Y_CREATE = (int) (CSG.screenWidth * 0.05f);

    public MenuEnemyCreation(Game game) {
        super(game);
        setUpScreenElements();
    }

    private void setUpScreenElements() {
        add(new Button(Strings.BUTTON_CREATE, CSG.menuFont, X_CREATE, Y_CREATE, BUTTON_WIDTH, BUTTON_HEIGHT, new OnClick() {
            public void onClick() {
                createNewEnemy();
            }
        }));
    }

    private void createNewEnemy() {

    }
}
