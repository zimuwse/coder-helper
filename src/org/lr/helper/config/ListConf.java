package org.lr.helper.config;

import com.alibaba.fastjson.JSON;
import org.lr.helper.util.ParamUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author: zimuwse
 * @time: 2018-01-22 11:41
 * @description:
 */
public abstract class ListConf<T> extends BaseConf<List<T>> {

    abstract boolean checkItem(T item);


    @Override
    public List<T> get() {
        String json = Conf.get(prefixKey());
        if (ParamUtil.isNullOrBlank(json))
            return def();
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        Type realType = parameterizedType.getActualTypeArguments()[0];

        //return (List<T>) JSON.parseArray(json, new Type[]{realType});
        try {
            return (List<T>) JSON.parseArray(json, Class.forName(realType.getTypeName()));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        /*Type type = new ParameterizedType() {
            @Override
            public Type[] getActualTypeArguments() {
                return new Type[]{realType};
            }

            @Override
            public Type getRawType() {
                return ArrayList.class;
            }

            @Override
            public Type getOwnerType() {
                return null;
            }
        };
        return new Gson().fromJson(json, type);
        */
    }


    @Override
    protected boolean check(List<T> config) {
        for (T item : config)
            if (!checkItem(item))
                return false;
        return true;
    }
}
