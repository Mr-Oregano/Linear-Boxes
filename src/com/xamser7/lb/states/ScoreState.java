
package com.xamser7.lb.states;

import java.awt.Color;
import java.awt.Graphics2D;

import com.xamser7.lb.Game;
import com.xamser7.lb.Main;
import com.xamser7.lb.assets.Assets;
import com.xamser7.lb.gfx.Painter;
import com.xamser7.lb.ui.ClickListener;
import com.xamser7.lb.ui.UIButton;

public class ScoreState extends States
{
    private static int Exp;
    private boolean NewScoreRecord;
    private boolean NewTimeRecord;
    private int score;
    private int money;
    private float time;
    private int exp_earned;

    public ScoreState(final Main main)
    {
        super(main);
        
        this.ui.addObject(new UIButton(main, 683.0f, 382.0f, 0.5, true, Assets.shop, new ClickListener()
        {
            @Override
            public void onClick()
            {
                States.setState(new ShopState(main));
            }
        }));
        
        this.ui.addObject(new UIButton(main, 683.0f, 434.0f, 0.5, true, Assets.newgame, new ClickListener()
        {
            @Override
            public void onClick()
            {
                States.setState(new GameState(main));
            }
        }));
        
        this.ui.addObject(new UIButton(main, 683.0f, 486.0f, 0.5, true, Assets.stats, new ClickListener()
        {
            @Override
            public void onClick()
            {
                States.setState(new StatState(main));
            }
        }));

        this.ui.addObject(new UIButton(main, 683.0f, 538.0f, 0.5, true, Assets.mainmenu, new ClickListener()
        {
            @Override
            public void onClick()
            {
                States.setState(new MenuState(main));
            }
        }));
        
        this.score = GameState.getGame().getScore();
        this.money = GameState.getGame().getMoney();
        this.time = GameState.getGame().getTime();
        this.exp_earned = GameState.getGame().getExp();
        ScoreState.Exp = GameState.getGame().getExp();
        Game.Money += this.money;
        
        while (Game.Exp + ScoreState.Exp >= Game.MaxExp)
        {
            ScoreState.Exp = Math.abs(Game.MaxExp - (Game.Exp + this.exp_earned));
            Game.nextLevel();
        }
        
        Game.Exp += ScoreState.Exp;
        ++Game.TotalGames;
        Game.TotalScore += this.score;
        Game.TotalTimeSurvived += this.time;
        Game.AverageScore = Game.TotalScore / (float) Game.TotalGames;
        Game.AverageTimeSurvived = Game.TotalTimeSurvived / Game.TotalGames;
        Game.CumulativeMoneyEarned += this.money;
        
        if (this.score > Game.HighestScoreReached)
        {
            Game.HighestScoreReached = this.score;
            this.NewScoreRecord = true;
        }

        if (this.time > Game.LongestTimeSurvived)
        {
            Game.LongestTimeSurvived = this.time;
            this.NewTimeRecord = true;
        }
    }

    @Override
    public void update()
    {
        this.ui.update();
    }

    @Override
    public void render(final Graphics2D g)
    {
        GameState.getGame().render(g);
        Painter.fillRect(g, 0, 0, Main.GAME_WIDTH, Main.GAME_HEIGHT, Color.BLACK, 75.0f, false, true);
        Painter.drawString(g, "Exp +" + this.exp_earned, 683, 129, Assets.algaeGreen, Assets.getFont(20), 100.0f, true, true);
        Painter.drawString(g, "Money: " + Painter.Format(this.money, "$#,###"), 683, 159, Assets.gemBlue, Assets.getFont(30), 100.0f, true, true);
        Painter.drawString(g, "Score: " + Painter.Format(this.score, "#,###"), 683, 220, Assets.gemBlue, Assets.getFont(120), 100.0f, true, true);
        Painter.drawString(g, "Time Survived: " + Painter.Format(this.time, "#,###.##") + "s", 683, 314, Assets.gemBlue, Assets.getFont(30), 100.0f, true, true);

        if (this.NewScoreRecord)
        {
            final int scorelength = Painter.getTextWidth(ScoreState.main, "Score: " + Painter.Format(this.score, "#,###"), Assets.getFont(120));
            Painter.drawString(g, "RECORD!", (Main.GAME_WIDTH + scorelength - Painter.getTextWidth(ScoreState.main, Painter.Format(this.score, "#,###"), Assets.getFont(120))) / 2, 170, Color.RED, Assets.getFontBold(20), 100.0f, true, true);
        }

        if (this.NewTimeRecord)
        {
            final int timelength = Painter.getTextWidth(ScoreState.main, "Time Survived: " + Painter.Format(this.time, "#,###.##") + "s", Assets.getFont(30));
            Painter.drawString(g, "RECORD!", (Main.GAME_WIDTH + timelength - Painter.getTextWidth(ScoreState.main, String.valueOf(Painter.Format(this.time, "#,###.##")) + "s", Assets.getFont(30))) / 2, 290, Color.RED, Assets.getFontBold(13), 100.0f, true, true);
        }

        this.ui.render(g);
    }
}
