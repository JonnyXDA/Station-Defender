package com.aston.group.stationdefender.gamesetting;

import com.aston.group.stationdefender.actors.Actor;
import com.aston.group.stationdefender.actors.Unit;
import com.aston.group.stationdefender.config.Constants;
import com.aston.group.stationdefender.utils.ProjectileFactory;
import com.badlogic.gdx.utils.Array;

/**
 * Board class
 *
 * @author Jonathon Fitch
 * @author Twba Alshaghdari
 */
public class Board {
    private static int numberOfLanes = 4;
    private static int numberOfTiles = 4;
    private Array<Lane> lanes = new Array<Lane>();
    private ProjectileFactory projectileFactory;
    private boolean hasWon;
    private boolean hasLost;
    private Player player;

    /**
     * Construct a new Board with a default of 4 Lanes
     */
    public Board(Player player) {
        this(player, numberOfLanes, numberOfTiles);
    }

    /**
     * Construct a new Board with a given number of Lanes each lane will have
     * the same number of Tiles.
     *
     * @param numberOfLanes The number of Lanes for the Board to have
     * @param numberOfTiles The number of Lanes for the Board to have
     */
    public Board(Player player, int numberOfLanes, int numberOfTiles) {
        int laneY = 120;

        for (int i = 0; i < numberOfLanes - 1; i++) {
            lanes.add(new Lane(player, 100, laneY, Constants.TILE_AMOUNT));

            laneY += (Constants.TILE_HEIGHT + (Constants.TILE_HEIGHT / 2));
        }
    }

    /**
     * Adds a Lane to the Board
     *
     * @param lane The Lane to add to the Board
     */
    public void addLane(Lane lane) {
        lanes.add(lane);
    }

    /**
     * Removes a Lane from the Board by lane number
     *
     * @param index The Lane number to remove from the Board
     */
    public void removeLaneByIndex(int index) {
        lanes.removeIndex(index - 1);
    }

    /**
     * Removes a Lane from the Board by Lane Object
     *
     * @param lane The Lane Object to be removed from the Board
     */
    public void removeLaneByObject(Lane lane) {
        lanes.removeValue(lane, true);
    }

    /**
     * Returns a Lane by the specific Lane number
     *
     * @param index The lane number of the lane to get
     * @return The lane of the specific lane number
     */
    public Lane getLane(int index) {
        return lanes.get(index - 1);
    }

    /**
     * Empty the board
     **/
    public void clear() {
        lanes.clear();
    }

    /**
     * Place an actor at the given lane and tile. if there is already an actor
     * at that tile placing should not happen.
     *
     * @return true if the actor has been placed,
     * false if the actor hasn't been placed in the tile
     **/
    public boolean place(Unit unit, int x, int y) {
        for (Lane lane : lanes) {
            if (lane.isColliding(x, y, 1, 1)) {
                lane.place(unit, x, y);
            }
        }
        return true;
    }

    /**
     * Place an entity at the given lane and tile. if there is already an entity
     * at that tile it will be lost.
     *
     * @param laneNo The index of the lane the entity is in
     * @param tileNo The index of the tile the entity is in
     **/
    public Actor getActorAt(int laneNo, int tileNo) {
        return lanes.get(laneNo).getTile(tileNo).getActor();
    }

    /**
     * Render the Board.
     *
     * @param delta - The time in seconds since the last render.
     */
    public void render(float delta) {
        for (Lane lane : lanes) {
            lane.render(delta);

            if(lane.isOverrun())
                hasLost = true;


        }

        if(isAllLanesCleared()){
            hasWon = true;
        }
    }

    public boolean isAllLanesCleared(){
        boolean cleared = true;

        for (int i = 0; i < lanes.size; i++) {
            if(!lanes.get(i).isCleared()){
                cleared = false;
            }
        }

        return cleared;
    }

    /**
     * Dispose of unused resources
     */
    public void dispose() {
        for (Lane lane : lanes) {
            lane.dispose();
        }
    }

    public boolean isHasWon() {
        return hasWon;
    }

    public void setHasWon(boolean hasWon) {
        this.hasWon = hasWon;
    }

    public boolean isHasLost() {
        return hasLost;
    }

    public void setHasLost(boolean hasLost) {
        this.hasLost = hasLost;
    }
}