package tr.tugrul.cachedemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

@Component
public class SomethingKeyGenerator implements KeyGenerator {

    @Autowired
    private Environment environment;

    @Value("${cache.version:1}")
    private String version;

    @Override
    public Object generate(Object target, Method method, Object... params) {
        return prepareCachePrefix() + "_" + this.version + "_" + target.getClass().getSimpleName() + "_"
                + method.getName() + "_"
                + StringUtils.arrayToDelimitedString(params, "_");
    }

    private String prepareCachePrefix() {
        List<String> profiles = Arrays.asList(environment.getActiveProfiles());
        if (CollectionUtils.isEmpty(profiles)) {
            return "dev_";
        }
        return String.join("_", profiles);
    }
}
