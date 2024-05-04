package uddbe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchResult {
    List<SearchResultContent> content;
    int total;
    int page;
    int size;
}
