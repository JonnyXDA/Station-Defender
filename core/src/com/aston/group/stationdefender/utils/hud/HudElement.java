package com.aston.group.stationdefender.utils.hud;

public abstract class HudElement {

    protected int x, y, width, height;
    public HudCallback hudCallback;

    public abstract void render(float delta);
    public abstract boolean isColliding();
    public abstract void dispose();

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public HudCallback getHudCallback() {
        return hudCallback;
    }

    public void setHudCallback(HudCallback hudCallback) {
        this.hudCallback = hudCallback;
    }
}
