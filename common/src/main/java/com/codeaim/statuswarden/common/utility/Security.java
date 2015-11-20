package com.codeaim.statuswarden.common.utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Security
{
    public static byte[] hashUsingSHA256(String input)
    {
        try
        {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(input.getBytes());
            return md.digest();
        } catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
            return new byte[0];
        }
    }

    public static String hashToString(byte[] bytes)
    {
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes)
        {
            sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
}
