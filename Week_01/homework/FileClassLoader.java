
package homework;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileClassLoader extends ClassLoader {

    private final String filepath;
    private Decoder decoder;


    public FileClassLoader(String filepath) {
        this.filepath = filepath;
    }

    public void setDecoder(Decoder decoder) {
        this.decoder = decoder;
    }


    public byte[] decode(byte[] origin) {
        return decoder.decode(origin);
    }

    public boolean hasDecoder(){
        return decoder != null;
    }


    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bytes = (getBytes(filepath));
        if (hasDecoder())
            bytes = decode(bytes);
        if (bytes == null)
            throw new ClassNotFoundException();
        return defineClass(name, bytes, 0, bytes.length);
    }


    public byte[] getBytes(String filepath) {
        try {
            return Files.readAllBytes(Paths.get(filepath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
