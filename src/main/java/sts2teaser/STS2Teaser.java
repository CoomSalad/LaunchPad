package sts2teaser;

import basemod.BaseMod;
import basemod.interfaces.*;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;

@SuppressWarnings({"unused", "WeakerAccess"})
@SpireInitializer
public class STS2Teaser implements PostInitializeSubscriber {

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

    public static void initialize() {
        STS2Teaser thismod = new STS2Teaser();
    }

    @Override
    public void receivePostInitialize() {

    }
}
