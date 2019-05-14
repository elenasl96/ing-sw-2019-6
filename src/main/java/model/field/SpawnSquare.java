package model.field;

import model.Board;
import model.PlayerBoard;
import model.decks.Grabbable;
import model.decks.WeaponTile;
import model.enums.Color;

import java.util.ArrayList;

public class SpawnSquare extends Square{
    private WeaponTile weapons;

    public SpawnSquare(Color color, Coordinate coordinate, Board board) {
        super(color, coordinate);
        this.weapons = new WeaponTile();
        this.setGrabbable(board);
    }

    public void addDamage(){

    }

    public void addMarks(){

    }

    public ArrayList<PlayerBoard> getPlayerBoard(){
        return super();
    }


    @Override
    public Grabbable getGrabbable() {
        return weapons;
    }

    @Override
    public void setGrabbable(Board board){
        for(int i=0; i<3; i++){
            weapons.addWeapon(board.getWeaponsLeft().pickCard());
        }
    }

}
