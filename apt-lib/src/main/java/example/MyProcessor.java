package example;

import java.io.IOException;
import java.util.Formatter;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

@SupportedAnnotationTypes({ "java.lang.Deprecated" })
public class MyProcessor extends AbstractProcessor {

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations,
            RoundEnvironment roundEnv) {
        if (roundEnv.processingOver()) {
            return true;
        }
        for (TypeElement annotation : annotations) {
            handleAnnotation(roundEnv, annotation);
        }
        return true;
    }

    private void handleAnnotation(RoundEnvironment roundEnv,
            TypeElement annotation) {
        for (Element element : roundEnv.getElementsAnnotatedWith(annotation)) {
            generate(element);
        }
    }

    private void generate(Element element) {
        String name = element.getSimpleName().toString();
        Filer filer = processingEnv.getFiler();
        try {
            JavaFileObject file = filer.createSourceFile("example.apt." + name,
                    element);
            try (Formatter f = new Formatter(file.openWriter())) {
                f.format("package example.apt;%n");
                f.format("public class %s {%n", name);
                f.format("}%n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
