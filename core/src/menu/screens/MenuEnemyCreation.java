package menu.screens;

import assets.AssetMan;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
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
        NORMAL, THRUSTER, PARAMETERS
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
            rorateLabel = new Label(Strings.BUTTON_ROTATE, Stats.MENU_STYLE);
    private MyTextButton createEnemy, changeColor, rotate;
    private int xStartThruster = 0, yStartThruster = 0, xEndThruster = 0, yEndThruster = 0;
    private int minSteps = 10, maxSteps = 30, minSubSteps = 10, maxSubsteps = 40;
    private InputProcessor menuInputs = new InputAdapter() {
        public boolean keyDown(int keycode) {
            switch (keycode) {
                case Input.Keys.F1:                 modeChange(Mode.NORMAL);                break;
                case Input.Keys.F2:                 modeChange(Mode.THRUSTER);              break;
                case Input.Keys.F3:                 modeChange(Mode.PARAMETERS);            break;
            }
            return super.keyDown(keycode);
        }
    };
    private InputProcessor generalInputs = new InputAdapter() {
        public boolean keyDown(int keycode) {
            switch (keycode) {
                case Input.Keys.NUMPAD_5:           createNewEnemy(shipNumber);             break;
            }
            return super.keyDown(keycode);
        }
    };
    private InputProcessor paramProcessor = new InputAdapter() {
        @Override
        public boolean keyDown(int keycode) {
            switch (keycode) {
                case Input.Keys.NUMPAD_7:           maxSteps++;                             break;
                case Input.Keys.NUMPAD_4:           maxSteps--;                             break;
                case Input.Keys.NUMPAD_9:           maxSubsteps++;                          break;
                case Input.Keys.NUMPAD_6:           minSubSteps--;                          break;
            }
            return super.keyDown(keycode);
        }
    };
    private InputProcessor thrusterProcessor = new InputAdapter() {
        @Override
        public boolean keyDown(int keycode) {
            switch (keycode) {
                case Input.Keys.LEFT:               xEndThruster--;                         break;
                case Input.Keys.RIGHT:              xEndThruster++;                         break;
                case Input.Keys.UP:                 yEndThruster++;                         break;
                case Input.Keys.DOWN:               yEndThruster--;                         break;
                case Input.Keys.Q:                  xStartThruster--;                       break;
                case Input.Keys.D:                  xStartThruster++;                       break;
                case Input.Keys.Z:                  yStartThruster++;                       break;
                case Input.Keys.S:                  yStartThruster--;                       break;
                case Input.Keys.SPACE:              createThruster();                       break;
                case Input.Keys.R:                  editableShip.resetThruster();           break;
            }
            return super.keyDown(keycode);
        }
    };

    public MenuEnemyCreation(Game game) {
        this.game = game;
        setUpScreenElements();
    }

    private void setUpScreenElements() {
        createEnemy = new MyTextButton(0, Y_CREATE, new LabelAction(createEnemyLabel, new OnClick() {
            public void onClick() {                createNewEnemy(++shipNumber);            }
        }));
        changeColor = new MyTextButton((int) createEnemy.getRight(), Y_CREATE, new LabelAction(changeColorLabel, new OnClick() {
            public void onClick() {                changeColor();            }
        }));
        rotate = new MyTextButton((int) (changeColor.getRight()), Y_CREATE, new LabelAction(rorateLabel, new OnClick() {
            public void onClick() {                rotate();            }
        }));
        stage.addActor(createEnemy);
        stage.addActor(rotate);
        stage.addActor(changeColor);
        addInputProcessor(menuInputs, generalInputs);
    }

    private void createThruster() {
        editableShip.setThruster(xStartThruster, yStartThruster, xEndThruster, yEndThruster);
    }

    private void modeChange(Mode mode) {
        this.mode = mode;
        removeInputProcessor(thrusterProcessor, paramProcessor);
        switch (mode) {
            case NORMAL:
                createEnemy.setVisible(true);
                changeColor.setVisible(true);
                rotate.setVisible(true);
                break;
            case THRUSTER:
                addInputProcessor(thrusterProcessor);
                createEnemy.setVisible(false);
                break;
            case PARAMETERS:
                addInputProcessor(paramProcessor);
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
        ShipFactory factory = new ShipFactory(Parameters.MINE, new Steps(minSteps, maxSteps, minSubSteps, maxSubsteps), new Rng(seed));
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
        CSG.batch.begin();
        switch (mode) {
            case THRUSTER:
                CSG.batch.setColor(Color.RED);
                CSG.batch.draw(AssetMan.debris, editableShip.x(HALF_SCALE) + xStartThruster * SCALE, editableShip.y(HALF_SCALE) + yStartThruster * SCALE, SCALE, SCALE);
                CSG.batch.setColor(Color.CYAN);
                CSG.batch.draw(AssetMan.debris, editableShip.x(HALF_SCALE) + xEndThruster * SCALE, editableShip.y(HALF_SCALE) + yEndThruster * SCALE, SCALE, SCALE);
                CSG.batch.setColor(Color.WHITE);
                break;
            case PARAMETERS:
                CSG.menuFont.draw(CSG.batch, "min steps : " + minSteps, 0, 0);
                CSG.menuFont.draw(CSG.batch, "max steps : " + maxSteps, 0, 20);
                CSG.menuFont.draw(CSG.batch, "min substeps : " + minSubSteps, 0, 40);
                CSG.menuFont.draw(CSG.batch, "max substeps : " + maxSubsteps, 0, 60);
                break;
        }
        CSG.batch.end();
    }

}