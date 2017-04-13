package com.aston.group.stationdefender.actors;

import com.aston.group.stationdefender.config.Constants;

import java.util.Objects;

/**
 * Superclass for different Alien types.
 *
 * @author IngramJ
 * @version 01/11/2016
 */
public class Alien extends Unit {
    private boolean overloaded = false;

    /**
     * Construct a new Alien with default X and Y co-ordinates of '0'
     */
    public Alien() {
        this("Alien", -120, Constants.DEFAULT_DAMAGE, 5, Constants.UNIT_HEALTH, 5.0, 12, 100, 38, 7);
    }

    /**
     * Construct a new Alien with given name, speed, damage, rateOfFile, health, range, x co-ordinate, y co-ordinate,
     * width and height
     *
     * @param name        The name of the Alien
     * @param speed       The speed of the Alien
     * @param damage      The damage the Alien inflicts
     * @param rateOfFire  The rate of fire of the Alien
     * @param health      The health of the Alien
     * @param range       The range of the Alien
     * @param chanceToHit The chance of the Weapon to score a hit
     * @param width       The width of the Alien
     * @param height      The height of the Alien
     * @param texture     The texture graphic of the Alien
     */
    public Alien(String name, double speed, double damage, double rateOfFire, double health, double range, double chanceToHit, int width, int height, int texture) {
        super(name, speed, damage, rateOfFire, health, range, chanceToHit, width, height, texture, true);
    }

    @Override
    public void render(float delta) {
        batch.begin();
        renderParticleEffect(delta, batch);
        if (!isAdjacent())
            batch.setColor(1f, 1f, 1f, 1f);
        else
            batch.setColor(.5f, .5f, .5f, 1f);
        batch.draw(texture, x, y, width, height);
        batch.end();
        checkInput();
        act(delta);
        indicatorManager.render(delta, x, y);
    }

    @Override
    public void act(float delta) {
        if (!checkZeroHealth()) {
            switch (name) {
                case "Kamikaze Alien":
                    if (isAdjacent && !Objects.equals(getAdjacentActor().getName(), "Mine")) {
                        adjacentActor.takeDamage(fire());
                        destroy();
                    } else {
                        move(delta);
                    }
                    break;
                case "Mine":
                    if (!isAdjacent) {
                        unitFireHelper(-10, -30);
                    }
                    break;
                case "Rapid Fire Alien":
                    if (!overloaded) {
                        if (isAdjacent && !Objects.equals(getAdjacentActor().getName(), "Mine")) {
                            overloaded = rapidFireHelper();
                        } else {
                            move(delta);
                        }
                    } else
                        overloaded = false;
                    break;
                default:
                    if (isAdjacent) {
                        adjacentActor.takeDamage(fire());
                    } else {
                        move(delta);
                    }
                    break;
            }
        } else {
            destroy();
        }
    }

    /**
     * Moves the Alien from the left side of the lane to the right
     *
     * @param delta The time in seconds since the last move
     */
    private void move(float delta) {
        if (!isAdjacent()) {
            x += (speed * delta);
        } else {
            if (getAdjacentActor() != null && !((Unit) getAdjacentActor()).isFacingLeft()) {
                getAdjacentActor().takeDamage(getDamage());
            }
        }
    }

    /**
     * Returns if the Alien is overloaded.
     *
     * @return Overloaded state of the Alien.
     */
    public boolean getOverloaded() {
        return overloaded;
    }

    /**
     * Sets if the Alien is overloaded or not.
     *
     * @param overloaded state of the Alien.
     */
    public void setOverloaded(boolean overloaded) {
        this.overloaded = overloaded;
    }
}