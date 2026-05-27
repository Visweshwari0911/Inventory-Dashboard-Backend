package com.inventory.serviceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inventory.Entity.Movement;
import com.inventory.exception.InvalidSHAException;
import com.inventory.repository.MovementRepository;
import com.inventory.service.MovementService;
import com.inventory.utility.SHA256Util;

@Service
public class MovementServiceImpl implements MovementService {

    @Autowired
    private MovementRepository repository;

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public List<Movement> getMovements(String from,
            String to,
            String type,
            String warehouse
    ) throws Exception {

        List<Movement> movements = repository.findAll();

        return movements.stream().filter(movement -> {

                    LocalDate movementDate= LocalDate.parse(movement.getTimestamp().substring(0, 10));

                    LocalDate fromDate = LocalDate.parse(from);

                    LocalDate toDate = LocalDate.parse(to);

                    boolean dateMatch =!movementDate.isBefore(fromDate)&&!movementDate.isAfter(toDate);

                    boolean typeMatch =type == null||type.equalsIgnoreCase("ALL") || movement.getMovementType() .equalsIgnoreCase(type);

                    boolean warehouseMatch =warehouse == null ||warehouse.isEmpty()  || movement.getWarehouse().equalsIgnoreCase(warehouse);

                    return dateMatch &&typeMatch&& warehouseMatch;
                })

                .collect(Collectors.toList());
    }

    @Override
    public List<Movement> verifyAndSaveFile(MultipartFile file,String sha256) throws Exception {

        byte[] fileBytes =file.getBytes();

        String generatedHash =SHA256Util.generateSHA256(fileBytes);

        if (!generatedHash.equalsIgnoreCase(sha256)) {

            throw new InvalidSHAException("SHA Validation Failed" );
        }

        List<Movement> movements =mapper.readValue(fileBytes,new TypeReference<List<Movement>>() {});

        repository.saveAll(movements);

        return movements;
    }
    
}