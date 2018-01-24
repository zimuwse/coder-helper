package org.lr.helper.ui;

import org.lr.helper.model.KV;

import java.util.List;

/**
 * @author: zimuwse
 * @time: 2018-01-23 10:59
 * @description:
 */
public interface IMultiSelect {

    List<KV> getOptions();

    void onFinishMultiSelect(List<KV> kvs);
}
