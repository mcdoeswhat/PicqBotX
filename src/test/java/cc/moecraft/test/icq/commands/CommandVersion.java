package cc.moecraft.test.icq.commands;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.sender.message.MessageBuilder;
import cc.moecraft.icq.sender.returndata.returnpojo.get.RStatus;
import cc.moecraft.icq.sender.returndata.returnpojo.get.RVersionInfo;
import cc.moecraft.icq.user.User;

import java.util.ArrayList;

import static cc.moecraft.icq.PicqConstants.VERSION;
import static cc.moecraft.utils.StringUtils.capitalizeFirstLetterOfEachWord;

/**
 * 此类由 Hykilpikonna 在 2018/05/26 创建!
 * Created by Hykilpikonna on 2018/05/26!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
public class CommandVersion implements EverywhereCommand // 继承EverywhereCommand就是无论私聊还是群聊都能收到的指令
{
    // 指令属性
    @Override
    public CommandProperties properties() {
        // 这个括号里填指令名和其他名称, 指令名必须至少有一个
        // 这个的话, 用"!v", "!version", 和"!版本"都能触发指令 (感叹号为你设置的前缀, 不一定必须要感叹号)
        return new CommandProperties("version", "v", "版本");
    }

    // 机器人接到指令后会执行这个方法 ( 实现不同的接口的话方法名不一定一样 )
    @Override
    public String run(EventMessage event, User sender, String command, ArrayList<String> args) {
        // 处理, 返回值会自动回复回去

        // 获取版本信息
        RVersionInfo versionInfo = event.getHttpApi().getVersionInfo().getData();
        RStatus status = event.getHttpApi().getStatus().getData();

        // 返回版本信息
        return new MessageBuilder()
                .add("PicqBotX v").add(VERSION).newLine()
                .add("- 酷Q版本: ").add(capitalizeFirstLetterOfEachWord(versionInfo.getCoolqEdition())).newLine()
                .add("- 插件版本: ").add(versionInfo.getPluginVersion())
                .add(" Build ").add(versionInfo.getPluginBuildNumber()).newLine()
                .add("- 运行状态: ").add(status.getGood() ? "Good" : "Bad")
                .toString();
    }
}
