package me.mathrandom7910.ghoulish.client.util;

import me.mathrandom7910.ghoulish.client.misc.MCInst;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class InvUtil implements MCInst {

    public static ItemStack getStack(int slot) {
        return mc.player.getInventory().getStack(slot);
    }

    public static Item getItem(int slot) {
        return getStack(slot).getItem();
    }

    private static int searchForItem(Item item, int start, int lessThan) {
        for(int i = start; i < lessThan; i++) {
            if(getItem(i).equals(item)) {
                return i;
            }
        }
        return -1;
    }

    public static int findItemInHotbar(Item item) {
        return searchForItem(item, 0, 9);
    }

    public static ItemStack offHandStack() {
        return mc.player.getOffHandStack();
    }

    public static Item offHand() {
        return offHandStack().getItem();
    }
}
