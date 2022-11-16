
package com.xamser7.lb.entities;

import com.xamser7.lb.states.GameState;
import com.xamser7.lb.Game;
import com.xamser7.lb.skins.Skin;
import com.xamser7.lb.assets.Assets;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import com.xamser7.lb.states.States;
import com.xamser7.lb.states.ScoreState;
import com.xamser7.lb.weapons.Weapon;
import com.xamser7.lb.Main;

public class Player extends Entity
{
    public double coolDown;
    public boolean fire;

    public Player(final Main main, final float x, final float y, final int width, final int height, final float angle, final float speed, final float alpha)
    {
        super(main, x, y, width, height, angle, speed, alpha);
        this.coolDown = Weapon.getSelected().getRecharge() * 60.0;
        this.fire = true;
    }

    @Override
    public void update()
    {
        if (this.active)
        {
            this.updatePosition();
        }
        else
        {
            this.alpha -= 2.0f;
            if (this.alpha <= 0.0f)
            {
                States.setState(new ScoreState(this.main));
            }
        }
    }

    public void updatePosition()
    {
        if (this.main.getKeyManager().up)
        {
            this.y -= this.Dy;
        }
        if (this.main.getKeyManager().down)
        {
            this.y += this.Dy;
        }
        if (this.main.getKeyManager().left)
        {
            this.x -= this.Dx;
        }
        if (this.main.getKeyManager().right)
        {
            this.x += this.Dx;
        }
        this.x = Math.max(Math.min((float) (this.main.getWidth() - this.width), this.x), 0.0f);
        this.y = Math.max(Math.min((float) (this.main.getHeight() - this.height), this.y), 0.0f);
        if (this.coolDown < Weapon.getSelected().getRecharge() * 60.0)
        {
            ++this.coolDown;
            this.fire = false;
        }
        else
        {
            this.fire = true;
        }
    }

    @Override
    public void render(final Graphics2D g)
    {
        g.setComposite(AlphaComposite.getInstance(3, this.alpha / 100.0f));
        g.drawImage(Assets.skins[Skin.selected.getID()], (int) this.x, (int) this.y, this.width, this.height, null);
    }

    public static void newPlayer(final Main main)
    {
        final float x = 683.0f;
        final float y = 382.0f;
        final int height;
        final int width = height = 75;
        final float angle = 45.0f;
        final float speed = (float) Game.PlayerSpeed;
        final float alpha = 100.0f;
        GameState.p.add(new Player(main, x, y, width, height, angle, speed, alpha));
    }
}
