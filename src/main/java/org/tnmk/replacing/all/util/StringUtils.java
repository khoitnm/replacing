package org.tnmk.replacing.all.util;

import org.tnmk.replacing.all.exception.UnexpectedException;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

public final class StringUtils {
    private StringUtils(){
        //utilities
    }

    public static String encodeUtf8(String input){
        return encode(input, "UTF-8");
    }
    public static String encodeUtf16(String input){
        return encode(input, "UTF-16");
    }
    public static String encode(String input, String encode){
        try {
            return new String(input.getBytes("UTF-8"), encode);
        } catch (UnsupportedEncodingException e) {
            throw new UnexpectedException(String.format("Cannot encode string '%s' with encode '%s'",input, encode));
        }
    }

    public static String charsetEncode(String input, String charset){
        CharsetEncoder encoder = Charset.forName(charset).newEncoder();
        ByteBuffer byteBuffer;
        try {
            CharBuffer charBuffer = CharBuffer.wrap(input);
            byteBuffer = encoder.encode(charBuffer);
            return new String(byteBuffer.array());
        } catch(CharacterCodingException e) {
            return null;
        }

    }
}
