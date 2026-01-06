package com.autoreconnectplus

import net.minecraft.client.Minecraft
import net.minecraft.scoreboard.ScoreObjective
import net.minecraft.scoreboard.Scoreboard
import net.minecraft.util.ChatComponentText
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.event.world.WorldEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent

object LimboDetector {
    private var inLimbo = false

    fun isInLimbo(): Boolean = inLimbo

    @SubscribeEvent
    fun onChatMessage(event: ClientChatReceivedEvent) {
        if (!AutoReconnectConfig.antiLimboEnabled) return

        val message = event.message.unformattedText

        // Check for limbo chat messages (exact Hypixel strings)
        if (message.contains("You were spawned in Limbo.") ||
            message.contains("You are AFK. Move around to return from AFK.")) {

            if (!inLimbo) {
                inLimbo = true

                if (AutoReconnectConfig.debugMode) {
                    Minecraft.getMinecraft().thePlayer?.addChatMessage(
                        ChatComponentText("§6[AutoReconnectPlus]§r Limbo detected via chat!")
                    )
                }

                CommandScheduler.scheduleLimboEscape()
            }
        }
    }

    @SubscribeEvent
    fun onClientTick(event: TickEvent.ClientTickEvent) {
        if (event.phase != TickEvent.Phase.END) return
        if (!AutoReconnectConfig.antiLimboEnabled) return

        // Check scoreboard for limbo detection
        if (isLimboScoreboard() && !inLimbo) {
            inLimbo = true

            if (AutoReconnectConfig.debugMode) {
                Minecraft.getMinecraft().thePlayer?.addChatMessage(
                    ChatComponentText("§6[AutoReconnectPlus]§r Limbo detected via scoreboard!")
                )
            }

            CommandScheduler.scheduleLimboEscape()
        } else if (!isLimboScoreboard() && inLimbo) {
            inLimbo = false
        }
    }

    @SubscribeEvent
    fun onWorldUnload(event: WorldEvent.Unload) {
        if (inLimbo && AutoReconnectConfig.debugMode) {
            println("[AutoReconnectPlus] World unloaded, resetting limbo state")
        }
        inLimbo = false
    }

    private fun isLimboScoreboard(): Boolean {
        val world = Minecraft.getMinecraft().theWorld ?: return false
        val scoreboard = world.scoreboard
        val sidebarObjective = scoreboard.getObjectiveInDisplaySlot(1) ?: return false

        val displayName = sidebarObjective.displayName
        val cleanName = displayName.replace("(?i)\\u00A7.".toRegex(), "")

        return cleanName.contains("LIMBO", ignoreCase = true)
    }
}
