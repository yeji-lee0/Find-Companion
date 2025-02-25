package yeji.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import yeji.DTO.MemberDto;
import yeji.service.MemberService;



@Controller
@RequestMapping("/member")
public class MemberController {

	@Autowired
	MemberService memberService;


	/**
	 * 회원정보 수정 페이지를 반환합니다.
	 * @return 회원정보 수정 페이지로 이동
	 */
	@GetMapping("/update")
	public String updateProfile() {
		return "member/update";
	}

	/**
	 * 회원정보 수정 처리 후 수정된 회원 정보를 세션에 업데이트합니다.
	 * @param memDto 수정된 회원 정보 DTO
	 * @param session HTTP 세션
	 * @return 수정된 회원 정보 페이지로 리다이렉트
	 */
	@PostMapping("/updateOk")
	public String updateProfile(@ModelAttribute MemberDto memDto, HttpSession session) {
		// 회원정보 수정 요청 처리
		memberService.updateMember(memDto);

		// 수정된 회원정보를 세션에 업데이트
		session.setAttribute("loginUser", memDto);

		return "redirect:update";
	}

	/**
	 * 회원의 프로필 정보를 조회하여 반환합니다.<br>
	 * 
	 * 이 메서드는 로그인된 회원의 프로필 정보를 조회하거나, 
	 * 특정 회원의 프로필을 조회하여 해당 회원의 기본 정보, 
	 * 게시글 목록, 댓글 목록을 뷰에 전달합니다. 또한 게시글과 댓글에 대한 페이징 처리를 포함합니다.
	 * 
	 * @param session HTTP 세션 (로그인된 회원 정보)
	 * @param model 뷰에 전달할 데이터 모델
	 * @param id 프로필을 조회할 회원 ID (기본값: 세션에 저장된 로그인된 회원의 ID)
	 * @param currentPostPage 현재 게시글 페이지 (기본값: 1)
	 * @param currentCommPage 현재 댓글 페이지 (기본값: 1)
	 * @return 회원 프로필 페이지로 이동
	 */
	@GetMapping("/profile")
	public String viewProfile(HttpSession session, 
			Model model,
			@RequestParam(defaultValue = "") String id,
			@RequestParam(defaultValue = "1", name = "cpp") int currentPostPage,
			@RequestParam(defaultValue = "1", name = "ccp") int currentCommPage) {

		// 프로필 조회 대상 ID 결정
		if (id.equals("")) {
			MemberDto loginUser = (MemberDto) session.getAttribute("loginUser");	// 세션에서 사용자 정보 가져오기
			id=loginUser.getId();
		}

		int pageSize = 5; // 한 페이지에 나오는 글과 댓글 개수
		int pageBlockSize = 5; // 네비게이션에 표시할 페이지 수

		// 회원 정보 가져오기
		MemberDto memberDto 
		= memberService.getMember(id, currentPostPage,currentCommPage, pageSize);
		model.addAttribute("user", memberDto);

		// 게시글 페이징 처리
		int postTotalCount = memberService.getMemberPostCount(id);
		int postTotalPage = (int) Math.ceil((double) postTotalCount / pageSize);
		int postStartPage = ((currentPostPage - 1) / pageBlockSize) * pageBlockSize + 1;
    int postEndPage = Math.min(postStartPage + pageBlockSize - 1, postTotalPage);
    
		// 댓글 페이징 처리
		int commTotalCount = memberService.getMemberCommCount(id);
		int commTotalPage = (int) Math.ceil((double) commTotalCount / pageSize);
		int commStartPage = ((currentCommPage - 1) / pageBlockSize) * pageBlockSize + 1;
    int commEndPage = Math.min(commStartPage + pageBlockSize - 1, commTotalPage);
    
		// 모델에 페이징 정보 추가
		model.addAttribute("currentPostPage", currentPostPage);
		model.addAttribute("postTotalPage", postTotalPage);
		model.addAttribute("postStartPage", postStartPage);
    model.addAttribute("postEndPage", postEndPage);
		
		model.addAttribute("currentCommPage", currentCommPage);
		model.addAttribute("commTotalPage", commTotalPage);
		model.addAttribute("commStartPage", commStartPage);
    model.addAttribute("commEndPage", commEndPage);

		// 프로필 페이지로 이동
		return "member/profile";
	}

