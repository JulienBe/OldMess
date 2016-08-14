package elements.procedural;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import jeu.CSG;

/**
 * Created by julein on 12/08/16.
 */
public class Ship {
    private final Grid grid;
    private ShipRenderer renderer = new ShipRenderer();

    public Ship(Grid grid) {
        this.grid = grid;
    }

    public void render(SpriteBatch batch, int i) {
        renderer.render(grid, batch, i);
    }

    public int width() {
        return grid.width();
    }

    public int height() {
        return grid.height();
    }

    public Texture getTexture() {
        FrameBuffer frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, height(), width(), false);
        frameBuffer.begin();
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        CSG.batch.begin();

        OrthographicCamera cam = new OrthographicCamera(frameBuffer.getWidth(), frameBuffer.getHeight());
        cam.position.set(cam.viewportWidth / 2, cam.viewportHeight / 2, 0);
        cam.update();
        CSG.batch.setProjectionMatrix(cam.combined);

        render(CSG.batch, 1);
        CSG.batch.end();
        frameBuffer.end();

        Texture texture = frameBuffer.getColorBufferTexture();
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        return texture;
    }
}
