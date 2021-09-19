package com.lhd.j2s.trans.aspect;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lhd.j2s.module.dict.service.DictService;
import com.lhd.j2s.trans.annotation.DictTrans;
import com.lhd.j2s.trans.annotation.DictTranslating;
import com.lhd.j2s.trans.field.DictTransRule;
import com.lhd.j2s.trans.util.ReflectUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 翻译字典切面
 * @author lhd
 */
@Slf4j
@Aspect
@Component
public class TransDictAspect {

    @Autowired
    private DictService dictService;

    @Pointcut("@annotation(com.lhd.j2s.trans.annotation.DictTranslating)")
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
        DictTranslating enumTranslating = method.getAnnotation(DictTranslating.class);
        if(enumTranslating == null || enumTranslating.value().length == 0){
            return result;
        }

        setEnumValue(result, enumTranslating);

        log.debug("字典翻译耗时：{}", (System.currentTimeMillis() - start));

        return result;
    }

    private void setEnumValue(Object result, DictTranslating dictTranslating) {

        List<DictTransRule> ruleList = new ArrayList<>();
        List<String> typeCodeList = new ArrayList<>();
        for (DictTrans dictTrans : dictTranslating.value()) {
            ruleList.add(new DictTransRule(dictTrans.typeCode(),
                    dictTrans.readFieldName(),
                    (dictTrans.writeFieldName() == null || dictTrans.writeFieldName().length() == 0)
                            ? dictTrans.readFieldName() + "Name"
                            : dictTrans.writeFieldName()));
            typeCodeList.add(dictTrans.typeCode());
        }


        Map<String, Map<Integer, String>> allValueMap = dictService.findDictMapByTypeCodeList(typeCodeList);
        if (result instanceof IPage) {
            for (Object entity : ((IPage<?>) result).getRecords()) {
                setEntityValue(entity, ruleList, allValueMap);
            }
        }
        else if (result instanceof List) {
            for (Object entity : (List<?>) result) {
                // 返回值为Map列表 List<Map<String, Object>>
                if (entity instanceof Map) {
                    setMapValue((Map<String, Object>) entity, ruleList, allValueMap);
                }
                // 返回值为实体列表 List<Entity>
                else {
                    setEntityValue(entity, ruleList, allValueMap);
                }
            }
        }
        // 返回值为单实体 Entity
        else {
            setEntityValue(result, ruleList, allValueMap);
        }
    }

    private void setEntityValue(Object entity, List<DictTransRule> ruleList, Map<String, Map<Integer, String>> allValueMap) {
        for (DictTransRule rule : ruleList) {
            Map<Integer, String> valueMap = allValueMap.get(rule.getTypeCode());
            Object readFieldValue = ReflectUtils.getFieldValue(entity, rule.getKeyFieldName());
            if (valueMap != null && readFieldValue != null) {
                ReflectUtils.setFieldValue(entity, rule.getValueFieldNames()[0], valueMap.get(Integer.parseInt(String.valueOf(readFieldValue))));
            }
        }
    }

    private void setMapValue(Map<String, Object> map, List<DictTransRule> ruleList, Map<String, Map<Integer, String>> allValueMap) {
        for (DictTransRule rule : ruleList) {
            Map<Integer, String> valueMap = allValueMap.get(rule.getTypeCode());
            Object readFieldValue = map.get(rule.getKeyFieldName());
            if (valueMap != null && readFieldValue != null) {
                map.put(rule.getValueFieldNames()[0], valueMap.get(Integer.parseInt(String.valueOf(readFieldValue))));
            }
        }
    }
}