	/**
	 * 회원의 취향을 수정하는 요청을 처리합니다.
	 * 
	 * @param session HTTP 세션, 로그인한 회원 정보에 접근하기 위해 사용됩니다.
	 * @param model Spring 모델 객체, 요청 처리 후 결과를 뷰에 전달하는 데 사용됩니다.
	 * @param selectedTastes 회원이 새로 선택한 취향들의 ID 리스트
	 * 
	 * <p>이 메서드는 로그인한 회원의 취향을 수정하는 요청을 처리합니다. 
	 * 회원의 현재 취향을 `selectedTastes` 리스트에 있는 취향으로 업데이트합니다.</p>
	 * <p>처리 후, 회원의 프로필 페이지로 리다이렉트합니다.</p>
	 */
	@PostMapping("/tasteOk")
	public String updateMemberTaste(HttpSession session, 
			Model model,
			@RequestParam(required = false) List<Integer> selectedTastes) {

		// 로그인한 회원 정보 가져오기
		MemberDto loginUser = (MemberDto) session.getAttribute("loginUser");
		// 취향 업데이트 서비스 호출
		memberService.updateMemberTaste(loginUser.getId(), selectedTastes);
		// 프로필 페이지로 리다이렉트		
		return "redirect:profile";

	}


	/**
	 * 로그아웃 처리를 수행하고 홈 페이지로 리다이렉트합니다.
	 * @param session HTTP 세션
	 * @return 홈 페이지로 리다이렉트
	 */
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate(); // 세션 무효화
		return "redirect:/"; // 로그아웃 후 이동할 페이지
	}

	/**
	 * 로그인 페이지를 반환합니다.
	 * @return 로그인 페이지로 이동
	 */
	@GetMapping("/login")
	public String login() {
		return "member/login";
	}

	/**
	 * 사용자의 로그인 정보를 확인하고 로그인 처리 후 홈페이지로 리다이렉트합니다.
	 * @param id 사용자 아이디
	 * @param pw 사용자 비밀번호
	 * @param model 뷰에 전달할 데이터 모델
	 * @param session HTTP 세션
	 * @return 로그인 성공 시 홈 페이지로 리다이렉트, 실패 시 로그인 페이지로 재요청
	 */
	@PostMapping("/login")
	public String login(@RequestParam String id, 
			@RequestParam String pw, 
			Model model,
			HttpSession session) {

		MemberDto dto = memberService.login(id, pw);

		if (dto != null) { // 로그인 성공
			session.setAttribute("loginUser", dto); 
			System.out.println();


			//session.setMaxInactiveInterval(30 * 60); // 세션 유효 기간을 30분으로 설정
			return "redirect:/"; // 로그인 후이동할 페이지 
		}else {
			model.addAttribute("loginFailMsg", "아이디와 비밀번호를 다시 입력해주세요.");
			// 로그인 실패해도 입력창에 아이디가남아있음
			model.addAttribute("id", id); // 입력된 아이디를 모델에 추가

			return "member/login";
		}


	}


	/**
	 * 회원가입 페이지를 반환합니다.
	 * @return 회원가입 페이지로 이동
	 */
	@GetMapping("/join")
	public String join() {
		return "member/join";
	}

	/**
	 * 아이디 중복 여부를 확인하여 결과를 반환합니다.
	 * @param id 확인할 아이디
	 * @return 중복 여부를 포함한 응답(Map)
	 */
	@GetMapping("/checkId")
	@ResponseBody
	public Map<String, Object> checkId(@RequestParam String id) {
		boolean result = memberService.isIdDuplicate(id);

		// 중복 여부와 메시지를 포함한 응답 반환
		Map<String, Object> response = new HashMap<>();
		response.put("isIdDuplicate", result);

		return response;
	}

	/**
	 * 회원가입 요청을 처리하고 로그인 페이지로 리다이렉트합니다.
	 * @param memDto 회원가입 폼에서 입력받은 회원 정보 DTO
	 * @param redirectAttributes 리다이렉트 후 플래시 메시지를 추가
	 * @return 회원가입 완료 후 로그인 페이지로 리다이렉트
	 */
	@PostMapping("/joinOk")
	public String join(@ModelAttribute MemberDto memDto,
			RedirectAttributes redirectAttributes) {
		memberService.join(memDto);

		redirectAttributes.addFlashAttribute("message", "회원가입이 완료되었습니다."); 
		return "redirect:/member/login";
	}



}
