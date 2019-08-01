package com.piglet.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextRegister implements ApplicationContextAware {

    private static ApplicationContext applicationContext;
    /**
     *  设置spring上下文
     * @param applicationContext spring上下文
     * @throws BeansException
     * */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextRegister.applicationContext = applicationContext;
    }
    /**
     * 获取容器
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 获取容器对象
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> type) {
        return applicationContext.getBean(type);
    }
}