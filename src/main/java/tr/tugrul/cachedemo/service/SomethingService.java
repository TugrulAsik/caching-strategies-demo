package tr.tugrul.cachedemo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import tr.tugrul.cachedemo.dto.SomethingWithoutParams;
import tr.tugrul.cachedemo.entity.Something;
import tr.tugrul.cachedemo.repository.SomethingRepository;
import tr.tugrul.cachedemo.thirdparty.SomethingThirdPartyService;

import javax.annotation.PostConstruct;

@Slf4j
@Service
@RequiredArgsConstructor
public class SomethingService {

    private final SomethingRepository somethingRepository;
    private final SomethingThirdPartyService somethingThirdPartyService;
    private final ApplicationContext applicationContext;

    private SomethingService self;

    @PostConstruct
    public void init() {
        self = applicationContext.getBean(SomethingService.class);
    }

    public Something findSomethingDirectly(String dummyType) {
        log.info("Service hit for {}", dummyType);
        return somethingRepository.findByTypeAsRandom(dummyType);
    }

    @Cacheable(cacheManager = "inMemoryFiveSecondsCache", cacheNames = "something")
    public Something findSomethingInMemory(String dummyType) {
        log.info("Service hit for {}", dummyType);
        return somethingRepository.findByTypeAsRandom(dummyType);
    }

    @Cacheable(cacheManager = "twentySecondsCache", cacheNames = "something")
    public Something findSomethingFast(String dummyType) {
        log.info("Service hit for {}", dummyType);
        return somethingRepository.findByTypeAsFast(dummyType);
    }

    @Cacheable(cacheManager = "twentySecondsCache", cacheNames = "something")
    public Something findSomethingSlow(String dummyType) {
        log.info("Service hit for {}", dummyType);
        self.findSomethingSlow2(dummyType);
        return somethingRepository.findByTypeAsSlow(dummyType);
    }

    @Cacheable(cacheManager = "twentySecondsCache", cacheNames = "something2")
    public Something findSomethingSlow2(String dummyType) {
        log.info("Service hit for {}", dummyType);
        return somethingRepository.findByTypeAsSlow(dummyType);

    }

    @Cacheable(cacheManager = "twentySecondsCache", cacheNames = "something")
    public SomethingWithoutParams findSomethingWithoutParams(String dummyType) {
        log.info("Service hit for {}", dummyType);
        Something something = somethingRepository.findByTypeAsRandom(dummyType);
        SomethingWithoutParams withoutParams = new SomethingWithoutParams();
        BeanUtils.copyProperties(something, withoutParams);
        return withoutParams;
    }

    @Cacheable(cacheManager = "twentySecondsCache", cacheNames = "something")
    public Something findSomethingRandom(String dummyType) {
        log.info("Service hit for {}", dummyType);
        return somethingRepository.findByTypeAsRandom(dummyType);
    }

    @Cacheable(cacheManager = "twentySecondsCache", cacheNames = "something")
    public String reverseNameOfSomethingFromThirdParty(String dummyKey) {
        SomethingWithoutParams somethingWithoutParams = self.findSomethingWithoutParams(dummyKey);
        return somethingThirdPartyService.calculateReversed(somethingWithoutParams.getName());
    }

    @Cacheable(cacheManager = "twentySecondsCache", cacheNames = "something")
    public Something findSomethingHuge(String dummyKey) {
        return somethingRepository.findByTypeAsHuge(dummyKey);
    }
}
