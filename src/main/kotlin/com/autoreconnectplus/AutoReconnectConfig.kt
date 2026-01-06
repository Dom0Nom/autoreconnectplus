package com.autoreconnectplus

import cc.polyfrost.oneconfig.config.Config
import cc.polyfrost.oneconfig.config.annotations.Slider
import cc.polyfrost.oneconfig.config.annotations.Switch
import cc.polyfrost.oneconfig.config.data.Mod
import cc.polyfrost.oneconfig.config.data.ModType

object AutoReconnectConfig : Config(
    Mod(
        "AutoReconnectPlus",
        ModType.UTIL_QOL
    ), "autoreconnectplus.json"
) {
    // Auto-Reconnect Settings
    @Switch(
        name = "Enable Auto-Reconnect",
        description = "Automatically reconnect to the last server after disconnect",
        category = "Reconnection"
    )
    var reconnectEnabled = true

    @Slider(
        name = "Reconnect Delay (seconds)",
        description = "Time to wait before reconnecting (20 ticks = 1 second)",
        category = "Reconnection",
        min = 1f,
        max = 60f,
        step = 1
    )
    var reconnectDelay = 10f

    // Anti-Limbo Settings
    @Switch(
        name = "Enable Anti-Limbo",
        description = "Automatically detect and escape Hypixel limbo",
        category = "Anti-Limbo"
    )
    var antiLimboEnabled = true

    @Slider(
        name = "Lobby Command Delay (seconds)",
        description = "Wait time after /lobby before sending /skyblock",
        category = "Anti-Limbo",
        min = 1f,
        max = 10f,
        step = 1
    )
    var lobbyDelay = 2f

    // Debug
    @Switch(
        name = "Debug Mode",
        description = "Print debug messages to chat",
        category = "Debug"
    )
    var debugMode = false

    init {
        initialize()
        addDependency("reconnectDelay", "reconnectEnabled")
        addDependency("lobbyDelay", "antiLimboEnabled")
    }

    fun getReconnectDelayTicks(): Int = (reconnectDelay * 20).toInt()
    fun getLobbyDelayTicks(): Int = (lobbyDelay * 20).toInt()
}
