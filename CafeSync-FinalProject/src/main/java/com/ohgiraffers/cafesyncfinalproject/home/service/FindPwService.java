package com.ohgiraffers.cafesyncfinalproject.home.service;

import com.ohgiraffers.cafesyncfinalproject.home.dao.AccountRepository;
import com.ohgiraffers.cafesyncfinalproject.home.entity.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FindPwService {

    private final AccountRepository accountRepository;
    private final StringRedisTemplate redisTemplate;
    private final JavaMailSender mailSender;
    private final PasswordEncoder passwordEncoder;

    // 이메일로 인증번호 생성 & 전송
    public void sendAuthCode(String userId, String email) {
        // 8자리 랜덤 인증번호 생성
        String authCode = generateAuthCode(8);

        // Redis에 저장 (5분 동안 유효)
        redisTemplate.opsForValue().set("AUTH_CODE_" + email, authCode, Duration.ofMinutes(5));

        // 이메일 전송
        sendEmail(email, authCode);
    }

    // 8자리 랜덤 인증번호 생성 (대문자+숫자 조합)
    private String generateAuthCode(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }

    // HTML 이메일 전송 로직 (SimpleMailMessage → HTML 이메일로 변경)
    private void sendEmail(String email, String authCode) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            String htmlContent = "<!DOCTYPE html>"
                    + "<html lang='ko'>"
                    + "<head>"
                    + "    <meta charset='UTF-8'>"
                    + "    <meta name='viewport' content='width=device-width, initial-scale=1.0'>"
                    + "    <title>비밀번호 찾기 인증번호</title>"
                    + "    <style>"
                    + "        body { font-family: 'Arial', sans-serif; background-color: #f4f4f4; text-align: center; padding: 40px; }"
                    + "        .container { max-width: 500px; background: white; padding: 20px; border-radius: 10px; "
                    + "            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1); margin: auto; }"
                    + "        h2 { color: #333; }"
                    + "        p { font-size: 16px; color: #555; }"
                    + "        .auth-code { font-size: 24px; font-weight: bold; color: #007bff; background: #f0f0f0; "
                    + "            padding: 10px 20px; display: inline-block; border-radius: 5px; margin-top: 10px; }"
                    + "        .footer { font-size: 12px; color: #999; margin-top: 20px; }"
                    + "    </style>"
                    + "</head>"
                    + "<body>"
                    + "    <div class='container'>"
                    + "        <h2>🔐 비밀번호 찾기 인증번호</h2>"
                    + "        <p>안녕하세요, <b>" + email + "</b>님!</p>"
                    + "        <p>비밀번호 찾기를 위해 아래 인증번호를 입력해주세요.</p>"
                    + "        <p class='auth-code'>" + authCode + "</p>"
                    + "        <p>(인증번호는 <b>5분</b> 동안만 유효합니다.)</p>"
                    + "        <div class='footer'>"
                    + "            이 메일은 자동 발송되었으며, 회신하지 마세요.<br>"
                    + "            문제가 발생하면 고객센터에 문의하세요."
                    + "        </div>"
                    + "    </div>"
                    + "</body>"
                    + "</html>";

            helper.setFrom("your-email@gmail.com"); // 발신자 이메일 (설정 가능)
            helper.setTo(email);
            helper.setSubject("🔐 비밀번호 찾기 인증번호");
            helper.setText(htmlContent, true); // HTML 형식으로 전송

            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 입력한 인증번호 검증
    public boolean verifyAuthCode(String email, String inputCode) {
        String storedCode = redisTemplate.opsForValue().get("AUTH_CODE_" + email);

        if (storedCode != null && storedCode.equals(inputCode)) {
            redisTemplate.delete("AUTH_CODE_" + email); // 인증 성공 후 Redis에서 삭제
            return true;
        }
        return false;
    }

    // 아이디와 이메일 동일한지 검증
    public boolean validateUser(String userId, String email) {
        return accountRepository.existsByUserIdAndEmail(userId, email); // 아이디 & 이메일이 DB에 존재하는지 확인
    }

    // ✅ 새 비밀번호 업데이트
    public boolean updateUserPassword(String userId, String newPassword) {
        Optional<Account> accountOpt = accountRepository.findByUserId(userId);

        if (accountOpt.isPresent()) {
            // ✅ 비밀번호 암호화 후 업데이트
            String encodedPassword = passwordEncoder.encode(newPassword);
            accountRepository.updatePassword(userId, encodedPassword);
            return true;
        }
        return false;
    }
}
