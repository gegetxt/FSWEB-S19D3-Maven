package com.workintech.s19d2.service;

import com.workintech.s19d2.entity.Member;
import com.workintech.s19d2.entity.Role;
import com.workintech.s19d2.repository.MemberRepository;
import com.workintech.s19d2.repository.RoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthenticationService {

    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(MemberRepository memberRepository,
                                 RoleRepository roleRepository,
                                 PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Member register(String email, String password) {
        if (memberRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("User with given email already exist");
        }

        Optional<Role> role = roleRepository.findByAuthority("ADMIN");
        if (role == null || role.isEmpty()) {
            role = roleRepository.findByAuthority("USER");
        }
        if (role == null || role.isEmpty()) {
            throw new RuntimeException("Default role not found");
        }

        Member member = new Member();
        member.setEmail(email);
        member.setPassword(passwordEncoder.encode(password));
        member.setRoles(List.of(role.get()));
        return memberRepository.save(member);
    }
}
