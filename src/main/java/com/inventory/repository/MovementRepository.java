package com.inventory.repository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inventory.Entity.Movement;

@Repository
public class MovementRepository {

    private static final String FILE_PATH = "src/main/resources/data/movements.json";

    private final ObjectMapper mapper =new ObjectMapper();

    public List<Movement> findAll()throws Exception {

        File file = new File(FILE_PATH);

        return mapper.readValue(file,new TypeReference<List<Movement>>() {});
    }

    public void saveAll(List<Movement> movements)throws Exception {

        mapper.writerWithDefaultPrettyPrinter()
                .writeValue(new File(FILE_PATH),movements);
    }
}