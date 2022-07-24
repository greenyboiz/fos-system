package fpt.edu.capstone.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Data
@NoArgsConstructor
public class PagingRequest {

    private static final int DEFAULT_PAGE_NUMBER = 1;
    private static final int DEFAULT_PAGE_SIZE = 20;

    private int pageNum = DEFAULT_PAGE_NUMBER;
    private int pageSize = DEFAULT_PAGE_SIZE;

//    private String sort; // field:ASC --> numberOfStudents:ASC;code:ASC

    public Pageable makePageable() {
        return PageRequest.of(pageNum - 1, pageSize);
    }

//    private Sort buildSort() {
//        String[] sortArray = sort.split(";");
//        for (String sortStr : sortArray) {
//            String[] sortSpec = sortStr.split(":");
//            Sort s = Sort.
//        }
//    }
}
