package com.example.springboot.demo.dynamicDataSource;//package com.example.springcloud.client.dynamicDataSource;
//
///**
// * @author qijx
// * @date 2019-07-04 11:55
// */
//
//import javax.sql.DataSource;
//import org.springframework.transaction.TransactionDefinition;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.transaction.support.DefaultTransactionStatus;
//
//public class DynamicTransactionManager extends DataSourceTransactionManager {
//    public DynamicTransactionManager(DataSource dataSource) {
//        super(dataSource);
//    }
//
//    @Override
//    protected void doBegin(Object transaction, TransactionDefinition definition) {
//        DynamicDataSourceContextHolder.setDataSource(DynamicDataSourceEnum.WRITE.name());
//        super.doBegin(transaction, definition);
//    }
//
//    @Override
//    protected void doCommit(DefaultTransactionStatus status) {
//        DynamicDataSourceContextHolder.clearDataSource();
//        super.doCommit(status);
//    }
//}
