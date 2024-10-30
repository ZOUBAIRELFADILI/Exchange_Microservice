package com.exchange.achat.web;

import com.exchange.achat.dto.AchatDTO;
import com.exchange.achat.dto.AchatReq;
import com.exchange.achat.service.AchatServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/achats")
public class AchatController {

    @Autowired
    private AchatServiceImp achatService;

    // Create a new Achat
    @PostMapping
    public ResponseEntity<AchatDTO> addAchat(@RequestBody AchatReq achatReq) {
        AchatDTO createdAchat = achatService.addAchat(achatReq);
        return ResponseEntity.ok(createdAchat);
    }

    // Get Achat by ID
    @GetMapping("/{id}")
    public ResponseEntity<AchatDTO> getAchatById(@PathVariable Long id) {
        AchatDTO achatDTO = achatService.getAchatbyId(id);
        return ResponseEntity.ok(achatDTO);
    }

    // Update an existing Achat
    @PutMapping("/{id}")
    public ResponseEntity<AchatDTO> updateAchat(@PathVariable Long id, @RequestBody AchatDTO achatDTO) {
        AchatDTO updatedAchat = achatService.updateAchat(id, achatDTO);
        return ResponseEntity.ok(updatedAchat);
    }
    // Get all Achats
    @GetMapping
    public ResponseEntity<List<AchatDTO>> getAllAchats() {
        List<AchatDTO> achats = achatService.getAllAchat();
        return ResponseEntity.ok(achats);
    }

    // Delete an Achat by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAchat(@PathVariable Long id) {
        achatService.deleteAchat(id);
        return ResponseEntity.noContent().build();
    }
}



