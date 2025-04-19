package sts2teaser.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static sts2teaser.STS2Teaser.makeID;

@SpirePatch(
        clz=com.megacrit.cardcrawl.powers.VulnerablePower.class,
        method="atDamageReceive"
)
public class VulnerablePowerPatch {
    @SpirePostfixPatch
    public static float Postfix(float __result, VulnerablePower __instance, float damage, DamageInfo.DamageType type) {
        if (__instance.owner != null && !__instance.owner.isPlayer && AbstractDungeon.player.hasPower(makeID("Cruelty")))
            return __result * (AbstractDungeon.player.getPower(makeID("Cruelty")).amount+100) / 100;
        return __result;
    }
}
