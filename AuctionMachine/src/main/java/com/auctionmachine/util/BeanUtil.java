package com.auctionmachine.util;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class BeanUtil {

	Logger logger = LoggerFactory.getLogger(super.getClass());
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    private static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());  // JavaTimeModuleを登録
        return mapper;
    }

    public static <T> T deepCopy(Class<T> clazz, T object) {
        try {
            String json = objectMapper.writeValueAsString(object);
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Deep copy failed", e);
        }
    }

	public static boolean equal(Object target1, Object target2) {
		ObjectNode target1Node = objectMapper.valueToTree(target1);
		ObjectNode target2Node = objectMapper.valueToTree(target2);
		return target1Node.equals(target2Node);
	}
	
	public static String describe(Object object) {
		try {
            return BeanUtils.describe(object).toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
	}
}