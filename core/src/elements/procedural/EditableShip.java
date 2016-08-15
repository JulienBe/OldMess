package elements.procedural;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import elements.particular.particles.ParticleEmitter;
import elements.particular.particles.individual.thruster.EnemyThruster;
import jeu.CSG;
import jeu.TimeKeeper;

/**
 * Created by julein on 12/08/16.
 */
public class EditableShip {
    private Array<ParticleEmitter> particleEmitters = new Array<ParticleEmitter>();
    private final Grid grid;
    private ShipRenderer renderer = new ShipRenderer();
    private float rotation = 0;
    private TextureRegion textureRegion;
    private Array<EnemyThruster> particles = new Array<EnemyThruster>();

    public EditableShip(Grid grid) {
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

    public void buildTexture() {
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

        textureRegion = new TextureRegion(texture);
    }

    public float rotation() {
        return rotation;
    }

    public void rotate(int i) {
        rotation += i;
        rotation %= 360;
    }

    public void setThruster(int xStartThruster, int yStartThruster, int xEndThruster, int yEndThruster) {
        particleEmitters.add(ParticleEmitter.instance(new Vector2(xStartThruster, yStartThruster), new Vector2(xEndThruster, yEndThruster)));
    }
    public void resetThruster() {
        particleEmitters.clear();
    }

    public void draw(SpriteBatch batch, float delta, float scale) {
        TimeKeeper.delta = delta;
        if (textureRegion != null) {
            batch.begin();
            batch.draw(textureRegion,
                    x(scale / 2), y(scale / 2),
                    textureRegion.getRegionWidth() / 2, textureRegion.getRegionHeight() / 2,
                    textureRegion.getRegionWidth(), textureRegion.getRegionHeight(),
                    scale, scale, rotation());
            EnemyThruster.act(particles, batch);
            batch.end();
        }
        for (ParticleEmitter p : particleEmitters)
            addParticle(scale, p);
    }

    private void addParticle(float scale, ParticleEmitter emitter) {
        EnemyThruster p = EnemyThruster.get();
        Vector2 pos = emitter.randomPos().scl(scale);
        Vector2 dir = emitter.dir().scl(scale * 50);
        particles.add(p.init(x(scale / 2) + pos.x, y(scale / 2) + pos.y, dir.x, dir.y, renderer.thrusterColor()));
    }

    public float x(float halfScale) {
        return CSG.halfWidth + (textureRegion.getRegionWidth() * halfScale);
    }

    public float y(float halfScale) {
        return CSG.halfHeight + (textureRegion.getRegionHeight() * halfScale);
    }
}
