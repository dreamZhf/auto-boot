package com.auto.boot.common.utils;


import cn.aos.isigning.autograph.autographcommon.beans.BeanCopier;
import cn.aos.isigning.autograph.autographcommon.beans.BeanMap;
import cn.aos.isigning.autograph.autographcommon.beans.BeanProperty;
import lombok.experimental.UtilityClass;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.cglib.beans.BeanGenerator;
import org.springframework.lang.Nullable;

import java.util.*;

/**
 * 实体工具类
 *
 */
@UtilityClass
public class BeanUtil extends org.springframework.beans.BeanUtils {

    /**
     * 实例化对象
     *
     * @param clazz 类
     * @param <T>   泛型标记
     * @return 对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T newInstance(Class<?> clazz) {
        return (T) instantiateClass(clazz);
    }

    /**
     * 实例化对象
     *
     * @param clazzStr 类名
     * @param <T>      泛型标记
     * @return 对象
     */
    public static <T> T newInstance(String clazzStr) {
        try {
            Class<?> clazz = ClassUtil.forName(clazzStr, null);
            return newInstance(clazz);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取Bean的属性, 支持 propertyName 多级 ：test.user.name
     *
     * @param bean         bean
     * @param propertyName 属性名
     * @return 属性值
     */
    @Nullable
    public static Object getProperty(@Nullable Object bean, String propertyName) {
        if (bean == null) {
            return null;
        }
        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(bean);
        return beanWrapper.getPropertyValue(propertyName);
    }

    /**
     * 设置Bean属性, 支持 propertyName 多级 ：test.user.name
     *
     * @param bean         bean
     * @param propertyName 属性名
     * @param value        属性值
     */
    public static void setProperty(Object bean, String propertyName, Object value) {
        Objects.requireNonNull(bean, "bean Could not null");
        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(bean);
        beanWrapper.setPropertyValue(propertyName, value);
    }

    /**
     * copy 对象属性，默认不使用Convert
     *
     * <p>
     * 支持 map bean copy
     * </p>
     *
     * @param source 源对象
     * @param clazz  类名
     * @param <T>    泛型标记
     * @return T
     */
    @Nullable
    public static <T> T copy(@Nullable Object source, Class<T> clazz) {
        if (source == null) {
            return null;
        }
        return BeanUtil.copy(source, source.getClass(), clazz);
    }

    /**
     * copy 对象属性，默认不使用Convert
     *
     * <p>
     * 支持 map bean copy
     * </p>
     *
     * @param source      源对象
     * @param sourceClazz 源类型
     * @param targetClazz 转换成的类型
     * @param <T>         泛型标记
     * @return T
     */
    @Nullable
    public static <T> T copy(@Nullable Object source, Class sourceClazz, Class<T> targetClazz) {
        if (source == null) {
            return null;
        }
        BeanCopier copier = BeanCopier.create(sourceClazz, targetClazz, false);
        T to = newInstance(targetClazz);
        copier.copy(source, to, null);
        return to;
    }

    /**
     * 拷贝对象
     *
     * <p>
     * 支持 map bean copy
     * </p>
     *
     * @param source     源对象
     * @param targetBean 需要赋值的对象
     */
    public static void copy(@Nullable Object source, @Nullable Object targetBean) {
        if (source == null || targetBean == null) {
            return;
        }
        BeanCopier copier = BeanCopier
                .create(source.getClass(), targetBean.getClass(), false);

        copier.copy(source, targetBean, null);
    }

    /**
     * 拷贝对象，source 属性做 null 判断，Map 不支持，map 会做 instanceof 判断，不会
     *
     * <p>
     * 支持 bean copy
     * </p>
     *
     * @param source     源对象
     * @param targetBean 需要赋值的对象
     */
    public static void copyNonNull(@Nullable Object source, @Nullable Object targetBean) {
        if (source == null || targetBean == null) {
            return;
        }
        BeanCopier copier = BeanCopier
                .create(source.getClass(), targetBean.getClass(), false, true);

        copier.copy(source, targetBean, null);
    }

    /**
     * Copy the property values of the given source bean into the target class.
     * <p>Note: The source and target classes do not have to match or even be derived
     * from each other, as long as the props match. Any bean props that the
     * source bean exposes but the target bean does not will silently be ignored.
     * <p>This is just a convenience method. For more complex transfer needs,
     *
     * @param source the source bean
     * @param targetClazz the target bean class
     * @param <T>    泛型标记
     * @return T
     * @throws BeansException if the copying failed
     */
    @Nullable
    public static <T> T copyProperties(@Nullable Object source, Class<T> targetClazz) throws BeansException {
        if (source == null) {
            return null;
        }
        T to = newInstance(targetClazz);
        BeanUtil.copyProperties(source, to);
        return to;
    }

    /**
     * Copy the property values of the given source bean into the target class.
     * <p>Note: The source and target classes do not have to match or even be derived
     * from each other, as long as the props match. Any bean props that the
     * source bean exposes but the target bean does not will silently be ignored.
     * <p>This is just a convenience method. For more complex transfer needs,
     *
     * @param sourceList the source list bean
     * @param targetClazz the target bean class
     * @param <T>    泛型标记
     * @return List
     * @throws BeansException if the copying failed
     */
    public static <T> List<T> copyProperties(@Nullable Collection<?> sourceList, Class<T> targetClazz) throws BeansException {
        if (sourceList == null || sourceList.isEmpty()) {
            return Collections.emptyList();
        }
        List<T> outList = new ArrayList<>(sourceList.size());
        for (Object source : sourceList) {
            if (source == null) {
                continue;
            }
            T bean = BeanUtil.copyProperties(source, targetClazz);
            outList.add(bean);
        }
        return outList;
    }

    /**
     * 将对象装成map形式
     *
     * @param bean 源对象
     * @return {Map}
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> toMap(@Nullable Object bean) {
        if (bean == null) {
            return new HashMap<>(0);
        }
        return BeanMap.create(bean);
    }

    /**
     * 将map 转为 bean
     *
     * @param beanMap   map
     * @param valueType 对象类型
     * @param <T>       泛型标记
     * @return {T}
     */
    public static <T> T toBean(Map<String, Object> beanMap, Class<T> valueType) {
        Objects.requireNonNull(beanMap, "beanMap Could not null");
        T to = newInstance(valueType);
        if (beanMap.isEmpty()) {
            return to;
        }
        BeanUtil.copy(beanMap, to);
        return to;
    }

    /**
     * 给一个Bean添加字段
     *
     * @param superBean 父级Bean
     * @param props     新增属性
     * @return {Object}
     */
    @Nullable
    public static Object generator(@Nullable Object superBean, BeanProperty... props) {
        if (superBean == null) {
            return null;
        }
        Class<?> superclass = superBean.getClass();
        Object genBean = generator(superclass, props);
        BeanUtil.copy(superBean, genBean);
        return genBean;
    }

    /**
     * 给一个class添加字段
     *
     * @param superclass 父级
     * @param props      新增属性
     * @return {Object}
     */
    public static Object generator(Class<?> superclass, BeanProperty... props) {
        BeanGenerator generator = new BeanGenerator();
        generator.setSuperclass(superclass);
        generator.setUseCache(true);
        for (BeanProperty prop : props) {
            generator.addProperty(prop.getName(), prop.getType());
        }
        return generator.create();
    }

}