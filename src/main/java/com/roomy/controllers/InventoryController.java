package com.roomy.controllers;

import com.roomy.models.Vendor;
import com.roomy.repositories.InventoryItemRepository;
import com.roomy.services.InventoryService;
import com.roomy.models.InventoryItem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/inventory")
public class InventoryController {
    private final InventoryService inventoryService;
    private final InventoryItemRepository inventoryItemRepository;

    public InventoryController(InventoryService inventoryService, InventoryItemRepository inventoryItemRepository) {
        this.inventoryService = inventoryService;
        this.inventoryItemRepository = inventoryItemRepository;
    }

    @GetMapping
    public String getAllItems(Model model) {
        List<InventoryItem> items = inventoryService.getAllItems();
        model.addAttribute("items", items);
        return "inventory/inventory";
    }

    @GetMapping("/show/{id}")
    public String showItem(@PathVariable int id, Model model) {
        inventoryItemRepository.findById(id).ifPresent(item -> {
            model.addAttribute("item", item);
        });
        return "inventory/show";
    }

    @GetMapping("/new")
    public String createNewItem(Model model) {
        model.addAttribute("item", new InventoryItem());
        model.addAttribute("vendors", inventoryService.getAllVendors());
        return "inventory/create";
    }

    @GetMapping("/update/{id}")
    public String getItemToUpdate(@PathVariable("id") Integer id, Model model) {
        Optional<InventoryItem> itemOptional = inventoryItemRepository.findById(id);
        if (!itemOptional.isPresent()) {
            return null;
        }
        InventoryItem item = itemOptional.get();
        model.addAttribute("item", item);
        model.addAttribute("vendors", inventoryService.getAllVendors());
        return "inventory/edit";
    }

    @PostMapping
    public String saveItem(@ModelAttribute InventoryItem item, @RequestParam int vendorId) {
        Vendor vendor = inventoryService.getVendorById(vendorId);
        item.setVendor(vendor);
        inventoryService.saveItem(item);
        return"redirect:/inventory";
    }

    @PutMapping("/update/{id}")
    public String updateItem(@PathVariable int id, @ModelAttribute InventoryItem item, @RequestParam int vendorId) {
        Optional<InventoryItem> itemOptional = inventoryItemRepository.findById(id);
        if (!itemOptional.isPresent()) {
            return null;
        }
        InventoryItem itemToUpdate = itemOptional.get();
        Vendor newVendor = inventoryService.getVendorById(vendorId);
        if (!itemToUpdate.getName().equals(item.getName())) {
            itemToUpdate.setName(item.getName());
        }
        if (itemToUpdate.getPrice() != (item.getPrice())) {
            itemToUpdate.setPrice(item.getPrice());
        }
        if (itemToUpdate.getStock() != (item.getStock())) {
            itemToUpdate.setStock(item.getStock());
        }
        if (!itemToUpdate.getVendor().equals(newVendor)) {
            itemToUpdate.setVendor(newVendor);
        }
        inventoryService.saveItem(itemToUpdate);
        return"redirect:/inventory/show/" + id;
    }

    @DeleteMapping("/{id}")
    public String deleteItem(@PathVariable("id") Integer id) {
        Optional<InventoryItem> itemOptional = inventoryItemRepository.findById(id);
        if (!itemOptional.isPresent()) {
            return null;
        }
        InventoryItem item = itemOptional.get();
        inventoryItemRepository.delete(item);
        return "redirect:/inventory";
    }

}
