package com.tdproject.towers;

import com.tdproject.enemies.Enemy;
import com.tdproject.gamestates.Playing;
import com.tdproject.graphics.Sprite;

import javax.vecmath.Vector2d;

import static com.tdproject.main.FieldParameters.ENEMY_RADIUS;

public class HomingMissile extends Sprite {

    //private Vector2d position;
    private Enemy target;
    private Tower origin;
    private int damage;
    private float speed;

    public HomingMissile(Vector2d pos, Enemy target, Tower t, float s, int d) {
        position = (Vector2d) pos.clone();
        this.target = target;
        origin = t;
        speed = s;
        damage = d;
        loadSprite(Type.MISSILE, "");
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
        direction.sub(target.getPosition(), position);
        //target reached (incl error)
        //System.err.println(direction.length());
        if (direction.length() < ENEMY_RADIUS) { // * Game.getInstance().getGameSpeed()) {
            origin.missiles.remove(this);
            target.damage(damage);
        }
        direction.normalize();
        direction.scale(speed * Playing.getInstance().getGameSpeed());
        position.add(direction);
    }

}
