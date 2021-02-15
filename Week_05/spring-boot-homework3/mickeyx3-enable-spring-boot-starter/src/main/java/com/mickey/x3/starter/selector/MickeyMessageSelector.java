package com.mickey.x3.starter.selector;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author mickey
 * @date 2/15/21 17:47
 */
public class MickeyMessageSelector implements ImportSelector {
    private static final String MESSAGE_CLASS_NAME = "com.mickey.x3.starter.config.MickeyX3AutoConfiguration";
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{MESSAGE_CLASS_NAME};
    }

}
