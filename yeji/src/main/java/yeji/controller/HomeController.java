package yeji.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import yeji.DTO.PostDto;
import yeji.DTO.TasteDto;
import yeji.service.PostService;
import yeji.service.TasteService;


@Controller
@RequestMapping("/")  // 루트 경로로 매핑
public class HomeController {

	@Autowired
	PostService postService;
	@Autowired
	TasteService tasteService;

	/**
	 * 홈 페이지를 처리합니다.
	 * 루트 경로("/")로 GET 요청이 들어오면 홈 페이지를 반환합니다.
	 * 
	 * @param model 뷰에 데이터를 전달하기 위한 Model 객체
	 * @return "/home" 뷰 이름
	 */
	@GetMapping("")// 루트 경로에 대한 GET 요청 처리
	public String Home(Model model) {

		// 카테고리 조회
		List<TasteDto> categorys = tasteService.getCategory();
		model.addAttribute("categorys", categorys);

		List<TasteDto> tastes = tasteService.getCategoryFieldTasteAll();
		model.addAttribute("groupedtaste", tasteService.groupTastes(tastes));

		return "/home";  // 홈 페이지로 이동
	}
}
