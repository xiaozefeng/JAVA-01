package io.github.mickey.aspect;

import io.github.mickey.context.Context;
import io.github.mickey.context.ContextHolder;
import io.github.mickey.filter.post.PostFilter;
import io.github.mickey.filter.pre.PreFilter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author mickey
 * @date 2/11/21 17:33
 */
@Aspect
@Component
public class FilterAspect {

    @Autowired
    private List<PreFilter> preFilters;

    @Autowired
    private List<PostFilter> postFilters;


    @Pointcut("execution(* io.github.mickey.router.HTTPEndpointRouter.route(..))")
    public void pointcut() {

    }

    @Around(value = "pointcut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Context c = ContextHolder.get();
        try {
            if (preFilters != null) {
                for (PreFilter f : preFilters)
                    f.preFilter(c.getReq(), c.getChc());
            }

            return pjp.proceed();
        } finally {
            if (postFilters != null) {
                for (PostFilter f : postFilters) {
                    f.postFilter(c.getResp(), c.getChc());
                }
            }
        }
    }
}
