package fpt.edu.capstone.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CategoryDTO {
    @JsonProperty
    private String categoryName;
}
