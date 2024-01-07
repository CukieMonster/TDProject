package test;

import com.tdproject.graphics.ButtonPanel;
import com.tdproject.ui.Button;
import java.util.List;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class ButtonPanelTest {

    @Test
    public void calculatePositions() {
        Button[] buttons = {
                new Button(
                        true,
                        0,
                        0,
                        "play",
                        null
                )
        };
        var buttonPanel = new ButtonPanel(500, 500, 500, 500, buttons);
        var result = buttonPanel.calculateButtonPositions(1, 1, 100, 100);

        assertEquals(1, result.length);
        //assertEquals(450, result[0][0]);
        assertEquals(450, result[0][1]);
    }

}
