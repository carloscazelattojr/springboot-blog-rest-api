package br.com.carlosjunior.blog.controller;

import br.com.carlosjunior.blog.entity.Role;
import br.com.carlosjunior.blog.entity.User;
import br.com.carlosjunior.blog.payload.JWTAuthResponse;
import br.com.carlosjunior.blog.payload.LoginDto;
import br.com.carlosjunior.blog.payload.SignUpDto;
import br.com.carlosjunior.blog.repository.RoleRepository;
import br.com.carlosjunior.blog.repository.UserRepository;
import br.com.carlosjunior.blog.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

   //private static Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto loginDto){

       Authentication authentication = authenticationManager
               .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(),loginDto.getPassword()));

       SecurityContextHolder.getContext().setAuthentication(authentication);

       // get token form tokenProvider
       String token = tokenProvider.generateToken(authentication);

        //return new ResponseEntity<>("User signed-in successfully!", HttpStatus.OK);
        return ResponseEntity.ok(new JWTAuthResponse(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){
        if (userRepository.existsByUsername(signUpDto.getUsername())){
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByEmail(signUpDto.getEmail())){
            return new ResponseEntity<>("E-mail is already taken!", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setName(signUpDto.getName());
        user.setEmail(signUpDto.getEmail());
        user.setUsername(signUpDto.getUsername());
        user.setPassword(passwordEncoder.encode( signUpDto.getPassword()));

        Role roles = roleRepository.findByName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(roles));

        userRepository.save(user);
        return new ResponseEntity<>("User registerd with sucessfully!",HttpStatus.CREATED);
    }


}
