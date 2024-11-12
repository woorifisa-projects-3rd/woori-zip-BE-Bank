package fisa.woorizip.bank.common.exception;

import lombok.SneakyThrows;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

public class EnumScanner {

    private static final String ERROR_CODE_SUFFIX = "ErrorCode";

    @SneakyThrows
    private static Set<Class<?>> findEnumClasses(String basePackage) {
        Set<Class<?>> enumClasses = new HashSet<>();

        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AssignableTypeFilter(Enum.class));

        for (BeanDefinition beanDefinition : scanner.findCandidateComponents(basePackage)) {
            String className = beanDefinition.getBeanClassName();
            if (!isNull(className) && className.startsWith(basePackage) && Class.forName(className).isEnum()) {
                enumClasses.add(Class.forName(className));
            }
        }
        return enumClasses;
    }

    public static Set<String> getEnumClasses() {
        Set<Class<?>> enumClasses = findEnumClasses("fisa.woorizip.backend");
        return enumClasses.stream().map(Class::getSimpleName).filter(name -> !name.endsWith(ERROR_CODE_SUFFIX)).collect(Collectors.toSet());
    }

}
