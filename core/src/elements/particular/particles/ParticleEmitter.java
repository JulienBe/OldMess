package elements.particular.particles;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import jeu.CSG;

/**
 * Created by julien on 14/08/16.
 */
public class ParticleEmitter {
    private static final Vector2 PLACEHOLDER = new Vector2(), PLACEHOLDER2 = new Vector2();
    private static final Pool<ParticleEmitter> POOl = new Pool<ParticleEmitter>() {
        protected ParticleEmitter newObject() {
            return new ParticleEmitter();
        }
    };
    private final Vector2 line = new Vector2(), offset = new Vector2();

    private ParticleEmitter() {}

    public static ParticleEmitter instance(Vector2 from, Vector2 to) {
        return POOl.obtain().set(from, to);
    }

    public ParticleEmitter set(Vector2 from, Vector2 to) {
        offset.set(from);
        line.set(to.sub(from));
        return this;
    }

    public Vector2 randomPos() {
        final float rnd = CSG.R.nextFloat();
        PLACEHOLDER.x = offset.x + (line.x * rnd);
        PLACEHOLDER.y = offset.y + (line.y * rnd);
        return PLACEHOLDER;
    }

    public Vector2 dir() {
        return PLACEHOLDER2.set(line.y, line.x).nor();
    }
}
