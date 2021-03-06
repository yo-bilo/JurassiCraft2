package net.ilexiconn.jurassicraft;

import net.ilexiconn.jurassicraft.block.JCBlockRegistry;
import net.ilexiconn.jurassicraft.creativetab.JCCreativeTabs;
import net.ilexiconn.jurassicraft.entity.base.JCEntityRegistry;
import net.ilexiconn.jurassicraft.handler.GuiHandler;
import net.ilexiconn.jurassicraft.item.JCItemRegistry;
import net.ilexiconn.jurassicraft.packets.MessageCleaningTable;
import net.ilexiconn.jurassicraft.proxy.ServerProxy;
import net.ilexiconn.llibrary.common.content.ContentHandlerList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import java.io.IOException;

@Mod(modid = "jurassicraft", name = "JurassiCraft", version = "${version}", dependencies = "required-after:llibrary@[0.1.0-1.8,)")
public class JurassiCraft
{
    @SidedProxy(serverSide = "net.ilexiconn.jurassicraft.proxy.ServerProxy", clientSide = "net.ilexiconn.jurassicraft.proxy.ClientProxy")
    public static ServerProxy proxy;
    public static SimpleNetworkWrapper wrapper;

    @Instance("jurassicraft")
    public static JurassiCraft instance;

    public static boolean debug;

    @Mod.EventHandler
    public void init(FMLPreInitializationEvent event) throws IOException
    {
        ContentHandlerList.createList(new JCCreativeTabs(), new JCItemRegistry(), new JCBlockRegistry(), new JCEntityRegistry()).init();
        proxy.init();

        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());

        this.wrapper = NetworkRegistry.INSTANCE.newSimpleChannel("jurassicraft");
        this.wrapper.registerMessage(MessageCleaningTable.Handler.class, MessageCleaningTable.class, 0, Side.SERVER);
    }

    @Mod.EventHandler
    public void init(FMLPostInitializationEvent event)
    {
        proxy.postInit();
    }
}