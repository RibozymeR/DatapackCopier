package ribozyme.dpc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.plugin.PluginLogger;
import org.bukkit.plugin.java.JavaPlugin;

public class DatapackCopier extends JavaPlugin implements Listener {
	
	Logger logger;
	
	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
		
		logger = PluginLogger.getLogger("DatapackCopier");
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onWorldInit(WorldInitEvent event) {
		logger.log(Level.INFO, "Init " + event.getWorld().getName());
		
		Path datapacks = getDataFolder().getAbsoluteFile().toPath().getParent().getParent().resolve("datapacks");
		
		if(!Files.exists(datapacks) || !Files.isDirectory(datapacks)) {
			logger.log(Level.INFO, "No datapacks found");
			return;
		}
		
		Path world_datapacks = getServer().getWorldContainer().getAbsoluteFile().toPath()
				.resolve(event.getWorld().getName()).resolve("datapacks");
		
		try {
			FileUtils.copyDirectory(datapacks.toFile(), world_datapacks.toFile());
			logger.log(Level.INFO, "Copied datapacks from " + datapacks + " to " + world_datapacks);
		}
		catch (IOException e) {
			logger.log(Level.WARNING, "Could not copy datapacks to " + world_datapacks);
			e.printStackTrace();
		}
	}
}
