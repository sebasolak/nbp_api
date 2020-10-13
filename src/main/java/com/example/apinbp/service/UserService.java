package com.example.apinbp.service;


import com.example.apinbp.dao.ExchangeRequestDao;
import com.example.apinbp.dao.UserDao;
import com.example.apinbp.model.Role;
import com.example.apinbp.model.User;
import com.example.apinbp.model.dtos.UserRegistrationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final ExchangeRequestDao exchangeRequestDao;

    @Autowired
    public UserService(@Qualifier("userDao") UserDao userDao,
                       @Lazy PasswordEncoder passwordEncoder,
                       @Qualifier("exchangeRequestDao") ExchangeRequestDao exchangeRequestDao) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.exchangeRequestDao = exchangeRequestDao;
    }

    public User save(UserRegistrationDTO userRegistrationDTO) {
        User user = new User(userRegistrationDTO.getLogin(),
                userRegistrationDTO.getEmail(),
                passwordEncoder.encode(userRegistrationDTO.getPassword()),
                Arrays.asList(new Role("ROLE_USER")));

        return userDao.save(user);
    }

    public String selectUserEmailByUserLogin(String login) {
        return userDao.selectUserEmailByUserLogin(login);
    }

    public List<String> selectOperationsByUserLogin(String login) {
        return exchangeRequestDao.selectOperationsByUserLogin(login);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userDao.findByLogin(username);

        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        return new org.springframework.security.core.userdetails
                .User(user.getLogin(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
