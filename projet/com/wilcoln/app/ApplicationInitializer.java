package com.wilcoln.app;

import com.wilcoln.config.ConfigManager;

public final class ApplicationInitializer {

    private ApplicationInitializer() {}

    public static void initializeApplication(ConfigManager configManager) {
        Context.initializeApplication(new Application(configManager));
    }
}
