package com.adms.common.service.aop;

import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import com.adms.common.domain.BaseAuditDomain;

@Aspect
public class TimeStampIntercept {

	@Pointcut("execution(* com.adms.bo.*.*.add*(..))")
	public void addPointCut() {
		
	}

	@Pointcut("execution(* com.adms.bo.*.*.update*(..))")
	public void updatePointCut() {
		
	}

	@Before("execution(* com.adms.bo.*.*.add*(..)) "
			+ " || execution(* com.adms.*.bo.*.*.add*(..)) "
			+ " || execution(* com.adms.*.service.*Service.add*(..)) "
			+ " || execution(* com.adms.**.service.*Service.add*(..)) "
			+ " || execution(* com.adms.**.service.**.*Service.add*(..))")
	//com.adms.**.service.*Service.add*()
	public void beforeInsert(JoinPoint jp) {
		Object[] objects = jp.getArgs();
		int objNum = -1;
		int userNum = -1;
		boolean isAuditDomain = false;
		if(objects != null && objects.length > 0) {
			for(int i = 0; i < objects.length; i++) {
				Object obj = objects[i];
				if(obj instanceof BaseAuditDomain) {
					((BaseAuditDomain) obj).setCreateDate(new Date());
					objNum = i;
					isAuditDomain = true;
				}
				if(obj instanceof String) {
					userNum = i;
				}
			}
			if(isAuditDomain) ((BaseAuditDomain) objects[objNum]).setCreateBy(String.valueOf(objects[userNum]));
		}
	}
	
	@Before("execution(* com.adms.bo.*.*.update*(..))"
			+ " || execution(* com.adms.*.bo.*.*.update*(..)) "
			+ " || execution(* com.adms.*.service.*Service.update*(..)) "
			+ " || execution(* com.adms.**.service.**.*Service.update*(..))")
	public void beforeUpdate(JoinPoint jp) {
		Object[] objects = jp.getArgs();

		int objNum = -1;
		int userNum = -1;
		boolean isAuditDomain = false;
		if(objects != null && objects.length > 0) {
			for(int i = 0; i < objects.length; i++) {
				Object obj = objects[i];
				if(obj instanceof BaseAuditDomain) {
					((BaseAuditDomain) obj).setUpdateDate(new Date());
					objNum = i;
					isAuditDomain = true;
				}
				if(obj instanceof String) {
					userNum = i;
				}
			}
			if(isAuditDomain) ((BaseAuditDomain) objects[objNum]).setUpdateBy(String.valueOf(objects[userNum]));
		}
	}
	
}
