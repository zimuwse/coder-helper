package org.lr.helper.config;

import com.alibaba.fastjson.JSON;
import org.lr.helper.util.ParamUtil;

import java.lang.reflect.ParameterizedType;

/**
 * @author: zimuwse
 * @time: 2018-01-22 09:48
 * @description:
 */
public abstract class BaseConf<T> {

    /**
     * config key
     *
     * @return
     */
    abstract protected String key();

    /**
     * checkItem config before save
     * throws exception to stop save operation
     *
     * @param config
     * @return
     */
    abstract protected boolean check(T config);

    /**
     * get default value
     *
     * @return
     */
    abstract protected T def();

    /**
     * key prefix
     *
     * @return
     */
    protected String prefixKey() {
        return "code-helper." + key();
    }

    /**
     * get the config of key
     *
     * @return
     */
    public T get() {
        String json = Conf.get(prefixKey());
        if (ParamUtil.isNullOrBlank(json))
            return def();
        return JSON.parseObject(json, ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    /**
     * save config
     *
     * @param config
     */
    public void save(T config) {
        try {
            if (check(config))
                Conf.set(prefixKey(), JSON.toJSONString(config));
        } catch (Exception e) {
            //todo how to show exception
        }
    }
}
