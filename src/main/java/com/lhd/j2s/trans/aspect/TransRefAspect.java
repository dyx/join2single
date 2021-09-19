package com.lhd.j2s.trans.aspect;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lhd.j2s.trans.annotation.RefTrans;
import com.lhd.j2s.trans.annotation.RefTranslating;
import com.lhd.j2s.trans.consts.RefTransType;
import com.lhd.j2s.trans.field.RefTransRule;
import com.lhd.j2s.trans.handler.TransValueMapHandlerFactory;
import com.lhd.j2s.trans.util.ReflectUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.*;

/**
 * 翻译参照切面
 * @author lhd
 */
@Slf4j
@Aspect
@Component
public class TransRefAspect {

    @Pointcut("@annotation(com.lhd.j2s.trans.annotation.RefTranslating)")
    public void pointcut() { }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {

    	long start = System.currentTimeMillis();
        Object result = point.proceed();
        if (result == null) {
            return null;
        }

        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        RefTranslating refTranslating = method.getAnnotation(RefTranslating.class);
        if(refTranslating == null || refTranslating.value().length == 0){
            return result;
        }

        // 获取规则列表
        List<RefTransRule> ruleList = new ArrayList<>();
        for (RefTrans refTrans : refTranslating.value()) {
            ruleList.add(new RefTransRule(refTrans.type(), refTrans.readFieldName(), refTrans.writeFieldNames()));
        }

        // 转换为实体列表
        List<?> entityList = result2EntityList(result);

        // 获取读取字段值
        Map<RefTransType, List<Object>> readFieldValueMap = getReadFieldValueMap(entityList, ruleList);
        if (readFieldValueMap.size() == 0) {
            return result;
        }

        // 获取翻译值
        Map<RefTransType, Map<Object, Map<String, Object>>> transValueMap = TransValueMapHandlerFactory.getTransValueMap(ruleList, readFieldValueMap);
        if (transValueMap.size() == 0) {
            return result;
        }

        // 设置翻译值
        for (Object entity : entityList) {
            setEntityValue(entity, ruleList, transValueMap);
        }

        log.debug("参照翻译耗时：{}", (System.currentTimeMillis() - start));

        return result;
    }

    private List<?> result2EntityList(Object result) {
        if (result instanceof IPage) {
            return ((IPage<?>) result).getRecords();
        }
        else if (result instanceof List) {
            return (List<?>) result;
        }
        else {
            return Collections.singletonList(result);
        }
    }

    private void setEntityValue(Object entity, List<RefTransRule> ruleList, Map<RefTransType, Map<Object, Map<String, Object>>> transValueMap) {

        for (RefTransRule refTransRule : ruleList) {
            Object readFieldValue;
            if (entity instanceof Map) {
                readFieldValue = ((Map<?, ?>) entity).get(refTransRule.getKeyFieldName());
            }
            else {
                readFieldValue = ReflectUtils.getFieldValue(entity, refTransRule.getKeyFieldName());
            }

            Map<Object, Map<String, Object>> allValueMap = transValueMap.get(refTransRule.getRefTransType());
            if (allValueMap != null && readFieldValue != null) {
                Map<String, Object> valueMap = allValueMap.get(readFieldValue);
                if (valueMap != null) {
                    for (String valueFieldName : refTransRule.getValueFieldNames()) {
                        ReflectUtils.setFieldValue(entity, valueFieldName, valueMap.get(valueFieldName));
                    }
                }
            }
        }
    }

    private Map<RefTransType, List<Object>> getReadFieldValueMap(List<?> entityList, List<RefTransRule> ruleList) {

        Map<RefTransType, List<Object>> readFieldValueMap = new HashMap<>(16);
        for (RefTransRule refTransRule : ruleList) {
            List<Object> readFieldValueList = readFieldValueMap.get(refTransRule.getRefTransType());
            for (Object entity : entityList) {
                Object readFieldValue = ReflectUtils.getFieldValue(entity, refTransRule.getKeyFieldName());
                if (readFieldValue != null) {
                    if (readFieldValueList == null) {
                        readFieldValueList = new ArrayList<>();
                    }
                    readFieldValueList.add(readFieldValue);
                }
            }
            readFieldValueMap.put(refTransRule.getRefTransType(), readFieldValueList);
        }

        return readFieldValueMap;
    }
}
