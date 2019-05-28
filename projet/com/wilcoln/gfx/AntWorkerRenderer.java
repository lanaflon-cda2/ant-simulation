package com.wilcoln.gfx;

import com.wilcoln.AntWorker;
import javafx.scene.image.Image;

final class AntWorkerRenderer implements SimpleEntitySpriteRenderer<AntWorker> {
    private static final double SIZE_X = 50;
    private static final double SIZE_Y = 50;
    private final Image sprite;

    AntWorkerRenderer() {
        sprite = GFXUtil.loadSprite(GFXUtil.RES_PATH+"fourmi_noire.png");
    }

    @Override
    public void render(JavaFXAntSimulationCanvas canvas, AntWorker entity) {
    	displayEntityInfo(canvas, entity);
        drawSingleSprite(canvas, entity);
    }

    @Override
    public double getWidth(AntWorker entity) {
        return SIZE_X;
    }

    @Override
    public double getHeight(AntWorker entity) {
        return SIZE_Y;
    }

    @Override
    public double getDirection(AntWorker entity) {
        return entity.getDirection();
    }

    @Override
    public Image getSprite(AntWorker entity) {
        return sprite;
    }
}
