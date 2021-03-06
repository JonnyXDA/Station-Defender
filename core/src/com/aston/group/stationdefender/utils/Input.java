package com.aston.group.stationdefender.utils;

import com.badlogic.gdx.math.Vector2;

/**
 * A utility to handle positions of the mouse input
 *
 * @author Mohammad Foysal
 */
public enum Input {
    ;
    private static final Vector2 position = new Vector2();

    /**
     * Returns the Vector2 position of the mouse
     *
     * @param x The X co-ordinate of the mouse
     * @param y The Y co-ordinate of the mouse
     */
    public static void setPosition(float x, float y) {
        position.set(x, y);
    }

    /**
     * Returns the specific X co-ordinate of the Vector2 mouse position
     *
     * @return The X co-ordinate of the Vector2 mouse position
     */
    public static int getX() {
        return (int) position.x;
    }

    /**
     * Returns the specific Y co-ordinate of the Vector2 mouse position
     *
     * @return The Y co-ordinate of the Vector2 mouse position
     */
    public static int getY() {
        return (int) position.y;
    }

    /**
     * Check if an objects X &amp; Y co-ordinates or width &amp; height
     * overlaps the Towers X &amp; Y co-ordinates, or width &amp; height
     *
     * @param x      The X co-ordinate of the object to check
     * @param y      The Y co-ordinate of the object to check
     * @param width  The width of the object to check
     * @param height The height of the object to check
     * @return true if the values overlap, false if the values do not overlap
     */
    public static boolean isColliding(int x, int y, int width, int height) {
        return x + width > position.x && x < position.x && y + height > position.y && y < position.y;
    }
}