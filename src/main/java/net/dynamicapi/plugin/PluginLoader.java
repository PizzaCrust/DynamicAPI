package net.dynamicapi.plugin;

import net.blockframe.BlockFramework;
import net.blockframe.plugin.PluginManager;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.jar.Attributes;
import java.util.jar.JarFile;

/**
 * A plugin loader for loading DynamicAPI plugins.
 */
public class PluginLoader {
    public static final ArrayList<PluginInformationContainer> infoContainers = new ArrayList<PluginInformationContainer>();

    private static void addURLs(URL[] urls) throws Exception {
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        if(cl instanceof URLClassLoader) {
            URLClassLoader ul = (URLClassLoader)cl;
            Class[] paraTypes = new Class[]{URL.class};
            Method method = URLClassLoader.class.getDeclaredMethod("addURL", paraTypes);
            method.setAccessible(true);
            Object[] args = new Object[1];

            for(int i = 0; i < urls.length; ++i) {
                args[0] = urls[i];
                method.invoke(ul, args);
            }
        }
    }


    public static void loadFile(File file) throws Exception {
        JarFile jarFile = new JarFile(file);
        Attributes attributes = jarFile.getManifest().getMainAttributes();
        String dynamicClass = attributes.getValue("DynamicClass");
        addURLs(new URL[] { file.toURI().toURL() });
        URLClassLoader classLoader = new URLClassLoader(new URL[] { file.toURI().toURL(), BlockFramework.class.getProtectionDomain().getCodeSource().getLocation().toURI().toURL() });
        Class mainClass = classLoader.loadClass(dynamicClass);
        // public static void pluginMain
        Method pluginMainMethod = mainClass.getDeclaredMethod("pluginMain", new Class[]{});
        pluginMainMethod.invoke(null, null);
    }

    public static class PluginInformationContainer {
        private final String name;

        public PluginInformationContainer(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
