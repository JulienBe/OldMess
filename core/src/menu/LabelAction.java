package menu;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import menu.tuto.OnClick;

/**
 * Created by julein on 14/08/16.
 */
public class LabelAction {
    private final Label label;
    private final OnClick onClick;

    public LabelAction(Label label, OnClick onClick) {
        this.label = label;
        this.onClick = onClick;
    }

    public Label label() {
        return label;
    }
    public OnClick onClick() {
        return onClick;
    }
}
