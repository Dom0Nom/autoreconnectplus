package com.autoreconnectplus

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiMainMenu
import net.minecraft.client.multiplayer.GuiConnecting
import net.minecraft.client.multiplayer.ServerData
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import net.minecraftforge.fml.common.network.FMLNetworkEvent

object ReconnectHandler {
    private val mc: Minecraft = Minecraft.getMinecraft()
    private var lastServerData: ServerData? = null
    private var shouldReconnect = false
    private var reconnectTicks = 0

    @SubscribeEvent
    fun onDisconnect(event: FMLNetworkEvent.ClientDisconnectionFromServerEvent) {
        if (!AutoReconnectConfig.reconnectEnabled) return

        // Save last server
        val serverData = mc.currentServerData
        if (serverData != null) {
            lastServerData = ServerData(serverData.serverName, serverData.serverIP, false)
            shouldReconnect = true
            reconnectTicks = AutoReconnectConfig.getReconnectDelayTicks()

            if (AutoReconnectConfig.debugMode) {
                println("[AutoReconnectPlus] Disconnect detected, will reconnect in ${reconnectTicks/20} seconds")
            }
        }
    }

    @SubscribeEvent
    fun onClientTick(event: TickEvent.ClientTickEvent) {
        if (event.phase != TickEvent.Phase.END) return
        if (!shouldReconnect) return

        reconnectTicks--

        if (reconnectTicks <= 0) {
            performReconnect()
        }
    }

    private fun performReconnect() {
        val server = lastServerData ?: return

        if (AutoReconnectConfig.debugMode) {
            println("[AutoReconnectPlus] Reconnecting to ${server.serverIP}")
        }

        mc.displayGuiScreen(GuiConnecting(GuiMainMenu(), mc, server))
        shouldReconnect = false
    }
}
