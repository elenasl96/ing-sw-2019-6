package model;

import model.decks.*;
import model.enums.Color;
import model.field.*;
import org.graalvm.compiler.lir.amd64.AMD64Move;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.lang.*;

import static java.lang.Character.isUpperCase;
import static model.enums.Color.found;

public class Board {
    private Field field;
    private List<Player> killshotTrack;
    private WeaponDeck weaponsLeft;
    private AmmoDeck ammosLeft;
    private PowerupDeck powerupsLeft;
    private List<AmmoTile> ammosLeftover;
    private List<Powerup> powerupsLeftover;

    public Board(){
        String line;
        Color cl;
        int r=0, c=0;
        char letter;
        try {
            BufferedReader lineReader = new BufferedReader(new FileReader("prova.txt"));
            while((line = lineReader.readLine())!=null){
                Scanner charReader = new Scanner(lineReader);
                while((letter = charReader.next().charAt(0)) != -1){
                    cl = found(letter);
                    if(cl!=null){
                        if(isUpperCase(letter)){
                            field.getSquares().add(new SpawnSquare(cl));
                        } else field.getSquares().add(new AmmoSquare(cl));
                    }
                }
            }

        } catch (IOException e){
            System.out.println(e.getMessage());
        }


        this.killshotTrack = new ArrayList<Player>();
        this.weaponsLeft = new WeaponDeck();
        this.ammosLeft = new AmmoDeck();
        this.powerupsLeft = new PowerupDeck();
        this.ammosLeftover = new ArrayList<AmmoTile>();
        this.powerupsLeftover = new ArrayList<Powerup>();

    }

    public Field getField() {
        return field;
    }

    /*
     setfield deleted as field not editable
    */

    public List<Player> getKillshotTrack() {
        return killshotTrack;
    }

    /**
     * Adds the player who killed on the kill shot track
     * @param pg player who killed
     */
    public void addKillshot(Player pg) {
        this.killshotTrack.add(pg);
    }

    public List<AmmoTile> getAmmosLeftover() {
        return ammosLeftover;
    }

    /**
     * add the ammo in ammos leftovers
     * @param ammoTileLeftover ammo discarded
     */
    public void addAmmoLeftover(AmmoTile ammoTileLeftover) {
        this.ammosLeftover.add(ammoTileLeftover);
    }

    public List<Powerup> getPowerupsLeftover() {
        return powerupsLeftover;
    }

    /**
     * add the powerup in powerups leftovers
     * @param powerupLeftover
     */
    public void addPowerupLeftover(Powerup powerupLeftover) {
        this.powerupsLeftover.add(powerupLeftover);
    }
}