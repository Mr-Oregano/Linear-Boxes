package com.xamser7.lb.weapons.bullets;

import com.xamser7.lb.Main;
import com.xamser7.lb.states.GameState;

public class BuhgzisBullet extends SimpleBullet
{
    private static final int SPAWN_CAPACITY = 10000;
    private static int spawnCount = 0;

    private boolean spawnNextFrame;
    private int nextSpawnFrame;

    public BuhgzisBullet(Main main, float x, float y, int width, int height, float angle, float speed, float alpha, double damage, double duration)
    {
        super(main, x, y, width, height, angle, speed, alpha, damage, duration);
    }

    @Override
    public void update()
    {
        if (spawnNextFrame && main.getFrame() >= nextSpawnFrame)
        {
            float direction = (float) Math.toDegrees(Math.atan2(Dy, Dx)) + 200f;
            GameState.a.add(new BuhgzisBullet(this.main, x, y, 10, 10, direction, 1f, 100.0f, this.damage, this.duration));
            spawnNextFrame = false;
        }

        super.update();
    }

    @Override
    public void onCollision()
    {
        if (spawnCount >= SPAWN_CAPACITY)
            return;
            
        spawnNextFrame = true;
        nextSpawnFrame = main.getFrame() + 2;
        ++spawnCount;
    }
}
