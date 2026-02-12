package com.ethan.janus.core.manager;

import com.ethan.janus.core.compare.JanusCompare;
import com.ethan.janus.core.exception.JanusException;
import com.ethan.janus.core.utils.JanusAopUtils;
import com.ethan.janus.core.utils.JanusUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class JanusCompareManager implements ApplicationRunner {

    @Autowired
    private ApplicationContext applicationContext;

    private final Map<Class<? extends JanusCompare>, JanusCompare> map = new HashMap<>();

    @Override
    public void run(ApplicationArguments args) {
        Map<String, JanusCompare> beanMap = applicationContext.getBeansOfType(JanusCompare.class);
        if (JanusUtils.isNotEmpty(beanMap)) {
            for (JanusCompare janusCompare : beanMap.values()) {
                // 防止有动态代理类导致无法获取正确的class，先获取原始的bean对象
                JanusCompare target = (JanusCompare) JanusAopUtils.getProxyTarget(janusCompare);
                // bean 类型
                Class<? extends JanusCompare> clazz = target.getClass();
                map.put(clazz, janusCompare);
            }
        }
    }

    /**
     * 获取比对实现
     *
     * @param clazz 具体实现的类对象
     * @return 具体实现对象
     */
    public JanusCompare getJanusCompare(Class<? extends JanusCompare> clazz) {
        JanusCompare janusCompare = this.map.get(clazz);
        if (janusCompare == null) {
            // bean 未找到
            throw new JanusException("No JanusCompare of type [" + clazz.getName() + "] found");
        }
        return janusCompare;
    }
}
