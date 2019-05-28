package com.wilcoln.gfx;

import com.wilcoln.Anthill;
import javafx.scene.image.Image;

import static com.wilcoln.gfx.GFXUtil.loadSprite;

final class AnthillRenderer implements SimpleEntitySpriteRenderer<Anthill> {
    private static final double SIZE_X = 100;
    private static final double SIZE_Y = 100;
    private final Image sprite;

    AnthillRenderer() {
        sprite = loadSprite(GFXUtil.RES_PATH+"anthill.png");
    }

    @Override
    public void render(JavaFXAntSimulationCanvas canvas, Anthill entity) {
    	displayEntityInfo(canvas, entity);
        drawSingleSprite(canvas, entity);
    }

    @Override
    public double getWidth(Anthill entity) {
        return SIZE_X;
    }

    @Override
    public double getHeight(Anthill entity) {
        return SIZE_Y;
    }

    @Override
    public double getDirection(Anthill entity) {
        return entity.hashCode();
    }

    @Override
    public Image getSprite(Anthill entity) {
        return sprite;
    }
}
