package com.dqv5.backstage.hibernate;

import com.dqv5.backstage.web.PageBean;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("unchecked")
public class BaseDao {

    private Logger logger = LoggerFactory.getLogger(BaseDao.class);

    /**
     * 查询类型 HQL
     */
    public static final String HQL_QUERY_TYPE = "HQL";

    /**
     * 查询类型 SQL
     */
    public static final String SQL_QUERY_TYPE = "SQL";
    /**
     * SessionFactory
     */
    protected SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    /**
     * 取得当前Session
     *
     * @return
     */
    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * 保存实体
     *
     * @param entity
     * @return
     */
    public Object save(Object entity) {
        this.getCurrentSession().save(entity);
        return entity;
    }

    /**
     * 更新实体
     *
     * @param entity
     * @return
     */
    public Object update(Object entity) {
        this.getCurrentSession().update(entity);
        return entity;
    }


    /**
     * 删除实体
     *
     * @param entity
     */
    public void delete(Object entity) {
        this.getCurrentSession().delete(entity);
    }

    /**
     * 使用sql语句删除数据(可以多条)
     *
     * @param queryString
     * @param param
     * @return
     */
    public int deleteBySQL(String queryString, List<Object> param) {
        return createQuery(queryString, param, SQL_QUERY_TYPE).executeUpdate();
    }

    public Object merge(Object entity) {
        this.getCurrentSession().merge(entity);
        return entity;
    }

    /**
     * 根据主键查找bean
     *
     * @param <T>
     * @param clazz
     * @param id
     * @return
     */
    public <T> T get(Class<T> clazz, Serializable id) {
        return (T) this.getCurrentSession().get(clazz, id);
    }

    /**
     * 保存更新
     *
     * @param entity
     * @return
     */
    public Object saveOrUpdate(Object entity) {
        this.getCurrentSession().saveOrUpdate(entity);
        return entity;
    }

    /**
     * SQL查询　根据查询函数与参数列表创建Query对象,后续可进行更多处理辅助函数(可以自动填充占位符).
     *
     * @param queryString 查询语句
     * @param param       参数集合
     * @param type        SQL or HQL
     * @return Query对象
     */
    protected Query createQuery(String queryString, List<Object> param,
                                String type) {
        return createQuery(queryString, param, null, type);
    }

    protected Query createQuery(String queryString, List<Object> param,
                                Map param2, String type) {
        Assert.hasText(queryString);
        Query queryObject = null;
        if (type.equals(SQL_QUERY_TYPE)) {
            queryObject = this.getCurrentSession().createSQLQuery(queryString);
        } else {
            queryObject = this.getCurrentSession().createQuery(queryString);
        }
        if (param != null && param.size() > 0) {
            for (int i = 0; i < param.size(); i++) {
                if (param.get(i) == null) {
                    queryObject.setParameter(i, new String());
                } else {
                    queryObject.setParameter(i, param.get(i));
                }
            }
        }
        if (param2 != null && param2.size() > 0) {
            Set<String> keySet = param2.keySet();
            for (String key : keySet) {
                Object obj = param2.get(key);
                if (obj instanceof Collection<?>) {
                    queryObject.setParameterList(key, (Collection<?>) obj);
                } else if (obj instanceof String[]) {
                    queryObject.setParameterList(key, (String[]) obj);
                } else if (obj instanceof Object[]) {
                    queryObject.setParameterList(key, (Object[]) obj);
                } else {
                    queryObject.setParameter(key, obj);
                }
            }
        }
        return queryObject;
    }

    /**
     * SQL查询　根据查询函数与参数列表创建Query对象,后续可进行更多处理辅助函数.
     *
     * @param queryString 查询语句
     * @param param       参数集合
     * @param type        SQL or HQL
     * @return Query对象
     */
    protected Query createQueryByName(String queryString,
                                      Map<String, Object> param, String type) {
        Assert.hasText(queryString);
        Query queryObject = null;
        if (type.equals(SQL_QUERY_TYPE)) {
            queryObject = this.getCurrentSession().createSQLQuery(queryString);
        } else {
            queryObject = this.getCurrentSession().createQuery(queryString);
        }
        if (param != null && param.size() > 0) {
            Set<String> keySet = param.keySet();
            for (String key : keySet) {
                Object obj = param.get(key);
                if (obj instanceof Collection<?>) {
                    queryObject.setParameterList(key, (Collection<?>) obj);
                } else if (obj instanceof Object[]) {
                    queryObject.setParameterList(key, (Object[]) obj);
                } else {
                    queryObject.setParameter(key, obj);
                }
            }
        }
        return queryObject;
    }

    /**
     * 统计记录数
     *
     * @param sql
     * @param param
     * @return
     */
    public int getCount(String queryString, List<Object> param, String type) {
        logger.debug("统计记录数");
        queryString = "select count(*) from (" + queryString + ")";
        return Integer.valueOf(createQuery(queryString, param, type)
                .uniqueResult().toString());
    }

