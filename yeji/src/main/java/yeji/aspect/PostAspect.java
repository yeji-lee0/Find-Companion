package yeji.aspect;

import java.nio.file.AccessDeniedException;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import yeji.DAO.PostDao;
import yeji.DTO.MemberDto;
import yeji.DTO.PostDto;

@Aspect
@Component
public class PostAspect {

    @Autowired
    private PostDao postDao;

    // 수정, 삭제 메서드에 대한 포인트컷 정의
    @Pointcut("execution(* yeji.controller.PostController.editPost*(..)) "
            + "|| execution(* yeji.controller.PostController.deletePost(..))")
    public void postModification() {}

    // 글 수정/삭제 요청 전에 본인이 작성한 글인지 확인
    @Before("postModification()")
    public void checkPostWriter(JoinPoint joinPoint) throws AccessDeniedException {

        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
        MemberDto loginUser = (MemberDto) session.getAttribute("loginUser"); // 세션에서 로그인한 회원 정보 가져오기

        if (loginUser == null) {
            throw new AccessDeniedException("로그인이 필요합니다.");
        }

        Object[] args = joinPoint.getArgs();  // 메서드의 매개변수들
        int post_id = (int) args[0];  // 메서드의 첫 번째 매개변수가 postId (게시글 ID)
        PostDto post = postDao.getPostByPostId(post_id); // 해당 글 정보 가져오기

        // 글이 존재하지 않으면 예외 처리
        if (post == null) {
            throw new AccessDeniedException("존재하지 않는 글입니다.");
        }

        // 게시글 수정 요청 처리
        if (joinPoint.getSignature().getName().startsWith("editPost")) {
            if (!post.getMemberId().equals(loginUser.getId())) {
                throw new AccessDeniedException("작성자만 수정할 수 있습니다.");
            }
        }

        // 게시글 삭제 요청 처리
        if (joinPoint.getSignature().getName().startsWith("deletePost")) {
            if (!post.getMemberId().equals(loginUser.getId())) {
                throw new AccessDeniedException("작성자만 삭제할 수 있습니다.");
            }
        }
    }
}
