package menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import jeu.CSG;

/**
 * Created by julein on 12/08/16.
 */
public class BaseStage implements Screen {

    private final InputMultiplexer inputMultiplexer = new InputMultiplexer();
    protected final Stage stage = new Stage(new ExtendViewport(CSG.height, CSG.screenWidth), CSG.batch);

    @Override
    public void show() {
        inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    protected void addInputProcessor(InputProcessor... inputProcessors) {
        for (InputProcessor inputProcessor : inputProcessors)
            inputMultiplexer.addProcessor(inputProcessor);
    }
    protected void removeInputProcessor(InputProcessor... inputProcessors) {
        for (InputProcessor inputProcessor : inputProcessors)
            inputMultiplexer.removeProcessor(inputProcessor);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
