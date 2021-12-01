package twilightforest.network;

import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import twilightforest.TwilightForestMod;
import twilightforest.client.TwilightForestRenderInfo;
import twilightforest.client.renderer.TFWeatherRenderer;
import twilightforest.lib.BasePacket;

import java.util.function.Supplier;

public class StructureProtectionPacket implements BasePacket<StructureProtectionPacket> {

	private final BoundingBox sbb;

	public StructureProtectionPacket(BoundingBox sbb) {
		this.sbb = sbb;
	}

	public StructureProtectionPacket(FriendlyByteBuf buf) {
		sbb = new BoundingBox(
				buf.readInt(), buf.readInt(), buf.readInt(),
				buf.readInt(), buf.readInt(), buf.readInt()
		);
	}

	public void encode(FriendlyByteBuf buf) {
		buf.writeInt(sbb.minX());
		buf.writeInt(sbb.minY());
		buf.writeInt(sbb.minZ());
		buf.writeInt(sbb.maxX());
		buf.writeInt(sbb.maxY());
		buf.writeInt(sbb.maxZ());
	}

	@Override
	public void handle(StructureProtectionPacket packet, Context context) {
		Handler.onMessage(packet, context);
	}

	public static class Handler {
		public static boolean onMessage(StructureProtectionPacket message, Supplier<Context> ctx) {
			ctx.get().enqueueWork(() -> {
				DimensionSpecialEffects info = DimensionSpecialEffects.EFFECTS.get(TwilightForestMod.prefix("renderer"));

				// add weather box if needed
				if (info instanceof TwilightForestRenderInfo tfInfo) {
					TFWeatherRenderer weatherRenderer = tfInfo.getWeatherRenderHandler();

					if (weatherRenderer != null) {
						weatherRenderer.setProtectedBox(message.sbb);
					}
				}
			});

			return true;
		}
	}
}
