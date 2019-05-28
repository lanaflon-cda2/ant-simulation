package com.wilcoln.app;

import com.wilcoln.config.ImmutableConfigManager;
import com.wilcoln.gfx.JavaFXContainer;

import java.io.File;

public class Main {

	

    public static void main(String[] args) {
        Context.initializeApplication(new Application(
                new ImmutableConfigManager(
                        new File(Context.CONFIG_PATH)
                )
        ));

        javafx.application.Application.launch(JavaFXContainer.class, args);
    }
}
