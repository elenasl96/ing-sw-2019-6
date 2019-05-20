package model.decks;

import model.Ammo;
import model.Player;
import model.enums.Color;
import model.enums.WeaponStatus;
import model.field.AmmoSquare;
import model.moves.*;
import model.moves.Target.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static model.enums.EffectType.*;

public class WeaponDeck {
    private List<Weapon> weapons = new ArrayList<>();

    public WeaponDeck() {

        //LOCK RIFLE
        this.weapons.add(new Weapon(
                "Lock rifle",
                "basic effect: Deal 2 damage and 1 mark to 1 target\n" +
                        "you can see.\n" +
                        "with second lock: Deal 1 mark to a different target\n" +
                        "you can see.", WeaponStatus.PARTIALLY_LOADED));
        //Basic Effect
        Player p1 = new Player(true, null, null, null);
        List<Target> t1 = Target.addTargetList(p1);
        this.weapons.get(0).getEffects().add(new DamageEffect(BASIC, t1 ,2, false, null));
        this.weapons.get(0).getEffects().add(new MarkEffect(BASIC, t1, 1, false, null));
        this.weapons.get(0).getEffects().get(0).setCost(Stream
                .of(new Ammo(Color.BLUE), new Ammo(Color.BLUE))
                .collect(Collectors.toCollection(ArrayList::new)));
        //Optional effect -- p2 is different from p1
        Player p2 = new Player(true, false, null, null);
        this.weapons.get(0).getEffects().add(new MarkEffect(BASIC, Target.addTargetList(p2),1, false, null)); //2
        this.weapons.get(0).getEffects().get(2).setCost(Stream
                .of(new Ammo(Color.RED))
                .collect(Collectors.toCollection(ArrayList::new)));

        //ELECTROSCYTHE
        this.weapons.add(new Weapon(
                "ELECTROSCYTHE",
                "basic mode: Deal 1 damage to every other player\n" +
                        "on your square.\n" +
                        "in reaper mode: Deal 2 damage to every other player\n" +
                        "on your square", WeaponStatus.PARTIALLY_LOADED));
        //Basic effect -- target = null -> mySquare
        this.weapons.get(1).getEffects().add(new DamageEffect(BASIC, null,1, false, null));
        this.weapons.get(1).getEffects().get(0).setCost(Stream.of(new Ammo(Color.BLUE))
                .collect(Collectors.toCollection(ArrayList::new)));
        //Alternative
        this.weapons.get(1).getEffects().add(new DamageEffect(ALTERNATIVE,
                Target.addTargetList(new AmmoSquare(null, null, 0, 0)),
                2, false, null));
        this.weapons.get(1).getEffects().get(1).setCost(Stream
                .of(new Ammo(Color.BLUE), new Ammo(Color.RED))
                .collect(Collectors.toCollection(ArrayList::new)));

        //Machine Gun
        this.weapons.add(new Weapon("MACHINE GUN",
                "basic effect: Choose 1 or 2 targets you can see and deal\n" +
                        "1 damage to each.\n" +
                        "with focus shot: Deal 1 additional damage to one of those\n" +
                        "targets.\n" +
                        "with turret tripod: Deal 1 additional damage to the other\n" +
                        "of those targets and/or deal 1 damage to a different target\n" +
                        "you can see.\n" +
                        "Notes: If you deal both additional points of damage,\n" +
                        "they must be dealt to 2 different targets. If you see only\n" +
                        "2 targets, you deal 2 to each if you use both optional\n" +
                        "effects. If you use the basic effect on only 1 target, you can\n" +
                        "still use the the turret tripod to give it 1 additional damage", WeaponStatus.PARTIALLY_LOADED));
        //Basic effect
        t1 = Target.addTargetList(new Player(true, null, null, null));
        this.weapons.get(2).getEffects().add(
                new DamageEffect(BASIC, t1,1, false, null));
        List<Target> t2 = Target.addTargetList(new Player(true, null, null, null));
        this.weapons.get(2).getEffects()
                .add(new DamageEffect(BASIC, t2, 1,true, null));
        this.weapons.get(2).getEffects().get(0).setCost(Stream
                .of(new Ammo(Color.BLUE), new Ammo(Color.RED))
                .collect(Collectors.toCollection(ArrayList::new)));

        //Optional effect 1
        this.weapons.get(2).getEffects()
                .add(new DamageEffect(OPTIONAL,
                        Stream.concat(t1.stream(), t1.stream()).collect(Collectors.toCollection(ArrayList::new)),
                        1, false, null));
        this.weapons.get(2).getEffects().get(2).setCost(Stream
                .of(new Ammo(Color.YELLOW))
                .collect(Collectors.toCollection(ArrayList::new)));

        //Optional effect 2
        this.weapons.get(2).getEffects()
                .add(new DamageEffect(OPTIONAL,
                        t2,1, false, true));
        this.weapons.get(2).getEffects().get(3).setCost(Stream
                .of(new Ammo(Color.BLUE))
                .collect(Collectors.toCollection(ArrayList::new)));

        //Tractor Beam
        this.weapons.add(new Weapon("TRACTOR BEAM",
                "basic mode: Move a target 0, 1, or 2 squares to a square\n" +
                        "you can see, and give it 1 damage.\n" +
                        "in punisher mode: Choose a target 0, 1, or 2 moves away\n" +
                        "from you. Move the target to your square\n" +
                        "and deal 3 damage to it.\n" +
                        "Notes: You can move a target even if you can't see it.\n" +
                        "The target ends up in a place where you can see and\n" +
                        "damage it. The moves do not have to be in the same\n" +
                        "direction.", WeaponStatus.PARTIALLY_LOADED));
        //Basic Effect
        Player p3 = new Player(null, null, null, null);
        this.weapons.get(3).getEffects()
                .add(new Movement(BASIC, Target.addTargetList(p3), new AmmoSquare(true, null, 0, 2), false, null));
        this.weapons.get(3).getEffects()
                .add(new DamageEffect(BASIC,
                        Target.addTargetList(p3) ,1, false, null));
        this.weapons.get(3).getEffects().get(0).setCost(Stream
                .of(new Ammo(Color.BLUE))
                .collect(Collectors.toCollection(ArrayList::new)));
        //ALternative Effect -- destination.cansee = null -> mysquare
        p3 = new Player(null, null, 0, 2);
        this.weapons.get(3).getEffects().add(new Movement(ALTERNATIVE, Target.addTargetList(p3),
                new AmmoSquare(null, null, 0,0), false, null));
        this.weapons.get(3).getEffects()
                .add(new DamageEffect(ALTERNATIVE, Target.addTargetList(p3),3,false, null));
        this.weapons.get(2).getEffects().get(2).setCost(Stream
                .of(new Ammo(Color.RED), new Ammo(Color.YELLOW))
                .collect(Collectors.toCollection(ArrayList::new)));

        //T.H.O.R.
        this.weapons.add(new Weapon("T.H.O.R.",
                "basic effect: Deal 2 damage to 1 target you can see.\n" +
                        "with chain reaction: Deal 1 damage to a second target that\n" +
                        "your first target can see.\n" +
                        "with high voltage: Deal 2 damage to a third target that\n" +
                        "your second target can see. You cannot use this effect\n" +
                        "unless you first use the chain reaction.\n" +
                        "Notes: This card constrains the order in which you can use\n" +
                        "its effects. (Most cards don't.) Also note that each target\n" +
                        "must be a different player", WeaponStatus.PARTIALLY_LOADED));
        //Basic Effect
        this.weapons.get(4).getEffects()
                .add(new DamageEffect(BASIC,
                        Target.addTargetList(new Player(true, false, null, null)), 2, false, null));
        this.weapons.get(4).getEffects().get(0).setCost(Stream
                .of(new Ammo(Color.BLUE), new Ammo(Color.RED))
                .collect(Collectors.toCollection(ArrayList::new)));

        //Optional Effect 1
        //TODO add "player1.cansee"
        this.weapons.get(4).getEffects()
                .add(new DamageEffect(OPTIONAL,
                        Target.addTargetList(new Player()), 1, false, null));
        this.weapons.get(4).getEffects().get(1).setCost(Stream
                .of(new Ammo(Color.BLUE))
                .collect(Collectors.toCollection(ArrayList::new)));


        //Optional Effect 2
        //TODO add "player2.cansee
        this.weapons.get(4).getEffects()
                .add(new DamageEffect(OPTIONAL,
                        Target.addTargetList(new Player()), 1, false, null));
        this.weapons.get(4).getEffects().get(2).setCost(Stream
                .of(new Ammo(Color.BLUE))
                .collect(Collectors.toCollection(ArrayList::new)));

        //Plasma Gun
        this.weapons.add(new Weapon("Plasma Gun",
                "basic effect: Deal 2 damage to 1 target you can see.\n" +
                        "with phase glide: Move 1 or 2 squares. This effect can be\n" +
                        "used either before or after the basic effect.\n" +
                        "with charged shot: Deal 1 additional damage to your\n" +
                        "target.\n" +
                        "Notes: The two moves have no ammo cost. You don't have\n" +
                        "to be able to see your target when you play the card.\n" +
                        "For example, you can move 2 squares and shoot a target\n" +
                        "you now see. You cannot use 1 move before shooting and\n" +
                        "1 move after", WeaponStatus.PARTIALLY_LOADED));
        //Basic Effect
        Player p5 = new Player (true, null, null, null);
        this.weapons.get(5).getEffects()
                .add(new DamageEffect(BASIC, Target.addTargetList(p5), 2, false, null));
        this.weapons.get(5).getEffects().get(0).setCost(Stream
                .of(new Ammo(Color.BLUE), new Ammo(Color.YELLOW))
                .collect(Collectors.toCollection(ArrayList::new)));

        //Optional Effect 1
        //if target == null -> is me
        this.weapons.get(5).getEffects()
                .add(new Movement(BEFORE_AFTER_BASIC, null,
                        new AmmoSquare(null, null, 1,2), false, null));

        //Optional Effect 2
        this.weapons.get(5).getEffects()
                .add(new DamageEffect(OPTIONAL, Target.addTargetList(p5), 1, false, null));
        this.weapons.get(5).getEffects().get(2).setCost(Stream
                .of(new Ammo(Color.BLUE))
                .collect(Collectors.toCollection(ArrayList::new)));


        //Whisper
        this.weapons.add(new Weapon("Whisper",
                "effect: Deal 3 damage and 1 mark to 1 target you can see.\n" +
                        "Your target must be at least 2 moves away from you.\n" +
                        "Notes: For example, in the 2-by-2 room, you cannot shoot\n" +
                        "a target on an adjacent square, but you can shoot a target\n" +
                        "on the diagonal. If you are beside a door, you can't shoot\n" +
                        "a target on the other side of the door, but you can shoot\n" +
                        "a target on a different square of that room.", WeaponStatus.PARTIALLY_LOADED));
        /*this.weapons.get(6).getEffects().add(new Damage()); //3
        this.weapons.get(6).getEffects().add(new MarkEffect());
        this.weapons.get(6).getAmmoBasic().add(new Ammo(Color.BLUE));
        this.weapons.get(6).getAmmoBasic().add(new Ammo(Color.BLUE));
        this.weapons.get(6).getAmmoBasic().add(new Ammo(Color.YELLOW));*/

        //Vortex cannon
        this.weapons.add(new Weapon("Vortex cannon",
                "basic effect: Choose a square you can see, but not your\n" +
                        "square. Call it \"the vortex\". Choose a target on the vortex\n" +
                        "or 1 move away from it. Move it onto the vortex and give it\n" +
                        "2 damage.\n" +
                        "with black hole: Choose up to 2 other targets on the\n" +
                        "vortex or 1 move away from it. Move them onto the vortex\n" +
                        "and give them each 1 damage.\n" +
                        "Notes: The 3 targets must be different, but some might\n" +
                        "start on the same square. It is legal to choose targets on\n" +
                        "your square, on the vortex, or even on squares you can't\n" +
                        "see. They all end up on the vortex", WeaponStatus.PARTIALLY_LOADED));
        /*this.weapons.get(7).getEffects().add(new Movement(5));
        this.weapons.get(7).getEffects().add(new Damage()); //2
        this.weapons.get(7).getAmmoBasic().add(new Ammo(Color.RED));
        this.weapons.get(7).getAmmoBasic().add(new Ammo(Color.BLUE));

        this.weapons.get(7).getEffects().add(new Movement(1));
        this.weapons.get(7).getOptionalEffect().add(new Damage());
        this.weapons.get(7).getAmmoOptional().add(new Ammo(Color.BLUE));*/

        //Furnace
        this.weapons.add(new Weapon("Furnace",
                "basic mode: Choose a room you can see, but not the room\n" +
                        "you are in. Deal 1 damage to everyone in that room.\n" +
                        "in cozy fire mode: Choose a square exactly one move\n" +
                        "away. Deal 1 damage and 1 mark to everyone on that\n" +
                        "square.", WeaponStatus.PARTIALLY_LOADED));
        /*this.weapons.get(8).getEffects().add(new Damage()); //to all room different from yours
        this.weapons.get(8).getAmmoBasic().add(new Ammo(Color.RED));
        this.weapons.get(8).getAmmoBasic().add(new Ammo(Color.BLUE));

        this.weapons.get(8).getAlternateFireEffect().add(new Damage());
        this.weapons.get(8).getAlternateFireEffect().add(new MarkEffect());*/

        //Heatseeker
        this.weapons.add(new Weapon("Heatseeker",
                "effect: Choose 1 target you cannot see and deal 3 damage\n" +
                        "to it.\n" +
                        "Notes: Yes, this can only hit targets you cannot see", WeaponStatus.PARTIALLY_LOADED));
        /*this.weapons.get(9).getEffects().add(new Damage()); //3 to targets you cannot see
        this.weapons.get(9).getAmmoBasic().add(new Ammo(Color.RED));
        this.weapons.get(9).getAmmoBasic().add(new Ammo(Color.RED));
        this.weapons.get(9).getAmmoBasic().add(new Ammo(Color.YELLOW));*/

        //Hellion
        this.weapons.add(new Weapon("Hellion",
                "basic mode: Deal 1 damage to 1 target you can see at least\n" +
                        "1 move away. Then give 1 mark to that target and everyone\n" +
                        "else on that square.\n" +
                        "in nano-tracer mode: Deal 1 damage to 1 target you can\n" +
                        "see at least 1 move away. Then give 2 marks to that target\n" +
                        "and everyone else on that square", WeaponStatus.PARTIALLY_LOADED));
        /*this.weapons.get(10).getEffects().add(new Damage()); //2
        this.weapons.get(10).getEffects().add(new MarkEffect());
        this.weapons.get(10).getAmmoBasic().add(new Ammo(Color.RED));
        this.weapons.get(10).getAmmoBasic().add(new Ammo(Color.YELLOW));

        this.weapons.get(10).getAlternateFireEffect().add(new Damage());
        this.weapons.get(10).getAlternateFireEffect().add(new MarkEffect());
        this.weapons.get(10).getAmmoOptional().add(new Ammo(Color.RED));*/

        //Flamethrowner
        this.weapons.add(new Weapon("Flamethrowner",
                "basic mode: Choose a square 1 move away and possibly a second square\n" +
                        "1 more move away in the same direction. On each square, you may\n" +
                        "choose 1 target and give it 1 damage.\n" +
                        "in barbecue mode: Choose 2 squares as above. Deal 2 damage to\n" +
                        "everyone on the first square and 1 damage to everyone on the second\n" +
                        "square.\n" +
                        "Notes: This weapon cannot damage anyone in your square. However,\n" +
                        "it can sometimes damage a target you can't see – the flame won't go\n" +
                        "through walls, but it will go through doors. Think of it as a straight-line\n" +
                        "blast of flame that can travel 2 squares in a cardinal direction", WeaponStatus.PARTIALLY_LOADED));
        /*this.weapons.get(11).getEffects().add(new Damage()); //2
        this.weapons.get(11).getAmmoBasic().add(new Ammo(Color.RED));

        this.weapons.get(11).getAlternateFireEffect().add(new Damage());
        this.weapons.get(11).getAmmoOptional().add(new Ammo(Color.RED));
        this.weapons.get(11).getAmmoOptional().add(new Ammo(Color.YELLOW));*/

        //Grenade Launcher
        this.weapons.add(new Weapon("Grenade Launcher",
                "basic effect: Deal 1 damage to 1 target you can see. Then you may move\n" +
                        "the target 1 square.\n" +
                        "with extra grenade: Deal 1 damage to every player on a square you can\n" +
                        "see. You can use this before or after the basic effect's move.\n" +
                        "Notes: For example, you can shoot a target, move it onto a square with\n" +
                        "other targets, then damage everyone including the first target.\n" +
                        "Or you can deal 2 to a main target, 1 to everyone else on that square,\n" +
                        "then move the main target. Or you can deal 1 to an isolated target and\n" +
                        "1 to everyone on a different square. If you target your own square,\n" +
                        "you will not be moved or damaged.", WeaponStatus.PARTIALLY_LOADED));
        /*this.weapons.get(12).getEffects().add(new Damage());
        this.weapons.get(12).getAmmoBasic().add(new Ammo(Color.RED));

        this.weapons.get(12).getOptionalEffect().add(new Damage());
        this.weapons.get(12).getAmmoOptional().add(new Ammo(Color.RED));*/

        //Rocket Launcher
        this.weapons.add(new Weapon("Rocket Launcher",
                "basic effect: Deal 2 damage to 1 target you can see that is not on your\n" +
                        "square. Then you may move the target 1 square.\n" +
                        "with rocket jump: Move 1 or 2 squares. This effect can be used either\n" +
                        "before or after the basic effect.\n" +
                        "with fragmenting warhead: During the basic effect, deal 1 damage to\n" +
                        "every player on your target's original square – including the target,\n" +
                        "even if you move it.\n" +
                        "Notes: If you use the rocket jump before the basic effect, you consider\n" +
                        "only your new square when determining if a target is legal. You can\n" +
                        "even move off a square so you can shoot someone on it. If you use the\n" +
                        "fragmenting warhead, you deal damage to everyone on the target's\n" +
                        "square before you move the target – your target will take 3 damage total", WeaponStatus.PARTIALLY_LOADED));
        /*this.weapons.get(13).getEffects().add(new Damage()); //2
        this.weapons.get(13).getAmmoBasic().add(new Ammo(Color.RED));

        this.weapons.get(13).getOptionalEffect().add(new Movement(2));
        this.weapons.get(13).getAmmoOptional().add(new Ammo(Color.BLUE));

        this.weapons.get(13).getOptionalEffect().add(new Damage());
        this.weapons.get(13).getAmmoOptional().add(new Ammo(Color.YELLOW));

     */
        shuffleDeck();
    }

    public void shuffleDeck () {
        Collections.shuffle(weapons);
    }

    public Weapon pickCard(){

        if(!weapons.isEmpty()) {
            Weapon weaponCard=weapons.get(0);
            weapons.remove(0);
            return weaponCard;
        }

        return null;
    }

    public List<Weapon> getWeapons() {
        return weapons;
    }

    public void setWeapons(List<Weapon> weapons) {
        this.weapons = weapons;
    }
}
