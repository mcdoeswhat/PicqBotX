package cc.moecraft.icq.utils;

import cc.moecraft.logger.HyLogger;
import cc.moecraft.utils.cli.ResourceUtils;

import static cc.moecraft.logger.format.AnsiColor.*;
import static cc.moecraft.logger.format.AnsiFormat.replaceAllFormatWithANSI;
import static cc.moecraft.utils.StringUtils.repeat;

/**
 * The class {@code MiscUtils} is an utilities class for random things.
 * <p>
 * Class created by the HyDEV Team on 2019-03-21!
 *
 * @author HyDEV Team (https://github.com/HyDevelop)
 * @author Hykilpikonna (https://github.com/hykilpikonna)
 * @author Vanilla (https://github.com/VergeDX)
 * @since 2019-03-21 19:58
 */
public class MiscUtils {
    public static boolean disabled = false;

    /**
     * 输出一行"初始化完成"的日志
     *
     * @param logger 日志对象
     * @param name   要输出的名字
     * @param greens 绿色星号数量
     * @param reds   红色星号数量
     */
    public static void logInitDone(HyLogger logger, String name, int greens, int reds) {
        if (disabled) return;

        String green = repeat("*", greens);
        String red = repeat("*", reds);

        logger.log(String.format("%s%s%s初始化完成%s [%s%s%s%s%s] ...(%s ms)",
                YELLOW, name, GREEN, YELLOW,
                GREEN, green, RED, red, YELLOW,
                Math.round(logger.timing.getMilliseconds() * 100d) / 100d));

        logger.timing.reset();
    }

    /**
     * 日志一个资源
     *
     * @param logger Logger
     * @param name   资源名
     * @param vars   变量
     */
    public static void logResource(HyLogger logger, String name, Object... vars) {
        if (disabled) return;

        ResourceUtils.printResource(MiscUtils.class.getClassLoader(),
                s -> logger.log(replaceAllFormatWithANSI(s)), name, vars);
    }
}
