package model.decks;

import model.Ammo;
import model.Player;
import model.enums.Color;
import model.enums.WeaponStatus;
import model.field.Square;
import model.moves.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.stream.Stream;

import static model.enums.EffectType.*;
import static model.enums.TargetType.*;

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
        Target targetBasic = new Player(VISIBLE, null, null);
        this.weapons.get(0).getEffectsList()
                .add(new CardEffect(BASIC, Stream.of(new Ammo(Color.BLUE), new Ammo(Color.BLUE))));
        this.weapons.get(0).getEffectsList().get(0).getEffects()
                .add(new DamageEffect(Stream.of(targetBasic) ,2, false));
        this.weapons.get(0).getEffectsList().get(0).getEffects()
                .add(new MarkEffect(Stream.of(targetBasic), 1, false));
        //PlayerOptional is different from PlayerBasic
        Player playerOptional = new Player(VISIBLE, null, null);
        this.weapons.get(0).getEffectsList()
                .add(new CardEffect(OPTIONAL, Stream.of(new Ammo(Color.RED))));
        this.weapons.get(0).getEffectsList().get(1).getEffects()
                .add(new MarkEffect(Stream.of(playerOptional),1, false));

        //ELECTROSCYTHE
        this.weapons.add(new Weapon(
                "ELECTROSCYTHE",
                "basic mode: Deal 1 damage to every other player\n" +
                        "on your square.\n" +
                        "in reaper mode: Deal 2 damage to every other player\n" +
                        "on your square", WeaponStatus.PARTIALLY_LOADED));
        //Basic effect -- target = null -> mySquare
        targetBasic = new Square(VISIBLE, 0, 0);
        this.weapons.get(1).getEffectsList().add(new CardEffect(BASIC, Stream
                .of(new Ammo(Color.BLUE))));
        this.weapons.get(1).getEffectsList().get(0).getEffects()
                .add(new DamageEffect(Stream.of(targetBasic),1, false));
        //Alternative
        this.weapons.get(1).getEffectsList()
                .add(new CardEffect(ALTERNATIVE, Stream.of(new Ammo(Color.BLUE), new Ammo(Color.RED))));
        this.weapons.get(1).getEffectsList().get(1).getEffects()
                .add(new DamageEffect(Stream.of(targetBasic),2, false));
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
        targetBasic = new Player(VISIBLE, null, null);
        Target targetBasic2 = new Player(VISIBLE, null, null);
        this.weapons.get(2).getEffectsList()
                .add(new CardEffect(BASIC, Stream.of(new Ammo(Color.BLUE), new Ammo(Color.RED))));
        this.weapons.get(2).getEffectsList().get(0).getEffects()
                .add(new DamageEffect(Stream.of(targetBasic),1, false));
        this.weapons.get(2).getEffectsList().get(0).getEffects()
                .add(new DamageEffect(Stream.of(targetBasic2),1, true));

        //Optional effect 1
        this.weapons.get(2).getEffectsList()
                .add(new CardEffect(OPTIONAL1, Stream
                        .of(new Ammo(Color.YELLOW))));
        this.weapons.get(2).getEffectsList().get(1).getEffects()
                .add(new DamageEffect(Stream.of(targetBasic, targetBasic2), 1, false));

        //Optional effect 2
        this.weapons.get(2).getEffectsList()
                .add(new CardEffect(OPTIONAL2, Stream
                        .of(new Ammo(Color.BLUE))));
        this.weapons.get(2).getEffectsList().get(2).getEffects()
                .add(new DamageEffect(Stream.of(targetBasic, targetBasic2),1, false));

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
        targetBasic = new Player(NONE, null, null);
        this.weapons.get(3).getEffectsList().add(new CardEffect(BASIC, Stream
                .of(new Ammo(Color.BLUE))));
        this.weapons.get(3).getEffectsList().get(0).getEffects()
                .add(new Movement(Stream.of(targetBasic),
                        new Square(VISIBLE, 0, 2), false));
        this.weapons.get(3).getEffectsList().get(0).getEffects()
                .add(new DamageEffect(Stream.of(targetBasic),1, false));
        //ALternative Effect -- destination.cansee = null -> mysquare
        Target targetOptional = new Player(NONE,0, 2);
        this.weapons.get(3).getEffectsList().add(new CardEffect(OPTIONAL, Stream
                .of(new Ammo(Color.RED), new Ammo(Color.YELLOW))));
        this.weapons.get(3).getEffectsList().get(1).getEffects()
                .add(new Movement(Stream.of(targetOptional),
                        new Square(VISIBLE,0,0), false));
        this.weapons.get(3).getEffectsList().get(1).getEffects()
                .add(new DamageEffect(Stream.of(targetOptional),3,false));

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
        targetBasic = new Player(VISIBLE, null, null);
        this.weapons.get(4).getEffectsList().add(new CardEffect(BASIC, Stream
                .of(new Ammo(Color.BLUE), new Ammo(Color.RED))));
        this.weapons.get(4).getEffectsList().get(0).getEffects().add(new DamageEffect(Stream.of(targetBasic), 2, false));

        //Optional Effect 1
        //BASIC_VISIBLE = basictarget.cansee
        targetOptional = new Player(BASIC_VISIBLE, null, null);
        this.weapons.get(4).getEffectsList()
                .add(new CardEffect(OPTIONAL1, Stream.of(new Ammo(Color.BLUE))));
        this.weapons.get(4).getEffectsList().get(1).getEffects()
                .add(new DamageEffect( Stream.of(targetOptional), 1, false));

        //Optional Effect 2
        //BASIC_VISIBLE = basictarget.cansee
        targetOptional = new Player(OPTIONAL1_VISIBLE, null, null);
        this.weapons.get(4).getEffectsList().add(new CardEffect(OPTIONAL1, Stream
                .of(new Ammo(Color.BLUE))));
        this.weapons.get(4).getEffectsList().get(1).getEffects()
                .add(new DamageEffect( Stream.of(targetOptional), 1, false));

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
        targetBasic = new Player (VISIBLE, null, null);
        this.weapons.get(5).getEffectsList().add(new CardEffect(BASIC, Stream
                .of(new Ammo(Color.BLUE), new Ammo(Color.YELLOW))));
        this.weapons.get(5).getEffectsList().get(0).getEffects()
                .add(new DamageEffect(Stream.of(targetBasic), 2, false));

        //Optional Effect 1
        targetOptional = new Player (ME, null, null);
        this.weapons.get(5).getEffectsList().add(new CardEffect(BEFORE_AFTER_BASIC, null));
        this.weapons.get(5).getEffectsList().get(1).getEffects()
                .add(new Movement(Stream.of(targetOptional), new Square(null, 1,2), false));

        //Optional Effect 2
        this.weapons.get(5).getEffectsList().add(new CardEffect(OPTIONAL, Stream
                .of(new Ammo(Color.BLUE))));
        this.weapons.get(5).getEffectsList().get(2).getEffects()
                .add(new DamageEffect(Stream.of(targetBasic), 1, false));

        //Whisper
        this.weapons.add(new Weapon("Whisper",
                "effect: Deal 3 damage and 1 mark to 1 target you can see.\n" +
                        "Your target must be at least 2 moves away from you.\n" +
                        "Notes: For example, in the 2-by-2 room, you cannot shoot\n" +
                        "a target on an adjacent square, but you can shoot a target\n" +
                        "on the diagonal. If you are beside a door, you can't shoot\n" +
                        "a target on the other side of the door, but you can shoot\n" +
                        "a target on a different square of that room.", WeaponStatus.PARTIALLY_LOADED));
        targetBasic = new Player(VISIBLE, 2, null);
        this.weapons.get(6).getEffectsList().add(new CardEffect(BASIC, Stream
                .of(new Ammo(Color.BLUE), new Ammo(Color.BLUE), new Ammo(Color.YELLOW))));
        this.weapons.get(6).getEffectsList().get(0).getEffects()
                .add(new DamageEffect(Stream.of(targetBasic), 1, false)); //3
        this.weapons.get(6).getEffectsList().get(0).getEffects()
                .add(new MarkEffect(Stream.of(targetBasic), 3, false));

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
        //Basic Effect
        //TODO controllare
        Square vortex = new Square(NOT_MINE, 0, 1);
        targetBasic = new Player(NONE, null, null);
        this.weapons.get(7).getEffectsList()
                .add(new CardEffect(BASIC, Stream.of(new Ammo(Color.RED), new Ammo(Color.BLUE))));
        this.weapons.get(7).getEffectsList().get(0).getEffects().add(new Movement(Stream.of(targetBasic), vortex,false));
        this.weapons.get(7).getEffectsList().get(0).getEffects()
                .add(new DamageEffect(Stream.of((Target)vortex), 1, false));
        //Optional Effect
        targetOptional = new Player(NONE, null, null);
        this.weapons.get(7).getEffectsList().add(new CardEffect(OPTIONAL_VORTEX, null));
        this.weapons.get(7).getEffectsList().get(1).getEffects()
                .add(new DamageEffect(Stream.of(targetOptional), 1, false));
        this.weapons.get(7).getEffectsList().get(1).getEffects()
                .add(new DamageEffect(Stream.of(targetOptional), 1, true));

        this.weapons.get(7).getEffectsList().add(new CardEffect(OPTIONAL_VORTEX, Stream.of(new Ammo(Color.BLUE))));
        this.weapons.get(7).getEffectsList().get(1).getEffects()
                .add(new Movement(Stream.of(targetOptional), vortex, false));
        this.weapons.get(7).getEffectsList().get(1).getEffects()
                .add(new DamageEffect(Stream.of(targetOptional), 1,false));

        //Furnace
        this.weapons.add(new Weapon("Furnace",
                "basic mode: Choose a room you can see, but not the room\n" +
                        "you are in. Deal 1 damage to everyone in that room.\n" +
                        "in cozy fire mode: Choose a square exactly one move\n" +
                        "away. Deal 1 damage and 1 mark to everyone on that\n" +
                        "square.", WeaponStatus.PARTIALLY_LOADED));
        //Basic Effect
        targetBasic = new Square(VISIBLE, 1, null);
        this.weapons.get(8).getEffectsList().add(new CardEffect(BASIC, Stream
                .of(new Ammo(Color.RED), new Ammo(Color.BLUE))));
        this.weapons.get(8).getEffectsList().get(0).getEffects()
                .add(new DamageEffect(Stream.of(targetBasic), 1, false));

        //Alternative Effect
        Target targetAlternative = new Square(NONE, 1,1);
        this.weapons.get(8).getEffectsList().add(new CardEffect(ALTERNATIVE, null));
        this.weapons.get(8).getEffectsList().get(1).getEffects()
                .add(new DamageEffect(Stream.of(targetAlternative), 1, false));
        this.weapons.get(8).getEffectsList().get(1).getEffects()
                .add(new MarkEffect(Stream.of(targetAlternative), 1, false));

        //Heatseeker
        this.weapons.add(new Weapon("Heatseeker",
                "effect: Choose 1 target you cannot see and deal 3 damage\n" +
                        "to it.\n" +
                        "Notes: Yes, this can only hit targets you cannot see", WeaponStatus.PARTIALLY_LOADED));
        targetBasic = new Player(NOT_VISIBLE, null,null);
        this.weapons.get(9).getEffectsList().add(new CardEffect(BASIC, Stream
                .of(new Ammo(Color.RED), new Ammo(Color.RED), new Ammo(Color.YELLOW))));
        this.weapons.get(9).getEffectsList().get(0).getEffects()
                .add(new DamageEffect(Stream.of(targetBasic), 3, false));

        //Hellion
        this.weapons.add(new Weapon("Hellion",
                "basic mode: Deal 1 damage to 1 target you can see at least\n" +
                        "1 move away. Then give 1 mark to that target and everyone\n" +
                        "else on that square.\n" +
                        "in nano-tracer mode: Deal 1 damage to 1 target you can\n" +
                        "see at least 1 move away. Then give 2 marks to that target\n" +
                        "and everyone else on that square", WeaponStatus.PARTIALLY_LOADED));
        //Basic Effect
        targetBasic = new Player(VISIBLE, 1, null);
        targetBasic2 = new Square(SAME_TARGET, null, null);
        this.weapons.get(10).getEffectsList()
                .add(new CardEffect(BASIC, Stream.of(new Ammo(Color.RED), new Ammo(Color.YELLOW))));
        this.weapons.get(10).getEffectsList().get(0).getEffects()
                .add(new DamageEffect(Stream.of(targetBasic), 1, false));
        this.weapons.get(10).getEffectsList().get(0).getEffects()
                .add(new MarkEffect(Stream.of(targetBasic2), 1, false));

        //Alternative Effect
        targetAlternative = new Player(VISIBLE, 1, null);
        Target targetAlternative2 = new Square(SAME_TARGET, null, null);
        this.weapons.get(10).getEffectsList().add(new CardEffect(ALTERNATIVE, Stream.of(new Ammo(Color.RED))));
        this.weapons.get(10).getEffectsList().get(1).getEffects()
                .add(new DamageEffect(Stream.of(targetAlternative), 1, false));
        this.weapons.get(10).getEffectsList().get(1).getEffects()
                .add(new MarkEffect(Stream.of(targetAlternative2), 2, false));

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
        //Basic Mode
        this.weapons.get(11).getEffectsList().add(new CardEffect(BASIC, Stream.of(new Ammo(Color.RED))));
        //TODO implementation

        //Alternative Mode
        this.weapons.get(11).getEffectsList()
                .add(new CardEffect(ALTERNATIVE, Stream.of(new Ammo(Color.RED), new Ammo(Color.YELLOW))));
        //TODO implementation

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
        //Basic Effect
        targetBasic = new Player(VISIBLE, null, null);
        Square destination = new Square (NONE, 1,1);
        targetOptional = new Square(VISIBLE, null, null);
        this.weapons.get(12).getEffectsList()
                .add(new CardEffect(BASIC, Stream.of(new Ammo(Color.RED))));
        this.weapons.get(12).getEffectsList().get(0).getEffects()
                .add(new DamageEffect(Stream.of(targetBasic), 1, false));
        this.weapons.get(12).getEffectsList().get(0).getEffects()
                .add(new Movement(Stream.of(targetBasic), destination, true ));
        //Alternative Effect
        this.weapons.get(12).getEffectsList()
                .add(new CardEffect(BEFORE_AFTER_BASIC, Stream.of(new Ammo(Color.RED))));
        this.weapons.get(12).getEffectsList().get(1).getEffects()
                .add(new DamageEffect(Stream.of(targetOptional), 1, false));

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
        //Basic Effect
        targetBasic = new Player(VISIBLE, 1, null);
        destination = new Square(NONE, 1, 1);
        this.weapons.get(13).getEffectsList().add(new CardEffect(BASIC, Stream.of(new Ammo(Color.RED))));
        this.weapons.get(13).getEffectsList().get(0).getEffects()
                .add(new DamageEffect(Stream.of(targetBasic), 2, false));
        this.weapons.get(13).getEffectsList().get(0).getEffects()
                .add(new Movement(Stream.of(targetBasic), destination, true));

        //Optional Effect
        targetOptional = new Player(ME, null, null);
        destination = new Square(NONE, 1, 2);
        this.weapons.get(13).getEffectsList()
                .add(new CardEffect(BEFORE_AFTER_BASIC, Stream.of(new Ammo(Color.BLUE))));
        this.weapons.get(13).getEffectsList().get(1).getEffects()
                .add(new Movement(Stream.of(targetOptional), destination, false));

        //Optional Effect 2
        targetOptional = new Square(SAME_TARGET, null, null);
        this.weapons.get(13).getEffectsList()
                .add(new CardEffect(BEFORE_BASIC, Stream.of(new Ammo(Color.YELLOW))));
        this.weapons.get(13).getEffectsList().get(2).getEffects()
                .add(new DamageEffect(Stream.of(targetBasic, targetOptional), 1, false));

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
}
