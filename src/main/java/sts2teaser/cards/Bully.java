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
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Iterator;

import static sts2teaser.STS2Teaser.*;

public class Bully extends CustomCard {
    public final static String ID = makeID("Bully");

    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public Bully() {
        super(ID, cardStrings.NAME, makeCardPath("Bully"), 0, cardStrings.DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCard.CardColor.RED, CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY);
        baseDamage = 4;
        baseMagicNumber = magicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot((AbstractGameAction)new DamageAction((AbstractCreature)m, new DamageInfo((AbstractCreature)p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    public void applyPowers() {
        int realBaseDamage = baseDamage;
        int targetMonsterVulnerable = 0;
        Iterator var3 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
        while(var3.hasNext()) {
            AbstractMonster m = (AbstractMonster)var3.next();
            m.hb.update();
            if (m.hb.hovered && !m.isDying && !m.isEscaping && m.currentHealth > 0) {
                if (m.hasPower("Vulnerable")) {
                    targetMonsterVulnerable = m.getPower("Vulnerable").amount;
                }
                break;
            }
        }
        baseDamage += targetMonsterVulnerable * baseMagicNumber;
        super.applyPowers();
        baseDamage = realBaseDamage;
        isDamageModified = (damage != baseDamage);
    }

    public void calculateCardDamage(AbstractMonster mo) {
        int targetMonsterVulnerable = 0;
        if (mo.hasPower("Vulnerable")) {
            targetMonsterVulnerable = mo.getPower("Vulnerable").amount;
        }
        int realBaseDamage = baseDamage;
        baseDamage += targetMonsterVulnerable * baseMagicNumber;
        super.calculateCardDamage(mo);
        baseDamage = realBaseDamage;
        isDamageModified = (damage != baseDamage);
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(1);
            upgradeMagicNumber(1);
        }
    }

    public AbstractCard makeCopy() {
        return new Bully();
    }
}