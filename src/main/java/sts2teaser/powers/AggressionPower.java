package sts2teaser.powers;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static sts2teaser.STS2Teaser.makeID;

public class AggressionPower extends AbstractPower {
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(makeID("Aggression"));

    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public AggressionPower(AbstractCreature owner, int amt) {
        name = powerStrings.NAME;
        ID = makeID("Aggression");
        this.owner = owner;
        amount = amt;
        updateDescription();
        loadRegion("firebreathing");
    }

    public void updateDescription() {
        if (amount > 1) {
            description = powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[2];
        } else {
            description = powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[1];
        }
    }

    public void atStartOfTurn() {
        flash();
        CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        AbstractPlayer p = AbstractDungeon.player;
        for (AbstractCard c : p.discardPile.group) {
            if (c.type == AbstractCard.CardType.ATTACK)
                tmp.addToRandomSpot(c);
        }
        if (tmp.size() == 0) {
            return;
        }
        for (int i = 0; i < this.amount; i++) {
            if (tmp.isEmpty())
                break;
            tmp.shuffle();
            AbstractCard card = tmp.getBottomCard();
            tmp.removeCard(card);
            if (AbstractDungeon.player.hand.size() == BaseMod.MAX_HAND_SIZE) {
                AbstractDungeon.player.createHandIsFullDialog();
            } else {
                card.unhover();
                card.lighten(true);
                card.setAngle(0.0F);
                card.drawScale = 0.12F;
                card.targetDrawScale = 0.75F;
                card.current_x = CardGroup.DISCARD_PILE_X;
                card.current_y = CardGroup.DISCARD_PILE_Y;
                AbstractDungeon.player.discardPile.removeCard(card);
                AbstractDungeon.player.hand.addToTop(card);
                AbstractDungeon.player.hand.refreshHandLayout();
                if (!card.upgraded) {
                    card.upgrade();
                    card.superFlash();
                }
                AbstractDungeon.player.hand.applyPowers();
            }
        }
    }
}
