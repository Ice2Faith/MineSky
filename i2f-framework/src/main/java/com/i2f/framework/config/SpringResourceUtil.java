package com.i2f.framework.config;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.util.HashSet;
import java.util.Set;

public class SpringResourceUtil {
    public static Resource[] resourceResolve(String location) throws Exception {
        PathMatchingResourcePatternResolver resolver=new PathMatchingResourcePatternResolver();
        Set<Resource> resourceSet=new HashSet<>();
        if(location==null || "".equals(location)){
            return new Resource[0];
        }
        if(location.indexOf(",")>=0){
            String[] locations=location.split(",");
            for(String loc : locations){
                Resource[] res=resolver.getResources(loc);
                for(Resource item : res){
                    resourceSet.add(item);
                }
            }
        }else{
            Resource[] res=resolver.getResources(location);
            for(Resource item : res){
                resourceSet.add(item);
            }
        }

        int size=resourceSet.size();
        Resource[] ret=new Resource[size];
        int i=0;
        for(Resource item : resourceSet){
            ret[i++]=item;
        }
        return ret;
    }
}
