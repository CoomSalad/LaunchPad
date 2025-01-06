package sts2teaser;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.interfaces.*;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import sts2teaser.cards.Cinder;

@SuppressWarnings({"unused", "WeakerAccess"})
@SpireInitializer
public class STS2Teaser implements EditCardsSubscriber, PostInitializeSubscriber {

    public static final String modID = "sts2-teaser"; //TODO: Change this.

    public static String makeID(String idText) {
        return modID + ":" + idText;
    }

    public STS2Teaser() {
        BaseMod.subscribe(this);
    }

    public static String makePath(String resourcePath) {
        return modID + "Resources/" + resourcePath;
    }

    public static String makeImagePath(String resourcePath) {
        return modID + "Resources/images/" + resourcePath;
    }

    public static String makeCardPath(String cardName) {
        return modID + "Resources/images/cards/" + cardName + ".png";
    }

    public static void initialize() {
        STS2Teaser thismod = new STS2Teaser();
    }

    @Override
    public void receivePostInitialize() {

    }

    @Override
    public void receiveEditCards() { //somewhere in the class
        new AutoAdd(modID) //Loads files from this mod
                .packageFilter(Cinder.class) //In the same package as this class
                .setDefaultSeen(true) //And marks them as seen in the compendium
                .cards(); //Adds the cards
    }
}
