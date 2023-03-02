package test;

import com.tdproject.main.Square;
import org.junit.Test;

import javax.vecmath.Vector2d;

import static org.junit.Assert.assertEquals;

public class SquareTest {

    @Test
    public void positionToSquare() {
        Square expectedSquare1 = new Square(0, 12);
        Square expectedSquare2 = new Square(0, 13);
        Square square1 = Square.positionToSquare(new Vector2d(100, 891));
        Square square2 = Square.positionToSquare(new Vector2d(100, 892));
        Square square3 = Square.positionToSquare(new Vector2d(100, 893));

        assertEquals(expectedSquare1, square1);
        assertEquals(expectedSquare2, square2);
        assertEquals(expectedSquare2, square3);
    }
}
