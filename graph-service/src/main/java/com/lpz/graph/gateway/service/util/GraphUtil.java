package com.lpz.graph.gateway.service.util;

import com.google.common.collect.Maps;
import com.vesoft.nebula.NullType;
import com.vesoft.nebula.Value;
import com.vesoft.nebula.client.graph.data.Node;
import com.vesoft.nebula.client.graph.data.PathWrapper;
import com.vesoft.nebula.client.graph.data.Relationship;
import com.vesoft.nebula.client.graph.data.ValueWrapper;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: lpz
 * @Date: 2022-03-28 16:16
 */
@Slf4j
public class GraphUtil {


    /**
     * @param value
     * @return
     */
    public static String descType(Value value) {
        switch (value.getSetField()) {
            case Value.NVAL:
                return "NULL";
            case Value.BVAL:
                return "BOOLEAN";
            case Value.IVAL:
                return "INT";
            case Value.FVAL:
                return "FLOAT";
            case Value.SVAL:
                return "STRING";
            case Value.DVAL:
                return "DATE";
            case Value.TVAL:
                return "TIME";
            case Value.DTVAL:
                return "DATETIME";
            //
            case Value.VVAL:
                return "VERTEX";
            case Value.EVAL:
                return "EDGE";
            case Value.PVAL:
                return "PATH";
            case Value.LVAL:
                return "LIST";
            case Value.MVAL:
                return "MAP";
            case Value.UVAL:
                return "SET";
            case Value.GVAL:
                return "DATASET";
            default:
                // TODO: NullType有点问题
                if (value.getFieldValue() instanceof NullType) {
                    return NullType.findByValue(value.getSetField()).name();
                }
                if (NullType.findByValue(value.getSetField()) != null) {
                    return NullType.findByValue(value.getSetField()).name();
                }
                throw new IllegalArgumentException("Unknown field id " + value.getSetField());
        }
    }


    public static Object getID(ValueWrapper idValue) throws UnsupportedEncodingException {
        if (idValue.isString()) {
            return idValue.asString();
        } else if (idValue.isLong()) {
            return idValue.asLong();
        }
        log.error("Vertex vid node setField is illegal: {}", idValue.getValue().getSetField());
        return idValue.getValue().getFieldValue();
    }

    /**
     * @param valueWrapper
     * @return
     */
    public static Object getValue(ValueWrapper valueWrapper) throws UnsupportedEncodingException {
        Value value = valueWrapper.getValue();
        int setField = value.getSetField();
        switch (setField) {
            case Value.VVAL:
            case Value.EVAL:
            case Value.PVAL:
            case Value.LVAL:
            case Value.MVAL:
            case Value.UVAL:
                // NullType
            case Value.NVAL:
                return valueWrapper.toString();
            case Value.BVAL:
                return value.isBVal();
            case Value.IVAL:
                return value.getIVal();
            case Value.FVAL:
                return value.getFVal();
            case Value.SVAL:
//                return new String(value.getSVal(), "utf-8");
                return valueWrapper.asString();
            case Value.DVAL:
                return value.getDVal();
            case Value.TVAL:
                return value.getTVal();
            case Value.DTVAL:
                return value.getDtVal();
            case Value.GVAL:
                return value.getGVal();

            default:
                log.error("Cannot read data with unknown field: {}", setField);
                return "";
        }
    }

    /**
     *
     * @param valWarp
     * @param dataMap
     * @return
     * @throws UnsupportedEncodingException
     */
    public static Map<String, Object> getPathInfo(ValueWrapper valWarp, Map<String, Object> dataMap) throws UnsupportedEncodingException {
        PathWrapper pathWrapper = valWarp.asPath();
        List<Relationship> relationships = pathWrapper.getRelationships();
        List<Map<String, Object>> relshipsTemp = new ArrayList<>();
        for (Relationship relationship : relationships) {
            Map<String, Object> relation = Maps.newHashMap();
            relation.put("srcID", GraphUtil.getID(relationship.srcId()));
            relation.put("dstID", GraphUtil.getID(relationship.dstId()));
            relation.put("edgeName", relationship.edgeName());
            relation.put("rank", relationship.ranking());
            relshipsTemp.add(relation);
        }
        dataMap.put("relationships", relshipsTemp);
        return dataMap;
    }

    /**
     *
     * @param valWarp
     * @param dataMap
     * @return
     * @throws UnsupportedEncodingException
     */
    public static Map<String, Object> getEdgeInfo(ValueWrapper valWarp, Map<String, Object> dataMap) throws UnsupportedEncodingException {
        Relationship relationship = valWarp.asRelationship();
        ValueWrapper srcID = relationship.srcId();
        dataMap.put("srcID", GraphUtil.getID(srcID));
        ValueWrapper dstID = relationship.dstId();
        dataMap.put("dstID", GraphUtil.getID(dstID));
        String edgeName = relationship.edgeName();
        dataMap.put("edgeName", edgeName);
        long rank = relationship.ranking();
        dataMap.put("rank", rank);
        Map<String, Object> properties = Maps.newHashMap();
        HashMap<String, ValueWrapper> props = relationship.properties();
        for (Map.Entry<String, ValueWrapper> entry : props.entrySet()) {
            Object value = GraphUtil.getValue(entry.getValue());
            properties.put(entry.getKey(), value);
        }
        dataMap.put("properties", properties);
        return dataMap;
    }

    /**
     * @param valueWrapper
     * @param dataMap
     * @return
     */
    public static Map<String, Object> getVertexInfo(ValueWrapper valueWrapper, Map<String, Object> dataMap) throws UnsupportedEncodingException {
        Node node = valueWrapper.asNode();
        ValueWrapper id = node.getId();
        dataMap.put("vid", GraphUtil.getID(id));
        //
        ArrayList<String> tagsList = new ArrayList<>();
        List<String> tagNames = node.tagNames();
        HashMap<String, Map<String, Object>> propertiesMap = new HashMap<>();
        for (String tagName : tagNames) {
            tagsList.add(tagName);
            HashMap<String, Object> propsTemp = new HashMap<>();
            HashMap<String, ValueWrapper> props = node.properties(tagName);

            for (Map.Entry<String, ValueWrapper> wrapperEntry : props.entrySet()) {
                Object value = GraphUtil.getValue(wrapperEntry.getValue());
                propsTemp.put(wrapperEntry.getKey(), value);
            }
            propertiesMap.put(tagName, propsTemp);
        }
        dataMap.put("tags", tagsList);
        dataMap.put("properties", propertiesMap);
        return dataMap;
    }

}
