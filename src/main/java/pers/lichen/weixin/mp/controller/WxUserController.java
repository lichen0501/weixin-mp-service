package pers.lichen.weixin.mp.controller;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpUserService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;
import org.springframework.web.bind.annotation.*;
import pers.lichen.weixin.mp.config.WxMpConfiguration;
import pers.lichen.weixin.mp.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;

/**
* @Description: 微信用户管理
* @Author: lichen
* @Date: 2018-12-27 10:27
*/
@RestController
@RequestMapping("/wx/user/{appid}")
public class WxUserController {
    /**
     * @Description: 查询公众号关注用户的openid列表
     * @Param: [appid]
     * @return: me.chanjar.weixin.mp.bean.result.WxMpUserList
     * @Author: lichen
     * @Date: 2018-12-27 10:30
     */
    @GetMapping("/getOpenIdList")
    public WxMpUserList getUserOpenIdList(@PathVariable String appid) throws WxErrorException {
        WxMpUserList list = new WxMpUserList();
        WxMpUserService service = WxMpConfiguration.getMpServices().get(appid).getUserService();
        list = service.userList("");

        //拉取openid列表，一次最多10000个
        if(list.getTotal()>10000){
            long totle = list.getTotal();
            int count = (int)Math.ceil(totle/10000);
            for (int i = 1;i<count;i++){
                WxMpUserList oneList = new WxMpUserList();
                oneList = service.userList(list.getNextOpenid());

                //扩充list内容
                list.setCount(list.getCount()+oneList.getCount());

                List<String> oneOpenids = new ArrayList();
                oneOpenids = list.getOpenids();
                oneOpenids.addAll(oneList.getOpenids());
                list.setOpenids(oneOpenids);

                list.setNextOpenid(oneList.getNextOpenid());
            }
        }

        return list;
    }

    /**
     * @Description: 通过openid获取用户信息
     * @Param: [appid, openid]
     * @return: WxMpUser
     * @Author: lichen
     * @Date: 2018-12-27 14:55
     */
    @GetMapping("/getInfo/{openid}")
    public WxMpUser getUserInfoByOpenId(@PathVariable String appid, @PathVariable String openid) throws WxErrorException {
        return WxMpConfiguration.getMpServices().get(appid).getUserService().userInfo(openid);
    }

    /**
     * @Description: 批量获取用户信息
     * @Param: [appid, openids]
     * @return: java.util.List<me.chanjar.weixin.mp.bean.result.WxMpUser>
     * @Author: lichen
     * @Date: 2018-12-27 15:03
     */
    @PostMapping("/getInfoBatch")
    public List<WxMpUser> getUserInfoByOpenIdBatch(@PathVariable String appid, @RequestBody List<String> openids) throws WxErrorException {
        List<WxMpUser> users = new ArrayList<>();
        WxMpUserService service = WxMpConfiguration.getMpServices().get(appid).getUserService();

        //批量获取用户信息，一次只能获取100个
        ListUtils.split(openids,100).forEach(e -> {
            try {
                users.addAll(service.userInfoList(e));
            } catch (WxErrorException e1) {
                e1.printStackTrace();
            }
        });

        return users;
    }
}
