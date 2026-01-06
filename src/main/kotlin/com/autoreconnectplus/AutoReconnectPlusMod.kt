package com.autoreconnectplus

import cc.polyfrost.oneconfig.utils.commands.CommandManager
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent

@Mod(
    modid = AutoReconnectPlusMod.MODID,
    name = AutoReconnectPlusMod.NAME,
    version = AutoReconnectPlusMod.VERSION,
    modLanguageAdapter = "cc.polyfrost.oneconfig.utils.KotlinLanguageAdapter"
)
object AutoReconnectPlusMod {
    const val MODID = "autoreconnectplus"
    const val NAME = "AutoReconnectPlus"
    const val VERSION = "1.0.0"

    @Mod.EventHandler
    fun onPreInit(event: FMLPreInitializationEvent) {
        MinecraftForge.EVENT_BUS.register(ReconnectHandler)
        MinecraftForge.EVENT_BUS.register(LimboDetector)
        MinecraftForge.EVENT_BUS.register(CommandScheduler)
    }

    @Mod.EventHandler
    fun onInit(event: FMLInitializationEvent) {
        // Initialize config (triggers init block which calls initialize())
        AutoReconnectConfig
        CommandManager.INSTANCE.registerCommand(AutoReconnectCommand())
        println("[$NAME] Mod loaded with OneConfig!")
    }
}
