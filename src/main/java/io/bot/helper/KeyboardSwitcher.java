package io.bot.helper;

import org.springframework.stereotype.Component;

@Component
public class KeyboardSwitcher {

    private final static String RU = "ЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮйцукенгшщзхъфывапролджэячсмитьбю";
    private final static String EN = "QWERTYUIOP{}ASDFGHJKL:\"ZXCVBNM,.qwertyuiop[]asdfghjkl;'zxcvbnm,.";

    public String changeKeyboardLayout(String input) {
        char[] chars = input.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char currentChar = chars[i];
            if (isEnglishLayout(currentChar))
                chars[i] = RU.charAt(EN.indexOf(currentChar));
        }
        return new String(chars);
    }

    private boolean isEnglishLayout(char a) {
        char[] chars = EN.toCharArray();
        for (char x :chars) {
            if (x == a) {
                return true;
            }
        }
        return false;
    }
}
