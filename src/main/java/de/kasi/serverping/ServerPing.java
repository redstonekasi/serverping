package de.kasi.serverping;

import de.kasi.serverping.config.ServerPingConfig;
import net.fabricmc.api.DedicatedServerModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerPing implements DedicatedServerModInitializer {

	public static final Logger LOGGER = LogManager.getLogger();

	public void onInitializeServer() {
		ServerPingConfig.load();
	}

}
