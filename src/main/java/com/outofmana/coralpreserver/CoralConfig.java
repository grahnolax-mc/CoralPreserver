package com.outofmana.coralpreserver;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;

final class CoralConfig {
    final boolean preserveEnabled;
    final boolean dryingEnabled;
    final boolean requireSneak;
    final Material tool;
    final boolean consumeTool;
    final boolean playSound;
    final Sound sound;
    final boolean showParticles;

    private CoralConfig(boolean preserveEnabled, boolean dryingEnabled, boolean requireSneak,
                        Material tool, boolean consumeTool, boolean playSound, Sound sound,
                        boolean showParticles) {
        this.preserveEnabled = preserveEnabled;
        this.dryingEnabled = dryingEnabled;
        this.requireSneak = requireSneak;
        this.tool = tool;
        this.consumeTool = consumeTool;
        this.playSound = playSound;
        this.sound = sound;
        this.showParticles = showParticles;
    }

    static CoralConfig load(CoralPreserverPlugin plugin) {
        FileConfiguration config = plugin.getConfig();
        Material tool = parseMaterial(plugin, config.getString("drying.tool", "SPONGE"));
        Sound sound = parseSound(plugin, config.getString("drying.sound", "BLOCK_SPONGE_ABSORB"));
        return new CoralConfig(
                config.getBoolean("preserve.enabled", true),
                config.getBoolean("drying.enabled", true),
                config.getBoolean("drying.require-sneak", true),
                tool,
                config.getBoolean("drying.consume-tool", false),
                config.getBoolean("drying.play-sound", true),
                sound,
                config.getBoolean("drying.show-particles", true)
        );
    }

    private static Material parseMaterial(CoralPreserverPlugin plugin, String name) {
        Material material = Material.matchMaterial(name == null ? "" : name);
        if (material != null && material.isItem()) return material;
        plugin.getLogger().warning("Invalid drying.tool ('" + name + "'); using SPONGE instead.");
        return Material.SPONGE;
    }

    private static Sound parseSound(CoralPreserverPlugin plugin, String name) {
        NamespacedKey key = NamespacedKey.fromString(name == null ? "" : name.toLowerCase(java.util.Locale.ROOT));
        Sound sound = key == null ? null : Registry.SOUNDS.get(key);
        if (sound != null) return sound;
        plugin.getLogger().warning("Invalid drying.sound ('" + name
                + "'); using BLOCK_SPONGE_ABSORB instead.");
        return Sound.BLOCK_SPONGE_ABSORB;
    }
}
