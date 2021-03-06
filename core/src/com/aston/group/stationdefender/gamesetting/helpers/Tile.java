package com.aston.group.stationdefender.gamesetting.helpers;

import com.aston.group.stationdefender.actors.Actor;
import com.aston.group.stationdefender.config.Constants;
import com.aston.group.stationdefender.engine.GameEngine;
import com.aston.group.stationdefender.utils.Input;
import com.aston.group.stationdefender.utils.TextureManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

/**
 * Tile class
 *
 * @author Jonathon Fitch
 * @author Twba Al-shaghdari
 */
public class Tile {
    private final int x, y;
    private final int width = Constants.TILE_WIDTH;
    private final int height = Constants.TILE_HEIGHT;
    private final SpriteBatch batch = GameEngine.getBatch();
    private final Texture texture = TextureManager.loadTexture(TextureManager.TILE);
    private final ShapeRenderer shapeRenderer = GameEngine.getShapeRenderer();
    private boolean hasItem;
    private boolean invalid;

    /**
     * Construct a new Tile with given X and Y co-ordinates
     *
     * @param x The X co-ordinate of the Tile
     * @param y The Y co-ordinate of the Tile
     */
    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Check if an objects X &amp; Y co-ordinates or width &amp; height
     * overlaps the Tiles X &amp; Y co-ordinates, or width &amp; height
     *
     * @param x      The X co-ordinate of the object to check
     * @param y      The Y co-ordinate of the object to check
     * @param width  The width of the object to check
     * @param height The height of the object to check
     * @return true if the values overlap, false if the values do not overlap
     */
    public boolean isColliding(int x, int y, int width, int height) {
        return x + width > this.x && x < this.x + this.width && y + height > this.y && y < this.y + this.height;
    }

    /**
     * Returns whether a Unit is colliding with a Tile
     *
     * @param actor The Unit to check the collision status
     * @return true if the Unit is colliding, false if the Unit is not colliding
     */
    public boolean isColliding(Actor actor) {
        return isColliding(actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight());
    }

    /**
     * Returns the X co-ordinate of the center of the Tile
     *
     * @return The X co-ordinate of the center of the Tile
     */
    public int getCenterX() {
        return x + (width / 2);
    }

    /**
     * Returns the Y co-ordinate of the center of the Tile
     *
     * @return The Y co-ordinate of the center of the Tile
     */
    public int getCenterY() {
        return y + (height / 2);
    }

    /**
     * Render the Tile.
     */
    public void render() {
        batch.begin();
        batch.draw(texture, x, y, width, height);
        batch.end();

        if (isColliding(Input.getX(), Input.getY(), 1, 1)) {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeType.Filled);
            if (invalid) {
                shapeRenderer.setColor(255, 0, 0, 0.35f);
            } else if (hasItem) {
                shapeRenderer.setColor(0, 255, 0, 0.35f);
            } else {
                shapeRenderer.setColor(222, 222, 222, 0.35f);
            }
            shapeRenderer.rect(x, y, width, height);
            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }
    }

    /**
     * Dispose of unused resources
     */
    public void dispose() {
        texture.dispose();
    }

    /**
     * Return whether the Tile has an item placed on it
     *
     * @return Whether the Tile has an item placed on it
     */
    public boolean isHasItem() {
        return hasItem;
    }

    /**
     * Sets whether a Tile has an item upon it
     *
     * @param hasItem The boolean of whether the Tile has an item upon it
     */
    public void setHasItem(boolean hasItem) {
        this.hasItem = hasItem;
    }

    /**
     * Returns whether a Tile can have a weapon placed upon it
     *
     * @return True if the Tile is invalid, false if it is not
     */
    public boolean isInvalid() {
        return invalid;
    }

    /**
     * Sets the Tile invalid status to determine whether items can be placed on it
     *
     * @param invalid The boolean of whether the Tile is invalid or not
     */
    public void setInvalid(boolean invalid) {
        this.invalid = invalid;
    }
}