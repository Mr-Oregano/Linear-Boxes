
package com.xamser7.lb.states;

import com.xamser7.lb.Game;
import java.awt.Color;
import com.xamser7.lb.skins.Skin;
import com.xamser7.lb.gfx.Painter;
import java.awt.Graphics2D;
import com.xamser7.lb.ui.UIButton;
import com.xamser7.lb.ui.ClickListener;
import com.xamser7.lb.assets.Assets;
import com.xamser7.lb.Main;

public class StatState extends States
{
    public StatState(final Main main)
    {
        super(main);
        this.ui.addObject(new UIButton(main, 68.0f, 52.0f, 0.15, false, Assets.back, new ClickListener()
        {
            @Override
            public void onClick()
            {
                States.setState(States.getPrevState());
            }
        }));
    }

    @Override
    public void update()
    {
        this.ui.update();
    }

    @Override
    public void render(final Graphics2D g)
    {
        Painter.drawBackground(g, StatState.main, Assets.stats_back, 100.0f);
        Painter.drawImage(g, 383.0f, 150.0f, 200, 200, Assets.skins[Skin.selected.getID()], 100.0f, false, true);
        Painter.fillRect(g, 633, 190, 400, 50, Color.GRAY, 25.0f, false, true);
        Painter.fillRect(g, 633, 190, (int) (Game.Exp * 400.0 / Game.MaxExp), 50, Assets.gemBlue, 100.0f, false, true);
        Painter.drawString(g, "Current Exp:", 638, 277, Assets.gemBlue, Assets.getFont(20), 100.0f, false, true);
        Painter.drawString(g, String.valueOf(Painter.Format(Game.Exp, "#,###")) + " / " + Painter.Format(Game.MaxExp, "#,###"), 763, 278, Assets.algaeGreen, Assets.getFont(30), 100.0f, false, true);
        Painter.drawString(g, "Level:", 638, 307, Assets.gemBlue, Assets.getFont(20), 100.0f, false, true);
        Painter.drawString(g, new StringBuilder(String.valueOf(Game.Level + 1)).toString(), 703, 308, Assets.algaeGreen, Assets.getFont(30), 100.0f, false, true);
        Painter.drawString(g, "Highest Score reached:", 100, 420, Assets.gemBlue, Assets.getFont(20), 100.0f, false, true);
        Painter.drawString(g, Painter.Format(Game.HighestScoreReached, "#,###"), 380, 420, Assets.algaeGreen, Assets.getFont(20), 100.0f, false, true);
        Painter.drawString(g, "Longest Time Survived:", 100, 450, Assets.gemBlue, Assets.getFont(20), 100.0f, false, true);
        Painter.drawString(g, String.valueOf(Painter.Format(Game.LongestTimeSurvived, "#,###.##")) + "s", 380, 450, Assets.algaeGreen, Assets.getFont(20), 100.0f, false, true);
        Painter.drawString(g, "Average Score:", 100, 500, Assets.gemBlue, Assets.getFont(20), 100.0f, false, true);
        Painter.drawString(g, Painter.Format(Game.AverageScore, "#,###.##"), 380, 500, Assets.algaeGreen, Assets.getFont(20), 100.0f, false, true);
        Painter.drawString(g, "Average Time Survived:", 100, 530, Assets.gemBlue, Assets.getFont(20), 100.0f, false, true);
        Painter.drawString(g, String.valueOf(Painter.Format(Game.AverageTimeSurvived, "#,###.##")) + "s", 380, 530, Assets.algaeGreen, Assets.getFont(20), 100.0f, false, true);
        Painter.drawString(g, "Weighted Score:", 100, 560, Assets.gemBlue, Assets.getFont(20), 100.0f, false, true);
        Painter.drawString(g, Painter.Format(Math.sqrt(Game.AverageTimeSurvived * Game.AverageScore * Game.TotalGames), "#,###.##"), 380, 560, Assets.algaeGreen, Assets.getFont(20), 100.0f, false, true);
        Painter.drawString(g, "Total Bits Acquired:", 100, 610, Assets.gemBlue, Assets.getFont(20), 100.0f, false, true);
        Painter.drawString(g, Painter.Format(Game.TotalBits, "#,###"), 380, 610, Assets.algaeGreen, Assets.getFont(20), 100.0f, false, true);
        Painter.drawString(g, "Total Enemies Killed:", 100, 640, Assets.gemBlue, Assets.getFont(20), 100.0f, false, true);
        Painter.drawString(g, Painter.Format(Game.TotalEnemiesKilled, "#,###"), 380, 640, Assets.algaeGreen, Assets.getFont(20), 100.0f, false, true);
        Painter.drawString(g, "Total Games Finished:", 100, 670, Assets.gemBlue, Assets.getFont(20), 100.0f, false, true);
        Painter.drawString(g, Painter.Format(Game.TotalGames, "#,###"), 380, 670, Assets.algaeGreen, Assets.getFont(20), 100.0f, false, true);
        Painter.drawString(g, "Cumulative Money Earned:", 100, 700, Assets.gemBlue, Assets.getFont(20), 100.0f, false, true);
        Painter.drawString(g, "$" + Painter.Format((double) Game.CumulativeMoneyEarned, "#,###"), 380, 700, Assets.algaeGreen, Assets.getFont(20), 100.0f, false, true);
        this.ui.render(g);
    }
}
