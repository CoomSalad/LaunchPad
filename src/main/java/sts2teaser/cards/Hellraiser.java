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
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import sts2teaser.powers.AggressionPower;
import sts2teaser.powers.HellraiserPower;

import java.util.Iterator;

import static sts2teaser.STS2Teaser.*;

public class Hellraiser extends CustomCard {
    public final static String ID = makeID("Hellraiser");

    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public Hellraiser() {
        super(ID, cardStrings.NAME, makeCardPath("Hellraiser"), 3, cardStrings.DESCRIPTION, CardType.POWER, AbstractCard.CardColor.RED, CardRarity.RARE, AbstractCard.CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)p, (AbstractCreature)p, (AbstractPower)new HellraiserPower((AbstractCreature)p, this.magicNumber), this.magicNumber));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(2);
        }
    }

    public AbstractCard makeCopy() {
        return new Hellraiser();
    }
}