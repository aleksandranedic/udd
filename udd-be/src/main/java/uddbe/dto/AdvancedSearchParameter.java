package uddbe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdvancedSearchParameter {
    String field;
    String value;
    String operator;
    boolean negation;
    boolean phrase;
}
