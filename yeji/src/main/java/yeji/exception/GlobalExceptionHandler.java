package yeji.exception;

import java.nio.file.AccessDeniedException;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	
	// 회원 로그인하지 않고 게시판, 회원정보 페이지에 접근할 때 생기는 예외
	@ExceptionHandler(MemberSecurityException.class)
    public String handleMemberSecurityException(MemberSecurityException e, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
		//System.out.println("예외핸들러: 로그인 안 된 상태에서 접근 시도");  
		return "redirect:/member/login"; // 회원 로그인 페이지로 리다이렉트
    }
	
	// 작성자가 아닌 사람이 글을 수정/삭제하려 할 때 생기는 예외
	// RedirectAttributes를 사용하면 Flash Scope에 메시지가 저장된다 
	// Flash Scope는 리다이렉트된 요청에서만 유효하고, 요청이 끝난 후 메시지가 삭제된다.
	@ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDenied(AccessDeniedException e, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        return "redirect:/post/list"; // 게시판 페이지로 돌아가기
    }
}
