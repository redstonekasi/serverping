package de.kasi.serverping.mixin;

import com.mojang.authlib.GameProfile;
import de.kasi.serverping.config.ServerPingConfig;
import net.minecraft.network.packet.s2c.query.QueryResponseS2CPacket;
import net.minecraft.server.ServerMetadata;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(QueryResponseS2CPacket.class)
public class QueryResponseMixin {

    @Inject(method = "<init>(Lnet/minecraft/server/ServerMetadata;)V", at = @At("TAIL"))
    private void onInit(ServerMetadata metadata, CallbackInfo ci) {
        ServerMetadata.Players players;

        if(ServerPingConfig.playerCountEnabled.getValue()) {
            players = new ServerMetadata.Players(
                    ServerPingConfig.playerCountValueMax.getValue(),
                    ServerPingConfig.playerCountValue.getValue()
            );
            players.setSample(metadata.getPlayers().getSample());
        } else
            players = metadata.getPlayers();

        if(ServerPingConfig.playerListEnabled.getValue()) {
            String[] nameList = ServerPingConfig.playerListValue.getValue();
            GameProfile[] profileList = new GameProfile[nameList.length];

            for(int i = 0; i < profileList.length; i++)
                profileList[i] = new GameProfile(UUID.randomUUID(), nameList[i]);

            players.setSample(profileList);
        }

        metadata.setPlayers(players);
    }
}
