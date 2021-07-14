package com.example.appealsservice.dto.response;

import com.example.appealsservice.domain.CostCat;
import lombok.Data;

@Data
public class CostCatDto {
    public Long id;
    public String name;

    public CostCatDto() {

    }

    public CostCatDto(CostCat costCat) {
        if (costCat == null)
            return;
        id = costCat.getId();
        name = costCat.getName();
    }
}
