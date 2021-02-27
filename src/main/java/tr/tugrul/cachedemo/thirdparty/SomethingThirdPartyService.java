package tr.tugrul.cachedemo.thirdparty;

import org.springframework.stereotype.Service;

@Service
public class SomethingThirdPartyService {

    public String calculateReversed(String word) {
        return new StringBuffer(word).reverse().toString();
    }
}
