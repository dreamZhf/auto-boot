package com.auto.boot.starter.mybatis.template;

/**
 * 业务执行器 无返回结果
 *
 * @author zhaohaifan
 */
public interface ServiceNotResultActuator extends ServiceActuator<Void> {

    /**
     * 业务执行
     *
     * @return 返回结果
     * @throws Exception 异常
     */
    @Override
    default Void execute() throws Exception {
        doExecute();
        return null;
    }

    /**
     * 执行业务, 无返回值
     *
     * @throws Exception 异常
     */
    void doExecute() throws Exception;
}
