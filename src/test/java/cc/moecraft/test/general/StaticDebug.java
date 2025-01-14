package cc.moecraft.test.general;

import cc.moecraft.icq.PicqBotX;
import cc.moecraft.icq.PicqConfig;
import cc.moecraft.icq.command.CommandArgs;
import cc.moecraft.icq.command.CommandManager;
import cc.moecraft.icq.command.exceptions.CommandNotFoundException;
import cc.moecraft.icq.command.exceptions.NotACommandException;
import cc.moecraft.test.icq.commands.CommandTest;

import static cc.moecraft.icq.command.CommandArgsParser.parse;

/**
 * 此类由 Hykilpikonna 在 2018/08/10 创建!
 * Created by Hykilpikonna on 2018/08/10!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
public class StaticDebug {
    public static void main(String[] args) throws NotACommandException, CommandNotFoundException {
        CommandManager commandManager = new CommandManager(new PicqBotX(new PicqConfig(1234), false), "!", "bot -");
        commandManager.registerCommand(new CommandTest());

        CommandArgs commandArgs = parse(commandManager, "   !  test a b  cc d   ", true);
        System.out.println(commandArgs);
    }
}
