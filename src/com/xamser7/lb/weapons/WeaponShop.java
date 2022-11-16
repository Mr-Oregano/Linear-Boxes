
package com.xamser7.lb.weapons;

import java.awt.Color;
import java.awt.Graphics2D;
import com.xamser7.lb.gfx.Painter;
import com.xamser7.lb.Game;
import java.util.ArrayList;
import com.xamser7.lb.states.ShopState;
import com.xamser7.lb.entities.MainShop;
import com.xamser7.lb.ui.ClickListener;
import com.xamser7.lb.assets.Assets;
import com.xamser7.lb.Main;
import com.xamser7.lb.ui.UITextButton;
import com.xamser7.lb.ui.UIButton;
import com.xamser7.lb.states.States;

public class WeaponShop extends States
{
    private UIButton equip;
    private UIButton buy;
    private Weapon current;
    private int index;
    private UITextButton damageUpgrade;
    private UITextButton rechargeUpgrade;
    private UITextButton durationUpgrade;
    private boolean switch_upgrades;

    public WeaponShop(final Main main)
    {
        super(main);
        this.switch_upgrades = false;
        this.current = Weapon.getSelected();
        this.index = this.current.getID();
        this.ui.addObject(new UIButton(main, 68.0f, 52.0f, 0.15, false, Assets.back, new ClickListener()
        {
            @Override
            public void onClick()
            {
                ShopState.newWindow(new MainShop(main));
            }
        }));
        this.ui.addObject(new UIButton(main, 408.0f, 312.0f, 0.2, true, Assets.back, new ClickListener()
        {
            @Override
            public void onClick()
            {
                final WeaponShop this$0 = WeaponShop.this;
                final ArrayList<Weapon> list = Weapon.list;
                final WeaponShop this$2 = WeaponShop.this;
                final int index = this$2.index - ((WeaponShop.this.index < 1) ? (-Weapon.list.size() + 1) : 1);
                WeaponShop.access$1(this$2, index);
                WeaponShop.access$2(this$0, list.get(index));
                WeaponShop.access$3(WeaponShop.this, true);
            }
        }));
        this.ui.addObject(new UIButton(main, 958.0f, 312.0f, 0.2, true, Assets.forward, new ClickListener()
        {
            @Override
            public void onClick()
            {
                final WeaponShop this$0 = WeaponShop.this;
                final ArrayList<Weapon> list = Weapon.list;
                final WeaponShop this$2 = WeaponShop.this;
                final int index = this$2.index + ((WeaponShop.this.index > Weapon.list.size() - 2) ? (-WeaponShop.this.index) : 1);
                WeaponShop.access$1(this$2, index);
                WeaponShop.access$2(this$0, list.get(index));
                WeaponShop.access$3(WeaponShop.this, true);
            }
        }));
    }

