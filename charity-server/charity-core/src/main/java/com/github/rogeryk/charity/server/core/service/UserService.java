package com.github.rogeryk.charity.server.core.service;

import com.github.rogeryk.charity.server.core.bumo.BumoService;
import com.github.rogeryk.charity.server.core.bumo.util.AccountResult;
import com.github.rogeryk.charity.server.core.exception.ServiceException;
import com.github.rogeryk.charity.server.core.search.repository.UserDocumentRepository;
import com.github.rogeryk.charity.server.core.util.ErrorCodes;
import com.github.rogeryk.charity.server.db.domain.Project;
import com.github.rogeryk.charity.server.db.domain.Transaction;
import com.github.rogeryk.charity.server.db.domain.User;
import com.github.rogeryk.charity.server.db.domain.vo.PageData;
import com.github.rogeryk.charity.server.db.domain.vo.UserInfo;
import com.github.rogeryk.charity.server.db.repository.ProjectRepository;
import com.github.rogeryk.charity.server.db.repository.TransactionRepository;
import com.github.rogeryk.charity.server.db.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
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
    @Autowired
    private BumoService bumoService;
    @Autowired
    private UserDocumentRepository userDocumentRepository;

    @Transactional
    public void registerUser(String phoneNumber, String password){
        User user = new User();
        user.setPhoneNumber(phoneNumber);
        password = passwordEncoder.encode(password);
        user.setPassword(password);
        user.setUserStatus(User.UserStatus.Creating);
        userRepository.save(user);
        user = userRepository.findByPhoneNumber(phoneNumber);
        user.setNickName(String.format("CS_%06d", user.getId()));

        AccountResult account = bumoService.createActiveAccount();
        user.setBumoAddress(account.getAddress());
        user.setBumoPrivateKey(account.getPrivateKey());
        user.setActiveHash(account.getHash());
        userRepository.saveAndFlush(user);
    }

    public User findUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber );
    }

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(ErrorCodes.USER_NOT_EXIST, "用户不存在"));
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
        user.getFollowProjects().add(project);
        userRepository.save(user);
    }

    public UserInfo getUserInfo(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorCodes.USER_NOT_EXIST, "用户不存在"));
        int donatedCount =  transactionRepository.countAllByPayer_IdEqualsAndType(user.getId(), Transaction.TransactionType.Donation);
        int releasedProjectCount = projectRepository.countProjectByAuthor(user);
        return UserInfo.from(user, donatedCount, releasedProjectCount);
    }

    public UserInfo getUserInfo(String phoneNumber) {
        User user = userRepository.findByPhoneNumber(phoneNumber);
        int donatedCount =  transactionRepository.countAllByPayer_IdEqualsAndType(user.getId(), Transaction.TransactionType.Donation);
        int releasedProjectCount = projectRepository.countProjectByAuthor(user);
        return UserInfo.from(user, donatedCount, releasedProjectCount);
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
                findAllByPayer_IdAndType(user.getId(), Transaction.TransactionType.Donation, pageable);
    }

    public PageData<User> list(Long id, String nickName, String phoneNumber, Pageable pageable) {
        Specification<User> specification = (Specification<User>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (id != null) {
                predicateList.add(criteriaBuilder.equal(root.get("id"), id));
            }
            if (!StringUtils.isEmpty(nickName)) {
                predicateList.add(criteriaBuilder.equal(root.get("nickName"), nickName));
            }
            if (!StringUtils.isEmpty(phoneNumber)) {
                predicateList.add(criteriaBuilder.equal(root.get("phoneNumber"), phoneNumber));
            }
            Predicate[] predicates = new Predicate[predicateList.size()];
            predicateList.toArray(predicates);
            return criteriaBuilder.and(predicates);
        };
        return PageData.of(userRepository.findAll(specification, pageable));
    }
}
