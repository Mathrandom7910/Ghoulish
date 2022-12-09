package me.mathrandom7910.ghoulish.client.features.modules.module.modules.combat;

import me.mathrandom7910.ghoulish.client.event.sub.SubData;
import me.mathrandom7910.ghoulish.client.event.sub.interfaces.ISubTick;
import me.mathrandom7910.ghoulish.client.features.modules.Category;
import me.mathrandom7910.ghoulish.client.features.modules.module.Module;
import me.mathrandom7910.ghoulish.client.features.modules.settings.Setting;
import me.mathrandom7910.ghoulish.client.features.modules.settings.settings.EnumSetting;
import me.mathrandom7910.ghoulish.client.features.modules.settings.settings.num.nums.IntSetting;
import me.mathrandom7910.ghoulish.client.features.modules.settings.settings.num.nums.floatingpoint.floatingpoint.FloatSetting;
import me.mathrandom7910.ghoulish.client.misc.Rot;
import me.mathrandom7910.ghoulish.client.misc.ThreadHolder;
import me.mathrandom7910.ghoulish.client.util.EntityUtil;
import me.mathrandom7910.ghoulish.client.util.MathUtil;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;

public class AimAssist extends Module implements ISubTick {
    private final FloatSetting distance = addFloat("distance", "distance to begin aiming", 6, 3, 10);
    private final IntSetting interval = addInt("interval", "interval between rotation updates", 10, 0, 100);
    private final EnumSetting<RotMode> rotMode = addEnum("rotation", "the mode for rotations", RotMode.EASE);
    private final Setting<Float> strength = addFloat("strength", "lerp percentage", 0.1F, 0, 1).requires(rotMode, RotMode.EASE);

    public AimAssist() {
        super("aimassist", "makes you aim better", Category.COMBAT);
    }

    private final ThreadHolder th =
            createInterval(() -> {
                PlayerEntity player = EntityUtil.findClosest(PlayerEntity.class, distance.get());

                if(player == null) {
                    return;
                }

                Rot targetRot = MathUtil.getDir(mc.player, player.getPos());

                if(rotMode.get().equals(RotMode.EASE)) {
                    float yaw = MathHelper.lerpAngleDegrees(strength.get(), mc.player.getYaw(), (float) targetRot.yaw());
                    float pitch = MathHelper.lerpAngleDegrees(strength.get(), mc.player.getPitch(), (float) targetRot.pitch());

                    mc.player.setYaw(yaw);
                    mc.player.setPitch(pitch);
                } else {
                    rotMode.set(RotMode.EASE);
                }
            }, interval.get());

    @Override
    public void onTick(SubData<ClientWorld, Void> subData) {
        th.setInt(interval.get());
    }

    private enum RotMode {
        EASE,
        NORMAL
    }
}
