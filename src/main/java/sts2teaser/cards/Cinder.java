package sts2teaser.cards;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static sts2teaser.STS2Teaser.*;

public class Cinder extends CustomCard {
    public final static String ID = makeID("Cinder");

    private static String NAME = "Cinder";
    private static String DESC = "Deal !D! damage. NL Exhaust the top !M! cards in NL your Draw Pile.";

    public Cinder() {
        super(ID, NAME, makeCardPath("Cinder"), 2, DESC, AbstractCard.CardType.ATTACK, AbstractCard.CardColor.RED, AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.ENEMY);
        baseDamage = 15;
        baseMagicNumber = magicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot((AbstractGameAction)new DamageAction((AbstractCreature)m, new DamageInfo((AbstractCreature)p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
        for (int i = 0; i < magicNumber; i++) {
            if (p.drawPile.size() > i) {
                addToBot((AbstractGameAction)new ExhaustSpecificCardAction(p.drawPile.getNCardFromTop(i), p.drawPile));
            }
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(5);
        }
    }

    public AbstractCard makeCopy() {
        return new Cinder();
    }
}