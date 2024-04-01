package com.nebula.datasource.listener;

import com.mybatisflex.annotation.InsertListener;
import com.mybatisflex.annotation.UpdateListener;
import com.nebula.datasource.domain.BaseEntity;

public class BaseListener implements InsertListener, UpdateListener {

    @Override
    public void onInsert(Object o) {
        BaseEntity baseEntity = (BaseEntity) o;
        baseEntity.setCreateId(1L);
        baseEntity.setCreateBy("admin");
        baseEntity.setUpdateId(1L);
        baseEntity.setUpdateBy("admin");
    }

    @Override
    public void onUpdate(Object o) {
        BaseEntity baseEntity = (BaseEntity) o;
        baseEntity.setUpdateId(1L);
        baseEntity.setUpdateBy("admin");
    }
}
