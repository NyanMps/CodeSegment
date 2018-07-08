package com.bfchengnuo.common.spring.exetend.converter.json;

import java.io.IOException;
import java.lang.reflect.Type;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 定义一个通用的消息转换器，用来做 JSONP 支持(扩展自 Jackson) 凡是检测到请求带有 callbackName 参数就进行 JSONP
 * 转换，如果没有参数就做普通的 JSON 转换 这个参数通过配置文件知道参数名
 * 
 * SpringMVC 会先尝试自定义的转换器进行转换，所以所有方法都是可以的，并且是透明的 只要传入了规定的 callback 参数就会进行 JSONP
 * 转换
 * 
 * @author Kerronex
 *
 */
public class CallbackMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {

	// 做jsonp的支持的标识，在请求参数中加该参数
	private String callbackName;


	@Override
	protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		// 从threadLocal中获取当前的Request对象
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();
		String callbackParam = request.getParameter(callbackName);
		if (StringUtils.isEmpty(callbackParam)) {
			// 没有找到callback参数，直接返回json数据
			super.writeInternal(object, type, outputMessage);
//			JsonEncoding encoding = getJsonEncoding(outputMessage.getHeaders().getContentType());
//			String result = super.getObjectMapper().writeValueAsString(object);
//			IOUtils.write(result, outputMessage.getBody(), encoding.getJavaName());
		} else {
			JsonEncoding encoding = getJsonEncoding(outputMessage.getHeaders().getContentType());
			try {
				String result = callbackParam + "(" + super.getObjectMapper().writeValueAsString(object) + ");";
				IOUtils.write(result, outputMessage.getBody(), encoding.getJavaName());
			} catch (JsonProcessingException ex) {
				throw new HttpMessageNotWritableException("Could not write JSON: " + ex.getMessage(), ex);
			}
		}
	}

	public String getCallbackName() {
		return callbackName;
	}

	public void setCallbackName(String callbackName) {
		this.callbackName = callbackName;
	}

}
