package jrails;

import java.util.Map;
import java.util.HashMap;
import java.lang.reflect.*;

public class JRouter {

    private String className;
    private Map<String, Map<String, String>> routes;

    public JRouter() {
        routes = new HashMap<>();
    }

    public void addRoute(String verb, String path, Class clazz, String method) {
        className = clazz.getName();
        if (!routes.containsKey(verb)) {
            Map<String, String> route = new HashMap<>();
            route.put(path, method);
            routes.put(verb, route);
        } else {
            routes.get(verb).put(path, method);
        }
    }

    // Returns "clazz#method" corresponding to verb+URN
    // Null if no such route
    public String getRoute(String verb, String path) {
        if (!routes.containsKey(verb) || !routes.get(verb).containsKey(path)) {
            return null;
        }
        return className + "#" + routes.get(verb).get(path);
    }

    // Call the appropriate controller method and
    // return the result
    public Html route(String verb, String path, Map<String, String> params) {
        try {
            if (!routes.containsKey(verb) || !routes.get(verb).containsKey(path)) {
                throw new UnsupportedOperationException();
            }
            String method = routes.get(verb).get(path);
            Class<?> c = Class.forName(className);
            Method m = c.getMethod(method, Map.class);
            return (Html) m.invoke(c, params);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        throw new UnsupportedOperationException();
    }
}
