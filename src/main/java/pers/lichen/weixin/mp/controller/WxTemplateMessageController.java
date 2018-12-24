package pers.lichen.weixin.mp.controller;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpTemplateMsgService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.web.bind.annotation.*;
import pers.lichen.weixin.mp.config.WxMpConfiguration;
import pers.lichen.weixin.mp.model.params.WxMpTemplateMessages;

import java.util.List;

@RestController
@RequestMapping("/wx/templateMsg/{appid}")
public class WxTemplateMessageController {

    /**
     * @Description: 发送模板消息
     * @Param: [appid, toUser, templateId, url, data]
     * @return: void
     * @Author: lichen
     * @Date: 2018-12-22 15:39
     */
    @RequestMapping(value = "/send",method = RequestMethod.POST)
    public String sendTempMessage(@PathVariable String appid,@RequestBody WxMpTemplateMessages data) throws WxErrorException {

        String faildUser = "";
        String error = "";
        WxMpTemplateMessage wxMpTemplateMessage = data.setSuper();
        for(String toUser:data.getToUsers()){
            wxMpTemplateMessage.setToUser(toUser);
            try {
                WxMpConfiguration.getMpServices().get(appid).getTemplateMsgService().sendTemplateMsg(wxMpTemplateMessage);
            }
            catch (Exception e){
                faildUser+=(toUser+",");
                error+=(e.getMessage()+",");
            }
        }

        if("".equals(faildUser)){
            return "消息推送成功";
        }else {
            System.out.println(faildUser.substring(0,faildUser.length()-1));
            return "用户"+faildUser.substring(0,faildUser.length()-1)+"推送失败,错误信息："+error.substring(0,error.length()-1);
        }
    }
}
