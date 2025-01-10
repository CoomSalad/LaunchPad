package sts2teaser;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.interfaces.*;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import sts2teaser.cards.Cinder;

@SuppressWarnings({"unused", "WeakerAccess"})
@SpireInitializer
public class STS2Teaser implements EditCardsSubscriber, EditStringsSubscriber, PostInitializeSubscriber {

    public static final String modID = "sts2-teaser"; //TODO: Change this.

    public static String makeID(String idText) {
        return modID + ":" + idText;
    }

    public STS2Teaser() {
        BaseMod.subscribe(this);
    }

    public static Settings.GameLanguage[] SupportedLanguages = {
            Settings.GameLanguage.ENG,
    };

    private String getLangString() {
        for (Settings.GameLanguage lang : SupportedLanguages) {
            if (lang.equals(Settings.language)) {
                return Settings.language.name().toLowerCase();
            }
        }
        return "eng";
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
    public static String makePowerPath(String powerName) {
        return modID + "Resources/images/powers/" + powerName + ".png";
    }
    public static String makeHiDefPowerPath(String powerName) {
        return modID + "Resources/images/powers/" + powerName + "_l.png";
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

    @Override
    public void receiveEditStrings() {
        BaseMod.loadCustomStringsFile(CardStrings.class, modID + "Resources/localization/" + getLangString() + "/CardStrings.json");
        BaseMod.loadCustomStringsFile(PowerStrings.class, modID + "Resources/localization/" + getLangString() + "/PowerStrings.json");
    }
}
