package com.aston.group.stationdefender.callbacks;

import com.aston.group.stationdefender.gamesetting.items.Item;

/**
 * LevelCallback is the callback class for Level
 *
 * @author Mohammed Foysal
 */
public interface LevelCallback {

    /**
     * The win/lost situation callback
     *
     * @param won Whether the Player has won the game or not
     */
    void onWinLost(boolean won);

    /**
     * Callback to add money to the Player
     *
     * @param money The amount of money to add to the Player
     */
    void addMoney(int money);

    /**
     * Callback to add points to the Player
     *
     * @param score The amount of points to add to the Player
     */
    void addScore(int score);

    /**
     * Callback to collect an Item
     *
     * @param item The Item to be collected
     */
    void collectItem(Item item);
}