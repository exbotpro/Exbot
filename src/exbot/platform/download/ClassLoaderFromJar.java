package exbot.platform.download;

import java.net.URL;
import java.net.URLClassLoader;

public class ClassLoaderFromJar extends URLClassLoader{
	/**
     * @param urls, to carryforward the existing classpath.
     */
    public ClassLoaderFromJar(URL[] urls) {
        super(urls);
    }
     
    @Override
    /**
     * add ckasspath to the loader.
     */
    public void addURL(URL url) {
        super.addURL(url);
    }
}
