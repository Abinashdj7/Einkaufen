package com.Abinash.Nouveauecommerce.Config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * Routes each connection request to the primary or replica datasource
 * based on whether the current Spring transaction is read-only.
 *
 * @Transactional(readOnly = true)  → replica  (SELECT-only traffic)
 * @Transactional                   → primary  (INSERT / UPDATE / DELETE)
 */
public class ReplicationRoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return TransactionSynchronizationManager.isCurrentTransactionReadOnly()
                ? "replica"
                : "primary";
    }
}
