package com.hy.datacompile;


import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;


@AutoService(Processor.class)
public class JavaDataApiProcessor extends AbstractProcessor {

    private Elements mElementUtils = null;
    private Messager mMessager = null;

    private Filer mFiler;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        System.out.println("JavaDataApiProcessor init");
        super.init(processingEnv);
        if(processingEnv !=null){
            mFiler = processingEnv.getFiler();
        }
        mElementUtils = processingEnv.getElementUtils();
        mMessager = processingEnv.getMessager();

    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        HashSet<String> types= new HashSet<>();
        types.add(JavaDataApi.class.getCanonicalName());
        types.add(JavaDataMethod.class.getCanonicalName());
        return types;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        System.out.println("JavaDataApiProcessor process");
       if(annotations.isEmpty() || roundEnv==null){
           mMessager.printMessage(Diagnostic.Kind.NOTE,"has no annotation end");
           return false;
       }
        processDataApi(roundEnv);

        return true;
    }

    private void processDataApi(RoundEnvironment roundEnv){
        try {
            Set<? extends Element> classElements = roundEnv.getElementsAnnotatedWith(JavaDataApi.class);
            Set<? extends Element> methodElements = roundEnv.getElementsAnnotatedWith(JavaDataMethod.class);

            for(Element classElement:classElements){
                PackageElement packageElement = mElementUtils.getPackageOf(classElement);
                String packageName = packageElement.getQualifiedName().toString();
                String className = classElement.getSimpleName().toString()+"JavaRepository";
                TypeSpec.Builder repositoryTypeSpecBuilder = TypeSpec.classBuilder(className);
                for (Element methodElement:methodElements){
                     String methodName = methodElement.getSimpleName().toString();
                     String enclosingName = methodElement.getEnclosingElement().getSimpleName().toString();
                     if(classElement.getSimpleName().toString().equals(enclosingName)){
                         ExecutableElement executableElement = (ExecutableElement)methodElement;
                         List<? extends VariableElement> variableElements = executableElement.getParameters();
                         List parameterSpecs = new ArrayList<HyParameter>();
                         for (VariableElement parameter:variableElements){
                             parameterSpecs.add(createParameterSpec(parameter));
                         }
                         repositoryTypeSpecBuilder.addMethod(createMethodSpec(methodName,executableElement,classElement,parameterSpecs));

                     }
                }

                JavaFile javaFile = JavaFile.builder(packageName,repositoryTypeSpecBuilder.build()).build();
                javaFile.writeTo(mFiler);
            }

        }catch (Exception e){
            System.out.println("JavaDataApiProcessor3 exception : " + e.getMessage());
        }
    }


    private MethodSpec createMethodSpec(String methodName,
                                        ExecutableElement executableElement,
                                        Element classElement,
                                        List<JavaParameter> parameterSpecs) throws ClassNotFoundException {
        MethodSpec.Builder methodSpecBuild = MethodSpec.methodBuilder(methodName);
        methodSpecBuild.addModifiers(Modifier.PUBLIC);
        ClassName threadPoolManagerClass = ClassName.get("com.hy.common.threadpool","ThreadPoolManager");
        String datalocalClassStr = getClassFromAnnotaton(classElement);
        int lastIndex = datalocalClassStr.lastIndexOf(".");
        ClassName datalocalClassName = ClassName.get(datalocalClassStr.substring(0,lastIndex),
                datalocalClassStr.substring(lastIndex+1));
        for (JavaParameter javaParameter:parameterSpecs){
            methodSpecBuild.addParameter(javaParameter.parameterSpec);
        }
        TypeName reponseCallParameter;
        if(executableElement.getReturnType().toString().contains("List")){
            int startIndex = executableElement.getReturnType().toString().indexOf("<");
            int lastIndex1 = executableElement.getReturnType().toString().lastIndexOf("<");
            String modelTypeStr = executableElement.getReturnType().toString().substring(startIndex+1,lastIndex);

            ClassName reponseCallParameterTemp = ClassName.get("kotlin.collections","List");

            reponseCallParameter = ParameterizedTypeName.
                    get(reponseCallParameterTemp,ClassName.get(Class.forName(modelTypeStr)));
        }else {
            reponseCallParameter = ClassName.get(executableElement.getReturnType());
            if(reponseCallParameter.toString().equals("int")){
                reponseCallParameter = ClassName.get(Integer.class);
            }
        }

        ClassName reponseClassName = ClassName.get("com.hy.common.repo","ReponseCall");
        ParameterizedTypeName parameterReponseClassName=ParameterizedTypeName.get(reponseClassName,reponseCallParameter);

        ParameterSpec reponseParameter = ParameterSpec.builder(parameterReponseClassName,"reponse").addModifiers(Modifier.FINAL).build();
        methodSpecBuild.addParameter(reponseParameter);

        TypeSpec.Builder runable = TypeSpec.anonymousClassBuilder("")
                .addSuperinterface(ClassName.get(Runnable.class));

        MethodSpec.Builder run  = MethodSpec.methodBuilder("run")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(void.class);
        run.beginControlFlow("try");
        if (parameterSpecs.size() == 1) {
            run.addStatement(
                    "$T t = new $T().$N($N)",
                    reponseCallParameter,
                    datalocalClassName,
                    methodName,
                    parameterSpecs.get(0).name
            );
        }
        TypeSpec.Builder runable1 = TypeSpec.anonymousClassBuilder("")
                .addSuperinterface(ClassName.get(Runnable.class));
        MethodSpec.Builder run1  = MethodSpec.methodBuilder("run")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(void.class);
        run1.addStatement("reponse.onResponse(t)");
        runable1.addMethod(run1.build());

        run.addStatement("$T.INSTANCE.getMainHandler().post($L)", threadPoolManagerClass,runable1.build());
        run.nextControlFlow("catch(Exception e)");
        run.addStatement("reponse.onError(e)");
        run.endControlFlow();
        runable.addMethod(run.build());

        methodSpecBuild.addStatement("$T.INSTANCE.getThreadPool().execute($L)", threadPoolManagerClass,runable.build());


//    methodSpecBuild.beginControlFlow("try");
//        if (parameterSpecs.isEmpty()) {
//            methodSpecBuild.addStatement("$T t = new $T().$N()", reponseCallParameter,datalocalClassName, methodName);
//        }
//        if (parameterSpecs.size() == 1) {
//            methodSpecBuild.addStatement(
//                    "$T t = $T().$N($N)",
//                    reponseCallParameter,
//                    datalocalClassName,
//                    methodName,
//                    parameterSpecs.get(0).name
//            );
//        }
//        if (parameterSpecs.size() == 2) {
//            methodSpecBuild.addStatement(
//                    "$T t = $T().$N($N,$N)",
//                    reponseCallParameter,
//                    datalocalClassName,
//                    methodName,
//                    parameterSpecs.get(0).name,
//                    parameterSpecs.get(1).name
//            );
//        }
//        methodSpecBuild.addStatement("$T.INSTANCE.$N.post{", threadPoolManagerClass, "mainHandler")
//                .addStatement("reponse?.onResponse(t)")
//                .addStatement("}")
//                .endControlFlow()
//                .beginControlFlow("catch($T e)", java.lang.Exception.class)
//                .addStatement("$T.INSTANCE.$N.post{", threadPoolManagerClass, "mainHandler")
//                .addStatement("reponse?.onError(e)")
//                .endControlFlow()
//                .endControlFlow();

        methodSpecBuild.returns(void.class);

        return methodSpecBuild.build();
    }

    private JavaParameter createParameterSpec(VariableElement parameterElement){
        ParameterSpec parameterSpec;
        parameterSpec = ParameterSpec.builder(
                TypeName.get(parameterElement.asType()),
                parameterElement.getSimpleName().toString()
        ).build();
        System.out.println("JavaDataApiProcessor6 :" + parameterElement.getSimpleName());
        return new JavaParameter(parameterSpec,parameterElement.getSimpleName().toString());

    }

    private String getClassFromAnnotaton(Element key){
        List<? extends AnnotationMirror> annotationMirrors = key.getAnnotationMirrors();
        for (AnnotationMirror annotationMirror : annotationMirrors){
            if(JavaDataApi.class.getName().equals(annotationMirror.getAnnotationType().toString())){
                System.out.println("JavaDataApiProcessor7 :" + annotationMirror.getAnnotationType().toString());
                Set<? extends ExecutableElement> keySet = annotationMirror.getElementValues().keySet();
                for (ExecutableElement executableElement : keySet){
                    if(executableElement.getSimpleName().toString() .equals("local")){
                        return annotationMirror.getElementValues().get(executableElement).getValue().toString();
                    }
                }

            }
        }
        return null;
    }
}
