package menu.screens;

import assets.AssetMan;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import elements.procedural.*;
import jeu.CSG;
import jeu.Stats;
import jeu.Strings;
import menu.BaseStage;
import menu.LabelAction;
import menu.MyTextButton;
import menu.tuto.OnClick;

/**
 * Created by julein on 11/08/16.
 */
public class MenuEnemyCreation extends BaseStage {

    private enum Mode {
        NORMAL, THRUSTER
    }
    private static final int Y_CREATE = (int) (CSG.height * 1.55f);
    private static final float SCALE = Stats.PIXEL, HALF_SCALE = SCALE / 2;
    private static int shipNumber = 0;
    private EditableShip editableShip;
    private boolean shipCreated = false;
    private Mode mode = Mode.NORMAL;
    private final Game game;
    private Label createEnemyLabel = new Label(Strings.BUTTON_CREATE_ENEMIES, Stats.MENU_STYLE),
        changeColorLabel = new Label(Strings.BUTTON_CHANGE_COLOR, Stats.MENU_STYLE),
        rorateLabel = new Label(Strings.BUTTON_ROTATE, Stats.MENU_STYLE),
        setThrusterLabel = new Label(Strings.BUTTON_SET_THRUSTER, Stats.MENU_STYLE);
    private MyTextButton createEnemy, changeColor, rotate, thruster;
    private int xStartThruster = 0, yStartThruster = 0, xEndThruster = 0, yEndThruster = 0;

    public MenuEnemyCreation(Game game) {
        this.game = game;
        setUpScreenElements();
    }

    private void setUpScreenElements() {
        createEnemy = new MyTextButton(0, Y_CREATE, new LabelAction(createEnemyLabel, new OnClick() { public void onClick() { createNewEnemy(++shipNumber); }
                }));
        changeColor = new MyTextButton((int)createEnemy.getRight() , Y_CREATE, new LabelAction(changeColorLabel, new OnClick() { public void onClick() { changeColor(); } }));
        rotate = new MyTextButton((int)(changeColor.getRight()), Y_CREATE, new LabelAction(rorateLabel, new OnClick() { public void onClick() { rotate(); } }));
        thruster = new MyTextButton((int)(rotate.getRight()), Y_CREATE, new LabelAction(setThrusterLabel, new OnClick() { public void onClick() { toggleThrusterMode(); } }));
        stage.addActor(createEnemy);
        stage.addActor(rotate);
        stage.addActor(changeColor);
        stage.addActor(thruster);
        stage.addListener(new InputListener() {
            public boolean keyDown(InputEvent event, int keycode) {
                switch (keycode) {
                    case Input.Keys.LEFT: xEndThruster--; break;
                    case Input.Keys.RIGHT: xEndThruster++; break;
                    case Input.Keys.UP: yEndThruster++; break;
                    case Input.Keys.DOWN: yEndThruster--; break;
                    case Input.Keys.Q: xStartThruster--; break;
                    case Input.Keys.D: xStartThruster++; break;
                    case Input.Keys.Z: yStartThruster++; break;
                    case Input.Keys.S: yStartThruster--; break;
                    case Input.Keys.SPACE: createThruster(); break;
                    case Input.Keys.R: editableShip.resetThruster(); break;
                }
                return super.keyDown(event, keycode);
            }
        });
    }

    private void createThruster() {
        editableShip.setThruster(xStartThruster, yStartThruster, xEndThruster, yEndThruster);
    }

    private void toggleThrusterMode() {
        if (mode == Mode.THRUSTER)
            mode = Mode.NORMAL;
        else
            mode = Mode.THRUSTER;
        modeChange();
    }

    private void modeChange() {
        switch (mode) {
            case NORMAL:
                createEnemy.setVisible(true);
                changeColor.setVisible(true);
                rotate.setVisible(true);
                thruster.setVisible(true);
                break;
            case THRUSTER:
                createEnemy.setVisible(false);
                break;
        }
    }

    private void rotate() {
        if (editableShip != null)
            editableShip.rotate(-90);
    }

    private void changeColor() {
        createNewEnemy(shipNumber);
    }

    private void createNewEnemy(int seed) {
        ShipFactory factory = new ShipFactory(Parameters.MINE, Steps.MEDIUM, new Rng(seed));
        editableShip = new EditableShip(factory.create());
        shipCreated = true;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        super.render(delta);
        if (shipCreated) {
            editableShip.buildTexture();
            shipCreated = false;
        }
        if (editableShip != null)
            editableShip.draw(CSG.batch, delta, SCALE);
        if (mode == MenuEnemyCreation.Mode.THRUSTER) {
            CSG.batch.begin();
            CSG.batch.setColor(Color.RED);
            CSG.batch.draw(AssetMan.debris, editableShip.x(HALF_SCALE) + xStartThruster * SCALE, editableShip.y(HALF_SCALE) + yStartThruster * SCALE, SCALE, SCALE);
            CSG.batch.setColor(Color.CYAN);
            CSG.batch.draw(AssetMan.debris, editableShip.x(HALF_SCALE) + xEndThruster * SCALE, editableShip.y(HALF_SCALE) + yEndThruster * SCALE, SCALE, SCALE);
            CSG.batch.setColor(Color.WHITE);
            CSG.batch.end();
        }
    }

}