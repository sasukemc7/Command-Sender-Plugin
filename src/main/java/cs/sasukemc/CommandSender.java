package cs.sasukemc;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import static spark.Spark.*;

public class CommandSender extends JavaPlugin {

    private String password;

    @Override
    public void onEnable() {
        getLogger().info("CommandSenderPlugin has been enabled!");

        // Registrar el comando
        this.getCommand("csreload").setExecutor((sender, command, label, args) -> {
            if (sender.hasPermission("commandsender.reload")) {
                reloadConfig();
                password = getConfig().getString("password", "default_password");
                sender.sendMessage("Configuration reloaded.");
                return true;
            } else {
                sender.sendMessage("You do not have permission to execute this command.");
                return false;
            }
        });

        try {
            // Guardar la configuración predeterminada si no existe
            saveDefaultConfig();
            FileConfiguration config = getConfig();
            int port = config.getInt("port", 4567); // Leer el puerto desde config.yml
            password = config.getString("password", "default_password");

            // Inicia el servidor HTTP
            port(port);
            post("/execute", (request, response) -> {
                String command = request.queryParams("command");
                String requestPassword = request.headers("password");

                if (requestPassword == null || !requestPassword.equals(password)) {
                    response.status(401);
                    response.type("application/json");
                    return "{\"status\":\"error\",\"message\":\"Unauthorized\"}";
                }

                if (command != null && !command.isEmpty()) {
                    Bukkit.getScheduler().runTask(this, () -> executeCommand(command));
                    response.type("application/json");
                    return "{\"status\":\"success\",\"message\":\"Command executed: " + command + "\"}";
                } else {
                    response.status(400);
                    response.type("application/json");
                    return "{\"status\":\"error\",\"message\":\"Command parameter is missing\"}";
                }
            });
        } catch (Exception e) {
            getLogger().severe("Error loading configuration: " + e.getMessage());
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("CommandSenderPlugin has been disabled!");
    }

    private void executeCommand(String command) {
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        Bukkit.dispatchCommand(console, command);
    }
}