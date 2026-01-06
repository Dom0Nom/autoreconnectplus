# AutoReconnectPlus

Minecraft 1.8.9 Forge mod that automatically reconnects to servers and escapes Hypixel limbo.

## Features

- **Auto-Reconnect**: Automatically reconnect to the last server after any disconnect
- **Anti-Limbo**: Detects Hypixel limbo and executes `/lobby` then `/skyblock`
- **Configurable Delays**: Customize reconnect timing and command delays
- **OneConfig Integration**: Easy configuration via `/autoreconnectplus`

## Installation

1. Download the latest release
2. Place in `.minecraft/mods` folder
3. Requires Minecraft 1.8.9 with Forge and OneConfig

## Configuration

Use `/autoreconnectplus` (or `/arp` or `/reconnect`) in-game to open settings.

## Building

```bash
./gradlew clean build
```

Output: `versions/1.8.9-forge/build/libs/`
