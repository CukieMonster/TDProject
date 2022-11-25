package enemies;

import main.Game;
import towers.Tower;

import javax.vecmath.Vector2d;

public class HomingMissile {

    public Vector2d position;
    public Enemy target;
    private Tower origin;
    public int damage;
    public float speed;

    public HomingMissile(Vector2d pos, Enemy target, Tower t, float s, int d) {
        position = (Vector2d) pos.clone();
        this.target = target;
        origin = t;
        speed = s;
        damage = d;
    }

    public void update() {
        move();
    }

    private void move() {
        if (target == null) {
            //destroy missile
            origin.missiles.remove(this);
        }
        Vector2d direction = new Vector2d();// = Vector2d. target.position - position);
        direction.sub(target.position, position);
        //target reached (incl error)
        //System.err.println(direction.length());
        if (direction.length() < 5 * Game.gameSpeed) {
            origin.missiles.remove(this);
            //TODO: deal damage
            target.damage(damage);
        }
        direction.normalize();
        direction.scale(speed * Game.gameSpeed);
        position.add(direction);
    }
}
