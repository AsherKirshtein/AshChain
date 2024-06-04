package crypto.Utilities;

import java.io.*;
import java.security.*;
import java.util.*;



public class SHA256MerkleTree
{
    public static String digestToString(byte[] digest) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < digest.length; ++i)
            result.append(String.format("%02x", digest[i]));
        return result.toString();
    }

    public static byte[] sha256MerkleTree(InputStream in, int blockSize) 
    {
        byte[] buffer = new byte[blockSize];
        int bytes;
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
        
        List<byte[]> digests = new ArrayList<>();
        while ((bytes = in.read(buffer)) > 0) {
            md.reset();
            md.update(buffer, 0, bytes);
            digests.add(md.digest());
        }
        int length = digests.size();
        if (length == 0)
            return null;
        while (length > 1) {
            int j = 0;
            for (int i = 0; i < length; i += 2, ++j) {
                byte[] digest1 = digests.get(i);
                if (i + 1 < length) {
                    byte[] digest2 = digests.get(i + 1);
                    md.reset();
                    md.update(digest1);
                    md.update(digest2);
                    digests.set(j, md.digest());
                } else {
                    digests.set(j, digest1);
                }
            }
            length = j;
        }
            return digests.get(0);
        }
        catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}