/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.pubg.analysis.base;

import com.pubg.analysis.entity.log.BaseLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

/**
 * @author sunpeikai
 * @version MongoBaseDao, v0.1 2020/7/10 10:40
 * @description
 */
@Slf4j
public abstract class MongoBaseDao<T> {

    @Autowired
    protected MongoTemplate mongoTemplate;

    public void insert(T t) {
        this.mongoTemplate.insert(t);
    }

    public void insert(T t, String collectionName) {
        this.mongoTemplate.insert(t, collectionName);
    }

    public void insertAll(List<T> listT) {
        this.mongoTemplate.insertAll(listT);
    }

    public void insertClass(List<T> listT) {
        this.mongoTemplate.insert(listT);
    }

    public void save(T t) {
        this.mongoTemplate.save(t);
    }

    public void save(T t, String collectionName) {
        this.mongoTemplate.save(t, collectionName);
    }

    public T findOne(Query query) {
        return this.mongoTemplate.findOne(query, this.getEntityClass());
    }

    public T findOne(Query query, String collectionName) {
        return this.mongoTemplate.findOne(query, this.getEntityClass(), collectionName);
    }

    public List<T> find(Query query) {
        return this.mongoTemplate.find(query, this.getEntityClass());
    }

    public List<T> find(Query query, String collectionName) {
        return this.mongoTemplate.find(query, this.getEntityClass(), collectionName);
    }

    public Page<T> page(Query query,Integer page,Integer limit){
        Page<T> result = new Page<>();
        // 查询条件添加分页
        query.skip((page - 1) * limit).limit(limit);
        // 查询数据
        List<T> records = find(query);
        // 查询总条数
        int count = count(query).intValue();
        // 处理数据
        return result.records(records).total(count).current(page).size(limit);
    }

    public void update(Query query, Update update) {
        this.mongoTemplate.upsert(query, update, this.getEntityClass());
    }

    public void updateAll(Query query, Update update) {
        this.mongoTemplate.updateMulti(query, update, this.getEntityClass());
    }

    public void del(Query query) {
        this.mongoTemplate.remove(query, this.getEntityClass());
    }

    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    protected abstract Class<T> getEntityClass();

    public void deleteBatch(List list) {
        for(int i = 0; i < list.size(); ++i) {
            this.mongoTemplate.remove(list.get(i));
        }

    }

    public Long count(Query query) {
        return this.mongoTemplate.count(query, this.getEntityClass());
    }

	public List<T> aggregate(Aggregation aggregation, String collectionName) {

        AggregationResults<T> results = this.mongoTemplate.aggregate(aggregation, collectionName, this.getEntityClass());
        log.debug("读取到聚合数据: {}", results.getRawResults().toJson());
		return results.getMappedResults();
	}

    public <K> List<K> aggregate(Class<K> clazz, Aggregation aggregation, String collectionName) {

        AggregationResults<K> results = this.mongoTemplate.aggregate(aggregation, collectionName, clazz);
        log.debug("读取到聚合数据: {}", results.getRawResults().toJson());
        return results.getMappedResults();
    }
}
