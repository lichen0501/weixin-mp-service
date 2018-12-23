package pers.lichen.weixin.mp.controller;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.lichen.weixin.mp.config.WxMpConfiguration;

/**
* @Description: 身份验证
* @Author: lichen
* @Date: 2018-12-22 10:50
*/
@RestController
@RequestMapping("/wx/oauth2/{appid}")
public class WxOAuth2Controller {

    /**
     * @Description: 获取跳转url
     * @Param: [appid, url]
     * @return: java.lang.String
     * @Author: lichen
     * @Date: 2018-12-22 14:56
     */
    @RequestMapping("/getUrl")
    public String getOauth2Url(@PathVariable String appid,String url){

        return WxMpConfiguration.getMpServices().get(appid).oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_USERINFO,null);
    }

    /**
     * @Description: 获取用户信息
     * @Param: [appid, code]
     * @return: me.chanjar.weixin.mp.bean.result.WxMpUser
     * @Author: lichen
     * @Date: 2018-12-22 14:57
     */
    @RequestMapping("/getUserInfo")
    public WxMpUser getUserInfo(@PathVariable String appid,String code) throws WxErrorException {

        WxMpService wxMpService = WxMpConfiguration.getMpServices().get(appid);
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        return WxMpConfiguration.getMpServices().get(appid).oauth2getUserInfo(wxMpOAuth2AccessToken,null);
    }
}
