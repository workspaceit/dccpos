package com.workspaceit.pos.dao.accounting;

import com.workspaceit.pos.constant.accounting.GROUP_CODE;
import com.workspaceit.pos.dao.BaseDao;
import com.workspaceit.pos.entity.Employee;
import com.workspaceit.pos.entity.accounting.Ledger;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class LedgerDao extends BaseDao {
    public List<Ledger> findAll(){
        Session session = this.getCurrentSession();
        return session.createQuery("FROM Ledger")
                .list();
    }
    public Ledger findByPersonalInfoIdAndCode(int personalInformationId, GROUP_CODE groupCode){
        Session session = this.getCurrentSession();
        return (Ledger)session.createQuery(" FROM Ledger lg where lg.personalInformation.id =:personalInformationId " +
                                                " and lg.groupAccount.code=:groupCode")
                .setParameter("personalInformationId",personalInformationId)
                .setParameter("groupCode",groupCode)
                .setMaxResults(1)
                .uniqueResult();
    }
}