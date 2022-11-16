
package com.xamser7.lb.entities;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class EntityManager
{
    public ArrayList<ArrayList<?>> a;

    public EntityManager()
    {
        this.a = new ArrayList<ArrayList<?>>();
    }

    public void update()
    {
        for (int i = 0; i < this.a.size(); ++i)
            for (int j = 0; j < this.a.get(i).size(); ++j)
                ((Entity) this.a.get(i).get(j)).update();
    }

    public void render(final Graphics2D g)
    {
        for (int i = 0; i < this.a.size(); ++i)
            for (int j = 0; j < this.a.get(i).size(); ++j)
                ((Entity) this.a.get(i).get(j)).render(g);
    }

    public void add(final ArrayList<?> arrayList)
    {
        this.a.add(arrayList);
    }
}