    @Override
    public void update()
    {
        if (this.equip == null && Weapon.getSelected() != this.current && this.current.isOwned())
        {
            this.ui.addObject(this.equip = new UIButton(WeaponShop.main, 683.0f, 622.0f, 0.35, true, Assets.equip, new ClickListener()
            {
                @Override
                public void onClick()
                {
                    Weapon.setSelected(WeaponShop.this.current);
                }
            }));
        }
        else if (this.equip != null && (Weapon.getSelected() == this.current || !this.current.isOwned()))
        {
            this.ui.removeObject(this.equip);
            this.equip = null;
        }
        else if (this.buy == null && !this.current.isOwned() && Game.Level + 1 >= this.current.unlock)
        {
            this.ui.addObject(this.buy = new UIButton(WeaponShop.main, 858.0f, 422.0f, 0.35, true, Assets.buy, new ClickListener()
            {
                @Override
                public void onClick()
                {
                    if (Game.Money >= WeaponShop.this.current.getCost())
                    {
                        Game.Money -= WeaponShop.this.current.getCost();
                        WeaponShop.this.current.setOwned(true);
                    }
                }
            }));
        }
        else if (this.buy != null && (this.current.isOwned() || Game.Level + 1 < this.current.unlock))
        {
            this.ui.removeObject(this.buy);
            this.buy = null;
        }
        if (this.switch_upgrades)
        {
            this.switch_upgrades = false;
            if (this.damageUpgrade != null)
            {
                this.ui.removeObject(this.damageUpgrade);
                this.damageUpgrade = null;
            }
            if (this.rechargeUpgrade != null)
            {
                this.ui.removeObject(this.rechargeUpgrade);
                this.rechargeUpgrade = null;
            }
            if (this.durationUpgrade != null)
            {
                this.ui.removeObject(this.durationUpgrade);
                this.durationUpgrade = null;
            }
        }
        if (this.current.isOwned())
        {
            if (this.damageUpgrade == null)
            {
                this.ui.addObject(this.damageUpgrade = new UITextButton(WeaponShop.main, "$" + Painter.Format(this.current.upgradeDamageCost(this.current.getDamageLevel() + 1), "#,###") + " | Damage: " + Painter.Format(this.current.upgradeDamageAmount(this.current.getDamageLevel() + 1), "#,###.0#"), 783.0f, 422.0f, Assets.gemBlue, Assets.getFont(20), false, 100.0f, new ClickListener()
                {
                    @Override
                    public void onClick()
                    {
                        final int cost = WeaponShop.this.current.upgradeDamageCost(WeaponShop.this.current.getDamageLevel() + 1);
                        if (Game.Money < cost)
                        {
                            return;
                        }
                        Game.Money -= cost;
                        WeaponShop.this.current.upgradeDamage();
                        if (WeaponShop.this.current.isDamageMax())
                        {
                            WeaponShop.this.damageUpgrade.setString("MAX");
                            WeaponShop.this.damageUpgrade.disabled(true);
                            return;
                        }
                        WeaponShop.this.damageUpgrade.setString("$" + Painter.Format(WeaponShop.this.current.upgradeDamageCost(WeaponShop.this.current.getDamageLevel() + 1), "#,###") + " | Damage: " + Painter.Format(WeaponShop.this.current.upgradeDamageAmount(WeaponShop.this.current.getDamageLevel() + 1), "#,###.0#"));
                    }
                }));
            }
            if (this.rechargeUpgrade == null)
            {
                this.ui.addObject(this.rechargeUpgrade = new UITextButton(WeaponShop.main, "$" + Painter.Format(this.current.upgradeRechargeCost(this.current.getRechargeLevel() + 1), "#,###") + " | Recharge: " + Painter.Format(this.current.upgradeRechargeAmount(this.current.getRechargeLevel() + 1), "#,###.0#") + ((this.current.getRecharge() == Double.POSITIVE_INFINITY) ? "" : "s"), 783.0f, 452.0f, Assets.gemBlue, Assets.getFont(20), false, 100.0f, new ClickListener()
                {
                    @Override
                    public void onClick()
                    {
                        final int cost = WeaponShop.this.current.upgradeRechargeCost(WeaponShop.this.current.getRechargeLevel() + 1);
                        if (Game.Money < cost)
                        {
                            return;
                        }
                        Game.Money -= cost;
                        WeaponShop.this.current.upgradeRecharge();
                        if (WeaponShop.this.current.isRechargeMax())
                        {
                            WeaponShop.this.rechargeUpgrade.setString("MAX");
                            WeaponShop.this.rechargeUpgrade.disabled(true);
                            return;
                        }
                        WeaponShop.this.rechargeUpgrade.setString("$" + Painter.Format(WeaponShop.this.current.upgradeRechargeCost(WeaponShop.this.current.getRechargeLevel() + 1), "#,###") + " | Recharge: " + Painter.Format(WeaponShop.this.current.upgradeRechargeAmount(WeaponShop.this.current.getRechargeLevel() + 1), "#,###.0#") + ((WeaponShop.this.current.getRecharge() == Double.POSITIVE_INFINITY) ? "" : "s"));
                    }
                }));
            }
            if (this.durationUpgrade == null)
            {
                this.ui.addObject(this.durationUpgrade = new UITextButton(WeaponShop.main, "$" + Painter.Format(this.current.upgradeDurationCost(this.current.getDurationLevel() + 1), "#,###") + " | Duration: " + Painter.Format(this.current.upgradeDurationAmount(this.current.getDurationLevel() + 1), "#,###.0#") + ((this.current.getDuration() == Double.POSITIVE_INFINITY) ? "" : "s"), 783.0f, 482.0f, Assets.gemBlue, Assets.getFont(20), false, 100.0f, new ClickListener()
                {
                    @Override
                    public void onClick()
                    {
                        final int cost = WeaponShop.this.current.upgradeDurationCost(WeaponShop.this.current.getDurationLevel() + 1);
                        if (Game.Money < cost)
                        {
                            return;
                        }
                        Game.Money -= cost;
                        WeaponShop.this.current.upgradeDuration();
                        if (WeaponShop.this.current.isDurationMax())
                        {
                            WeaponShop.this.durationUpgrade.setString("MAX");
                            WeaponShop.this.durationUpgrade.disabled(true);
                            return;
                        }
                        WeaponShop.this.durationUpgrade.setString("$" + Painter.Format(WeaponShop.this.current.upgradeDurationCost(WeaponShop.this.current.getDurationLevel() + 1), "#,###") + " | Duration: " + Painter.Format(WeaponShop.this.current.upgradeDurationAmount(WeaponShop.this.current.getDurationLevel() + 1), "#,###.0#") + ((WeaponShop.this.current.getDuration() == Double.POSITIVE_INFINITY) ? "" : "s"));
                    }
                }));
            }
            if (this.current.isDamageMax())
            {
                this.damageUpgrade.setString("MAX");
                this.damageUpgrade.disabled(true);
            }
            if (this.current.isRechargeMax())
            {
                this.rechargeUpgrade.setString("MAX");
                this.rechargeUpgrade.disabled(true);
            }
            if (this.current.isDurationMax())
            {
                this.durationUpgrade.setString("MAX");
                this.durationUpgrade.disabled(true);
            }
        }
        this.ui.update();
    }

