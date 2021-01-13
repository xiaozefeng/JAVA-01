package homework;

public class NoneDecoder implements Decoder {
    @Override
    public byte[] decode(byte[] origin) {
        // do nothing
        return origin;
    }
}