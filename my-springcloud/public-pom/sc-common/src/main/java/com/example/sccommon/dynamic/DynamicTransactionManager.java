package com.example.sccommon.dynamic;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionStatus;

import javax.sql.DataSource;

/**
 * @author qijx
 * @date 2019-10-12 15:57
 */
public class DynamicTransactionManager extends DataSourceTransactionManager {
    public DynamicTransactionManager(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected void doBegin(Object transaction, TransactionDefinition definition) {
        DynamicDataSourceContextHolder.setDataSource(DynamicDataSourceEnum.WRITE.name());
        super.doBegin(transaction, definition);
    }

    @Override
    protected void doCommit(DefaultTransactionStatus status) {
        DynamicDataSourceContextHolder.clearDataSource();
        super.doCommit(status);
    }
}
