package com.autoreconnectplus

import net.minecraft.client.Minecraft
import net.minecraft.util.ChatComponentText
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent

object CommandScheduler {
    private val mc: Minecraft = Minecraft.getMinecraft()
    private val commandQueue = mutableListOf<ScheduledCommand>()

    data class ScheduledCommand(
        val command: String,
        var ticksRemaining: Int
    )

    fun scheduleLimboEscape() {
        commandQueue.clear()

        // Execute /lobby after 1 second (20 ticks)
        commandQueue.add(ScheduledCommand("/lobby", 20))

        // Execute /skyblock after additional delay from config
        val additionalDelay = AutoReconnectConfig.getLobbyDelayTicks()
        val totalDelay = 20 + additionalDelay
        commandQueue.add(ScheduledCommand("/skyblock", totalDelay))

        if (AutoReconnectConfig.debugMode) {
            println("[AutoReconnectPlus] Scheduled limbo escape: wait 1s -> /lobby -> wait ${additionalDelay/20}s -> /skyblock")
        }
    }

    @SubscribeEvent
    fun onClientTick(event: TickEvent.ClientTickEvent) {
        if (event.phase != TickEvent.Phase.END) return
        if (commandQueue.isEmpty()) return

        val player = mc.thePlayer ?: return

        // Process commands
        val iterator = commandQueue.iterator()
        while (iterator.hasNext()) {
            val cmd = iterator.next()

            if (cmd.ticksRemaining <= 0) {
                player.sendChatMessage(cmd.command)

                if (AutoReconnectConfig.debugMode) {
                    mc.thePlayer?.addChatMessage(
                        ChatComponentText("§6[AutoReconnectPlus]§r Executed: ${cmd.command}")
                    )
                }

                iterator.remove()
            } else {
                cmd.ticksRemaining--
            }
        }
    }
}
