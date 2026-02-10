package github.amvern.outputpops.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.FurnaceResultSlot;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ResultSlot.class, FurnaceResultSlot.class})
public abstract class ResultSlotClientMixin {

    @Inject(method = "onTake", at = @At("TAIL"))
    private void outputpops$playClientResultSlotSound(Player player, ItemStack stack, CallbackInfo ci) {
        if (!player.level().isClientSide()) return;

        Minecraft mc = Minecraft.getInstance();
        if (player != mc.player) return;

        if (mc.hasSingleplayerServer()) return;

        Level level = player.level();
        RandomSource random = level.getRandom();

        level.playLocalSound(
            player.getX(),
            player.getY(),
            player.getZ(),
            SoundEvents.ITEM_PICKUP,
            SoundSource.PLAYERS,
            0.2F,
            (random.nextFloat() - random.nextFloat()) * 1.4F + 2.0F,
            false
        );
    }

}