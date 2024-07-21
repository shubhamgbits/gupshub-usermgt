package com.gupshub.usermanagement.jwt;

import com.gupshub.usermanagement.model.entity.UserInfo;
import com.gupshub.usermanagement.dao.repository.UserManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * This class is a mandatory implementation needed if we are creating
 * role based authentication in Spring Security using dedicated DB
 */
@Service
public class GSUserDetailService implements UserDetailsService {

    @Autowired
    private UserManagementRepository userManagementRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userInfoOptional = userManagementRepository.findByUsername(username.toLowerCase());

        if(userInfoOptional.isPresent()){
            UserInfo userInfo = userInfoOptional.get();
            return User.builder()
                    .username(userInfo.getUsername())
                    .password(userInfo.getPassword())
                    .roles(getRoles(userInfo))
                    .build();
        }
        else {
            throw new UsernameNotFoundException("User Not Available");
        }
    }

    private String[] getRoles(UserInfo userInfo) {
        if(userInfo.getRole() == null)
            return new String[]{"USER"};

        return userInfo.getRole().split(",");
    }
}
