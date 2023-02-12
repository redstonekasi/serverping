package de.kasi.serverping;

import de.kasi.serverping.config.ServerPingConfig;
import net.fabricmc.api.DedicatedServerModInitializer;

public class ServerPing implements DedicatedServerModInitializer {
    public void onInitializeServer() {
        ServerPingConfig.load();
    }
}
