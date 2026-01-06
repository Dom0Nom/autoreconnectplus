package com.autoreconnectplus

import cc.polyfrost.oneconfig.utils.commands.annotations.Command
import cc.polyfrost.oneconfig.utils.commands.annotations.Main

@Command(value = "autoreconnectplus", aliases = ["arp", "reconnect"])
class AutoReconnectCommand {
    @Main
    fun main() {
        AutoReconnectConfig.openGui()
    }
}
