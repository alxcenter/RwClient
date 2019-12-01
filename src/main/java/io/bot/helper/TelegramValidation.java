package io.bot.helper;

import com.google.common.hash.Hashing;
import io.bot.telega.Bot;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.StringJoiner;


@Component
//@Scope("request")
public class TelegramValidation {

    private String BOT_TOKEN = "869759161:AAH3W6MAoiz2qMbUoQqZ7-_-QgqmIE-2JLA";

    public boolean validate(Map<String, String> authPayload){
        String dataCheckString = generateDataCheckString(authPayload);
        byte[] secretKey = getSecretKey();
        String encodedHash = null;
        try {
            encodedHash = encode(secretKey, dataCheckString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encodedHash.equals(authPayload.get("hash"));
    }

    private String generateDataCheckString(Map<String, String> authPayload){
        StringJoiner stringJoiner = new StringJoiner("\n");
        authPayload.keySet().stream()
                .filter(key -> !key.equals("hash"))
                .sorted()
                .forEach(x -> stringJoiner.add(x + "=" + authPayload.get(x)));
        return stringJoiner.toString();
    }

    private byte[] getSecretKey(){
        return Hashing.sha256()
                .hashString(BOT_TOKEN, StandardCharsets.UTF_8)
                .asBytes();
    }

    private String encode(byte[] key, String data) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key, "HmacSHA256");
        sha256_HMAC.init(secret_key);
        return Hex.encodeHexString(sha256_HMAC.doFinal(data.getBytes()));
    }
}
