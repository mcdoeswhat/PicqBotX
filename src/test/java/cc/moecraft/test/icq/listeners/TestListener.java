package cc.moecraft.test.icq.listeners;

import cc.moecraft.icq.PicqBotX;
import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.event.events.message.EventPrivateMessage;
import cc.moecraft.icq.sender.message.MessageBuilder;
import cc.moecraft.icq.sender.message.components.ComponentImageBase64;
import cc.moecraft.icq.sender.returndata.ReturnListData;
import cc.moecraft.icq.sender.returndata.returnpojo.get.RGroup;

/**
 * 此类由 Hykilpikonna 在 2018/05/24 创建!
 * Created by Hykilpikonna on 2018/05/24!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
public class TestListener extends IcqListener {
    @EventHandler
    public void onPMEvent(EventPrivateMessage event) {
        // System.out.println("接到消息");

        // 纯属测试没有嘲讽意思啦...
        if (event.getMessage().equals("你以为这是yangjinhe/maintain-robot?")) {
            event.respond("其实是我HyDevelop/PicqBotX哒!");
        }

        if (event.getMessage().equals("测试给小桂发Hi")) {
            event.getHttpApi().sendPrivateMsg(871674895, "hi");
        }

        if (event.getMessage().equals("测试回复数据")) {
            testDataReturn(event.getBot());
        }

        if (event.getMessage().equals("测试MessageBuilder")) {
            event.respond(new MessageBuilder()
                    .add("图片前面的消息").newLine()
                    .add(new ComponentImageBase64("iVBORw0KGgoAAAANSUhEUgAAABQAAAAVCAIAAADJt1n/AAAAKElEQVQ4EWPk5+RmIBcwkasRpG9UM4mhNxpgowFGMARGEwnBIEJVAAAdBgBNAZf+QAAAAABJRU5ErkJggg==")).newLine()
                    .add("图片后面的").newLine()
                    .add("换行之后的消息")
                    .toString());
        }
    }

    /**
     * 用来测试回复数据的
     */
    public void testDataReturn(PicqBotX bot) {
        // 测试成功 #1: 普通泛型
        // ReturnData<RLoginInfo> returnData = bot.getAccountManager().getNonAccountSpecifiedApi().getLoginInfo();
        // bot.getLogger().log("Return Data = " + returnData);

        // 测试 #2: List泛型
        ReturnListData<RGroup> returnListData = bot.getAccountManager().getNonAccountSpecifiedApi().getGroupList();
        bot.getLogger().log("Return List Data = " + returnListData);
    }
}
