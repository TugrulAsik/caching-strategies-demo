package tr.tugrul.cachedemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Something extends AbstractSomethingDTO {

    private String name;
    private Boolean valid;
    private List<String> extraParams = Collections.emptyList();
    private List<String> extraLazyParams = Collections.emptyList();
}
