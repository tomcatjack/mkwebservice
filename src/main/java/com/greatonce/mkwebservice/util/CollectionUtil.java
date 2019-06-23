package com.greatonce.mkwebservice.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import org.apache.commons.collections4.map.CaseInsensitiveMap;

/**
 * 集合工具类
 * @author Grh
 * @author buer
 * @version 2017-08-21 18:24 1.0
 */
public class CollectionUtil {

    /**
     * 根据属性去重
     * @param keyExtractor
     * @param <T>
     */
    public static <T> Predicate<T> distinct(Function<? super T,?> keyExtractor){
        Map<Object,Boolean> seen = new ConcurrentHashMap<>();
        return t->seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    /**
     * List 按指定列排序
     * @param list
     * @param method
     * @param sortType
     * @param <E>
     */
    public static <E> void sort(List<E> list, final String method, final SortType sortType){
        list.sort((a, b)->{
            int ret = 0;
            try{
                Method m1 = ((E) a).getClass().getMethod(method, null);
                Method m2 = ((E) b).getClass().getMethod(method, null);
                if(sortType == SortType.ASC){
                    ret = m1.invoke(a, null).toString().compareTo(m2.invoke(b, null).toString());
                } else{
                    ret = m2.invoke(b, null).toString().compareTo(m1.invoke(a, null).toString());
                }
            } catch(NoSuchMethodException | InvocationTargetException | IllegalAccessException ne){
                System.out.println(ne);
            }
            return ret;
        });
    }

    /**
     * List转Map
     * @param list     集合
     * @param function 提取Key函数
     * @param <K>
     * @param <V>
     */
    public static <K, V> Map<K,V> listToMap(List<V> list, Function<V,K> function){
        if(null == list || list.isEmpty()) return null;
        Map<K,V> map = new CaseInsensitiveMap<>(list.size());
        for(V v : list){
            map.put(function.apply(v), v);
        }
        return map;
    }

    /**
     * List分组为Map
     * @param list
     * @param function
     * @param <K>
     * @param <V>
     */
    public static <K, V> Map<K,List<V>> listToMapList(List<V> list, Function<V,K> function){
        if(null == list || list.isEmpty()) return null;
        Map<K,List<V>> map = new CaseInsensitiveMap<>();
        for(V v : list){
            K k = function.apply(v);
            if(!map.containsKey(k)){
                map.put(k, new ArrayList<>());
            }
            map.get(k).add(v);
        }
        return map;
    }

    public enum SortType{
        ASC,
        DESC
    }
}
