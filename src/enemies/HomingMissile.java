package enemies;

import javax.vecmath.Vector2d;

public class HomingMissile {
    public Enemy target;
    public int damage;
    public float speed;

    public void update() {
        move();
    }

    private void move() {
        if (target == null) {
            //destroy
        }
        //Vector2d direction = target
    }
}
