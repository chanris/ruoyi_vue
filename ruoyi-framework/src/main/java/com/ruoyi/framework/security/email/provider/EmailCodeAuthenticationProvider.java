package com.ruoyi.framework.security.email.provider;

import com.ruoyi.framework.security.email.EmailCodeAuthenticationToken;
import com.ruoyi.framework.security.email.realm.UserDetailsByEmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

/**
 * @author chenyue7@qq.com
 * @date 2022/7/21
 * @description
 */
@Component
public class EmailCodeAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsByEmailServiceImpl userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        EmailCodeAuthenticationToken authenticationToken = (EmailCodeAuthenticationToken) authentication;

        String email = (String) authenticationToken.getPrincipal();

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        // 此时鉴权成功后，应当重新 new 一个拥有鉴权的 authenticationResult 返回
        EmailCodeAuthenticationToken authenticationResult = new EmailCodeAuthenticationToken(userDetails, ((UserDetails) userDetails).getAuthorities());

        authenticationResult.setDetails(authenticationToken.getDetails());

        return authenticationResult;
    }


    @Override
    public boolean supports(Class<?> authentication) {
        // 判断 authentication 是不是 emailCodeAuthenticationToken 的子类或子接口
        return EmailCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

}
