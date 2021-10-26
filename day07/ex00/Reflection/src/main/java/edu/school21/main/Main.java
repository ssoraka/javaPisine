package edu.school21.main;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    private static final String PACKAGE_NAME = "edu.school21.classes.";
    private static final String DELIMITER = "---------------------";
    private static final String CLASS_NAME = "java.lang.";
    private static final String PUBLIC_MODIFICATOR = "public ";
    private static final String PRIVATE_MODIFICATOR = "private ";
    private static final char PKG_SEPARATOR = '.';
    private static final char DIR_SEPARATOR = '/';
    private static final String CLASS_FILE_SUFFIX = ".class";

    private List<Class<?>> classes;
    private Class<?> aClass;
    private Object object;
    private int fieldsCount;
    private int methodsCount;

    public static void main(String[] args) {
        Main main = new Main();
        if (!main.showClasses()) {
            System.exit(0);
        }
        System.out.println(DELIMITER);
        main.readClass();
        System.out.println(DELIMITER);
        main.showClassStructure();
        System.out.println(DELIMITER);
        main.createdObject();
        System.out.println(DELIMITER);
        main.modifyFiledObject();
        System.out.println(DELIMITER);
        main.callMethod();
        System.out.println(DELIMITER);
    }

    private boolean showClasses() {
        String scannedPath = PACKAGE_NAME.replace(PKG_SEPARATOR, DIR_SEPARATOR);
        URL scannedUrl = Thread.currentThread().getContextClassLoader().getResource(scannedPath);
        if (scannedUrl == null) {
            System.err.println("Not found classes!");
            return false;
        }
        File scannedDir = new File(scannedUrl.getFile());
        if (!scannedDir.isDirectory()) {
            System.err.println("Error with package!");
            return false;
        }

        List<String> names = Arrays.stream(Objects.requireNonNull(scannedDir.listFiles(file -> !file.isDirectory() && file.getName().endsWith(CLASS_FILE_SUFFIX))))
                .map(file -> PACKAGE_NAME + file.getName().replace(CLASS_FILE_SUFFIX, ""))
                .collect(Collectors.toList());

        System.out.println("Classes:");
        classes = new ArrayList<>();
        for (String name : names) {
            try {
                Class<?> aClass = Class.forName(name);
                classes.add(aClass);
                System.out.println("\t-" + aClass.getSimpleName());
            } catch (ClassNotFoundException e) {
            }
        }
        return true;
    }

    private void readClass() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("Enter class name\n->");
            if (sc.hasNext()) {
                String name = sc.next();
                aClass = classes.stream().filter(c -> c.getSimpleName().equals(name)).findFirst().orElse(null);
                if (aClass != null) {
                    return;
                } else {
                    System.err.println("class not found!");
                }
            }
        }
    }

    private void showClassStructure() {
        fieldsCount = aClass.getDeclaredFields().length;
        if (fieldsCount > 0) {
            System.out.println("Fields:");
            Arrays.stream(aClass.getDeclaredFields()).forEach(f ->
                    System.out.println("\t" + f.getType().getSimpleName() + " " + f.getName())
            );
        }

        methodsCount = aClass.getDeclaredMethods().length;
        if (methodsCount > 0) {
            System.out.println("Methods:");
            Arrays.stream(aClass.getDeclaredMethods()).forEach(m ->
                    System.out.println("\t" +
                            m.toString()
                                    .replace(PUBLIC_MODIFICATOR, "")
                                    .replace(PRIVATE_MODIFICATOR, "")
                                    .replace(CLASS_NAME, "")
                                    .replace(PACKAGE_NAME, "")
                                    .replace(aClass.getSimpleName() + PKG_SEPARATOR, "")
                    ));
        }
    }

    private void createdObject() {
        System.out.println("Let`s create an object.");
        try {
            Scanner sc = new Scanner(System.in);

            object = aClass.newInstance();
            for (Field field : aClass.getDeclaredFields()) {
                Object value = null;
                do {
                    System.out.print(field.getName() + "\n->");
                    value = convertValue(field.getType().getSimpleName(), sc.nextLine());
                    field.setAccessible(true);
                    try {
                        field.set(object, value);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                } while (value == null);
            }
            System.out.println("Object created: " + object);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private Object convertValue(String typeName, String value) {
        try {
            switch (typeName) {
                case "Integer":
                    return (Integer.parseInt(value));
                case "Long":
                    return (Long.parseLong(value));
                case "Boolean":
                    return (Boolean.parseBoolean(value));
                case "Float":
                    return (Float.parseFloat(value));
                case "Double":
                    return (Double.parseDouble(value));
                case "Short":
                    return (Short.parseShort(value));
                case "String":
                    return (value);
                default:
                    return null;
            }
        } catch (Exception e) {
            System.out.println("Can't cast " + value + " to " + typeName);
            return null;
        }
    }

    private void modifyFiledObject() {
        if (fieldsCount == 0) {
            System.out.println("The object " + object.getClass().getSimpleName() + " has no fields for modify!");
        }
        System.out.print("Enter name of the field for changing:\n->");
        Scanner sc = new Scanner(System.in);
        String fieldName = sc.next();

        Field field = Arrays.stream(aClass.getDeclaredFields()).filter(f -> f.getName().equals(fieldName)).findFirst().orElse(null);
        if (field == null) {
            System.err.println("Field not found!");
            return;
        }
        sc.nextLine();

        Object value = null;
        do {
            System.out.print("Enter " + field.getType().getSimpleName() + " value \n->");
            value = convertValue(field.getType().getSimpleName(), sc.nextLine());
            field.setAccessible(true);
            try {
                field.set(object, value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } while (value == null);

        System.out.println("Object updated:" + object);
    }

    private void callMethod() {
        if (methodsCount == 0) {
            System.out.println("The object " + object.getClass().getSimpleName() + " has no methods for call!");
            return;
        }

        System.out.print("Enter name of the method for call:\n->");
        Scanner sc = new Scanner(System.in);
        String methodName = sc.next();

        Method method = Arrays.stream(aClass.getDeclaredMethods()).filter(m -> m.getName().equals(methodName)).findFirst().orElse(null);
        if (method == null) {
            System.err.println("Method not found!");
            return;
        }
        sc.nextLine();

        Parameter[] parametrs = method.getParameters();
        Object[] arguments = new Object[parametrs.length];
        for (int i = 0; i < parametrs.length; i++) {
            String typeName = parametrs[i].getType().getSimpleName();
            do {
                System.out.print("Enter " + typeName + " value \n->");
                arguments[i] = convertValue(typeName, sc.nextLine());
            } while (arguments[i] == null);
        }

        Object result = null;
        try {
            method.setAccessible(true);
            result = method.invoke(object, arguments);
        } catch (IllegalArgumentException e) {
            System.out.println("The method was not called due to incorrect arguments!");
            return;
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            return;
        }
        if (!(method.getReturnType().getSimpleName().equals("void"))) {
            System.out.println("Method returned:");
            System.out.println(result);
        }
    }
}

