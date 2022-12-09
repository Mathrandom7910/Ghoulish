package me.mathrandom7910.ghoulish.client.features.modules.module.modules.misc;

import com.mojang.authlib.GameProfile;
import me.mathrandom7910.ghoulish.client.features.modules.Category;
import me.mathrandom7910.ghoulish.client.features.modules.module.Module;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class FakePlayer extends Module {
    public FakePlayer() {
        super("fakeplayer", "spawns a fake player :blush:", Category.MISC);
    }

    private @Nullable OtherClientPlayerEntity oPlayer;

    @Override
    public void onEnable() {
        if(mc.world == null) disable();
        oPlayer = new OtherClientPlayerEntity(mc.world, new GameProfile(UUID.randomUUID(), "Fake.Player"), null);
        oPlayer.copyFrom(mc.player);
        oPlayer.setInvulnerable(true);
        mc.world.addEntity(507189, oPlayer);
    }

    @Override
    public void onDisable() {
        if(oPlayer == null) return;
        mc.world.removeEntity(507189, Entity.RemovalReason.DISCARDED);
        oPlayer = null;
    }
}
