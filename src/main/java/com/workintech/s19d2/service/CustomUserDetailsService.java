package com.workintech.s19d2.service;

import com.workintech.s19d2.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final String basicUsername;
    private final String basicPassword;
    private final String basicAuthority;

    public CustomUserDetailsService(MemberRepository memberRepository,
                                    PasswordEncoder passwordEncoder,
                                    @Value("${app.security.basic.username:workintech}") String basicUsername,
                                    @Value("${app.security.basic.password:workintech123}") String basicPassword,
                                    @Value("${app.security.basic.authority:ADMIN}") String basicAuthority) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.basicUsername = basicUsername;
        this.basicPassword = basicPassword;
        this.basicAuthority = basicAuthority;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (basicUsername.equals(username)) {
            return User.withUsername(basicUsername)
                    .password(passwordEncoder.encode(basicPassword))
                    .authorities(basicAuthority)
                    .build();
        }

        return memberRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}
