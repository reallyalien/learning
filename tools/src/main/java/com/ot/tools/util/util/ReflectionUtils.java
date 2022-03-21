/**
 * APDPlat - Application Product Development Platform
 * Copyright (c) 2013, 杨尚川, yang-shangchuan@qq.com
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.ot.tools.util.util;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

import java.io.File;
import java.lang.reflect.*;
import java.net.URL;
import java.util.*;

/**
 * 反射工具类
 *
 * @author Xie
 * @date 19-3-19
 */
public class ReflectionUtils {
    private static final String THE_METHOD_START_WITH_SET = "set";


    private ReflectionUtils() {
    }

    ;

    /**
     * 搜索给定的所有的类里，某个类的所有子类或实现类
     */
    public static List<Class<?>> getAssignedClass(Class<?> cls, List<Class<?>> clses) {
        List<Class<?>> classes = new ArrayList<>();
        for (Class<?> c : clses) {
            if (cls.isAssignableFrom(c) && !cls.equals(c)) {
                classes.add(c);
            }
        }
        return classes;
    }

    /**
     * 获取某个类型的同一路径下的所有类
     */
    public static List<Class<?>> getClasses(Class<?> cls) throws ClassNotFoundException {
        String pk = cls.getPackage().getName();
        String path = pk.replace('.', '/');
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        URL url = classloader.getResource(path);
        return getClasses(new File(url.getFile()), pk, null);
    }

    /**
     * 获取某个路径下的指定的Package下的所有类，不包括<code>outsides</code>中的
     */
    public static List<Class<?>> getClasses(File dir, String pk, String[] outsides) throws ClassNotFoundException {
        //LOG.debug("  Dir: {}, PK: {}", new Object[]{dir, pk});
        List<Class<?>> classes = new ArrayList<>();
        if (!dir.exists()) {
            return classes;
        }
        String thisPk = StringUtils.isBlank(pk) ? "" : pk + ".";
        for (File f : dir.listFiles()) {
            if (f.isDirectory()) {
                classes.addAll(getClasses(f, thisPk + f.getName(), outsides));
            }
            String name = f.getName();
            if (name.endsWith(".class")) {
                Class<?> clazz = null;
                String clazzName = thisPk + name.substring(0, name.length() - 6);
                //LOG.debug("Class: {}", clazzName);
                if (outsides == null || outsides.length == 0 || !ArrayUtils.contains(outsides, clazzName)) {
                    try {
                        clazz = Class.forName(clazzName);
                    } catch (Throwable e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                        //LOG.error("实例化失败", e);
                    }
                    if (clazz != null) {
                        classes.add(clazz);
                    }
                }
            }
        }
        return classes;
    }

    /**
     * @param <T>
     * @param instance
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T cloneInstance(T instance) {
        Class<T> cls = (Class<T>) instance.getClass();
        T newIns = (T) BeanUtils.instantiateClass(cls);
        BeanUtils.copyProperties(instance, newIns);
        return newIns;
    }

    /**
     * 类似Class.getSimpleName()，但是可以忽略它是一个javassist生成的动态类
     *
     * @see Class#getSimpleName()
     */
    public static String getSimpleSurname(Class<?> clazz) {
        if (clazz == null) {
            return null;
        }
        return StringUtils.substringBefore(clazz.getSimpleName(), "_$$_");
    }

    /**
     * 类似Class.getName()，但是可以忽略它是一个javassist生成的动态类
     *
     * @see Class#getName()
     */
    public static String getSurname(Class<?> clazz) {
        if (clazz == null) {
            return null;
        }
        return StringUtils.substringBefore(clazz.getName(), "_$$_");
    }

