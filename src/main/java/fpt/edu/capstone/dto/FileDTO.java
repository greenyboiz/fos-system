package fpt.edu.capstone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileDTO {

    private String fieldName;
    private String fileName;
    private String contentType;
    private Double size;
    private String repository;
    private String tempFile;
}
