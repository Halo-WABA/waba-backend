package com.halo.eventer.global.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {

    @GetMapping("/api/test/method")
    public ResponseEntity<String> getTest() {
        return ResponseEntity.ok("GET success");
    }

    @PostMapping("/api/test/requestBody")
    public ResponseEntity<String> postTest(@RequestBody TestDto testDto){
        return ResponseEntity.ok("POST success");
    }

    @GetMapping("/api/test/requestParam")
    public ResponseEntity<String> postTestParam(@RequestParam int param){
        return ResponseEntity.ok("POST success");
    }
}
