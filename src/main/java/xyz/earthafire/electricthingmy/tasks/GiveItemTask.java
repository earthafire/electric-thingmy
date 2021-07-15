package xyz.earthafire.electricthingmy.tasks;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GiveItemTask extends BukkitRunnable{
    
    private Player player;
    String name;
    ItemStack item;

    
    //assumes "target" is not in "onlinePlayers"
    public GiveItemTask(Player player, String name, String itemName) {
        this.player = player;
        this.name = name;
        if(itemName.equalsIgnoreCase("lacroix")){
            this.item = getRandomLacroixLoreItem();
        } else {
            this.item = new ItemStack(Material.STICK);
        }
    }

    public GiveItemTask(Player player, String name, ItemStack item) {
        this.player = player;
        this.name = name;
        this.item = item;
    }

    @Override
    public void run() {
        if(item.getType().equals(Material.POTION)){
            player.sendMessage(ChatColor.LIGHT_PURPLE + name + ChatColor.WHITE + " has offered you a \"refreshing\" lacroix!");
        } else {
            player.sendMessage(ChatColor.LIGHT_PURPLE + name + ChatColor.WHITE + " has offered you a " + item.getType());
        }

        player.getWorld().dropItemNaturally(player.getLocation(), item);
        player.playSound(player.getLocation(), Sound.BLOCK_BAMBOO_BREAK, .8F, 1.0F);
    }

    private ItemStack getRandomLacroixLoreItem(){
        item = new ItemStack(Material.POTION, 1);
        
        PotionMeta lacroix = (PotionMeta) this.item.getItemMeta();

        int cases = 8; //should be number of possible cases
        Random r = new Random();

        //set random flavortext
        List<Component> lore = new ArrayList<Component>();
        TextComponent text = Component.text("lacroix");
        lacroix.displayName(text);

        switch (r.nextInt(cases)){
            case 0:
                lacroix.setColor(Color.FUCHSIA);
                lore.add(Component.text("PAMPLEMOUSSE").color(TextColor.fromHexString("FF55FF")));
                lore.add(Component.text("single skittle dissolved").color(TextColor.fromHexString("AAAAAA")));
                lore.add(Component.text("in water").color(TextColor.fromHexString("AAAAAA")));
                break;
            case 1:
                lacroix.setColor(Color.PURPLE);
                lore.add(Component.text("PASSIONFRUIT").color(TextColor.fromHexString("AA00AA")));
                lore.add(Component.text("imagine like, a strawberry").color(TextColor.fromHexString("AAAAAA")));
                lore.add(Component.text("but with low battery").color(TextColor.fromHexString("AAAAAA")));
                break;
            case 2:
                lacroix.setColor(Color.ORANGE);
                lore.add(Component.text("TANGERINE").color(TextColor.fromHexString("FFAA00")));
                lore.add(Component.text("transported behind a truck").color(TextColor.fromHexString("AAAAAA")));
                lore.add(Component.text("filled with tangerines").color(TextColor.fromHexString("AAAAAA")));
                break;
            case 3:
                lacroix.setColor(Color.AQUA);
                lore.add(Component.text("PURE").color(TextColor.fromHexString("55FFFF")));
                lore.add(Component.text("idk water lmfao").color(TextColor.fromHexString("AAAAAA")));
                break;
            case 4:
                lacroix.setColor(Color.YELLOW);
                lore.add(Component.text("LEMON").color(TextColor.fromHexString("FFFF55")));
                lore.add(Component.text("if citrus could whisper").color(TextColor.fromHexString("AAAAAA")));
                break;
            case 5:
                lacroix.setColor(Color.WHITE);
                lore.add(Component.text("COCONUT").color(TextColor.fromHexString("FFFFFF")));
                lore.add(Component.text("canned across the room").color(TextColor.fromHexString("AAAAAA")));
                lore.add(Component.text("from a tropical breeze").color(TextColor.fromHexString("AAAAAA")));
                break;
            case 6:
                lacroix.setColor(Color.RED);
                lore.add(Component.text("PASTEQUE").color(TextColor.fromHexString("AA0000")));
                lore.add(Component.text("i literally can't tell").color(TextColor.fromHexString("AAAAAA")));
                lore.add(Component.text("what farted in this").color(TextColor.fromHexString("AAAAAA")));
                break;
            default:
                lacroix.setColor(Color.LIME);
                lore.add(Component.text("KEY LIME").color(TextColor.fromHexString("55FF55")));
                lore.add(Component.text("hint of hint of lime").color(TextColor.fromHexString("AAAAAA")));
                break;
        }

        lacroix.lore(lore);
            
        item.setItemMeta(lacroix);
        return item;
    }
}