package com.lucasmoyano.willywonka.dao.imp;

import com.github.tennaito.rsql.jpa.JpaCriteriaCountQueryVisitor;
import com.github.tennaito.rsql.jpa.JpaCriteriaQueryVisitor;
import com.lucasmoyano.willywonka.dao.CrudDao;
import com.lucasmoyano.willywonka.entity.BaseEntity;
import com.lucasmoyano.willywonka.utils.PageAdapter;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import cz.jirutka.rsql.parser.ast.RSQLVisitor;
import org.hibernate.jpa.criteria.OrderImpl;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CrudDaoImp<TEntity extends BaseEntity> implements CrudDao<TEntity> {

    private final int PAGE_SIZE = 20;

    protected Class<TEntity> entityClass;

    public CrudDaoImp(Class<TEntity> entityClass) {
        this.entityClass = entityClass;
    }


    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    @Transactional
    public TEntity get(long id) {
        return entityManager.find(entityClass, id);
    }

    @Override
    public List<TEntity> getAll() {
        return (List<TEntity>) getAll("id");
    }

    @Override
    @Transactional
    public List<TEntity> getAll(String orderField) {
        String hql = "FROM " + entityClass.getName() + " as u ORDER BY u." + orderField;
        return (List<TEntity>) entityManager.createQuery(hql).getResultList();
    }

    @Transactional
    @Override
    public List<TEntity> save(List<TEntity> list) {

        for (TEntity entity : list) {
            if (entity == null) {
                continue;
            }
            if (entity.getId() > 0) {
                entityManager.merge(entity);
            } else {
                entityManager.persist(entity);
            }
        }
        return list;
    }

    @Transactional
    @Override
    public TEntity save(TEntity entity) {
        List<TEntity> list = new ArrayList<>();
        list.add(entity);
        List<TEntity> result = this.save(list);
        if (result.size() == 0) {
            return null;
        }
        return result.get(0);
    }

    @Override
    @Transactional
    public boolean exists(long id) {
        String hql = "FROM " + entityClass.getName() + " as u WHERE u.id = ?";
        int count = entityManager.createQuery(hql).setParameter(1, id)
                .getResultList().size();
        return count > 0 ? true : false;
    }

    @Override
    @Transactional
    public void delete(long[] ids) {
        for (long id : ids) {
            entityManager.remove(get(id));
        }
    }

    @Override
    @Transactional
    public void delete(long id) {
        entityManager.remove(get(id));
    }

    /**
     * Make a generic search with RSQL.
     * Documentation: https://github.com/tennaito/rsql-jpa
     * @param params can be "query", "orderBy", or "asc"
     * @return
     */
    @Override
    @Transactional
    public List<TEntity> search(Map<String, Object> params) {
        String rsql = params.containsKey("query") ? String.valueOf(params.get("query")) : "";

        JpaCriteriaQueryVisitor criteria = new JpaCriteriaQueryVisitor();
        criteria.setEntityClass(entityClass);

        RSQLVisitor<CriteriaQuery<TEntity>, EntityManager> visitor = criteria;
        CriteriaQuery<TEntity> criteriaQuery = getCriteriaByRsql(rsql, visitor);

        setOrderByParams(criteriaQuery, params);
        Query query = entityManager.createQuery(criteriaQuery);
        setPageableByParams(query, params);

        List<TEntity> resultList = entityManager.createQuery(criteriaQuery).getResultList();
        return query.getResultList();
    }

    /**
     * Make the search with a pagination format. Need the params "query" and "page".
     * @param params can be "query", "page", "size", "orderBy", or "asc"
     * @return
     */
    @Override
    @Transactional
    public PageAdapter<TEntity> searchPageable(Map<String, Object> params) {
        String rsql = params.containsKey("query") ? String.valueOf(params.get("query")) : "";
        int page = params.containsKey("page") ? Integer.valueOf((String) params.get("page")) : 1;
        int size = params.containsKey("size") ? Integer.valueOf((String) params.get("size")) : PAGE_SIZE;

        List<TEntity> elements = search(params);
        long totalElements = countByRsql(rsql);

        return new PageAdapter<>(elements, totalElements, page, size);
    }

    /**
     * Set the order field by parameters in a criteria query.
     * @param criteriaQuery
     * @param params can be "orderBy" and "asc".
     */
    private void setOrderByParams(CriteriaQuery<TEntity> criteriaQuery, Map<String, Object> params) {
        boolean isAsc = true;
        String orderType = (String) params.get("asc");
        if (!StringUtils.isEmpty(orderType)) {
            isAsc = Boolean.valueOf(orderType);
        }

        String orderField = (String) params.get("orderBy");
        if (!StringUtils.isEmpty(orderField)) {
            Root<Order> routeRoot = (Root<Order>) criteriaQuery.getRoots().iterator().next();
            Order order = new OrderImpl(routeRoot.get(orderField), isAsc);
            criteriaQuery.orderBy(order);
        }
    }

    private void setPageableByParams(Query query, Map<String, Object> params) {
        String pageParam = (String) params.get("page");
        if (!StringUtils.isEmpty(pageParam)) {
            int page = Integer.valueOf(pageParam);
            int size = PAGE_SIZE;

            String sizeParam = (String) params.get("size");
            if (!StringUtils.isEmpty(sizeParam)) {
                size = Integer.valueOf(sizeParam);
            }
            query.setMaxResults(size);
            query.setFirstResult((page - 1) * size);
        }
    }

    private Long countByRsql(String rsql) {
        JpaCriteriaCountQueryVisitor criteria = new JpaCriteriaCountQueryVisitor();
        criteria.setEntityClass(entityClass);
        RSQLVisitor<CriteriaQuery<Long>, EntityManager> visitor = criteria;
        CriteriaQuery<Long> query = getCriteriaByRsql(rsql, visitor);
        return entityManager.createQuery(query).getSingleResult();
    }

    private <TEntity> CriteriaQuery<TEntity> getCriteriaByRsql(String rsql, RSQLVisitor<CriteriaQuery<TEntity>
            , EntityManager> visitor) {
        Node rootNode;
        CriteriaQuery<TEntity> query;
        rsql = rsql.equals("") ? "id>=0" : rsql;
        try {
            rootNode = new RSQLParser().parse(rsql);
            query = rootNode.accept(visitor, entityManager);
        } catch (Exception e) {
            //log.error("An error happened while executing RSQL query", e);
            throw new IllegalArgumentException(e.getMessage());
        }
        return query;
    }

}