    @Override
    public void render(final Graphics2D g)
    {
        Painter.drawString(g, "< Weapons >", 683, 262, Assets.algaeGreen, Assets.getFont(25), 100.0f, true, true);
        if (Game.Level + 1 < this.current.unlock)
        {
            Painter.drawString(g, "???", 683, 312, Assets.gemBlue, Assets.getFont(40), 100.0f, true, true);
            Painter.drawString(g, "Unlock at level " + this.current.unlock, 683, 457, Color.WHITE, Assets.getFont(35), 100.0f, true, true);
        }
        else
        {
            Painter.drawString(g, this.current.getName(), 683, 312, Assets.gemBlue, Assets.getFont(40), 100.0f, true, true);
            Painter.drawString(g, "Damage:  " + this.current.getDamage(), 463, 422, Assets.algaeGreen, Assets.getFont(20), 100.0f, false, true);
            Painter.drawString(g, "Recharge:  " + this.current.getRecharge() + ((this.current.getRecharge() == Double.POSITIVE_INFINITY) ? "" : "s"), 463, 452, Assets.algaeGreen, Assets.getFont(20), 100.0f, false, true);
            Painter.drawString(g, "Bullet Lifetime:  " + this.current.getDuration() + ((this.current.getDuration() == Double.POSITIVE_INFINITY) ? "" : "s"), 463, 482, Assets.algaeGreen, Assets.getFont(20), 100.0f, false, true);
            Painter.drawString(g, "'" + this.current.getDesc() + "'", 463, 522, Assets.algaeGreen, Assets.getFont(25), 100.0f, false, true);
        }
        if (Weapon.getSelected() == this.current)
        {
            Painter.drawString(g, "Equipped!", 683, 622, Assets.gemBlue, Assets.getFont(25), 100.0f, true, true);
        }
        if (!this.current.isOwned() && Game.Level + 1 >= this.current.unlock)
        {
            Painter.drawString(g, "Cost : " + Painter.Format(this.current.getCost(), "$#,###"), 858, 382, Assets.gemBlue, Assets.getFont(20), 100.0f, true, true);
        }
        this.ui.render(g);
    }

    static /* synthetic */ void access$1(final WeaponShop weaponShop, final int index)
    {
        weaponShop.index = index;
    }

    static /* synthetic */ void access$2(final WeaponShop weaponShop, final Weapon current)
    {
        weaponShop.current = current;
    }

    static /* synthetic */ void access$3(final WeaponShop weaponShop, final boolean switch_upgrades)
    {
        weaponShop.switch_upgrades = switch_upgrades;
    }
}
