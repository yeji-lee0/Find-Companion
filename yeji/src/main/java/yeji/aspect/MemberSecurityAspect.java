package yeji.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import yeji.exception.MemberSecurityException;

import javax.servlet.http.HttpSession;

@Aspect
@Component
public class MemberSecurityAspect {

	// 해당 컨트롤러의 메서드에 적용. 
	// 로그인하지 않으면 게시판이나 회원의 페이지는 못들어감
    @Before("execution(* yeji.controller.PostController.*(..))"
       + "|| execution(* yeji.controller.MemberController.*Profile*(..))")
    public void checkLogin(JoinPoint joinPoint) {
        // HttpSession을 요청에서 가져오기
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpSession session = attributes.getRequest().getSession(false);

        //System.out.println("aspect: 로그인 안 된 상태에서 접근 시도");
        if (session == null || session.getAttribute("loginUser") == null) {
            throw new MemberSecurityException("로그인 후 이용 가능합니다.");
        }
        
    }
}
