package taskeren.extrabot.components;

import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 消息组件根，用于处理消息组件。
 *
 * @author Taskeren
 */
@ToString
public class ExComponent {
    private static final String REGEX_CQ_CODE = "(\\[CQ:.*?,.*?])";

    private static final Pattern CQC = Pattern.compile(REGEX_CQ_CODE);

    /**
     * 返回解析后的组件，任何错误的数据将使用默认值，基础数据为{@code -1}，其他为{@code null}。
     *
     * @param message 消息原文
     * @return 解析后的组件
     */
    public static ExComponent parseComponent(String message) {
        String s0 = message.substring(1, message.length() - 1); // 去掉头尾的"[]"
        String[] s1 = s0.split(","); // 处理成数据对，像这样：["CQ:at", "qq=123456"]
        HashMap<String, String> map = genDataMap(s1); // 处理成Map
        ParserMap data = new ParserMap(map);
        String type = data.get("CQ"); // 获取CQ码类型
        try {
            switch (type) {

                case "face":
                    return new ExComponentFace(data.getInteger("id"));

                case "bface":
                    return new ExComponentBFace(data.getInteger("p"), data.get("id"));

                case "image":
                    return new ExComponentImage(data.get("file"), data.get("url"));

                case "record":
                    return new ExComponentRecord(data.get("file"), data.getBoolean("magic"));

                case "at":
                    return new ExComponentAt(data.getLong("qq"));

                case "rps":
                    return new ExComponentRPS(ExComponentRPS.RPS.parse(data.getInteger("type")));

                case "dice":
                    return new ExComponentDice(data.getInteger("type"));

                case "shake":
                    return new ExComponentShake();

                case "sign":
                    return new ExComponentSign(data.get("location"), data.get("title"), data.get("image"));

                case "rich":
                    return new ExComponentRich(data.get("title"), data.get("text"), data.get("content"));

                case "location":
                    return new ExComponentLocation(data.get("lat"), data.get("lon"), data.get("title"), data.get("content"),
                            data.getInteger("style"));

                case "contact":
                    return new ExComponentContact(data.getLong("id"), ExComponentContact.ContactTo.parse(data.get("type")));

                case "share":
                    return new ExComponentShare(data.get("content"), data.get("img"), data.get("title"), data.get("url"));

                default:
                    return new ExComponentString(message);
            }
        } catch (Exception e) {
            return new ExComponentString(message);
        }
    }

    /**
     * 返回解析后的组件列表。解析方法：{@link #parseComponent(String)}。
     *
     * @param s 消息
     * @return 解析后组件列表
     */
    public static ArrayList<ExComponent> parseComponents(String s) {
        ArrayList<ExComponent> components = new ArrayList<>();
        int count = 0;

        Matcher matcher = CQC.matcher(s);
        int end = 0;
        while (matcher.find()) {
            // string before cq code
            if (matcher.start() > end) {
                String strBefore = s.substring(end, matcher.start());
                components.add(new ExComponentString(strBefore));
            }
            String content = matcher.group();
            ExComponent component = parseComponent(content);
            components.add(component);
            end = matcher.end();
            count++;
        }
        // string after last cq code
        if (end < s.length()) {
            String strAfter = s.substring(end);
            components.add(new ExComponentString(strAfter));
            count++;
        }
        if (count == 0) {
            components.add(new ExComponentString(s));
        }
        return components;
    }

    /**
     * 返回解析后的组件列表。解析方法：{@link #parseComponent(String)}。
     *
     * @param args 消息列表
     * @return 解析后组件列表
     */
    public static ArrayList<ExComponent> parseComponents(ArrayList<String> args) {
        ArrayList<ExComponent> components = new ArrayList<>();

        for (String arg : args) {
            components.addAll(parseComponents(arg));
        }

        return components;
    }

    private static HashMap<String, String> genDataMap(String[] array) {
        HashMap<String, String> map = new HashMap<>();

        for (String s : array) {
            String[] s0 = s.split("[:=]", 2);
            if (s0.length < 2) {
                continue;
            }
            map.put(s0[0], s0[1]);
        }

        return map;
    }

    public static class ParserMap {
        final Map<String, String> data;

        public ParserMap(Map<String, String> data) {
            this.data = data;
        }

        public String get(String key) {
            return data.getOrDefault(key, "");
        }

        public int getInteger(String key) {
            return Integer.parseInt(data.getOrDefault(key, "-1"));
        }

        public long getLong(String key) {
            return Long.parseLong(data.getOrDefault(key, "-1"));
        }

        public boolean getBoolean(String key) {
            return Boolean.parseBoolean(data.get(key));
        }
    }
}
