### 自定义一个 Classloader，加载一个 Hello.xlass 文件，执行 hello 方法，此文件内 容是一个 Hello.class 文件所有字节（x=255-x）处理后的文件。文件群里提供。

![图片](https://uploader.shimo.im/f/0Ywytk8EUIKXYN5H.png!thumbnail?fileGuid=jVJcRYRRkD3hgWvC)

```java
package homework;
public interface Decoder {
    byte[] decode(byte[] origin);
}
```

```java
package homework;
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
```

```java
package homework;
import java.io.IOException;
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
```

```java
package homework;
import java.lang.reflect.Method;
public static void main(String[] args) throws Exception {
    String path = "./Hello.xlass";
    String className = "Hello";
    String methodName = "hello";
    FileClassLoader fcl = new FileClassLoader(path);
    fcl.setDecoder(new ComplementDecoder());
    Class<?> clazz = fcl.findClass(className);
    Object instance = clazz.newInstance();
    invoke(methodName, clazz, instance);
}
private static void invoke(String methodName,
                           Class<?> clazz,
                           Object instance) throws Exception {
    Method method = clazz.getDeclaredMethod(methodName);
    method.setAccessible(true);
    method.invoke(instance);
}
```

### 编译&运行

```plain
javac -d .  Decoder.java ComplementDecoder.java FileClassLoader.java Main.java
# 运行
java homework.Main
```

## 

## 3、画一张图，展示 Xmx、Xms、Xmn、Meta、DirectMemory、Xss 这些内存参数的 关系。

![图片](https://uploader.shimo.im/f/fpzzHQ3wpqAVtN3b.png!thumbnail?fileGuid=jVJcRYRRkD3hgWvC)

xmx:  包括整个堆内存,  xms, xmn, metaspace 都属于堆

DirectMemory 属于堆外内存, 跟堆无关

Xss 影响的是线程栈的大小限制，跟堆无关

