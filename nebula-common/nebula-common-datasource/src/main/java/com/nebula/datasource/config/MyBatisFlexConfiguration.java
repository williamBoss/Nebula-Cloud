package com.nebula.datasource.config;

import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.core.FlexGlobalConfig;
import com.mybatisflex.core.FlexGlobalConfig.KeyConfig;
import com.mybatisflex.core.audit.AuditManager;
import com.mybatisflex.core.query.QueryColumnBehavior;
import com.mybatisflex.spring.boot.MyBatisFlexCustomizer;
import com.nebula.datasource.domain.BaseEntity;
import com.nebula.datasource.listener.BaseListener;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@MapperScan("com.nebula.**.mapper")
public class MyBatisFlexConfiguration implements MyBatisFlexCustomizer {

    @Override
    public void customize(FlexGlobalConfig flexGlobalConfig) {
        log.info("=======Mybatis Flex 配置加载=======");
        KeyConfig keyConfig = new KeyConfig();
        keyConfig.setKeyType(KeyType.Auto);
        flexGlobalConfig.setKeyConfig(keyConfig);
        flexGlobalConfig.setLogicDeleteColumn("del_flag");
        BaseListener baseListener = new BaseListener();
        flexGlobalConfig.registerInsertListener(baseListener, BaseEntity.class);
        flexGlobalConfig.registerUpdateListener(baseListener, BaseEntity.class);
        // 使用内置规则自动忽略 null 和 空白字符串
        QueryColumnBehavior.setIgnoreFunction(QueryColumnBehavior.IGNORE_BLANK);
        // 如果传入的值是集合或数组，则使用 in 逻辑，否则使用 =（等于） 逻辑
        QueryColumnBehavior.setSmartConvertInToEquals(true);
        // 开启sql审计
        AuditManager.setAuditEnable(false);
    }
}