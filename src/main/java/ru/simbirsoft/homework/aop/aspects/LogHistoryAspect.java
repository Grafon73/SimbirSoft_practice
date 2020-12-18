package ru.simbirsoft.homework.aop.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@ConditionalOnExpression("${aspect.enabled} == true && ${aspect.loghistory} == true")
public class LogHistoryAspect {
    @AfterReturning(pointcut = "@annotation(ru.simbirsoft.homework.aop.annotations.LogHistory)")
    public void after(JoinPoint joinPoint) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("\n Пользователь " + auth.getName() +
                "\n Вызвал метод: " + joinPoint.getSignature().getName() +
                "\n В контроллере: " + joinPoint.getTarget().getClass().getSimpleName());
    }
}
