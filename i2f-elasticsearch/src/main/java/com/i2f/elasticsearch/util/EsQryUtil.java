package com.i2f.elasticsearch.util;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

/**
 * @author ltb
 * @date 2021/9/13
 */
public class EsQryUtil {
    /**
     * 查询构造器
     * 基于状态的构造器，根据先指定的状态，执行不同的状态下的构造
     * 用法：
     * BoolQueryBuilder builder=EsQryUtil.builder().
     * ..
     * .done();
     */
    public static class Builder {
        protected BoolQueryBuilder builder;
        public static final int MUST=1;
        public static final int MUST_NOT=2;
        public static final int SHOULD=3;
        public static final int FILTER=4;
        protected int state=MUST;
        public Builder(BoolQueryBuilder builder){
            this.builder=builder;
        }
        public Builder(){
            builder= qry();
        }

        /**
         * 调整为Must状态，之后再进行的条件都会以must添加
         * 直到下一次状态改变
         * @return
         */
        public Builder must(){
            this.state=MUST;
            return this;
        }
        public Builder mustNot(){
            this.state=MUST_NOT;
            return this;
        }
        public Builder should(){
            this.state=SHOULD;
            return this;
        }
        public Builder filter(){
            this.state=FILTER;
            return this;
        }
        public Builder range(String fieldName, Object from, Object to){
            QueryBuilder item= EsQryUtil.range(fieldName,from,to);
            return stateProxy(item);
        }
        public Builder eqs(String fieldName, Object ... vals){
            QueryBuilder item= EsQryUtil.eqs(fieldName, vals);
            return stateProxy(item);
        }
        public Builder eqsByStr(String fieldName, String val,String splitRegex){
            QueryBuilder item= EsQryUtil.eqsByStr(fieldName, val,splitRegex);
            return stateProxy(item);
        }
        public Builder likes(String fieldName, String ... vals){
            QueryBuilder item= EsQryUtil.likes(fieldName, vals);
            return stateProxy(item);
        }

        /**
         * 状态转换解析代理
         * @param item
         * @return
         */
        protected Builder stateProxy(QueryBuilder item){
            switch (this.state){
                case MUST:
                    EsQryUtil.must(builder,item);
                    break;
                case MUST_NOT:
                    EsQryUtil.mustNot(builder,item);
                    break;
                case SHOULD:
                    EsQryUtil.should(builder,item);
                    break;
                case FILTER:
                    EsQryUtil.filter(builder,item);
                    break;
                default:
                    break;
            }
            return this;
        }
        public BoolQueryBuilder done(){
            return builder;
        }

        public EsQry doQry(){
            return new EsQry(builder);
        }
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class EsQry{
        private QueryBuilder queryBuilder;
        private String[] indices;
        private String[] types={"_doc"};
        private Integer pageIndex;
        private Integer pageSize;
        private Integer timeout;
        private TimeUnit timeUnit;

        private SearchSourceBuilder sourceBuilder;

        private SearchRequest request;
        private SearchResponse response;


        public EsQry(QueryBuilder queryBuilder){
            this.queryBuilder=queryBuilder;
        }

        public EsQry request(String index){
            this.indices=new String[1];
            this.indices[0]=index;

            this.types=new String[1];
            this.types[0]="_doc";

            return this;
        }

        public EsQry request(String index,String type){
            this.indices=new String[1];
            this.indices[0]=index;

            this.types=new String[1];
            this.types[0]=type;

            return this;
        }

        public EsQry request(String[] indices,String[] types){
            this.indices=indices;
            this.types=types;
            return this;
        }

        public EsQry query(QueryBuilder queryBuilder){
            this.queryBuilder=queryBuilder;
            return this;
        }

        public EsQry page(Integer pageIndex,Integer pageSize){
            this.pageIndex=pageIndex;
            this.pageSize=pageSize;
            return this;
        }

        public EsQry timeout(int timeout, TimeUnit timeUnit){
            this.timeout=timeout;
            this.timeUnit=timeUnit;
            return this;
        }

        public EsQry submit(RestHighLevelClient client) throws IOException {
            this.request=new SearchRequest();
            this.request.indices(this.indices);
            this.request.types(this.types);

            this.sourceBuilder=new SearchSourceBuilder();
            this.sourceBuilder.query(this.queryBuilder);
            if(this.pageIndex!=null && this.pageSize!=null){
                int offset=this.pageIndex*this.pageSize+1;
                this.sourceBuilder.from(offset);
                this.sourceBuilder.size(this.pageSize);
            }
            if(this.timeout!=null && this.timeUnit!=null) {
                this.sourceBuilder.timeout(new TimeValue(this.timeout, this.timeUnit));
            }

            this.request.source(this.sourceBuilder);

            System.out.println("request:"+this.request);
            System.out.println("source:"+this.sourceBuilder);

            this.response=client.search(this.request, RequestOptions.DEFAULT);

            return this;
        }

        public SearchResponse resp(){
            return this.response;
        }

        public SearchHits hits(){
            return this.response.getHits();
        }
    }

