package com.david.springsecrest.services.serviceImpls;

import com.david.springsecrest.enums.EUserStatus;
import com.david.springsecrest.exceptions.AppException;
import com.david.springsecrest.exceptions.BadRequestException;
import com.david.springsecrest.exceptions.ResourceNotFoundException;
import com.david.springsecrest.helpers.MailService;
import com.david.springsecrest.helpers.Utility;
import com.david.springsecrest.models.User;
import com.david.springsecrest.payload.response.JwtAuthenticationResponse;
import com.david.springsecrest.security.JwtTokenProvider;
import com.david.springsecrest.services.IAuthService;
import com.david.springsecrest.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {

    private final IUserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final MailService mailService;

    @Override
    public JwtAuthenticationResponse login(String email, String password) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(email, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = null;
        try {
            jwt = jwtTokenProvider.generateToken(authentication);
        } catch (Exception e) {
            throw new AppException("Error generating token", e);
        }
        User user = this.userService.getByEmail(email);
        return new JwtAuthenticationResponse(jwt, user);
    }

//    @Override
//    public void resetPassword(String email, String passwordResetCode, String newPassword) {
//        User user = this.userService.getByEmail(email);
//        if (Utility.isCodeValid(user.getActivationCode(), passwordResetCode) &&
//                (user.getStatus().equals(EUserStatus.RESET)) || user.getStatus().equals(EUserStatus.PENDING)) {
//            user.setPassword(passwordEncoder.encode(newPassword));
//            user.setActivationCode(Utility.randomUUID(6, 0, 'N'));
//            user.setStatus(EUserStatus.ACTIVE);
//            this.userService.save(user);
//            this.mailService.sendPasswordResetSuccessfully(user.getEmail(), user.getFullName());
//        } else {
//            throw new BadRequestException("Invalid code or account status");
//        }
//    }
//
//    @Override
//    public void initiateAccountVerification(String email) {
//        User user = this.userService.getByEmail(email);
//        if (user.getStatus() == EUserStatus.ACTIVE) {
//            throw new BadRequestException("User is already verified");
//        }
//        String verificationCode;
//        do {
//            verificationCode = Utility.generateAuthCode();
//        } while (this.userService.findByActivationCode(verificationCode).isPresent());
//        LocalDateTime verificationCodeExpiresAt = LocalDateTime.now().plusHours(6);
//        user.setActivationCode(verificationCode);
//        user.setActivationCodeExpiresAt(verificationCodeExpiresAt);
//        this.mailService.sendActivateAccountEmail(user.getEmail(), user.getFullName(), verificationCode);
//        this.userService.save(user);
//    }
//
//    @Override
//    public void verifyAccount(String verificationCode) {
//        Optional<User> _user = this.userService.findByActivationCode(verificationCode);
//        if (_user.isEmpty()) {
//            throw new ResourceNotFoundException("User", verificationCode, verificationCode);
//        }
//        User user = _user.get();
//        if (user.getActivationCodeExpiresAt().isBefore(LocalDateTime.now())) {
//            throw new BadRequestException("Verification code is invalid or expired");
//        }
//        user.setStatus(EUserStatus.ACTIVE);
//        user.setActivationCodeExpiresAt(null);
//        user.setActivationCode(null);
//        this.mailService.sendAccountVerifiedSuccessfullyEmail(user.getEmail(), user.getFullName());
//        this.userService.save(user);
//    }


}
