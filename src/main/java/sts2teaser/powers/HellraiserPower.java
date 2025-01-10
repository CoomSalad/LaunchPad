package sts2teaser.powers;

import basemod.BaseMod;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static sts2teaser.STS2Teaser.*;
import static sts2teaser.util.TexLoader.getTexture;

public class HellraiserPower extends AbstractPower {
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(makeID("Hellraiser"));

    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public HellraiserPower(AbstractCreature owner, int amt) {
        name = powerStrings.NAME;
        ID = makeID("Hellraiser");
        this.owner = owner;
        amount = amt;
        updateDescription();
        Texture normalTexture = getTexture(makePowerPath("Hellraiser"));
        Texture hiDefImage = getTexture(makeHiDefPowerPath("Hellraiser"));
        if (hiDefImage != null)
        {
            region128 = new TextureAtlas.AtlasRegion(hiDefImage, 0, 0, hiDefImage.getWidth(), hiDefImage.getHeight());
            if (normalTexture != null)
                region48 = new TextureAtlas.AtlasRegion(normalTexture, 0, 0, normalTexture.getWidth(), normalTexture.getHeight());
        }
        else
        {
            this.img = normalTexture;
            region48 = new TextureAtlas.AtlasRegion(normalTexture, 0, 0, normalTexture.getWidth(), normalTexture.getHeight());
        }
    }

    public void updateDescription() {
        if (amount > 1) {
            description = powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[2];
        } else {
            description = powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[1];
        }
    }

    public void onCardDraw(AbstractCard card) {
        if (card.hasTag(AbstractCard.CardTags.STRIKE)) {
            flash();
            AbstractDungeon.player.hand.group.remove(card);
            (AbstractDungeon.getCurrRoom()).souls.remove(card);
            // card.exhaustOnUseOnce = this.exhaustCards;
            AbstractDungeon.player.limbo.group.add(card);
            card.current_y = -200.0F * Settings.scale;
            card.target_x = Settings.WIDTH / 2.0F + 200.0F * Settings.xScale;
            card.target_y = Settings.HEIGHT / 2.0F;
            card.targetAngle = 0.0F;
            card.lighten(false);
            card.drawScale = 0.12F;
            card.targetDrawScale = 0.75F;
            card.applyPowers();
            addToBot((AbstractGameAction)new NewQueueCardAction(card, (AbstractCreature)(AbstractDungeon.getCurrRoom()).monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng), false, true));
            addToBot((AbstractGameAction)new UnlimboAction(card));
            if (!Settings.FAST_MODE) {
                addToBot((AbstractGameAction)new WaitAction(Settings.ACTION_DUR_MED));
            } else {
                addToBot((AbstractGameAction)new WaitAction(Settings.ACTION_DUR_FASTER));
            }
            for (int i = 1; i < this.amount; i++) {
                if (!card.purgeOnUse) {
                    AbstractMonster m = (AbstractDungeon.getCurrRoom()).monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
                    if (m == null)
                        break;
                    AbstractCard tmp = card.makeSameInstanceOf();
                    AbstractDungeon.player.limbo.addToBottom(tmp);
                    tmp.current_x = card.current_x;
                    tmp.current_y = card.current_y;
                    tmp.target_x = Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
                    tmp.target_y = Settings.HEIGHT / 2.0F;
                    if (m != null)
                        tmp.calculateCardDamage(m);
                    tmp.purgeOnUse = true;
                    AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m, card.energyOnUse, true, true), true);
                }
            }
        }
    }
}
