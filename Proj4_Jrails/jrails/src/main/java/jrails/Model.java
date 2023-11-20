package jrails;

import java.util.*;
import java.io.*;
import java.lang.reflect.*;

public class Model {
    private static String className;
    private static int numItems = 0;
    private static Map<Integer, Object> db = new HashMap<>();
    private static String filePath = "./db.txt";

    private int id = 0;

    public void save() {
        try {
            className = this.getClass().getName();
            File dbFile = new File(filePath);
            if (!dbFile.exists() || !dbFile.isFile()) {
                dbFile.createNewFile();
                writeTitleRow();
            }

            if (id == 0) {
                numItems++;
                this.id = numItems;
                db.put(this.id, this); 
                FileWriter fw = new FileWriter(filePath, true);
                fw.write(makeLine(this, this.id));
                fw.close();
            } else {
                db.put(this.id, this);
                writeDbToFile();
            }
        } catch (Exception ex) {
            System.out.println("Exeption " + ex.getCause() + " was found");
            ex.printStackTrace();
        }
    }

    private static void writeTitleRow() {
        try {
            Field[] fs = Class.forName(className).getFields();
            StringBuilder sb = new StringBuilder("id"); 
            for (Field f : fs) {
                sb.append(" | ").append(f.getName());
            }
            sb.append("\n");
            FileWriter fw = new FileWriter(filePath);
            fw.write(sb.toString());
            fw.close();
        } catch (Exception ex) {
            System.out.println("Exception " + ex.getCause() + " was found");
            ex.printStackTrace();
        }
    }

    private static String makeLine(Object o, int id) {
        try {
            StringBuilder sb = new StringBuilder(String.valueOf(id));
            Field[] fs = o.getClass().getFields();
            for (Field f : fs) {
                sb.append(" | ").append(f.get(o));
            }
            sb.append("\n");
            return sb.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    private static void writeDbToFile() {
        try {
            writeTitleRow();
            FileWriter fw = new FileWriter(filePath, true);
            for (Integer id : db.keySet()) {
                String line = makeLine(db.get(id), id);
                fw.write(line);
            }
            fw.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public int id() {
        return this.id;
    }

    public static <T> T find(Class<T> c, int id) {
        if (!db.containsKey(id)) {
            return null;
        }
        try {
            Object obj = db.get(id);
            T inst = c.getConstructor().newInstance();
            c.getMethod("setId", int.class).invoke(inst, id);
            for (int i = 0; i < c.getFields().length; i++) {
                Field f = c.getFields()[i];
                Field val = obj.getClass().getFields()[i];
                f.set(inst, val.get(obj));
            }
            return inst;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void setId(int i) {
        this.id = i;
    }

    public static <T> List<T> all(Class<T> c) {
        List<T> elts = new ArrayList<>();
        if (db.isEmpty()) { return elts; }
        for (Integer id : db.keySet()) {
            elts.add(find(c, id));
        }
        return elts;
    }

    public void destroy() {
        if (!db.containsKey(this.id)) {
            throw new RuntimeException();
        }
        db.remove(this.id);
        writeDbToFile();
    }

    public static void reset() {
        try {
            db.clear();
            new PrintWriter(filePath).close();
            numItems = 0;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
