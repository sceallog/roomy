package com.roomy.controllers;

import com.roomy.models.Vendor;
import com.roomy.services.InventoryService;
import com.roomy.models.InventoryItem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;

@Controller
@RequestMapping("/inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public String getAllItems(Model model) {
        List<InventoryItem> items = inventoryService.getAllItems();
        model.addAttribute("items", items);
        return "inventory/inventory";
    }

    @GetMapping("/new")
    public String createNewItem(Model model) {
        model.addAttribute("item", new InventoryItem());
        model.addAttribute("vendors", inventoryService.getAllVendors());
        return "inventory/create";
    }

    @PostMapping
    public String saveItem(@ModelAttribute InventoryItem item, @RequestParam int vendorId) {
        Vendor vendor = inventoryService.getVendorById(vendorId);
        item.setVendor(vendor);
        inventoryService.saveItem(item);
        return"redirect:/inventory";
    }

}
