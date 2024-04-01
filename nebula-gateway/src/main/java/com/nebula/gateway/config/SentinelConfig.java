package com.nebula.gateway.config;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
import jakarta.annotation.PostConstruct;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class SentinelConfig {

    /**
     * 初始化流控规则
     */
    @PostConstruct
    public void initFlowRules() {
        log.info("初始化流控规则");
        FlowRule rule = new FlowRule();
        rule.setResource("customResource"); // 资源名，对应请求路径
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS); // 限流阈值类型，这里是每秒查询量QPS
        rule.setCount(100); // 限流阈值
        rule.setStrategy(RuleConstant.STRATEGY_DIRECT); // 流控模式，直接限流
        rule.setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_DEFAULT); // 流控效果，直接拒绝
        FlowRuleManager.loadRules(Collections.singletonList(rule));
    }

    /**
     * 初始化降级规则
     */
    @PostConstruct
    public void initDegradeRules() {
        log.info("初始化降级规则");
        DegradeRule rule = new DegradeRule();
        rule.setResource("customResource");
        rule.setGrade(RuleConstant.DEGRADE_GRADE_RT); // 根据RT降级
        rule.setCount(200); // 平均响应时间阈值，超过该值则降级
        rule.setTimeWindow(60); // 降级的时间窗口，单位为s
        DegradeRuleManager.loadRules(Collections.singletonList(rule));
    }

    /**
     * 初始化系统规则
     */
    @PostConstruct
    public void initSystemRules() {
        log.info("初始化系统规则");
        SystemRule rule = new SystemRule();
        rule.setHighestSystemLoad(10.0); // 最高系统负载
        rule.setHighestCpuUsage(0.8); // 最高CPU使用率
        rule.setAvgRt(1000); // 所有入口资源的平均响应时间
        rule.setMaxThread(100); // 入口流量的最大并发数
        rule.setQps(100); // 每秒总请求数
        SystemRuleManager.loadRules(Collections.singletonList(rule));
    }

    /**
     * 注册切面 支持使用@SentinelResource注解进行声明式的资源保护
     */
    @Bean
    public SentinelResourceAspect sentinelResourceAspect() {
        return new SentinelResourceAspect();
    }
}
