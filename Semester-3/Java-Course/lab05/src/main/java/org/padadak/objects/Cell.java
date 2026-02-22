package org.padadak.objects;

public class Cell {
    private Figure figure;
    private boolean treasure;
    private boolean shoot;
    private boolean dead;
    private boolean creator;


    public Figure getFigure() {
        return figure;
    }

    public void setFigure(Figure figure) {
        this.figure = figure;
    }

    public boolean getTreasure() {
        return treasure;
    }

    public void setTreasure(boolean treasure) {
        this.treasure = treasure;
    }

    public void setShoot(boolean shoot)
    {
        this.shoot = shoot;
    }

    public boolean getShoot()
    {
        return this.shoot;
    }

    public void setDead(boolean dead)
    {
        this.dead = dead;
    }

    public boolean getDead()
    {
        return this.dead;
    }

    public void setCreator(boolean creator)
    {
        this.creator = creator;
    }

    public boolean getCreator(){
        return this.creator;
    }
}
