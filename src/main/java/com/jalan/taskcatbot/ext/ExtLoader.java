package com.jalan.taskcatbot.ext;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jalan.taskcatbot.bot.handlers.IHandler;
import com.jalan.taskcatbot.bot.handlers.IHandlerSelector;

public class ExtLoader {

    private List<String> finderPaths;
    private List<ExtensionFile> extFiles;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public ExtLoader() {
        this.finderPaths = new ArrayList<String>();
        this.extFiles = new ArrayList<ExtensionFile>();

        this.finderPaths.add(new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath()).getParentFile().getAbsolutePath() + File.separator + "plugins");
    }

    public ExtLoader(ArrayList<String> finderPaths) {
        super();
        this.finderPaths.addAll(finderPaths);
        this.logger.info("ext loader paths: {}", this.finderPaths);
    }

    public void addFinderPath(String finderPath) {
        if (this.finderPaths == null) {
            this.finderPaths = new ArrayList<>();
        }

        if(finderPath != null)
            this.finderPaths.add(finderPath);
    }

    public List<String> getFinderPaths() {
        return finderPaths;
    }

    public void load() throws IOException {
        if(this.finderPaths == null || this.finderPaths.isEmpty()) {
            return;
        }

        for(String path : finderPaths) {
            this.logger.info("revisando {}", path);
            List<ExtensionFile> exts = findExtFile(path);
            
            if(exts != null)
                extFiles.addAll(exts);
        }

        for(ExtensionFile extFile : extFiles) {
            this.logger.info("extension loaded: {}", extFile.getHandler().getName());

            if(extFile.getHandler() instanceof IHandlerSelector) {
                this.logger.info("extension is handlerselector! {}", extFile.getPackageName());
            }
        }
    }

    public List<ExtensionFile> findExtFile(String path) throws IOException {
        List<ExtensionFile> exts = new ArrayList<>();
        File folderExt = new File(path);

        if (!folderExt.exists()) {
            this.logger.warn("finder path {} no existe", path);
            return null;
        }

        if (folderExt.isDirectory()) {
            for (File file : folderExt.listFiles()) {
                if (file.isDirectory()) {
                    exts.addAll(findExtFile(file.getAbsolutePath()));
                } else {
                    exts.addAll(inspectFile(file));
                }
            }
        }else {
            exts.addAll(inspectFile(folderExt));
        }

        return exts;
    }

    public List<ExtensionFile> inspectFile(File file) throws IOException {
        List<ExtensionFile> extensionFiles = new ArrayList<>();
        URL[] urls = new URL[] { file.toURI().toURL() };
        ClassLoader classLoader = new URLClassLoader(urls);
        JarFile jarFile = new JarFile(file);
        Enumeration<JarEntry> entries = jarFile.entries();

        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();

            if (entry.getName().endsWith(".class")) {
                String className = entry.getName().replace('/', '.').substring(0, entry.getName().length() - 6);

                try {
                    Class<?> clazz = classLoader.loadClass(className);

                    if (!clazz.isInterface() && IHandler.class.isAssignableFrom(clazz)) {
                        this.logger.info("class {} is IHandler", className);
                        IHandler handler = (IHandler) clazz.getDeclaredConstructor().newInstance();
                        extensionFiles.add(new ExtensionFile(file, className, handler));
                    }
                } catch (ReflectiveOperationException | NoClassDefFoundError | UnsupportedClassVersionError cnfe) {
                    //logger.warn("class not found exception! {}", cnfe);
                    logger.warn("error on try load class! {}", className);
                }
            }
        }
        jarFile.close();
        return extensionFiles;
    }

    public List<ExtensionFile> getExtFiles() {
        return this.extFiles;
    }

}
