/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package hushset.utilities;

import java.math.BigInteger;

import org.apache.commons.codec.digest.*;

public class HashFuncUtils {
    public static double DegreeMD5Hex(String id) {
        String md5Hex = DigestUtils
            .md5Hex(id);

        BigInteger h = new BigInteger(md5Hex, 16);
        BigInteger modded = BigInteger.valueOf(1000000);

        return h.mod(modded).doubleValue() / modded.doubleValue() * (180 / Math.PI);
    }
}
