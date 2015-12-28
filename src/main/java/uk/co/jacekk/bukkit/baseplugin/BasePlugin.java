package uk.co.jacekk.bukkit.baseplugin;

import java.io.File;
import org.bukkit.ChatColor;

import org.bukkit.plugin.java.JavaPlugin;

import uk.co.jacekk.bukkit.baseplugin.command.CommandManager;

/**
 * The base class that the main plugin class should extend.
 * 
 * @author Jacek Kuzemczak
 */
public abstract class BasePlugin extends JavaPlugin {

    /**
     * The version of the BasePlugin library
     */
    public static final String VERSION = "16";
    private static final String PACKAGE_NAME = "16";

    /**
     * The plugin's data folder.
     */
    private File baseDir;

    /**
     * The absolute path to the plugin's data folder.
     */
    private String baseDirPath;

    private CommandManager commandManager;

    /**
     * Sets up the default fields for the plugin.
     * 
     * @param createFolder	If this is true then the plugin's data folder will be created if it does not exist.
     * @param minVersion	The minimum version of the BasePlugin library that is required.
     */
    public void onEnable(boolean createFolder) {
        // This prevents Maven Shade plugin from replacing the package name
        String packageName = new String(new char[]{'u', 'k', '.', 'c', 'o', '.', 'j', 'a', 'c', 'e', 'k', 'k', '.', 'b', 'u', 'k', 'k', 'i', 't', '.', 'b', 'a', 's', 'e', 'p', 'l', 'u', 'g', 'i', 'n'});

        if (!BasePlugin.class.getName().equals(packageName + ".v" + PACKAGE_NAME + ".BasePlugin")) {
            throw new PackageNameException(BasePlugin.class.getName());
        }

        this.baseDir = this.getDataFolder();
        this.baseDirPath = this.baseDir.getAbsolutePath();

        this.commandManager = new CommandManager(this);

        if (createFolder && !this.baseDir.exists()) {
            this.baseDir.mkdirs();
        }
    }
	
    /**
     * Gets the File for the base directory of the plugin.
     * 
     * @return	The File
     */
    public File getBaseDir() {
        return this.baseDir;
    }

    /**
     * Gets the path to the base directory of the plugin
     * 
     * @return	The path (does not end with a slash)
     */
    public String getBaseDirPath() {
        return this.baseDirPath;
    }

    /**
     * Gets the command manager for the plugin.
     * 
     * @return The command manager.
     */
    public CommandManager getCommandManager() {
        return this.commandManager;
    }

    /**
     * Formats a message for the plugin.
     * 
     * @param message	The message to be formatted.
     * @param colour	If set to true the prefix is coloured blue.
     * @param version	If set to true the version is included in the prefix.
     * @return		The formatted message.
     */
    public String formatMessage(String message, boolean colour, boolean version) {
        StringBuilder line = new StringBuilder();

        if (colour) {
            line.append(ChatColor.BLUE);
        }

        line.append("[");
        line.append(getDescription().getName());

        if (version) {
            line.append(" v");
            line.append(getDescription().getVersion());
        }

        line.append("] ");
        line.append(ChatColor.RESET);
        line.append(message);

        return line.toString();
    }

    /**
     * Formats a message for the plugin.
     * <p />
     * NOTE: The version number is included in the prefix if  colour is disabled.
     * 
     * @param message	The message to be formatted.
     * @param colour        	If set to true the prefix is coloured blue.
     * @return		The formatted message.
     */
    public String formatMessage(String message, boolean colour) {
        return this.formatMessage(message, colour, !colour);
    }

    /**
     * Formats a message for the plugin.
     * <p />
     * NOTE: Colour is enabled and the version number is not in the prefix.
     *
     * @param message	The message to be formatted.
     * @return                   The formatted message.
     */
    public String formatMessage(String message) {
        return this.formatMessage(message, true, false);
    }

}
