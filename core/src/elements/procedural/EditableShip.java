package elements.procedural;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Vector2;
import elements.particular.particles.ParticleEmitter;
import jeu.CSG;
import jeu.TimeKeeper;

/**
 * Created by julein on 12/08/16.
 */
public class EditableShip extends Ship {

    private static final float SCALE = 3;
    private final Grid grid;
    private ShipRenderer renderer = new ShipRenderer();

    public EditableShip(Grid grid) {
        this.grid = grid;
        grid.removeEmptyCells();
        buildTexture();
        init(200, 200, grid.width() * SCALE, grid.height() * SCALE, renderer.colors());
    }

    private int fbWidth() {
        return grid.width();
    }

    private int fbHeight() {
        return grid.height();
    }

    public void buildTexture() {
        FrameBuffer frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, fbWidth(), fbHeight(), false);
        frameBuffer.begin();
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        CSG.batch.begin();

        OrthographicCamera cam = new OrthographicCamera(frameBuffer.getWidth(), frameBuffer.getHeight());
        cam.position.set(cam.viewportWidth / 2, cam.viewportHeight / 2, 0);
        cam.update();
        CSG.batch.setProjectionMatrix(cam.combined);
        System.out.println(cam.viewportWidth);
        System.out.println(cam.viewportHeight);
        renderer.render(grid, CSG.batch, 1);
        CSG.batch.end();
        frameBuffer.end();

        Texture texture = frameBuffer.getColorBufferTexture();
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        texture(new TextureRegion(texture));
    }

    public void setThruster(int xStartThruster, int yStartThruster, int xEndThruster, int yEndThruster) {
        particleEmitters.add(ParticleEmitter.instance(new Vector2(xStartThruster, yStartThruster), new Vector2(xEndThruster, yEndThruster)));
    }
    public void resetThruster() {
        particleEmitters.clear();
    }

    public void draw(SpriteBatch batch, float delta) {
        TimeKeeper.delta = delta;
        if (textureRegion != null) {
            batch.begin();
            super.draw(batch);
            batch.end();
        }
    }

    public float x(float halfScale) {
        return CSG.halfWidth + (textureRegion.getRegionWidth() * halfScale);
    }

    public float y(float halfScale) {
        return CSG.halfHeight + (textureRegion.getRegionHeight() * halfScale);
    }

    public void dispose() {
    }

    public void addMvt(EnemyMvt mvt) {

    }
}
