package com.auto.boot.common.utils;

import cn.aos.isigning.autograph.autographcommon.constant.GlobalConstant;
import cn.aos.isigning.autograph.autographcommon.enums.OriginPlatformEnum;
import cn.aos.isigning.autograph.autographcommon.model.dto.JwtTokenUserInfo;
import cn.aos.isigning.autograph.autographcommon.model.dto.TokenInfoDTO;
import cn.aos.isigning.autograph.autographcommon.model.vo.UserInfoCacheVo;
import cn.aos.isigning.autograph.autographcommon.model.vo.UserInfoResponseVo;
import cn.hutool.core.util.StrUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Slf4j
public class JwtUtil {

    //todo 从nacos获取
    // 签名哈希的密钥，对于不同的加密算法来说含义不同
    public static final String JWT_SECRET = "XNwkhwiO1686038282675";

    /**
     * 根据用户id和昵称生成token
     * @param jwtTokenUserInfo  用户信息
     * @return JWT规则生成的token
     */
    public static String createJwtToken(JwtTokenUserInfo jwtTokenUserInfo){
        long expireTime = System.currentTimeMillis() + 24 * 3600 * 30 * 1000L;
        String JwtToken = Jwts.builder()
                .setId(IdUtil.snowNextId().toString())
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .setSubject(GlobalConstant.JWT_SUBJECT)
                .setIssuedAt(new Date())
                .setExpiration(new Date(expireTime))
                .claim(GlobalConstant.JWT_USER_INFO_KEY, JsonUtil.toJson(jwtTokenUserInfo))
                // HS256算法实际上就是MD5加盐值，此时APP_SECRET就代表盐值
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET)
                .compact();
        return JwtToken;
    }

    /**
     * 根据token获取用户信息
     * @return 根据token获取用户信息
     */
    public static JwtTokenUserInfo getUserInfoByJwtToken() {
        try{
            HttpServletRequest request = RequestUtil.getHttpServletRequest();
            if(null == request){
                return null;
            }
            String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);
            log.info("从请求头获取token：{}",jwtToken);
            if(StringUtils.isEmpty(jwtToken)) {
                return null;
            }
            if(jwtToken.startsWith(GlobalConstant.AUTH_TYPE)){
                jwtToken = jwtToken.replace(GlobalConstant.AUTH_TYPE, StrUtil.EMPTY);
            }
            if(checkToken(jwtToken)){
                Jws<Claims> claimsJws = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(jwtToken);
                Claims claims = claimsJws.getBody();
                String userInfo = (String)claims.get(GlobalConstant.JWT_USER_INFO_KEY);
                if(StringUtils.isBlank(userInfo)){
                    log.error("未获取到token中的用户信息");
                    return null;
                }
                return JsonUtil.parse(userInfo,JwtTokenUserInfo.class);
            }
            log.info("token校验未通过：token={}",jwtToken);
            return null;
        }catch(Exception e){
            log.error("从token获取用户信息异常：", e);
            return null;
        }
    }

    /**
     *  获取 jwt-token
     */
    public static String getJwtTokenFromRequest() {
        HttpServletRequest request = RequestUtil.getHttpServletRequest();
        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(StringUtils.isEmpty(jwtToken)) {
            log.error("未从请求头中获取到jwt-token信息");
            return null;
        }
        if(jwtToken.startsWith(GlobalConstant.AUTH_TYPE)){
            jwtToken = jwtToken.replace(GlobalConstant.AUTH_TYPE, StrUtil.EMPTY);
        }
        return jwtToken;
    }

    /**
     * 判断token是否存在与有效
     * @param jwtToken token字符串
     * @return 如果token有效返回true，否则返回false
     */
    public static boolean checkToken(String jwtToken) {
        if(StringUtils.isEmpty(jwtToken)) return false;
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            log.error("校验jwt-token异常：token={}，异常信息：",jwtToken,e);
            return false;
        }
        return true;
    }

    /**
     * 获取jwt中的用户信息
     * @return
     */
    public static JwtTokenUserInfo getUserInfoByJwtToken(String jwtToken) {
        if(jwtToken.startsWith(GlobalConstant.AUTH_TYPE)){
            jwtToken = jwtToken.replace(GlobalConstant.AUTH_TYPE, StrUtil.EMPTY);
        }
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(jwtToken);
        Claims claims = claimsJws.getBody();
        String userInfo = (String)claims.get(GlobalConstant.JWT_USER_INFO_KEY);
        if(StringUtils.isBlank(userInfo)){
            log.error("未获取到jwt-token中的用户信息");
            return null;
        }
        return JsonUtil.parse(userInfo,JwtTokenUserInfo.class);
    }

    /**
     * 获取jwt中的用户ID
     * @return
     */
    public static Long getUserId(String jwtToken) {
        if(jwtToken.startsWith(GlobalConstant.AUTH_TYPE)){
            jwtToken = jwtToken.replace(GlobalConstant.AUTH_TYPE, StrUtil.EMPTY);
        }
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(jwtToken);
        Claims claims = claimsJws.getBody();
        String userInfoStr = (String)claims.get(GlobalConstant.JWT_USER_INFO_KEY);
        if(StringUtils.isBlank(userInfoStr)){
            log.error("未获取到jwt-token中的用户信息");
            return null;
        }
        JwtTokenUserInfo userInfo = JsonUtil.parse(userInfoStr, JwtTokenUserInfo.class);
        return userInfo.getUserId();
    }

    /**
     * 获取jwt中的平台来源 code
     * @return
     */
    public static Integer getOriginPlatformCode(String jwtToken) {
        if(jwtToken.startsWith(GlobalConstant.AUTH_TYPE)){
            jwtToken = jwtToken.replace(GlobalConstant.AUTH_TYPE, StrUtil.EMPTY);
        }
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(jwtToken);
        Claims claims = claimsJws.getBody();
        String userInfoStr = (String)claims.get(GlobalConstant.JWT_USER_INFO_KEY);
        if(StringUtils.isBlank(userInfoStr)){
            log.error("未获取到jwt-token中的用户信息");
            return null;
        }
        JwtTokenUserInfo userInfo = JsonUtil.parse(userInfoStr, JwtTokenUserInfo.class);
        return userInfo.getOriginType();
    }

    /**
     * 获取jwt中的平台来源 message
     * @return
     */
    public static String getOriginPlatformMessage() {
        String jwtToken = getJwtTokenFromRequest();
        Integer originPlatformCode = getOriginPlatformCode(jwtToken);
        String originMessage = OriginPlatformEnum.getOriginMessageAlsoValid(originPlatformCode);
        return originMessage;
    }

    /**
     * 创建 tokenInfo
     *
     * @param vo userInfoVO
     * @return 返回 tokenInfo
     */
    public static TokenInfoDTO createTokenInfoDTO(UserInfoCacheVo vo) {
        return TokenInfoDTO.builder()
                .admin(null != vo.getIsAdmin() && vo.getIsAdmin())
                .corpId(vo.getCorpId())
                .loginPlatform(vo.getLoginPlatform())
                .originPlatform(vo.getPlatform())
                .thirdUserId(vo.getThirdUserId())
                .token(vo.getToken())
                .userId(Long.valueOf(vo.getUserId()))
                .build();
    }

    /**
     * 创建 tokenInfo
     *
     * @param vo userInfoVO
     * @return 返回 tokenInfo
     */
    public static TokenInfoDTO createTokenInfoDTO(UserInfoResponseVo vo) {
        return TokenInfoDTO.builder()
                .admin(null != vo.getIsAdmin() && vo.getIsAdmin())
                .corpId(vo.getCorpId())
                .loginPlatform(vo.getLoginPlatform())
                .originPlatform(vo.getPlatform())
                .thirdUserId(vo.getThirdUserId())
                .token(vo.getToken())
                .userId(Long.valueOf(vo.getUserId()))
                .build();
    }
}


