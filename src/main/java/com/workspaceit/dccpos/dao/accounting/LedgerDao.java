package com.workspaceit.dccpos.dao.accounting;

import com.workspaceit.dccpos.constant.accounting.GROUP_CODE;
import com.workspaceit.dccpos.constant.accounting.LEDGER_CODE;
import com.workspaceit.dccpos.dao.BaseDao;
import com.workspaceit.dccpos.entity.accounting.Ledger;
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
    public Ledger findByCode( LEDGER_CODE ledgerCode){
        Session session = this.getCurrentSession();
        return (Ledger)session.createQuery(" FROM Ledger lg where lg.code =:ledgerCode ")
                .setParameter("ledgerCode",ledgerCode)
                .setMaxResults(1)
                .uniqueResult();
    }
    public Ledger findById( int id){
        Session session = this.getCurrentSession();
        return (Ledger)session.createQuery(" FROM Ledger lg where lg.id =:id ")
                .setParameter("id",id)
                .setMaxResults(1)
                .uniqueResult();
    }
    public Ledger findByPersonalInfoIdAndGroupCode(int personalInformationId, GROUP_CODE groupCode){
        Session session = this.getCurrentSession();
        return (Ledger)session.createQuery(" FROM Ledger lg where lg.personalInformation.id =:personalInformationId " +
                                                " and lg.groupAccount.code=:groupCode")
                .setParameter("personalInformationId",personalInformationId)
                .setParameter("groupCode",groupCode)
                .setMaxResults(1)
                .uniqueResult();
    }
    public Ledger findByCompanyAndGroupCode(int companyId, GROUP_CODE groupCode){
        Session session = this.getCurrentSession();
        return (Ledger)session.createQuery(" FROM Ledger lg where lg.company.id =:companyId " +
                                        " and lg.groupAccount.code=:groupCode ")
                .setParameter("companyId",companyId)
                .setParameter("groupCode",groupCode)
                .setMaxResults(1)
                .uniqueResult();
    }
}