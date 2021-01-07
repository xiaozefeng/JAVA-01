package homework;

/**
 * 反码
 */
public class ComplementDecoder implements Decoder {

    @Override
    public byte[] decode(byte[] origin) {
        int len = origin.length;
        byte[] result = new byte[len];
        System.arraycopy(origin, 0, result, 0, len);
        for (int i = 0; i < len; i++)
            result[i] = (byte) (255 - origin[i]);
        return result;
    }

}
