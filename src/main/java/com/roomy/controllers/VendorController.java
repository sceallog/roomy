package com.roomy.controllers;

import com.roomy.models.Vendor;
import com.roomy.repositories.InventoryItemRepository;
import com.roomy.repositories.VendorRepository;
import com.roomy.services.VendorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/vendors")
public class VendorController {
    private final VendorService vendorService;
    private final VendorRepository vendorRepository;
    private final InventoryItemRepository inventoryItemRepository;

    public VendorController(VendorService vendorService, VendorRepository vendorRepository, InventoryItemRepository inventoryItemRepository) {
        this.vendorService = vendorService;
        this.vendorRepository = vendorRepository;
        this.inventoryItemRepository = inventoryItemRepository;
    }

    @GetMapping
    public String getAllVendors(Model model) {
        List<Vendor> vendors = vendorService.getAllVendors();
        model.addAttribute("vendors", vendors);
        return "vendor/vendors";
    }

    @GetMapping("/new")
    public String createNewVendor(Model model) {
        model.addAttribute("vendor", new Vendor());
        return "vendor/create";
    }

    @GetMapping("/update/{id}")
    public String getVendorToUpdate(@PathVariable int id, Model model) {
        Optional<Vendor> vendorOptional = vendorRepository.findById(id);
        if (!vendorOptional.isPresent()) {
            throw new RuntimeException("仕入先を見つかりませんでした。");
        }
        Vendor vendor = vendorOptional.get();
        model.addAttribute("vendor", vendor);
        return "vendor/edit";
    }

    @PostMapping
    public String saveVendor(@ModelAttribute Vendor vendor) {
        vendorService.saveVendor(vendor);
        return "redirect:/vendors";
    }

    @GetMapping("/show/{id}")
    public String showVendor(@PathVariable int id, Model model) {
        vendorRepository.findById(id).ifPresent(vendor -> {
            model.addAttribute("vendor", vendor);
        });
        inventoryItemRepository.findAllByVendor_Id(id).ifPresent(items -> {
            model.addAttribute("items", items);
        });
        return "vendor/show";
    }

    @GetMapping("/search")
    public String search(@RequestParam("keyword") String keyword, Model model) {
        List<Vendor> vendors = vendorService.searchVendors(keyword);
        model.addAttribute("vendors", vendors);
        return "vendor/vendors";
    }

    @DeleteMapping("/{id}")
    public String deleteVendor(@PathVariable int id) {
        Optional<Vendor> vendorOptional = vendorRepository.findById(id);
        if (!vendorOptional.isPresent()) {
            throw new RuntimeException("仕入先を見つかりませんでした。");
        }
        Vendor vendor = vendorOptional.get();
        vendorRepository.delete(vendor);
        return "redirect:/vendors";
    }

    @PutMapping("/update/{id}")
    public String updateVendor(@PathVariable int id, @ModelAttribute Vendor vendor) {
        Optional<Vendor> vendorOptional = vendorRepository.findById(id);
        if (!vendorOptional.isPresent()) {
            throw new RuntimeException("仕入先を見つかりませんでした。");
        }
        Vendor vendorToUpdate = vendorOptional.get();
        if (!vendorToUpdate.getName().equals(vendor.getName())) {
            vendorToUpdate.setName(vendor.getName());
        }
        if (!vendorToUpdate.getRepresentative().equals(vendor.getRepresentative())) {
            vendorToUpdate.setRepresentative(vendor.getRepresentative());
        }
        if (!vendorToUpdate.getEmail().equals(vendor.getEmail())) {
            vendorToUpdate.setEmail(vendor.getEmail());
        }
        if (!vendorToUpdate.getTelephoneNo().equals(vendor.getTelephoneNo())) {
            vendorToUpdate.setTelephoneNo(vendor.getTelephoneNo());
        }
        if (!vendorToUpdate.getZipCode().equals(vendor.getZipCode())) {
            vendorToUpdate.setZipCode(vendor.getZipCode());
        }
        if (!vendorToUpdate.getPrefecture().equals(vendor.getPrefecture())) {
            vendorToUpdate.setPrefecture(vendor.getPrefecture());
        }
        if (!vendorToUpdate.getCity().equals(vendor.getCity())) {
            vendorToUpdate.setCity(vendor.getCity());
        }
        if (!vendorToUpdate.getAddress().equals(vendor.getAddress())) {
            vendorToUpdate.setAddress(vendor.getAddress());
        }

        vendorService.saveVendor(vendorToUpdate);
        return "redirect:/vendors/show/" + id;
    }
}
