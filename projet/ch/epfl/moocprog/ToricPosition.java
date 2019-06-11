package ch.epfl.moocprog;

import ch.epfl.moocprog.app.Context;
import ch.epfl.moocprog.config.Config;
import ch.epfl.moocprog.utils.Vec2d;

public final class ToricPosition {
    
    private final Vec2d position;
    
    public ToricPosition() {
        position = new Vec2d(0,0);
    }
    public ToricPosition(Vec2d coords) {
        position = clampedPosition(coords.getX(), coords.getY());
    }
    
    public ToricPosition(double x, double y) {
        position = clampedPosition(x,y);
    }
    private static Vec2d clampedPosition(double x, double y) {
        int REFX = Context.getConfig().getInt(Config.WORLD_WIDTH);
        int REFY = Context.getConfig().getInt(Config.WORLD_HEIGHT);
        return new Vec2d(project(x, REFX), project(y, REFY));
    }
    public ToricPosition add(ToricPosition that) {
        double sumX = this.position.getX() + that.position.getX();
        double sumY = this.position.getY() + that.position.getY();
        return new ToricPosition(clampedPosition(sumX, sumY));
    }
    
    public ToricPosition add(Vec2d vec){
        return add(new ToricPosition(vec));
    }
    
    public Vec2d toVec2d() {
        return position;
    }
    public Vec2d toricVector(ToricPosition that) {
        ToricPosition possibilities[] = new ToricPosition[9];
        possibilities[0] = that;
        possibilities[1] = that.add(new Vec2d(0, Context.getConfig().getInt(Config.WORLD_HEIGHT)));
        possibilities[2] = that.add(new Vec2d(Context.getConfig().getInt(Config.WORLD_WIDTH), 0));
        possibilities[3] = that.add(new Vec2d(Context.getConfig().getInt(Config.WORLD_WIDTH), Context.getConfig().getInt(Config.WORLD_HEIGHT)));
        possibilities[4] = that.add(new Vec2d(-Context.getConfig().getInt(Config.WORLD_WIDTH), Context.getConfig().getInt(Config.WORLD_HEIGHT)));
        possibilities[5] = that.add(new Vec2d(Context.getConfig().getInt(Config.WORLD_WIDTH), -Context.getConfig().getInt(Config.WORLD_HEIGHT)));
        possibilities[6] = that.add(new Vec2d(-Context.getConfig().getInt(Config.WORLD_WIDTH), -Context.getConfig().getInt(Config.WORLD_HEIGHT)));
        possibilities[7] = that.add(new Vec2d(-Context.getConfig().getInt(Config.WORLD_WIDTH), 0));
        possibilities[8] = that.add(new Vec2d(0, -Context.getConfig().getInt(Config.WORLD_HEIGHT)));
        
        Vec2d smallestVect = new Vec2d(possibilities[0].toVec2d().getX() - position.getX(), possibilities[0].toVec2d().getY() - position.getY());
        double minDistance = smallestVect.length();
        double currentDistance = minDistance;
        for(ToricPosition tp : possibilities) {
            currentDistance = position.distance(tp.toVec2d());
            if(currentDistance < minDistance) {
                minDistance = currentDistance;
                smallestVect = new Vec2d(tp.toVec2d().getX() - position.getX(), tp.toVec2d().getY() - position.getY());
            }
        }
        
        return smallestVect;
    }
    
    public double toricDistance(ToricPosition that) {
        return this.toricVector(that).length();
    }
    public String toString() {
        return position.toString();
    }
    private static double project(double x, double REF) {
        double px = x;
        while(px < 0) {
            px+= REF;
        }
        while(px >= REF) {
            px-= REF;
        }
        return px;
    }

}
