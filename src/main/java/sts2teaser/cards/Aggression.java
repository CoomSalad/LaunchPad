package sts2teaser.cards;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import sts2teaser.powers.AggressionPower;

import java.util.Iterator;

import static sts2teaser.STS2Teaser.*;

public class Aggression extends CustomCard {
    public final static String ID = makeID("Aggression");

    private static String NAME = "Aggression";
    private static String DESC = "At the start of your turn, NL " +
            "put a random Attack (2 random Attacks) from NL " +
            "your discard pile into your NL " +
            "Hand and Upgrade it.";
    private static String UPGRADED_DESC = "At the start of your turn, NL " +
            "put !M! random Attacks from NL " +
            "your discard pile into your NL " +
            "Hand and Upgrade it.";

    public Aggression() {
        super(ID, NAME, makeCardPath("Aggression"), 1, DESC, CardType.POWER, AbstractCard.CardColor.RED, CardRarity.RARE, AbstractCard.CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)p, (AbstractCreature)p, (AbstractPower)new AggressionPower((AbstractCreature)p, this.magicNumber), this.magicNumber));
    }

    public void applyPowers() {
        super.applyPowers();
        if (magicNumber > 1) {
            rawDescription = UPGRADED_DESC;
        } else {
            rawDescription = DESC;
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            rawDescription = UPGRADED_DESC;
            upgradeMagicNumber(1);
        }
    }

    public AbstractCard makeCopy() {
        return new Aggression();
    }
}