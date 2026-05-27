package com.inventory.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.inventory.Entity.Movement;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MovementService {

    List<Movement> getMovements(
            String from,
            String to,
            String type,
            String warehouse
    ) throws Exception;

    List<Movement> verifyAndSaveFile(
            MultipartFile file,
            String sha256
    ) throws Exception;
}