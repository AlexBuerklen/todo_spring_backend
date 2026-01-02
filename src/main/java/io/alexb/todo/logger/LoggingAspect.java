package io.alexb.todo.logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("@within(org.springframework.web.bind.annotation.RestController) || @within(org.springframework.stereotype.Service)")
    public Object logMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getDeclaringTypeName() + "." + signature.getName();
        Object[] args = joinPoint.getArgs();

        logger.info("➡️  Entering method: {} with arguments: {}", methodName, Arrays.toString(args));

        long startTime = System.currentTimeMillis();

        try {
            Object result = joinPoint.proceed();
            long duration = System.currentTimeMillis() - startTime;
            logger.info("⬅️  Exiting method: {} with result: {} ({}ms)", methodName, result, duration);
            return result;
        } catch (Throwable ex) {
            logger.error("❌ Exception in method: {} with cause: {}", methodName, ex.getMessage(), ex);
            throw ex;
        }
    }
}
