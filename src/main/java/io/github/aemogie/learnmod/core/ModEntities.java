package io.github.aemogie.learnmod.core;

import io.github.aemogie.learnmod.client.entity.renderer.SeatRenderer;
import io.github.aemogie.learnmod.client.entity.renderer.TestEntityRenderer;
import io.github.aemogie.learnmod.common.entity.ModEntityType;
import io.github.aemogie.learnmod.common.entity.ModEntityType.Builder;
import io.github.aemogie.learnmod.common.entity.SeatEntity;
import io.github.aemogie.learnmod.common.entity.TestEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static io.github.aemogie.learnmod.References.MOD_ID;
import static net.minecraftforge.fml.client.registry.RenderingRegistry.registerEntityRenderingHandler;

@SuppressWarnings("unused")
public class ModEntities {
	public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITIES, MOD_ID);
	
	public static final RegistryObject<ModEntityType<SeatEntity>> SEAT_ENTITY = register("seat_entity", Builder.of(SeatEntity::new, EntityClassification.MISC, SeatEntity.class, SeatRenderer::new).sized(0.0f, 0.0f));
	
	public static final RegistryObject<ModEntityType<TestEntity>> TEST_ENTITY = register("test_entity", Builder.of(TestEntity::new, EntityClassification.MISC, TestEntity.class, TestEntityRenderer::new).sized(0.6F, 1.95F));
	
	public static <T extends Entity> RegistryObject<ModEntityType<T>> register(String name, Builder<T> builder) {
		return REGISTRY.register(name, () -> builder.build(name));
	}
	
	public static void setupRenderers() {
		for (RegistryObject<EntityType<?>> entityType : REGISTRY.getEntries()) setupRenderer(entityType.get());
	}
	
	private static <T extends Entity> void setupRenderer(EntityType<T> entityType) {
		registerEntityRenderingHandler(entityType, ((ModEntityType<T>) entityType).RENDERER);
	}
}