    /**
     * 直接读取对象属性值,无视private/protected修饰符,不经过getter函数.
     */
    public static Object getFieldValue(final Object object, final Field field) {
        makeAccessible(field);

        Object result = null;
        try {
            result = field.get(object);
        } catch (IllegalAccessException e) {
            //LOG.error("不可能抛出的异常{}", e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 直接读取对象属性值,无视private/protected修饰符,不经过getter函数.
     */
    public static Object getFieldValue(final Object object, final String fieldName) {
        try {
            Field field = getDeclaredField(object, fieldName);

            if (field == null) {
                throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
            }
            return getFieldValue(object, field);
        } catch (Exception e) {
            String methodName = "get" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
            try {
                Method method = object.getClass().getMethod(methodName);
                return method.invoke(object);
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                //LOG.error("Could not exec method [" + methodName + "] on target [" + object + "]", ex);
                e.printStackTrace();
                throw new RuntimeException(e);

            }
        }
    }

    /**
     * 通过反射,获得Class定义中声明的父类的泛型参数的类型.
     *
     * @param clazz The class to introspect
     * @return the first generic declaration, or Object.class if cannot be determined
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> getSuperClassGenricType(final Class clazz) {
        return getSuperClassGenricType(clazz, 0);
    }

    /**
     * 通过反射,获得定义Class时声明的父类的泛型参数的类型.
     *
     * @param clazz clazz The class to introspect
     * @param index the Index of the generic ddeclaration,start from 0.
     * @return the index generic declaration, or Object.class if cannot be determined
     */
    @SuppressWarnings("unchecked")
    public static Class getSuperClassGenricType(final Class clazz, final int index) {

        Type genType = clazz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType)) {
            //LOG.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
            return Object.class;
        }

        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0) {
            //LOG.warn("Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: "
            //        + params.length);
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            //LOG.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
            return Object.class;
        }
        return (Class) params[index];
    }

    /**
     * 直接设置对象属性值,无视private/protected修饰符,不经过setter函数.
     */
    public static <T> void setFieldValue(final T object, final Field field, final Object value) {
        makeAccessible(field);

        try {
            field.set(object, value);
        } catch (IllegalAccessException e) {
            //LOG.error("不可能抛出的异常:{}", e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 直接设置对象属性值,无视private/protected修饰符,不经过setter函数.
     */
    public static <T> void setFieldValue(final T object, final String fieldName, final Object value) {
        try {
            Field field = getDeclaredField(object, fieldName);

            if (field == null) {
                throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
            }
            setFieldValue(object, field, value);
        } catch (Exception e) {
            String methodName = "set" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
            try {
                Method method = object.getClass().getMethod(methodName, value.getClass());
                method.invoke(object, value);
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                //LOG.error("Could not exec method [" + methodName + "] on target [" + object + "]", ex);
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 循环向上转型,获取对象的DeclaredField.
     */
    public static Field getDeclaredField(final Object object, final String fieldName) {
        Assert.notNull(object, "object不能为空");
        Assert.hasText(fieldName, "fieldName");
        for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                return superClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                // Field不在当前类定义,继续向上转型
                //e.printStackTrace();
                //System.out.println(fieldName+"不在当前类定义,继续向上转型");
            }
        }
        return null;
    }

    public static List<Field> getDeclaredFields(final Object object) {
        Assert.notNull(object, "object不能为空");
        List<Field> fields = new ArrayList<>();
        for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            Field[] f = superClass.getDeclaredFields();
            fields.addAll(Arrays.asList(f));
        }
        return fields;
    }

    /**
     * 循环向上转型,获取对象的DeclaredField.
     */
    protected static void makeAccessible(final Field field) {
        if (!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers())) {
            field.setAccessible(true);
        }
    }

    /**
     * 通过cls查找所有属性
     *
     * @param cls
     * @return
     */
    public static List<Field> getDeclaredFieldsByClass(final Class cls) {
        Assert.notNull(cls, "object不能为空");
        List<Field> fields = new ArrayList<>();
        for (Class<?> superClass = cls; superClass != Object.class; superClass = superClass.getSuperclass()) {
            Field[] f = superClass.getDeclaredFields();
            fields.addAll(Arrays.asList(f));
        }
        return fields;
    }

    /**
     * 获取所有非空的属性名称
     * 将model 的属性赋值给target
     *
     * @param source
     * @return
     */
    public static void getNullField(final Object source, final Object target) throws IllegalAccessException {
        Class cls = source.getClass();
        Assert.notNull(cls, "object不能为空");
        List<String> result = new ArrayList<>();
        for (Class<?> superClass = cls; superClass != Object.class; superClass = superClass.getSuperclass()) {
            Field[] f = superClass.getDeclaredFields();
            for (int i = 0; i < f.length; i++) {
                f[i].setAccessible(true);
                //　不修改静态的值
                if (Modifier.isStatic(f[i].getModifiers())) {
                    continue;
                }
                Object o = f[i].get(source);
                if (o != null) {
                    //　不修改空集合的值
                    if (o instanceof Collection && ((Collection) o).size() == 0) {
                        continue;
                    }
                    result.add(f[i].getName());
                    f[i].set(target, o);
                }
            }
        }
    }

    /**
     * 获得field中实际的参数类型,eg:List<User> 这种类型的属性会返回 User
     *
     * @param field
     * @return
     */
    public static Type getFirstFiledActualTypeArgument(final Field field) {
        Assert.notNull(field, "field不能为空");
        return ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
    }

    /**
     * 因为一些***的原因，同时又不想过多用到这个方法，将所有set方法缓存起来。
     *
     * @param cls
     * @return
     */
    public static Map<String, Method> getSetMethodList(final Class cls) {
        Assert.notNull(cls, "object不能为空");
        Map<String, Method> methodMap = new HashMap<>();
        for (Method m : cls.getMethods()) {
            if (m.getGenericReturnType().equals(Void.TYPE)) {
                String methodName = m.getName();
                if (methodName.startsWith(THE_METHOD_START_WITH_SET)) {
                    methodMap.put(StrUtils.firstCharToLowerCase(methodName.substring(3)), m);
                }
            }
        }
        ;
        return methodMap;
    }
}