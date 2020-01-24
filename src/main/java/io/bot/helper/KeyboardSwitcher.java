package io.bot.helper;

import org.springframework.stereotype.Component;

@Component
public class KeyboardSwitcher {

    public final static String RU = "ЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮйцукенгшщзхъфывапролджэячсмитьбю";
    public final static String EN = "QWERTYUIOP[]ASDFGHJKL;'ZXCVBNM,.qwertyuiop[]asdfghjkl;'zxcvbnm,.";

    public String changheKeyboardLayout(String input) {
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
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == a) {
                return true;
            }
        }
        return false;
    }
}