    /**
     * 范围查询
     * 支持from和to出现null
     * 出现null，则是半闭区间查询，否则是完全闭区间查询
     * 同时为null,则返回null,不进行构建，方便实现动态查询逻辑
     * @param fieldName
     * @param from
     * @param to
     * @return
     */
    public static QueryBuilder range(String fieldName,Object from,Object to){
        if(from!=null && to!=null){
            return QueryBuilders.rangeQuery(fieldName).from(from).to(to);
        }
        if(from!=null){
            return QueryBuilders.rangeQuery(fieldName).gte(from);
        }
        if(to!=null){
            return QueryBuilders.rangeQuery(fieldName).lte(to);
        }
        return null;
    }

    /**
     * 多个值相等查询，也就是IN语句
     * 当值的个数退化到1个时，也就是等值查询
     * @param fieldName
     * @param vals
     * @return
     */
    public static QueryBuilder eqs(String fieldName,Object ... vals){
        if(vals==null || vals.length==0){
            return null;
        }
        Vector<Object> invals=new Vector<Object>();
        for(Object item : vals){
            if(item==null){
                continue;
            }
            if(item instanceof String){
                String iv=(String)item;
                if(iv==null || "".equals(iv)){
                    continue;
                }
            }
            invals.add(item);
        }
        if(invals==null || invals.size()==0){
            return null;
        }
        return QueryBuilders.termsQuery(fieldName, invals);
    }

    /**
     * 类似eqs,但是区别是，将会把val按照splitRegex指定的正则表达式进行拆分为多个，
     * 再进行条件构建
     * @param fieldName
     * @param val
     * @param splitRegex
     * @return
     */
    public static QueryBuilder eqsByStr(String fieldName,String val,String splitRegex){
        if(val==null || "".equals(val)){
            return null;
        }

        if(splitRegex==null || "".equals(splitRegex)){
            splitRegex="\\s+";
        }
        String[] arr=val.split(splitRegex);
        Vector<String> strs=new Vector<String>();
        for(String item : arr){
            if(item==null || "".equals(item)){
                continue;
            }
            strs.add(item);
        }
        return eqs(fieldName, strs.toArray());
    }

    /**
     * 多个值的模糊查询，也就是Like语句
     * 当值的个数退化到1个时，也就是SQL中的like语句
     * @param fieldName
     * @param vals
     * @return
     */
    public static QueryBuilder likes(String fieldName,String ... vals){
        if(vals==null || vals.length==0){
            return null;
        }
        BoolQueryBuilder queryBuilder=QueryBuilders.boolQuery();
        int count=0;
        for(String item : vals){
            if(item==null || "".equals(item)){
                continue;
            }
            queryBuilder.should(QueryBuilders.fuzzyQuery(fieldName, item));
            count++;
        }
        if(count==0){
            return null;
        }
        return queryBuilder;
    }

    /**
     * 构建一个符合查询对象
     * @return
     */
    public static BoolQueryBuilder qry(){
        return QueryBuilders.boolQuery();
    }

    /**
     * 动态检查，当builder不为null时，将会以must方式添加，作为组合查询条件
     * 否则不添加
     * @param boolBuilder
     * @param builder
     * @return
     */
    public static BoolQueryBuilder must(BoolQueryBuilder boolBuilder,QueryBuilder builder){
        if(builder!=null){
            boolBuilder.must(builder);
        }
        return boolBuilder;
    }
    public static BoolQueryBuilder mustNot(BoolQueryBuilder boolBuilder,QueryBuilder builder){
        if(builder!=null){
            boolBuilder.mustNot(builder);
        }
        return boolBuilder;
    }
    public static BoolQueryBuilder should(BoolQueryBuilder boolBuilder,QueryBuilder builder){
        if(builder!=null){
            boolBuilder.should(builder);
        }
        return boolBuilder;
    }
    public static BoolQueryBuilder filter(BoolQueryBuilder boolBuilder,QueryBuilder builder){
        if(builder!=null){
            boolBuilder.filter(builder);
        }
        return boolBuilder;
    }
}
