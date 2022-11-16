
package com.xamser7.lb.states;

import java.awt.Graphics2D;
import java.util.ArrayList;

import com.xamser7.lb.Game;
import com.xamser7.lb.Main;
import com.xamser7.lb.assets.Assets;
import com.xamser7.lb.bits.Bit;
import com.xamser7.lb.entities.EntityManager;
import com.xamser7.lb.entities.Player;
import com.xamser7.lb.entities.enemies.Enemy;
import com.xamser7.lb.gfx.Painter;
import com.xamser7.lb.ui.ClickListener;
import com.xamser7.lb.ui.UIButton;
import com.xamser7.lb.utils.Utils;
import com.xamser7.lb.weapons.Weapon;
import com.xamser7.lb.weapons.bullets.Bullet;

public class GameState extends States
{
    private static GameState currentGame;
    public static ArrayList<Player> p;
    public static ArrayList<Enemy> e;
    public static ArrayList<Bit> b;
    public static ArrayList<Bullet> a;
    private EntityManager entity;
    private int Score;
    private int Money;
    private int spawnInterval;
    private int time;
    private int enemy_cap;

    public GameState(final Main main)
    {
        super(main);
        GameState.currentGame = this;
        
        this.ui.addObject(new UIButton(main, 10.0f, 10.0f, 0.15, false, Assets.pause, new ClickListener()
        {
            @Override
            public void onClick()
            {
                States.setState(new PauseState(main));
            }
        }));

        (this.entity = new EntityManager()).add(GameState.b = new ArrayList<Bit>());
        this.entity.add(GameState.a = new ArrayList<Bullet>());
        this.entity.add(GameState.p = new ArrayList<Player>());
        this.entity.add(GameState.e = new ArrayList<Enemy>());

        Player.newPlayer(main);
        Enemy.newEnemy(3, main);
        
        this.Score = 0;
        this.spawnInterval = this.getSpawnInterval();
        this.enemy_cap = (int) Math.round(Math.pow(Game.Level, 1.4) + 7.0);
        
        main.setFrame(0);
    }

    @Override
    public void update()
    {
        ++this.time;
        if (GameState.p.get(0).active)
        {
            ++this.Score;
            this.Money = (int) (this.Score * Game.MoneyRate);
        }

        if (GameState.e.size() < this.enemy_cap && GameState.main.getFrame() != 0 && GameState.main.getFrame() % (this.spawnInterval * 60) == 0)
        {
            Enemy.newEnemy(1, GameState.main);
            this.spawnInterval = this.getSpawnInterval();
        }

        if (GameState.main.getFrame() != 0 && GameState.main.getFrame() % 240 == 0)
            Bit.newBit(1, GameState.main);

        if (GameState.p.get(0).active && GameState.p.get(0).fire && GameState.main.getKeyManager().space)
        {
            GameState.p.get(0).coolDown = 0.0;
            Weapon.getSelected().fire();
        }

        if (GameState.main.getKeyManager().esc && GameState.main.getKeyManager().lock)
        {
            GameState.main.getKeyManager().lock = false;
            States.setState(new PauseState(GameState.main));
        }

        this.ui.update();
        this.entity.update();
    }

    @Override
    public void render(final Graphics2D g)
    {
        this.entity.render(g);
        Painter.fillRect(g, 65, 20, (int) (GameState.p.get(0).coolDown * (1244.0 / (Weapon.getSelected().getRecharge() * 60.0))), 10, (GameState.p.get(0).coolDown < Weapon.getSelected().getRecharge() * 60.0) ? Assets.gemBlue : Assets.algaeGreen, 50.0f, false, true);
        Painter.drawString(g, "Money: " + Painter.Format(this.Money, "$#,###"), 10, 725, Assets.gemBlue, Assets.getFont(15), 100.0f, false, true);
        Painter.drawString(g, "Score: " + Painter.Format(this.Score, "#,###"), 10, 755, Assets.gemBlue, Assets.getFont(30), 100.0f, false, true);
        this.ui.render(g);
    }

    public int getScore()
    {
        return this.Score;
    }

    public int getMoney()
    {
        return this.Money;
    }

    public int getExp()
    {
        return (int) (this.Score * Game.ExpRate);
    }

    public float getTime()
    {
        return this.time / 60.0f;
    }

    public void addScore(final int s)
    {
        this.Score += s;
    }

    public static GameState getGame()
    {
        return GameState.currentGame;
    }

    private int getSpawnInterval()
    {
        return (int) Math.max(Math.round(4096.0 / ((Utils.RANDOM.nextInt(5) + 6) * (Game.Level + 64) * (this.getTime() / 60.0 + 1.0))), 1L);
    }
}
