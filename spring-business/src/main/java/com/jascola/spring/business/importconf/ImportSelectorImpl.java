package com.jascola.spring.business.importconf;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class ImportSelectorImpl implements ImportSelector {
    /**
     * AnnotationMetadata importingClassMetadata 会拿到当前配置类的信息（类信息，注解信息）
     * */
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[] { "com.jascola.spring.business.bo.PetBo" };
    }

}
