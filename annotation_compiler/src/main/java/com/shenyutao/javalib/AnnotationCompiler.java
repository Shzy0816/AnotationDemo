package com.shenyutao.javalib;

import com.google.auto.service.AutoService;
import com.shenyutao.annotations.BindView;

import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * @author shzy
 *
 * 注解处理程序 用来生成代码
 */

@AutoService(Processor.class)
public class AnnotationCompiler extends AbstractProcessor {
    /**
     * 定义一个用来生成APT目录下面文件的对象
     */
    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
    }

    // 支持的版本
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    // 能用来处理哪些注解
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new HashSet<>();
        types.add(BindView.class.getCanonicalName());
        return types;
    }



    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE,"shzy -- " + annotations);

        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(BindView.class);
        Map<String, List<VariableElement>> map = new HashMap<>(8);
        for (Element element : elements) {
            VariableElement variableElement = (VariableElement) element;
            // 获取到注解字段所在的类，比如加了注解的TextView所在的Activity类(MainActivity , SecActivity)
            String activityName = variableElement.getEnclosingElement().getSimpleName().toString();
            List<VariableElement> variableElements = map.get(activityName);
            if(variableElements == null){
                variableElements = new ArrayList<>();
                map.put(activityName,variableElements);
            }
            variableElements.add(variableElement);
        }

        if(map.size() > 0){
            Writer writer = null;
            for (String activityName : map.keySet()) {
                List<VariableElement> variableElements = map.get(activityName);
                // 得到包名
                TypeElement enclosingElement = (TypeElement) variableElements.get(0).getEnclosingElement();
                String packageName = processingEnv.getElementUtils().getPackageOf(enclosingElement).toString();

                try {
                    JavaFileObject sourceFile = filer.createSourceFile(packageName + "." + activityName + "_ViewBinding");
                    writer = sourceFile.openWriter();
                    // java文件所在包名
                    writer.write("package " + packageName + ";\n");
                    // java文件中的import语句
                    writer.write("import com.shenyutao.open_api" + ".IBinder;\n");
                    // java类
                    writer.write("public class " + activityName + "_ViewBinding implements IBinder<" +
                            packageName + "." + activityName + ">{\n");
                    writer.write(" @Override\n" +
                            "public void bind(" + packageName + "." + activityName + " target){");
                    for (VariableElement variableElement : variableElements) {
                        // 获取名字
                        String variableName = variableElement.getSimpleName().toString();
                        // 获取id
                        int id = variableElement.getAnnotation(BindView.class).value();
                        // 获取类型
                        TypeMirror typeMirror = variableElement.asType();
                        writer.write("target." + variableName + "=(" + typeMirror + ")target.findViewById(" + id + ");\n");
                    }
                    writer.write("}}\n");

                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if(writer != null){
                        try {
                            writer.close();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        return false;
    }
}