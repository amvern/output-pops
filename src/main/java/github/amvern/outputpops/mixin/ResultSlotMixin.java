package github.amvern.outputpops.mixin;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.FurnaceResultSlot;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ResultSlot.class, FurnaceResultSlot.class})
public abstract class ResultSlotMixin {

	@Inject(method = "onTake", at = @At("TAIL"))
	private void outputpops$playServerResultSlotSound(Player player, ItemStack stack, CallbackInfo ci) {
		if (player instanceof ServerPlayer serverPlayer) {
			serverPlayer.connection.send(
				new ClientboundSoundPacket(
					BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.ITEM_PICKUP),
					SoundSource.PLAYERS,
					serverPlayer.getX(),
					serverPlayer.getY(),
					serverPlayer.getZ(),
					0.2F,
					(serverPlayer.getRandom().nextFloat() - serverPlayer.getRandom().nextFloat()) * 1.4F + 2.0F,
					serverPlayer.getRandom().nextLong()
				)
			);
		}
	}
}