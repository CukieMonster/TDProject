package test;

import com.tdproject.graphics.ButtonPanel;
import com.tdproject.ui.Button;
import java.util.List;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class ButtonPanelTest {

    private final Button[] buttons = new Button[] {
            new Button(true, "play", null)
    };
//
//    @Test
//    public void calculatePositions() {
//        var buttonPanel = new ButtonPanel(500, 500, 500, 500, buttons);
//
//        var result = buttonPanel.calculateButtonPositions(1, 1, 100, 100);
//
//        assertEquals(1, result.length);
//        assertEquals(500, result[0][0]);
//        assertEquals(500, result[0][1]);
//    }
//
//    @Test
//    public void calculatePositions2() {
//        var buttonPanel = new ButtonPanel(500, 500, 500, 500, buttons);
//
//        var result = buttonPanel.calculateButtonPositions(2, 1, 100, 100);
//
//        assertEquals(2, result.length);
//        // first button
//        assertEquals(500, result[0][0]);
//        assertEquals(400, result[0][1]);
//        // second button
//        assertEquals(500, result[1][0]);
//        assertEquals(600, result[1][1]);
//    }
//
//    @Test
//    public void calculatePositions3() {
//        var buttonPanel = new ButtonPanel(500, 500, 500, 500, buttons);
//
//        var result = buttonPanel.calculateButtonPositions(3, 2, 100, 100);
//
//        assertEquals(3, result.length);
//        // first button
//        assertEquals(400, result[0][0]);
//        assertEquals(400, result[0][1]);
//        // second button
//        assertEquals(600, result[1][0]);
//        assertEquals(400, result[1][1]);
//        // third button
//        assertEquals(400, result[2][0]);
//        assertEquals(600, result[2][1]);
//    }
//
//    @Test
//    public void calculatePositions4() {
//        var buttonPanel = new ButtonPanel(500, 500, 500, 500, buttons);
//
//        var result = buttonPanel.calculateButtonPositions(4, 2, 100, 100);
//
//        assertEquals(4, result.length);
//        // first button
//        assertEquals(400, result[0][0]);
//        assertEquals(400, result[0][1]);
//        // second button
//        assertEquals(600, result[1][0]);
//        assertEquals(400, result[1][1]);
//        // third button
//        assertEquals(400, result[2][0]);
//        assertEquals(600, result[2][1]);
//        // fourth button
//        assertEquals(600, result[3][0]);
//        assertEquals(600, result[3][1]);
//    }
//
//    @Test
//    public void calculatePositions3x2() {
//        var buttonPanel = new ButtonPanel(500, 500, 500, 500, buttons);
//
//        var result = buttonPanel.calculateButtonPositions(6, 2, 100, 100);
//
//        assertEquals(6, result.length);
//        // first button
//        assertEquals(400, result[0][0]);
//        assertEquals(350, result[0][1]);
//        // second button
//        assertEquals(600, result[1][0]);
//        assertEquals(350, result[1][1]);
//        // third button
//        assertEquals(400, result[2][0]);
//        assertEquals(500, result[2][1]);
//        // fourth button
//        assertEquals(600, result[3][0]);
//        assertEquals(500, result[3][1]);
//        // fifth button
//        assertEquals(400, result[4][0]);
//        assertEquals(650, result[4][1]);
//        // sixth button
//        assertEquals(600, result[5][0]);
//        assertEquals(650, result[5][1]);
//    }
//
//    @Test
//    public void calculatePositions2x3() {
//        var buttonPanel = new ButtonPanel(500, 500, 500, 500, buttons);
//
//        var result = buttonPanel.calculateButtonPositions(6, 3, 100, 100);
//
//        assertEquals(6, result.length);
//        // first button
//        assertEquals(350, result[0][0]);
//        assertEquals(400, result[0][1]);
//        // second button
//        assertEquals(500, result[1][0]);
//        assertEquals(400, result[1][1]);
//        // third button
//        assertEquals(650, result[2][0]);
//        assertEquals(400, result[2][1]);
//        // fourth button
//        assertEquals(350, result[3][0]);
//        assertEquals(600, result[3][1]);
//        // fifth button
//        assertEquals(500, result[4][0]);
//        assertEquals(600, result[4][1]);
//        // sixth button
//        assertEquals(650, result[5][0]);
//        assertEquals(600, result[5][1]);
//    }

}
