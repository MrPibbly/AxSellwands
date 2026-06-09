package com.artillexstudios.axsellwands.hooks.shop;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import su.nightexpress.excellentshop.ShopPlugin;
import su.nightexpress.excellentshop.api.product.TradeType;
import su.nightexpress.excellentshop.virtualshop.VirtualShopModule;
import su.nightexpress.excellentshop.virtualshop.product.VirtualProduct;

public class ExcellentShopHook implements PricesHook {
    private VirtualShopModule module;

    @Override
    public void setup() {
        ShopPlugin plugin = (ShopPlugin) Bukkit.getPluginManager().getPlugin("ExcellentShop");
        if (plugin == null) return;
        module = plugin.getModuleRegistry().byType(VirtualShopModule.class).orElse(null);
    }

    @Override
    public double getPrice(ItemStack it) {
        VirtualProduct product = module.getBestProductFor(copy(it), TradeType.SELL);
        if (product == null) return -1.0D;
        return product.getFinalPrice(TradeType.SELL, it.getAmount());
    }

    @Override
    public double getPrice(Player player, ItemStack it) {
        VirtualProduct product = module.getBestProductFor(copy(it), TradeType.SELL);
        if (product == null) return -1.0D;
        return product.getFinalPrice(TradeType.SELL, it.getAmount(), player);
    }

    private ItemStack copy(@NotNull ItemStack item) {
        ItemStack copy = item.clone();
        copy.setAmount(1);
        return copy;
    }
}
