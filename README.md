# CoralPreserver

Build freely with living coral outside water—without losing access to dead coral variants.

CoralPreserver is a lightweight Paper plugin that prevents living coral from automatically dying when it is placed outside water. Players can still create dead coral intentionally by sneaking and right-clicking living coral with a sponge.

## Features

- Protects all 20 living coral, coral block, fan, and wall fan variants.
- Only reacts to coral fade events: no repeating tasks or world scans.
- Does not affect ice, snow, fire, turtle eggs, or any other fading block.
- Supports intentional drying with a configurable tool.
- Preserves wall-fan orientation and waterlogged state when drying coral.
- Optional tool consumption, sound, particles, and sneak requirement.
- No NMS and no runtime dependencies.

## Requirements

- Paper 1.21.8 or newer, including 26.2
- Java 21 or newer (Java 25 is required by Paper 26.1+)

This plugin is server-side only. Players do not need to install anything.

## Installation

1. Download `CoralPreserver-1.0.0.jar`.
2. Place it in your server's `plugins` directory.
3. Restart the server.
4. Edit `plugins/CoralPreserver/config.yml` if desired.
5. Run `/coralpreserver reload` after changing the configuration.

## Default behavior

Sneak and right-click any living coral with a regular sponge. The coral is immediately replaced by its matching dead variant. The sponge is not consumed by default.

Examples:

- `TUBE_CORAL_BLOCK` → `DEAD_TUBE_CORAL_BLOCK`
- `BRAIN_CORAL` → `DEAD_BRAIN_CORAL`
- `BUBBLE_CORAL_FAN` → `DEAD_BUBBLE_CORAL_FAN`
- `FIRE_CORAL_WALL_FAN` → `DEAD_FIRE_CORAL_WALL_FAN`

## Configuration

```yaml
preserve:
  enabled: true

drying:
  enabled: true
  require-sneak: true
  tool: "SPONGE"
  consume-tool: false
  play-sound: true
  sound: "BLOCK_SPONGE_ABSORB"
  show-particles: true
```

## Commands and permissions

| Command | Permission | Default |
| --- | --- | --- |
| `/coralpreserver reload` | `coralpreserver.reload` | Server operators |

No permission is required to preserve or intentionally dry coral.

## Building from source

Run `mvn clean package` with Java 21 or newer and Maven. The compiled plugin will be created at `target/CoralPreserver-1.0.0.jar`.

## License

CoralPreserver is available under the [MIT License](LICENSE).
