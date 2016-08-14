package menu;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.utils.Array;
import menu.tuto.OnClick;
import menu.ui.UiParticle;

/**
 * Created by julein on 12/08/16.
 */
public class MyButton extends Actor {

    protected final Array<UiParticle> particles = new Array<UiParticle>();

    public MyButton(float x, float y, float width, float height, final OnClick onClick) {
        x = alignWithParticle(x);
        y = alignWithParticle(y);
        width = alignWithParticle(width);
        height = alignWithParticle(height);
        setBounds(x, y, width, height);
        horizontalBarre(x, y + height, width, 1);
        verticalBarre(x + width, y + height, height, -1);
        horizontalBarre(x + width, y, width, -1);
        verticalBarre(x, y, height, 1);
        setOnClick(onClick);
    }

    protected void setOnClick(final OnClick onClick) {
        clearListeners();
        addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                onClick.onClick();
                touched();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }

    protected void touched() {
    }

    private float alignWithParticle(float f) {
        return f - (f % UiParticle.HEIGHT);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        for (UiParticle b : particles)
            b.draw(batch, particles.size);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }


    protected void verticalBarre(float x, float y, float heightToCover, int dir) {
        final int nbrBarre = (int) ((heightToCover / UiParticle.HEIGHT));
        for (int i = 0; i < nbrBarre; i++)
            particles.add(UiParticle.POOL.obtain().init(x, y + UiParticle.HEIGHT * i * dir));
    }

    protected void horizontalBarre(float x, float y, float widthToCover, int dir) {
        final int nbrBarre = (int) ((widthToCover / UiParticle.HEIGHT));
        for (int i = 0; i <= nbrBarre; i++)
            particles.add(UiParticle.POOL.obtain().init(x + UiParticle.HEIGHT * i * dir, y));
    }

}
