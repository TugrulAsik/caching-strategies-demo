package tr.tugrul.cachedemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Something implements Serializable {

    private Long id;
    private String name;
    private Boolean valid;
    private List<String> extraParams;
    /* ASSUME AS LAZY */
    private List<String> extraLazyParams;

    public Something() {
        this.extraParams = new ArrayList<>();
        this.extraLazyParams = new ArrayList<>();
    }

    public void addToExtraParams(String oneParam) {
        this.extraParams.add(oneParam);
    }

    public void addToExtraLazyParams(String oneLazyParam) {
        this.extraLazyParams.add(oneLazyParam);
    }
}
