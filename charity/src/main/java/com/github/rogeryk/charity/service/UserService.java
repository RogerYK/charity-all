package com.github.rogeryk.charity.service;

import com.github.rogeryk.charity.domain.Project;
import com.github.rogeryk.charity.domain.Transaction;
import com.github.rogeryk.charity.domain.User;
import com.github.rogeryk.charity.domain.form.UserInfo;
import com.github.rogeryk.charity.exception.ServiceException;
import com.github.rogeryk.charity.repository.ProjectRepository;
import com.github.rogeryk.charity.repository.TransactionRepository;
import com.github.rogeryk.charity.repository.UserRepository;
import com.github.rogeryk.charity.utils.ErrorCodes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.management.ServiceNotFoundException;
import java.util.List;

@Slf4j
@Service
public class UserService implements UserDetailsService {

//    @Autowired
//    private BubiService bubiService;

    private static PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public void registerUser(String phoneNumber, String password){
        User user = new User();
        user.setPhoneNumber(phoneNumber);
        password = passwordEncoder.encode(password);
        user.setPassword(password);
        userRepository.save(user);
        user = userRepository.findByPhoneNumber(phoneNumber);
        user.setNickName(String.format("CS_%06d", user.getId()));
        userRepository.save(user);
    }

    public User findUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber );
    }

    public User findById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByPhoneNumber(s);
        if (user == null) throw new UsernameNotFoundException(s);
        return user;
    }

    public void favorProject(User user, Long projectId) {
        Project project = new Project();
        project.setId(projectId);
        user.getFavorProjects().add(project);
        userRepository.save(user);
    }

    public UserInfo getUserInfo(Long id) {
        UserInfo userInfo = new UserInfo();
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorCodes.USER_NOT_EXIST, "用户不存在"));
        userInfo.setAvatar(user.getAvatar());
        userInfo.setAddress(user.getAddress());
        userInfo.setDonatedCount(transactionRepository.countAllByPayerEqualsAndType(user, Transaction.TransactionType.Donation));
        userInfo.setNickname(user.getNickName());
        userInfo.setPresentation(user.getProfession());
        userInfo.setReleasedProjectCount(projectRepository.countProjectByAuthor(user));
        return userInfo;
    }

    public UserInfo getUserInfo(String phoneNumber) {
        UserInfo userInfo = new UserInfo();
        User user = userRepository.findByPhoneNumber(phoneNumber);
        userInfo.setAvatar(user.getAvatar());
        userInfo.setAddress(user.getAddress());
        userInfo.setDonatedCount(transactionRepository.countAllByPayerEqualsAndType(user, Transaction.TransactionType.Donation));
        userInfo.setNickname(user.getNickName());
        userInfo.setPresentation(user.getPresentation());
        userInfo.setReleasedProjectCount(projectRepository.countProjectByAuthor(user));
        return userInfo;
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public Page<Transaction> getDonationRecord(User user, Pageable pageable) {
        if (pageable == null) {
            pageable = PageRequest.of(0, 10,
                    Sort.by(Sort.Direction.ASC, "time"));
        }
        return transactionRepository.
                findAllByPayerIsAndType(user, Transaction.TransactionType.Donation, pageable);
    }


}
