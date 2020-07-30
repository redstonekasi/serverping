package de.kasi.serverping.config;

import io.github.fablabsmc.fablabs.api.fiber.v1.exception.FiberException;
import io.github.fablabsmc.fablabs.api.fiber.v1.schema.type.derived.ConfigTypes;
import io.github.fablabsmc.fablabs.api.fiber.v1.serialization.FiberSerialization;
import io.github.fablabsmc.fablabs.api.fiber.v1.serialization.JanksonValueSerializer;
import io.github.fablabsmc.fablabs.api.fiber.v1.tree.ConfigTree;
import io.github.fablabsmc.fablabs.api.fiber.v1.tree.PropertyMirror;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ServerPingConfig {

    public static final Path path = FabricLoader.getInstance().getConfigDir().resolve("serverping.json5");

    public static final PropertyMirror<Boolean> playerListEnabled = PropertyMirror.create(ConfigTypes.BOOLEAN);
    public static final PropertyMirror<String[]> playerListValue = PropertyMirror.create(ConfigTypes.makeArray(ConfigTypes.STRING));

    public static final PropertyMirror<Boolean> playerCountEnabled = PropertyMirror.create(ConfigTypes.BOOLEAN);
    public static final PropertyMirror<Integer> playerCountValue = PropertyMirror.create(ConfigTypes.INTEGER);
    public static final PropertyMirror<Integer> playerCountValueMax = PropertyMirror.create(ConfigTypes.INTEGER);

    public static final ConfigTree tree = ConfigTree.builder()
            .fork("player_list")
                .withMirroredValue("enabled", playerListEnabled, false)
                .withMirroredValue("value", playerListValue, new String[] { "Fake Player", "Some other dude" })
                .finishBranch()
            .fork("player_count")
                .withMirroredValue("enabled", playerCountEnabled, false)
                .fork("value")
                    .withMirroredValue("count", playerCountValue, 1)
                    .withMirroredValue("max", playerCountValueMax, 12)
                    .finishBranch()
                .finishBranch()
            .build();

    private static final JanksonValueSerializer serializer = new JanksonValueSerializer(false);

    public static void load() {
        if(Files.exists(path)) {
            try {
                FiberSerialization.deserialize(tree, Files.newInputStream(path), serializer);
            } catch (IOException | FiberException e) {
                save();
            }
        } else
            save();
    }

    public static void save() {
        try {
            FiberSerialization.serialize(tree, Files.newOutputStream(path), serializer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
