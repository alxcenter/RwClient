package io.bot;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.hash.Hashing;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;
import java.util.StringJoiner;
import java.util.TreeMap;

public class HashTest {

    static String BOT_TOKEN = "";

    static String json = "{\n" +
            "        \"id\": 167944354,\n" +
            "        \"first_name\": \"Alexander\",\n" +
            "        \"username\": \"sasha_zaitsev\",\n" +
            "        \"photo_url\": \"https:\\/\\/t.me\\/i\\/userpic\\/320\\/84cqdCtYax6FTImXe-3Ua-YMfNpTvdG0SbiLKuP-Y-4.jpg\",\n" +
            "        \"auth_date\": 1574751989,\n    " +
            "        \"hash\": \"8e1fb7dce15d96a2a9479c94acee6594c4306b931e9ef72567569a46153e86b7\"\n" +
            "    }";

    public static void main(String[] args) throws Exception {
        TreeMap<String, Object> map = new TreeMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(json);
        Iterator<String> stringIterator = jsonNode.fieldNames();
        String data_check_string = "";
        stringIterator.forEachRemaining(x -> {
            if(jsonNode.get(x).isTextual()) {
                map.put(x, jsonNode.get(x).textValue());
            } else if(jsonNode.get(x).isInt()){
                map.put(x, jsonNode.get(x).asInt());
            }

        });
        StringJoiner stringJoiner = new StringJoiner("\n");
        map.keySet().stream().filter(x -> !x.equals("hash")).forEach(x -> stringJoiner.add(x + "=" + map.get(x)));
        data_check_string = stringJoiner.toString();
        System.out.println(data_check_string);
        byte[] secret_key = getSecretKey();
        String encode = encode(secret_key, data_check_string);
        System.out.println(secret_key);
        System.out.println(encode);
        System.out.println(encode.equals(map.get("hash")));
    }


    private static boolean validate(Map<String, String> authPayload){
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

    private static String generateDataCheckString(Map<String, String> authPayload){
        StringJoiner stringJoiner = new StringJoiner("\n");
        authPayload.keySet().stream()
                .filter(key -> !key.equals("hash"))
                .sorted()
                .forEach(x -> stringJoiner.add(x + "=" + authPayload.get(x)));
        return stringJoiner.toString();
    }

    private static byte[] getSecretKey(){
        return Hashing.sha256()
                .hashString(BOT_TOKEN, StandardCharsets.UTF_8)
                .asBytes();
    }

    public static String encode(byte[] key, String data) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key, "HmacSHA256");
        sha256_HMAC.init(secret_key);
        byte[] bytes = sha256_HMAC.doFinal(data.getBytes());
        StringBuilder builder = new StringBuilder();
        for (byte b :bytes) {
            builder.append(String.format("%02X ", b));
        }
        System.out.println(builder.toString());
        return Hex.encodeHexString(sha256_HMAC.doFinal(data.getBytes()));
    }
}
