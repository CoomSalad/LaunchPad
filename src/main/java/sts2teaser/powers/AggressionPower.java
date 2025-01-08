package sts2teaser.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static sts2teaser.STS2Teaser.makeID;

public class AggressionPower extends AbstractPower {
    public static final String POWER_ID = "Aggression";

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("Metallicize");

    public static final String NAME = "Aggression";

    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public AggressionPower(AbstractCreature owner, int armorAmt) {
        this.name = NAME;
        this.ID = makeID("Aggression");
        this.owner = owner;
        this.amount = armorAmt;
        updateDescription();
        loadRegion("armor");
    }

    public void playApplyPowerSfx() {
        CardCrawlGame.sound.play("POWER_METALLICIZE", 0.05F);
    }

    public void updateDescription() {
        if (this.owner.isPlayer) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[2] + this.amount + DESCRIPTIONS[3];
        }
    }

    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        flash();
        addToBot((AbstractGameAction)new GainBlockAction(this.owner, this.owner, this.amount));
    }
}
