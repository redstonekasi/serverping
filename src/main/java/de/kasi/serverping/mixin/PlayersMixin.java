package de.kasi.serverping.mixin;

import com.mojang.authlib.GameProfile;
import de.kasi.serverping.config.ServerPingConfig;
import net.minecraft.server.ServerMetadata;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(ServerMetadata.Players.class)
public class PlayersMixin {

    @Inject(method = "getPlayerLimit()I", at = @At("HEAD"), cancellable = true)
    private void getPlayerLimit(CallbackInfoReturnable<Integer> ci) {
        if (ServerPingConfig.playerCountEnabled.getValue()) {
            ci.setReturnValue(ServerPingConfig.playerCountValueMax.getValue());
        }
    }

    @Inject(method = "getOnlinePlayerCount()I", at = @At("HEAD"), cancellable = true)
    private void getOnlinePlayerCount(CallbackInfoReturnable<Integer> ci) {
        if (ServerPingConfig.playerCountEnabled.getValue()) {
            ci.setReturnValue(ServerPingConfig.playerCountValue.getValue());
        }
    }

    @Inject(method = "getSample()[Lcom/mojang/authlib/GameProfile;", at = @At("HEAD"), cancellable = true)
    private void getSample(CallbackInfoReturnable<GameProfile[]> ci) {
        if (ServerPingConfig.playerListEnabled.getValue()) {
            String[] nameList = ServerPingConfig.playerListValue.getValue();
            GameProfile[] profileList = new GameProfile[nameList.length];

            for(int i = 0; i < profileList.length; i++)
                profileList[i] = new GameProfile(UUID.randomUUID(), nameList[i]);

            ci.setReturnValue(profileList);
        }
    }
}
