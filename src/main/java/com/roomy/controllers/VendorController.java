package com.roomy.controllers;

import com.roomy.models.Vendor;
import com.roomy.services.VendorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/vendors")
public class VendorController {
    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {this.vendorService = vendorService;}

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

    @PostMapping
    public String saveVendor(@ModelAttribute Vendor vendor) {
        vendorService.saveVendor(vendor);
        return "redirect:/vendors";
    }
}