    public int getCountByName(String queryString, Map<String, Object> param,
                              String type) {
        logger.debug("统计记录数");
        queryString = "select count(*) from (" + queryString + ")";
        return Integer.valueOf(createQueryByName(queryString, param, type)
                .uniqueResult().toString());
    }

    public int getSqlCount(String sql, List<Object> param) {
        logger.debug("使用SQL统计记录数");
        sql = "select count(*) from (" + sql + ")";
        return Integer.valueOf(createQuery(sql, param, SQL_QUERY_TYPE)
                .uniqueResult().toString());
    }

    public int getSqlCountByName(String hql, Map<String, Object> param) {
        logger.debug("使用SQL统计记录数");
        hql = "select count(*) from (" + hql + ")";
        return Integer.valueOf(createQueryByName(hql, param, HQL_QUERY_TYPE)
                .uniqueResult().toString());
    }

    /**
     * SQL查询列表
     *
     * @param sql   SQL语句
     * @param param 参数
     * @return 集合列表
     */
    public List findSqlList(String sql, List<Object> param) {
        logger.debug("使用SQL查询列表");
        return createQuery(sql, param, SQL_QUERY_TYPE).list();
    }

    public List findSqlList(String sql, List<Object> param, Map param2) {
        logger.debug("使用SQL查询列表");
        return createQuery(sql, param, param2, SQL_QUERY_TYPE).list();
    }

    public List findSqlListByName(String sql, Map<String, Object> param) {
        logger.debug("使用SQL查询列表");
        return createQueryByName(sql, param, SQL_QUERY_TYPE).list();
    }

    /**
     * SQL查询列表返回List<Map>类型数据
     *
     * @param sql   SQL语句
     * @param param 参数
     * @return 集合列表
     */
    public List findSqlListMap(String sql, List<Object> param) {
        logger.debug("使用SQL查询列表,返回Map类型数据");
        return createQuery(sql, param, SQL_QUERY_TYPE).setResultTransformer(
                Transformers.ALIAS_TO_ENTITY_MAP).list();
    }

    /**
     * SQL查询列表返回List<Map>类型数据
     *
     * @param sql   SQL语句
     * @param param 参数
     * @return 集合列表
     */
    public List findSqlListMap(String sql, List<Object> param, Map param2) {
        logger.debug("使用SQL查询列表,返回Map类型数据");
        return createQuery(sql, param, param2, SQL_QUERY_TYPE)
                .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
    }

    public List findSqlListMapByName(String sql, Map<String, Object> param) {
        logger.debug("使用SQL查询列表,返回Map类型数据");
        return createQueryByName(sql, param, SQL_QUERY_TYPE)
                .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
    }

    /**
     * 分页sql语句查询
     *
     * @param sql      SQL语句
     * @param param    参数
     * @param pageNo   当前页码
     * @param pageSize 每页显示记录数
     * @return 分页Bean
     */
    public PageBean findSqlPage(String sql, List<Object> param, int pageNo,
                                int pageSize) {
        logger.debug("使用SQL查询PageBean");
        Query q = createQuery(sql, param, SQL_QUERY_TYPE);
        int count = getCount(sql, param, SQL_QUERY_TYPE);
        List<?> list = q.setFirstResult((pageNo - 1) * pageSize)
                .setMaxResults(pageSize)
                .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        PageBean page = new PageBean(pageNo, pageSize, count, list);
        return page;
    }

    public PageBean findSqlPageByName(String sql, Map<String, Object> param,
                                      int pageNo, int pageSize) {
        logger.debug("使用SQL查询PageBean");
        Query q = createQueryByName(sql, param, SQL_QUERY_TYPE);
        int count = getCountByName(sql, param, SQL_QUERY_TYPE);
        List<?> list = q.setFirstResult((pageNo - 1) * pageSize)
                .setMaxResults(pageSize)
                .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        PageBean page = new PageBean(pageNo, pageSize, count, list);
        return page;
    }

    /**
     * HQL查询列表
     *
     * @param hql   查询语句
     * @param param 参数
     * @return List集合
     */
    public List findHqlList(String hql, List<Object> param) {
        logger.debug("使用HQL查询列表");
        return createQuery(hql, param, HQL_QUERY_TYPE).list();
    }

    public List findHqlListByName(String hql, Map<String, Object> param) {
        logger.debug("使用HQL查询列表");
        return createQueryByName(hql, param, HQL_QUERY_TYPE).list();
    }

    public int executeUpdate(String sql, List<Object> param) {
        return createQuery(sql, param, SQL_QUERY_TYPE).executeUpdate();
    }

    public Object uniqueResult(String sql, List<Object> param) {
        return createQuery(sql, param, SQL_QUERY_TYPE).uniqueResult();
    }

    public Object uniqueResultHql(String hql, List<Object> param) {
        return createQuery(hql, param, HQL_QUERY_TYPE).uniqueResult();
    }

}
