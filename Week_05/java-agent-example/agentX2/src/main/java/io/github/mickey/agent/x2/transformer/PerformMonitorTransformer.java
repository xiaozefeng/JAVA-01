package io.github.mickey.agent.x2.transformer;

import javassist.*;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.HashSet;
import java.util.Set;

/**
 * @author mickey
 * @date 2/16/21 22:39
 */
public class PerformMonitorTransformer implements ClassFileTransformer {
    private static final Set<String> classNameSet = new HashSet<>();

    static {
        classNameSet.add("io.github.mickey.agent.x2.TargetX2");
    }

    @Override
    public byte[] transform(ClassLoader classLoader,
                            String className,
                            Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain,
                            byte[] bytes) throws IllegalClassFormatException {

        String currentClassName = className.replaceAll("/", ".");
        if (!classNameSet.contains(currentClassName)) return null;

        System.out.println("transform: [" + currentClassName + "]");
        final CtClass ctClass;
        try {
            ctClass = ClassPool.getDefault().get(currentClassName);
            final CtBehavior[] methods = ctClass.getDeclaredBehaviors();
            for (CtBehavior method : methods) {
                enhanceMethod(method);
            }
            return ctClass.toBytecode();
        } catch (NotFoundException | CannotCompileException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void enhanceMethod(CtBehavior method) throws CannotCompileException {
        if (method.isEmpty())
            return;

        final String name = method.getName();
        if (name.equalsIgnoreCase("main")) return;

        final StringBuilder source = new StringBuilder();
        // 前置增强: 打入时间戳
        // 保留原有的代码处理逻辑
        source.append("{")
                .append("long start = System.nanoTime();\n") // 前置增强: 打入时间戳
                .append("$_ = $proceed($$);\n") // 保留原有的代码处理逻辑
                .append("System.out.print(\"method:[" + name + "]\");").append("\n")
                .append("System.out.println(\" cost:[\" +(System.nanoTime() -start)+ \"ns]\");") // 后置增强
                .append("}");

        ExprEditor editor = new ExprEditor() {
            @Override
            public void edit(MethodCall call) throws CannotCompileException {
                call.replace(source.toString());
            }
        };
        method.instrument(editor);

    }
}
