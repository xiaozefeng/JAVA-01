package io.github.mickey.week5.util;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

/**
 * @author mickey
 * @date 2/11/21 00:08
 */
public class ClassUtil {
    private static final String CLASS_SUFFIX = ".class";
    private static final String CLASS_FILE_PREFIX = File.separator + "classes"  + File.separator;
    private static final String PACKAGE_SEPARATOR = ".";


    public static List<String> getClassNames(String packageName) {
        List<String> result = new ArrayList<>();
        packageName = packageName.replaceAll("\\.", File.separator);
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            Enumeration<URL> urls = loader.getResources(packageName);
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if (url != null) {
                    result.addAll(getAllClassNameByFile(new File(url.getPath())));
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static Collection<String> getAllClassNameByFile(File file) {
        List<String> result = new ArrayList<>();
        if (!file.exists())
            return result;

        if (file.isFile()) {
            String path = file.getPath();
            if (path.endsWith(CLASS_SUFFIX)) {
                path = path.replace(CLASS_SUFFIX, "");
                String clazzName = path.substring(path.indexOf(CLASS_FILE_PREFIX) + CLASS_FILE_PREFIX.length())
                        .replace(File.separator, PACKAGE_SEPARATOR);
                if (!clazzName.contains("$")) {
                    result.add(clazzName);
                }
            }
        }else {
            File[] files = file.listFiles();
            if (files != null && files.length > 0) {
                for (File f : files) {
                    result.addAll(getAllClassNameByFile(f));
                }
            }
        }
        return result;
    }
}
