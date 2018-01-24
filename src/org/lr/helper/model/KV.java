package org.lr.helper.model;

/**
 * @author: zimuwse
 * @time: 2018-01-19 20:09
 * @description:
 */
public class KV {
    private String key;
    private String value;

    public KV() {
    }

    public KV(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KV kv = (KV) o;

        return key.equals(kv.key);
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }
}
