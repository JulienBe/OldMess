package elements.procedural;

import assets.AssetMan;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import elements.particular.particles.ParticleEmitter;
import elements.particular.particles.individual.thruster.EnemyThruster;
import jeu.colors.MyColors;

/**
 * Created by julein on 15/08/16.
 */
public class Ship {

  protected TextureRegion textureRegion;
  protected Array<ParticleEmitter> particleEmitters = new Array<ParticleEmitter>();
  private Array<EnemyThruster> particles = new Array<EnemyThruster>();
  private float rotation = 270;
  private float x;
  private float y;
  protected float width;
  protected float height;
  private float halfWidth;
  private float halfHeight;
  private float speed = 50;
  private MyColors colors;

  public void init(float x, float y, float width, float height, MyColors myColors) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.colors = myColors;
    halfWidth = width / 2;
    halfHeight = height / 2;
  }

  public void draw(SpriteBatch batch) {
    batch.draw(AssetMan.debris, x, y, halfWidth, halfHeight, width, height, 1, 1, rotation);
    batch.draw(textureRegion, x, y, halfWidth, halfHeight, width, height, 1, 1, rotation);
    EnemyThruster.act(particles, batch);
    for (ParticleEmitter p : particleEmitters)
      addParticle(p);
  }

  private void addParticle(ParticleEmitter emitter) {
    EnemyThruster p = EnemyThruster.get();
    Vector2 pos = emitter.randomPos().scl(speed);
    Vector2 dir = emitter.dir().scl(speed);
    particles.add(p.init(x + pos.x, y + pos.y, dir.x, dir.y, colors.bunch));
  }

  protected void texture(TextureRegion textureRegion) {
    this.textureRegion = textureRegion;
  }

  public float rotation() {
    return rotation;
  }

  public void rotate(float f) {
    rotation += f;
    rotation %= 360;
  }

}
