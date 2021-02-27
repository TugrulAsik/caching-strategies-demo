package tr.tugrul.cachedemo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tr.tugrul.cachedemo.service.SomethingService;

@RestController
@RequestMapping("/demo")
@RequiredArgsConstructor
public class SomethingController {

    private final SomethingService somethingService;

    @GetMapping("/something/type/directly")
    public ResponseEntity listSthFromOrigin() {
        return ResponseEntity.ok(somethingService.findSomethingDirectly("DIRECT"));
    }

    @GetMapping("/something/type/fast")
    public ResponseEntity listSthFastFromCache() {
        return ResponseEntity.ok(somethingService.findSomethingFast("FAST"));
    }

    @GetMapping("/something/type/slow")
    public ResponseEntity listSthSlowFromCache() {
        return ResponseEntity.ok(somethingService.findSomethingSlow("SLOW"));
    }

    @GetMapping("/something/type/random")
    public ResponseEntity listSthRandomFromCache() {
        return ResponseEntity.ok(somethingService.findSomethingRandom("RANDOM"));
    }

    @GetMapping("/something/type/in-memory")
    public ResponseEntity listSthFromInMemoryCache() {
        return ResponseEntity.ok(somethingService.findSomethingInMemory("IN_MEMORY"));
    }

    @GetMapping("/something/type/reversed")
    public ResponseEntity listSthReversedFromCache() {
        return ResponseEntity.ok(somethingService.reverseNameOfSomethingFromThirdParty("REVERSED"));
    }

    @GetMapping("/something/type/huge")
    public ResponseEntity listSthHugeFromCache() {
        return ResponseEntity.ok(somethingService.findSomethingHuge("HUGE"));
    }
}
