package twilightforest.potions;

import net.minecraft.core.Registry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import twilightforest.lib.RegistryObject;
import twilightforest.TwilightForestMod;
import twilightforest.lib.TFRegistry;

public class TFMobEffects {

	public static final TFRegistry<MobEffect> MOB_EFFECTS = TFRegistry.create(Registry.MOB_EFFECT, TwilightForestMod.ID);

	public static final RegistryObject<MobEffect> FROSTY = MOB_EFFECTS.register("frosted", () -> new FrostedEffect(MobEffectCategory.HARMFUL, 0x56CBFD));
}
