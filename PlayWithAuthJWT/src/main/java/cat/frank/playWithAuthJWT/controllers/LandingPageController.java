package cat.frank.playWithAuthJWT.controllers;

import cat.frank.playWithAuthJWT.dto.LoginDto;
import cat.frank.playWithAuthJWT.model.Role;
import cat.frank.playWithAuthJWT.model.UserEntity;
import cat.frank.playWithAuthJWT.repository.RoleRepository;
import cat.frank.playWithAuthJWT.repository.UserRepository;
import cat.frank.playWithAuthJWT.dto.RegisterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Security;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class LandingPageController {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public LandingPageController(AuthenticationManager authenticationManager,
                                 UserRepository userRepository,
                                 RoleRepository roleRepository,
                                 PasswordEncoder passwordEncoder){
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/test")
    public String testRequest(){
        Role admin = new Role();
        admin.setRoleName("ADMIN");
        roleRepository.save(admin);
        Role user = new Role();
        user.setRoleName("USER");
        roleRepository.save(user);
        return "Hello Page" + roleRepository.findAll();
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        if(userRepository.existsByUsername(registerDto.getUsername())){
            return new ResponseEntity<>("Username is taken!", HttpStatus.BAD_REQUEST);
        }
        UserEntity user = new UserEntity();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        Role roles = roleRepository.findByRoleName("USER").get();
        user.setRoles(Collections.singletonList(roles));
        userRepository.save(user);
        return new ResponseEntity<>("User registered success", HttpStatus.OK);
    }

    @GetMapping("checkUserRecords")
    public ResponseEntity<List<UserEntity>> checkUserRecords(){
        List<UserEntity> userList =  userRepository.findAll();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        // the contextHolder will hold all the authentication information, so user will not need to keep login
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User signed success!", HttpStatus.OK);
    }
}
