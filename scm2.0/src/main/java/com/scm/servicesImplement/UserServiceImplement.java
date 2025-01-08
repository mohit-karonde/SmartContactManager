package com.scm.servicesImplement;

import java.util.List;
import java.util.Optional;
import java.util.UUID;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.scm.Services.UserService;
import com.scm.entities.User;
import com.scm.payload.AppConstants;
import com.scm.payload.Helper;
import com.scm.repositories.UserRepo;

@Service
public class UserServiceImplement implements UserService{

    @Autowired
    UserRepo userRepo;
    
    @Autowired
    private PasswordEncoder passEncoder;

    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
    private  Helper helper;

  //  private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public User saveUser(User user) {
          // user id : have to generate
          String userId = UUID.randomUUID().toString();
          user.setUserId(userId);
          // password encode
          // user.setPassword(userId);
          user.setPassword(passEncoder.encode(user.getPassword()));
  
          // set the user role
  
          user.setRoleList(List.of(AppConstants.ROLE_USER));
  
    //      logger.info(user.getProvider().toString());
          String emailToken = UUID.randomUUID().toString();
          user.setEmailToken(emailToken);
          User savedUser = userRepo.save(user);
          String emailLink = helper.getLinkForEmailVerificatiton(emailToken);
          emailService.sendEmail(savedUser.getEmail(), "Verify Account : Smart  Contact Manager", emailLink);
          return savedUser;


    }

    @Override
    public Optional<User> getUserById(String id) {
      
        return userRepo.findById(id);
    }

    @Override
    public Optional<User> updateUser(User user) {
        User newUser = userRepo.findById(user.getUserId()).orElseThrow(() -> new  ResourceAccessException( "user not found" ));
       
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        newUser.setAbout(user.getAbout());
        newUser.setPhoneNumber(user.getPhoneNumber());
        newUser.setProfilePic(user.getProfilePic());
        newUser.setEnabled(false);
        newUser.setEmailVerified(user.isEmailVerified());
        newUser.setPhoneVerified(user.isPhoneVerified());
        newUser.setProvider(user.getProvider());
        newUser.setProviderUserId(user.getProviderUserId());

        User save = userRepo.save(newUser);
        return Optional.ofNullable(save);


    }

    @Override
    public void deleteUser(String id) {

        User newUser = userRepo.findById(id).orElseThrow(() -> new  ResourceAccessException( "user not found" ));

        userRepo.delete(newUser);
        
       
    }

    @Override
    public boolean isUserExist(String userId) {
        User user2 = userRepo.findById(userId).orElse(null);
        return user2 != null ? true : false;
         
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        User user = userRepo.findByEmail(email).orElse(null);
        return user != null ? true : false;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email).orElse(null);

    
    }

}
