package com.inventory.dto;

import java.util.List;
import com.inventory.Entity.Movement;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VerifyResponseDto {

    private String message;

    private Boolean valid;

    private List<Movement> data;
}