package com.inventory.controller;

import com.inventory.Entity.Movement;
import com.inventory.dto.VerifyResponseDto;
import com.inventory.service.MovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class MovementController {

    @Autowired
    private MovementService movementService;

    @GetMapping("/movements")
    public ResponseEntity<List<Movement>>
    getMovements(@RequestParam String from,@RequestParam String to,@RequestParam(required = false)String type,
            @RequestParam(required = false)String warehouse) throws Exception {

        List<Movement> movements =movementService.getMovements(from,to,type,warehouse);

        return ResponseEntity.ok(movements);
    }

    @PostMapping("/verify-file")
    public ResponseEntity<VerifyResponseDto>
    verifyFile(@RequestParam("file")MultipartFile file, @RequestParam("sha256") String sha256)throws Exception{
        List<Movement> movements = movementService.verifyAndSaveFile(file, sha256);

        VerifyResponseDto response =new VerifyResponseDto( "SHA Validation Success",true, movements);

        return ResponseEntity.ok(response);
    }
}