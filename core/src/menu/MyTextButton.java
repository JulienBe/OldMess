package menu;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import jeu.CSG;
import menu.tuto.OnClick;
import menu.ui.UiParticle;

/**
 * Created by julein on 13/08/16.
 */
public class MyTextButton extends MyButton {
    private static final int PADDING = 8;
    private final Array<LabelAction> labelActions = new Array<LabelAction>();
    private LabelAction currentLabelAction;
    private int currentIndex = 0;

    public MyTextButton(int x, int y, LabelAction... labelActions) {
        super(x, y, getWidth(labelActions[0].label()), getHeight(labelActions[0].label()), labelActions[0].onClick());
        this.labelActions.addAll(labelActions);
        updateLabelAction(currentIndex);
    }

    private void updateLabelAction(int index) {
        currentLabelAction = labelActions.get(index % labelActions.size);
        currentLabelAction.label().setBounds(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        currentLabelAction.label().setAlignment(Align.center);
    }

    private static float getHeight(Label label) {
        return CSG.fontsDimensions.getHeight(label.getStyle().font, label.getText()) + UiParticle.HEIGHT * PADDING;
    }

    private static float getWidth(Label label) {
        return CSG.fontsDimensions.getWidth(label.getStyle().font, label.getText()) + UiParticle.HEIGHT * PADDING;
    }


    @Override
    protected void touched() {
        super.touched();
        updateLabelAction(currentIndex++);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        currentLabelAction.label().draw(batch, parentAlpha);
    }
}
