package com.autoreconnectplus

import net.minecraft.client.Minecraft
import net.minecraft.util.ChatComponentText
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

object LimboDetector {
    @SubscribeEvent
    fun onChatMessage(event: ClientChatReceivedEvent) {
        if (!AutoReconnectConfig.antiLimboEnabled) return

        val message = event.message.unformattedText.lowercase()

        // Check for various limbo detection messages
        if (message.contains("warped to limbo") ||
            message.contains("you are in limbo") ||
            message.contains("you are afk. move around to return from afk")) {

            if (AutoReconnectConfig.debugMode) {
                Minecraft.getMinecraft().thePlayer?.addChatMessage(
                    ChatComponentText("§6[AutoReconnectPlus]§r Limbo detected! Executing escape sequence...")
                )
            }

            CommandScheduler.scheduleLimboEscape()
        }
    }
}
