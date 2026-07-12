package com.outofmana.coralpreserver;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

final class CoralListener implements Listener {
    private static final Map<Material, Material> DEAD_VARIANTS = createDeadVariants();
    private final CoralPreserverPlugin plugin;

    CoralListener(CoralPreserverPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onCoralFade(BlockFadeEvent event) {
        if (plugin.settings().preserveEnabled && DEAD_VARIANTS.containsKey(event.getBlock().getType())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onCoralDry(PlayerInteractEvent event) {
        CoralConfig config = plugin.settings();
        if (!config.dryingEnabled || event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Block block = event.getClickedBlock();
        ItemStack item = event.getItem();
        EquipmentSlot hand = event.getHand();
        if (block == null || item == null || hand == null || item.getType() != config.tool) return;

        Material deadType = DEAD_VARIANTS.get(block.getType());
        if (deadType == null) return;

        Player player = event.getPlayer();
        if (config.requireSneak && !player.isSneaking()) return;

        event.setCancelled(true);
        BlockData oldData = block.getBlockData();
        BlockData deadData = deadType.createBlockData();
        copyCompatibleProperties(oldData, deadData);
        block.setBlockData(deadData, true);

        if (config.consumeTool && player.getGameMode() != org.bukkit.GameMode.CREATIVE) {
            consumeOne(player, hand, item);
        }
        if (config.playSound) {
            block.getWorld().playSound(block.getLocation().add(0.5, 0.5, 0.5), config.sound, 1.0f, 1.0f);
        }
        if (config.showParticles) {
            block.getWorld().spawnParticle(Particle.BLOCK, block.getLocation().add(0.5, 0.5, 0.5),
                    12, 0.25, 0.25, 0.25, 0.02, deadData);
        }
    }

    private static void copyCompatibleProperties(BlockData source, BlockData target) {
        if (source instanceof Directional sourceDirectional && target instanceof Directional targetDirectional
                && targetDirectional.getFaces().contains(sourceDirectional.getFacing())) {
            targetDirectional.setFacing(sourceDirectional.getFacing());
        }
        if (source instanceof Waterlogged sourceWaterlogged && target instanceof Waterlogged targetWaterlogged) {
            targetWaterlogged.setWaterlogged(sourceWaterlogged.isWaterlogged());
        }
    }

    private static void consumeOne(Player player, EquipmentSlot hand, ItemStack item) {
        if (item.getAmount() <= 1) {
            if (hand == EquipmentSlot.HAND) player.getInventory().setItemInMainHand(null);
            else player.getInventory().setItemInOffHand(null);
        } else {
            item.setAmount(item.getAmount() - 1);
        }
    }

    private static Map<Material, Material> createDeadVariants() {
        Map<Material, Material> variants = new EnumMap<>(Material.class);
        String[] colors = {"TUBE", "BRAIN", "BUBBLE", "FIRE", "HORN"};
        String[] shapes = {"CORAL", "CORAL_BLOCK", "CORAL_FAN", "CORAL_WALL_FAN"};
        for (String color : colors) {
            for (String shape : shapes) {
                variants.put(Material.valueOf(color + '_' + shape), Material.valueOf("DEAD_" + color + '_' + shape));
            }
        }
        return Collections.unmodifiableMap(variants);
    }
}
