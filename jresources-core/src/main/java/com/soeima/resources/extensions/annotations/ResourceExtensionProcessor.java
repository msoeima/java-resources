/*
 * Copyright 2012 Marco Soeima
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.soeima.resources.extensions.annotations;

import com.soeima.resources.PluginProperties;
import com.soeima.resources.util.IOUtil;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

/**
 * Process annotations of type {@link ResourceExtension}.
 *
 * @author   <a href="mailto:marco.soeima@gmail.com">Marco Soeima</a>
 * @version  2012/10/01
 */
@SupportedAnnotationTypes({"com.soeima.resources.extensions.annotations.ResourceExtension"})
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class ResourceExtensionProcessor extends AbstractProcessor {

    /** The filer object. */
    private Filer filer;

    /** The messenger; used to write any and all debugging output. */
    private Messager messager;

    /**
     * Creates a new {@link ResourceExtensionProcessor} object.
     */
    public ResourceExtensionProcessor() {
        super();
    }

    /**
     * @see  Processor#init(ProcessingEnvironment)
     */
    @Override public void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.filer = processingEnv.getFiler();
        this.messager = processingEnv.getMessager();
    }

    /**
     * @see  Processor#process(Set, RoundEnvironment)
     */
    @Override public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        for (TypeElement element : annotations) {

            for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(element)) {
                ResourceExtension plugin = annotatedElement.getAnnotation(ResourceExtension.class);
                createDescriptor(plugin, annotatedElement);
            }
        }

        return true;
    }

    /**
     * Returns the package name for the given <code>qName</code>.
     *
     * @param   qName  The fully qualified name whose package name is to be returned.
     *
     * @return  The package name for the given <code>qName</code> or an empty string if one does not exist.
     */
    private String getPackageName(String qName) {
        int index = qName.lastIndexOf(".");
        return (index >= 0) ? qName.substring(0, index) : "";
    }

    /**
     * Returns the file name for the given <code>pkgName</code>.
     *
     * @param   pkgName  The name of the package used to create the file.
     *
     * @return  The name of the file to create.
     */
    private String getFileName(String pkgName) {
        return (pkgName + PluginProperties.Extension);
    }

    /**
     * Creates the plugin descriptor file.
     *
     * @param  plugin   The name of the package.
     * @param  element  Represents the annotated element.
     */
    private void createDescriptor(ResourceExtension plugin, Element element) {

        if (plugin == null) {
            return;
        }

        String fileName = getFileName(plugin.name());
        FileObject fo = null;

        try {
            fo = filer.createResource(StandardLocation.SOURCE_OUTPUT, "", fileName, element);
        }
        catch (IOException e) {
            messager.printMessage(Diagnostic.Kind.ERROR, "Error creating file object for=" + fileName, element);
            return;
        }

        Properties properties = new Properties();
        properties.setProperty(PluginProperties.DisplayName, plugin.name());
        properties.setProperty(PluginProperties.FactoryName, getPackageName(element.toString()));
        properties.setProperty(PluginProperties.Description, plugin.description());
        OutputStream os = null;

        try {
            os = fo.openOutputStream();
            properties.store(os, "Auto-generated by the PathItemPluginProcessor.\n");
        }
        catch (IOException e) {
            messager.printMessage(Diagnostic.Kind.ERROR, "Error generating file=" + fileName, element);
        }
        finally {
            IOUtil.close(os);
        }
    } // end method createDescriptor
} // end class ResourceExtensionProcessor
