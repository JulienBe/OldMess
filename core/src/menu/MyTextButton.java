package menu;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import jeu.CSG;
import menu.tuto.OnClick;
import menu.ui.UiParticle;

/**
 * Created by julein on 13/08/16.
 */
public class MyTextButton extends MyButton {
    private static final int PADDING = 8;
    private final Label label;
    public MyTextButton(int x, int y, Label label, OnClick onClick) {
        super(x, y,
                CSG.fontsDimensions.getWidth(label.getStyle().font, label.getText()) + UiParticle.HEIGHT * PADDING,
                CSG.fontsDimensions.getHeight(label.getStyle().font, label.getText()) + UiParticle.HEIGHT * PADDING,
                onClick);
        label.setBounds(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        label.setAlignment(Align.center);
        this.label = label;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        label.draw(batch, parentAlpha);
    }
}
