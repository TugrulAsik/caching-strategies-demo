package tr.tugrul.cachedemo.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tr.tugrul.cachedemo.entity.Something;

import java.util.Random;
import java.util.UUID;

@Slf4j
@Component
public class SomethingRepository {

    public Something findByTypeAsFast(String dummyType) {
        log.info("DB hit for {}", dummyType);
        sleepSafely(200);
        return new Something(1L, "I'm fast!", Boolean.TRUE, null, null);
    }

    public Something findByTypeAsSlow(String dummyType) {
        log.info("DB hit for {}", dummyType);
        sleepSafely(12000);
        return new Something(2L, "I'm sss  ll oooow!", Boolean.TRUE, null, null);
    }

    public Something findByTypeAsRandom(String dummyType) {
        log.info("DB hit for {}", dummyType);
        int millis = new Random().nextInt(20000) + 1;
        sleepSafely(Long.valueOf(millis));
        return new Something(3L, "I'm RanDOM!", Boolean.TRUE, null, null);
    }

    //Fetch just what you need
    public Something findByTypeAsRandomWithValidAttr(String dummyType) {
        log.info("DB hit for {}", dummyType);
        int millis = new Random().nextInt(20000) + 1;
        sleepSafely(Long.valueOf(millis));
        return new Something(3L, "I'm RanDOM!", Boolean.TRUE, null, null);
    }

    public Something findByTypeAsHuge(String dummyType) {
        log.info("DB hit for {}", dummyType);
        int millis = new Random().nextInt(2000) + 1;
        sleepSafely(Long.valueOf(millis));
        Something something = new Something();
        something.setId(101L);
        something.setName("I'm H-U-G-E !!!");
        something.setValid(Boolean.FALSE);

        for (int i = 0; i < 10000; i++) {
            something.addToExtraParams(UUID.randomUUID().toString());
        }
        return something;
    }

    private void sleepSafely(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
