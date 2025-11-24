package com.qy.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import java.io.IOException;
import java.util.ArrayList;

/**
 * json辅助类库
 */
public class JsonUtils {
	/**
	 * json字符串 转 List泛型
	 * @param strJson
	 * @param elementClasses
	 * @param <T>
	 * @return
	 * @throws IOException
	 */
	public static <T> T formatList(String strJson, Class<?>... elementClasses) throws IOException {
		ObjectMapper mapper = jsonObjectMapper();
		JavaType javaType = getCollectionType(mapper, ArrayList.class, elementClasses);
		return mapper.readValue(strJson, javaType);
	}

	/**
	 * 获取json对象mapper
	 *
	 * @return
	 */
	private static ObjectMapper jsonObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
		return objectMapper;
	}
	/**
	 * 获取泛型的Collection Type
	 * @param collectionClass 泛型的Collection
	 * @param elementClasses 元素类
	 * @return JavaType Java类型
	 * @since 1.0
	 */
	public static JavaType getCollectionType(ObjectMapper mapper, Class<?> collectionClass, Class<?>... elementClasses) {
		return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
	}

	/**
	 * 将对象的大写转换为下划线加小写，例如：userName-->user_name
	 *
	 * @param object
	 * @return
	 * @throws JsonProcessingException
	 */
	public static String toUnderlineJSONString(Object object) {
		String reqJson = "";
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
			mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
			reqJson = mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return reqJson;
	}

	/**
	 * 将下划线转换为驼峰的形式，例如：user_name-->userName
	 *
	 * @param json
	 * @param clazz
	 * @return
	 * @throws IOException
	 */
	public static <T> T toSnakeObject(String json, Class<T> clazz) throws IOException{
		ObjectMapper mapper = new ObjectMapper();
		mapper
				.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
				.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		T reqJson =  mapper.readValue(json, clazz);

		return reqJson;
	}
}
