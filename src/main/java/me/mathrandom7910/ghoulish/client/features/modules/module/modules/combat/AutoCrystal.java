package me.mathrandom7910.ghoulish.client.features.modules.module.modules.combat;

import me.mathrandom7910.ghoulish.client.event.sub.SubData;
import me.mathrandom7910.ghoulish.client.event.sub.interfaces.ISubTick;
import me.mathrandom7910.ghoulish.client.features.modules.Category;
import me.mathrandom7910.ghoulish.client.features.modules.module.Module;
import me.mathrandom7910.ghoulish.client.features.modules.settings.settings.BoolSetting;
import me.mathrandom7910.ghoulish.client.features.modules.settings.settings.num.nums.IntSetting;
import me.mathrandom7910.ghoulish.client.features.modules.settings.settings.num.nums.floatingpoint.floatingpoint.DoubleSetting;
import me.mathrandom7910.ghoulish.client.util.CrystalUtil;
import me.mathrandom7910.ghoulish.client.util.InvUtil;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class AutoCrystal extends Module implements ISubTick {
    private final IntSetting clicksAfter = addInt("clicksafter", "amount of clicks after a crystal is destroyed to well... click", 0, 0, 20);
    private final IntSetting clickDelay = addInt("clickdelay", "tick delay between clicks", 0, 0, 10);
    private final BoolSetting breakCrystals = addBool("break", "auto breaks crystals", true);
    private final DoubleSetting maxDistBreak = addDub("maxdistbreak", "maximum distance to break crystals", 3, 1, 6);
    private final BoolSetting placeCrystals = addBool("place", "auto places crystals", true);
    private final DoubleSetting maxDistPlace = addDub("maxdistplace", "maximum distance to place crystals", 3, 1, 6);
    private final BoolSetting topOnly = addBool("toponly", "only places crystals when looking at the top of blocks", true);

    private int tick;
    private int clicksAfterLeft;

    public AutoCrystal() {
        super("autocrystal", "automatically places/breaks crystals", Category.COMBAT);
    }

    @Override
    public void onTick(SubData<ClientWorld, Void> subData) {
        tick++;
        if (!(tick > clickDelay.get())) {
            return;
        }
        tick = 0;

        if(clicksAfterLeft > 0) {
            clicksAfterLeft--;
            KeyBinding.onKeyPressed(mc.options.attackKey.getDefaultKey());
            return;
        }

        placeCrys();
        breakCrys();
    }

    private void placeCrys() {
        if(!placeCrystals.get()) return;

        var blHitRes = (BlockHitResult) mc.player.raycast(maxDistPlace.get(), 1, true);
        if((blHitRes.getSide().equals(Direction.UP) || !topOnly.get()) && CrystalUtil.canPlaceCrystal(blHitRes.getBlockPos())) {
            int crystalSlot = InvUtil.findItemInHotbar(Items.END_CRYSTAL);
            if(crystalSlot == -1) {
                return;
            }

            if(mc.player.getInventory().selectedSlot != crystalSlot) {
                mc.player.getInventory().selectedSlot = crystalSlot;
                return;
            }

            mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, blHitRes);
            mc.player.swingHand(Hand.MAIN_HAND);
        }
    }

    private void breakCrys() {
        if(!breakCrystals.get()) return;

        Vec3d vec = mc.player.getRotationVector().normalize();

        for (double i = 0; i < maxDistBreak.get(); i++) {

            var box = new Box(mc.player.getX() + (vec.x * i), mc.player.getEyeY() + (vec.y * i), mc.player.getZ() + (vec.z * i), mc.player.getX() + (vec.x * i), mc.player.getEyeY() + (vec.y * i), mc.player.getZ() + (vec.z * i));

            List<EndCrystalEntity> crystals = mc.world.getEntitiesByClass(EndCrystalEntity.class, box, (e) -> true);

            if (crystals.size() > 0) {
                mc.player.swingHand(Hand.MAIN_HAND);
                mc.interactionManager.attackEntity(mc.player, crystals.get(0));
                clicksAfterLeft = clicksAfter.get();
                break;
            }
        }
    }
}
