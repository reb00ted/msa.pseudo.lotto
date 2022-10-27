package msa.pseudo.lotto.userserivce.controller;

import lombok.RequiredArgsConstructor;
import msa.pseudo.lotto.userserivce.dto.*;
import msa.pseudo.lotto.userserivce.kafka.KafkaProducer;
import msa.pseudo.lotto.userserivce.kafka.UserCreationEvent;
import msa.pseudo.lotto.userserivce.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final KafkaProducer kafkaProducer;

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @PostMapping("/join")
    public ResponseEntity<JoinResponse> join(
            @RequestBody @Validated JoinRequest joinRequest,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        System.out.println(joinRequest.toString());
        JoinResponse response = userService.join(joinRequest.getUserId(), joinRequest.getPassword());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody @Validated LoginRequest loginRequest,
            BindingResult bindingResult)
    {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        System.out.println(loginRequest.toString());
        LoginResponse user = userService.login(loginRequest.getUserId(), loginRequest.getPassword());
        return ResponseEntity.ok(user);
    }

    @GetMapping("/test/{hello}")
    public ResponseEntity<String> hello(@PathVariable("hello") String hello) {
        kafkaProducer.sendMessage(new UserCreationEvent(hello));
        return ResponseEntity.ok(hello);
    }
}
