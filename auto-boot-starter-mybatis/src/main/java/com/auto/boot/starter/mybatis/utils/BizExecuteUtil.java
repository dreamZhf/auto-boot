package com.auto.boot.starter.mybatis.utils;

import com.auto.boot.common.exception.ServiceNotRollbackException;
import com.auto.boot.starter.mybatis.template.ServiceActuator;
import com.auto.boot.starter.mybatis.template.ServiceNotResultActuator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * 业务执行工具类
 *
 * @author zhaohaifan
 */
@Slf4j
public class BizExecuteUtil {

    private static PlatformTransactionManager platformTransactionManager;

    public static void setPlatformTransactionManager(PlatformTransactionManager platformTransactionManager) {
        BizExecuteUtil.platformTransactionManager = platformTransactionManager;
    }

    /**
     * 通过事务执行业务
     *
     * @param actuator 业务执行器
     * @param definition 事务定义
     * @return T 返回结果
     */
    public static <T> T transactionalExecute(ServiceActuator<T> actuator,
                                             DefaultTransactionDefinition definition) throws Exception {
        TransactionStatus transactionStatus = null;
        T result;
        try {
            transactionStatus = platformTransactionManager.getTransaction(definition);
            actuator.before();
            result = actuator.execute();
            if (!transactionStatus.isCompleted()) {
                platformTransactionManager.commit(transactionStatus);
            }
            return result;
        } catch (ServiceNotRollbackException e) {
            if (transactionStatus != null && !transactionStatus.isCompleted()) {
                platformTransactionManager.commit(transactionStatus);
            }
            throw e;
        } catch (Exception e) {
                log.error("业务执行异常，回滚数据库", e);
            if (transactionStatus != null && !transactionStatus.isCompleted()) {
                platformTransactionManager.rollback(transactionStatus);
            }
            throw e;
        }
    }

    /**
     * 通过事务执行业务
     *
     * @param actuator 业务执行器
     * @return T 返回结果
     */
    public static <T> T transactionalExecute(ServiceActuator<T> actuator) throws Exception {
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition(
                TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        return transactionalExecute(actuator, definition);
    }

    /**
     * 通过事务执行业务
     *
     * @param actuator 业务执行器
     */
    public static void transactionalNotResultExecute(ServiceNotResultActuator actuator) throws Exception {
        transactionalExecute(actuator);
    }

    /**
     * 通过无事务执行业务
     *
     * @param actuator 业务执行器
     * @return T 返回结果
     */
    public static <T> T noTransactionalExecute(ServiceActuator<T> actuator) throws Exception {
        TransactionStatus transactionStatus = null;
        T result;
        try {
            DefaultTransactionDefinition definition = new DefaultTransactionDefinition(
                    TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
            transactionStatus = platformTransactionManager.getTransaction(definition);
            actuator.before();
            result = actuator.execute();
            return result;
        } finally {
            if (transactionStatus != null && !transactionStatus.isCompleted()) {
                platformTransactionManager.commit(transactionStatus);
            }
        }
    }

    /**
     * 通过无事务执行业务
     *
     * @param actuator 业务执行器
     */
    public static void noTransactionalNotResultExecute(ServiceNotResultActuator actuator) throws Exception {
        noTransactionalExecute(actuator);
    }
}
