# Custom Messages Plugin

---

## Description:

The Custom Messages Plugin for Minecraft allows server administrators to tailor and personalize in-game messages for a more unique and engaging player experience. Whether you want to communicate custom error messages, success confirmations, or provide specific instructions, this plugin gives you the flexibility to shape your server's communication style.

## Features:

1. **Customize:**
   - Customize your killing message.

2. **Multilanguage support:**
   - Translate your own `[lang].yml` file and put it in `config.yml` file

## Usage:
1. **Command list:**
   - `/kmc` - Shows list of commands
   - Permission: none
2. **Set Custom Message:**
   - `/kcm set <message>` - Set a custom message for specific actions. (message must contains %killer%, %victim% and %weapon%)
   - Permission: `kcm.command.set`
3. **Reload Configuration:**
   - `/kmc reload` - Reload configurations file
   - Permission: `kcm.command.reload`
  

## Installation:
1. Download the Custom Messages Plugin JAR file.
2. Place the JAR file in the "plugins" folder of your Minecraft server.
3. Start or restart your server.

## Configuration:
- Customize messages and plugin settings in the `config.yml` file.
