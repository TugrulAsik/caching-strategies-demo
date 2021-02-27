package tr.tugrul.cachedemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SomethingWithoutParams extends AbstractSomethingDTO {

    private String name;
    private Boolean valid;
}
