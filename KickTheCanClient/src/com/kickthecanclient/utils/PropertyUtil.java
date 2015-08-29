package com.kickthecanclient.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kickthecanclient.beans.property.PropertyBean;

/**
 * プロパティ関連の処理クラス.
 *
 * @author ebihara
 */
public class PropertyUtil {

	public static void setProperty(Object target, String name, Object value) {
	    PropertyBean descriptor = getPropertyBean(target, name);
	    try {
			descriptor.getWriteMethod().invoke(target, new Object[]{value});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Object getProperty(Object bean, String propertyName) {
		Object property = null;
		try {
			property = getPropertyBean(bean, propertyName).getReadMethod().invoke(bean, new Object[]{});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return property;
	}

	public static void copyProperties(Object orig, Object dest) {

		if ((orig instanceof Map)) {
			for (Map.Entry<?,?> entry : ((Map<?, ?>) orig).entrySet()) {
				String name = entry.getKey().toString();
				if (isWriteable(dest, name)) {
					setProperty(dest, name, entry.getValue());
				}
			}
		} else {
			List<PropertyBean> origDescriptors = getPropertyBeans(orig.getClass());
			for (PropertyBean origDescriptor : origDescriptors) {
				String name = origDescriptor.getName();
				if (isReadable(orig, name) && isWriteable(dest, name)) {
					setProperty(dest, name, getProperty(orig, name));
				}
			}
		}
	}

	public static List<PropertyBean> getPropertyBeans(Class<? extends Object> clazz) {
		List<Method> methods = accessors(clazz);
		Set<String> properties = new HashSet<String>();
		for (Method method : methods) {
			properties.add(method.getName().substring(3));
		}

		List<PropertyBean> PropertyBeans = new ArrayList<PropertyBean>();
		for (String property : properties) {
			Method readMethod = getMethodWithPrefix(methods, "get", property);
			Method writeMethod = getMethodWithPrefix(methods, "set", property);
			PropertyBean descriptor = new PropertyBean(property, readMethod, writeMethod);
			PropertyBeans.add(descriptor);
		}

		Collections.sort(PropertyBeans, new Comparator<PropertyBean>() {
			public int compare(PropertyBean t, PropertyBean t1) {
				return t.getName().compareTo(t1.getName());
			}
		});
		return PropertyBeans;
	}

	private static boolean isReadable(Object bean, String propertyName) {
		return getPropertyBean(bean, propertyName).getReadMethod() != null;
	}

	private static boolean isWriteable(Object target, String name) {
		PropertyBean descriptor = getPropertyBean(target, name);
		boolean retval = descriptor.getWriteMethod() != null;
		return retval;
	}

	private static Method getMethodWithPrefix(List<Method> methods, String prefix, String name) {
		for (Method method : methods) {
			if (method.getName().equals(prefix + name)) {
				return method;
			}
		}
		return null;
	}

	private static PropertyBean getPropertyBean(Object target, String name) {
		List<PropertyBean> descriptors = getPropertyBeans(target.getClass());
		for (PropertyBean descriptor : descriptors) {
			if (descriptor.getName().equals(name)) {
				return descriptor;
			}
		}
		return null;
	}

	private static List<Method> accessors(Class<?> clazz) {
		List<Method> retval = new ArrayList<Method>();
		for (Method method : clazz.getMethods()) {
			if (!method.getName().endsWith("Class") && !(method.getName().length() == 11 && method.getName().endsWith("Property"))) {
				if (method.getName().startsWith("get") || method.getName().startsWith("set")) {
					retval.add(method);
				}
			}
		}
		return retval;
	}
}
