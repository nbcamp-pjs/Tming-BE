package com.spring.tming.global.security;

import com.spring.tming.domain.user.entity.User;
import com.spring.tming.domain.user.repository.UserRepository;
import com.spring.tming.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.spring.tming.global.meta.ResultCode.NOT_FOUND_USER;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user =
                userRepository.findByEmail(email);

        if (user == null) {
            throw new GlobalException(NOT_FOUND_USER);
        }
        return new UserDetailsImpl(user);
    }

    public User UserById(Long userId) {
        User user = userRepository
                .findByUserId(userId);
        if (user == null) {
            throw new GlobalException(NOT_FOUND_USER);
        }
        return user;
    }
}
