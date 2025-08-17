package ${package}.mixins;


@Mixin(RandomState.class)
public class NoiseConfigMixin {
	@Shadow
	@Final
	private Climate.Sampler sampler;

    @Inject(method = "<init>", at = @At("TAIL"), require = 0)

    private void init(NoiseGeneratorSettings chunkGeneratorSettings, HolderGetter<NormalNoise.NoiseParameters> arg, long seed, CallbackInfo ci) {
		((SamplerHooks) (Object) sampler).setSeed(seed);
	}
    private void initSafe(NoiseGeneratorSettings settings, NoiseRouter noiseRouter, long seed, CallbackInfo ci) {
        try {
            Object sampler = noiseRouter.sampler(); // dynamic access
            if (sampler instanceof SamplerHooks hooked) {
                hooked.setSeed(seed);
            }
        } catch (Throwable t) {
            // sampler not present or method change—suppress crash
        }
    }
}