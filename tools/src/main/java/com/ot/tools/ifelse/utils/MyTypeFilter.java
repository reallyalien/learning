package com.ot.tools.ifelse.utils;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

/**
 *    不论是component-scan标签，还是@ComponentScan注解。扫描或解析的bean只能是Spring内部所定义的，
 *    比如@Component、@Service、@Controller或@Repository。如果有一些自定义的注解，比如@Consumer、
 *    这个注解修饰的类是不会被扫描到的。这个时候就得自定义扫描器完成这个操作。
 */
@ComponentScan
public class MyTypeFilter implements TypeFilter {
    /**
     * @param metadataReader 读取到的正在扫描类的信
     * @param metadataReaderFactory
     * @return
     * @throws IOException
     */
    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        //当前类注解的信息
        AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
        //当前扫描类信息
        ClassMetadata classMetadata = metadataReader.getClassMetadata();
        //类路径
        Resource resource = metadataReader.getResource();
        return false;
    }
}
/**
 * 　Spring内置的扫描器
 *
 * 　　component-scan标签底层使用ClassPathBeanDefinitionScanner这个类完成扫描工作的。@ComponentScan注解配合@Configuration注解使用，底层使用ComponentScanAnnotationParser解析器完成解析工作。
 *
 * 　　ComponentScanAnnotationParser解析器内部使用ClassPathBeanDefinitionScanner扫描器，ClassPathBeanDefinitionScanner扫描器内部的处理过程整理如下：
 *
 * 1.遍历basePackages，根据每个basePackage找出这个包下的所有的class。比如basePackage为your/pkg，会找出your.pkg包下所有的class。
 * 2.找出之后封装成Resource接口集合，这个Resource接口是Spring对资源的封装，有FileSystemResource、ClassPathResource、UrlResource实现等
 * 3.遍历找到的Resource集合，通过includeFilters和excludeFilters判断是否解析。这里的includeFilters和excludeFilters是TypeFilter接口类型
 * 的集合，是ClassPathBeanDefinitionScanner内部的属性。TypeFilter接口是一个用于判断类型是否满足要求的类型过滤器。excludeFilters中只要
 * 有一个TypeFilter满足条件，这个Resource就会被过滤。includeFilters中只要有一个TypeFilter满足条件，这个Resource就不会被过滤
 * 如果没有被过滤。把Resource封装成ScannedGenericBeanDefinition添加到BeanDefinition结果集中
 * 4.返回最后的BeanDefinition结果集
 */
