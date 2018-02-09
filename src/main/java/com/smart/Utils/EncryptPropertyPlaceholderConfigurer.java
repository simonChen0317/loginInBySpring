package com.smart.Utils;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

//PropertyPlaceholderConfigurer本身不支持密文版的属性文件，不过我们可以扩展该类，覆盖String convertProperty(String propertyName, String propertyValue)方法，对userName和password的属性值进行解密转换
public class EncryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {	
	private String[] encryptPropNames ={"userName","password"};
	
	@Override
	protected String convertProperty(String propertyName, String propertyValue) {		
		if(isEncryptProp(propertyName)){
			String decryptValue = DESUtils.getDecryptString(propertyValue);
			System.out.println(decryptValue);
			return decryptValue;
		}else{
			return propertyValue;
		}
	}
	
	/**
	 * 判断是否是加密的属性
	 * @param propertyName
	 * @return
	 */
	private boolean isEncryptProp(String propertyName){
		for(String encryptPropName:encryptPropNames){
			if(encryptPropName.equals(propertyName)){
				return true;
			}
		}
		return false;
	}
}
