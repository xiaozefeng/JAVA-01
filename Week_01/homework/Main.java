package homework;

import java.lang.reflect.Method;

public class Main {

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

}
