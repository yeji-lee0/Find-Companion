package yeji.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import yeji.DTO.CommDto;
import yeji.DTO.PostDto;
import yeji.DTO.TasteDto;
import yeji.service.PostService;
import yeji.service.TasteService;

/**
 * 게시글 관련 요청을 처리하는 컨트롤러입니다.
 * 게시글 목록 조회, 상세 조회, 글 작성, 수정, 삭제 등의 기능을 제공합니다.
 */
@Controller
@RequestMapping("/post")
public class PostController {

	@Autowired
	PostService postService;
	@Autowired
	TasteService tasteService;

	/**
   * 게시글 목록을 조회하여 반환합니다.
   *
   * @param model 모델에 게시글 목록 및 관련 데이터를 추가
   * @param searchOption 검색 옵션 (all, title, memberId, content)
   * @param searchQuery 검색어
   * @param categoryId 카테고리 ID
   * @param currentPage 현재 페이지 번호
   * @return 게시글 목록을 출력할 뷰 페이지
   */
	@GetMapping("/list")
	public String viewPostList(
			Model model,
			@RequestParam(defaultValue = "all", name = "so") String searchOption,
			@RequestParam(defaultValue = "", name = "q") String searchQuery,
			@RequestParam(defaultValue = "0", name = "cid") Integer categoryId,
			@RequestParam(defaultValue = "1", name = "cp") int currentPage
			) {
		int pageSize = 10; // 한 페이지에 나오는 글 개수
		int pageBlockSize = 5; // 네비게이션에 표시할 페이지 수

		// 게시글 총 개수 및 총 페이지 계산
		int TotalCount = 
				postService.getSearchPostCount(searchOption, searchQuery, categoryId);
		int TotalPage = (int) Math.ceil((double) TotalCount / pageSize);

		// 현재 페이지에 해당하는 게시글 조회
		List<PostDto> posts =
				postService.searchPost(
						searchOption, searchQuery, categoryId, currentPage, pageSize);

		// 카테고리 조회
		List<TasteDto> categorys = tasteService.getCategory();

		// 페이지 네비게이션 범위 계산
		int startPage = ((currentPage - 1) / pageBlockSize) * pageBlockSize + 1;
		int endPage = Math.min(startPage + pageBlockSize - 1, TotalPage);

		// 모델에 데이터 추가
		model.addAttribute("posts", posts);
		model.addAttribute("categorys", categorys);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("TotalPage", TotalPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);

		return "post/list";
	}

	/**
   * 특정 게시글의 상세 정보를 보여줍니다.
   *
   * @param model 모델에 게시글 정보를 추가
   * @param postId 게시글 ID
   * @return 게시글 상세 페이지
   */
	@GetMapping("/detail")
	public String viewPostList(
			Model model,
			@RequestParam(name = "pi") int postId) {

		PostDto post =	postService.getPostDetail(postId);
		model.addAttribute("post", post);
		return"post/detail";
	}

	 /**
   * 댓글을 작성합니다.
   *
   * @param commDto 댓글 DTO 객체
   * @return 작성 후 게시글 상세 페이지로 리다이렉트
   */
	@PostMapping("/writeComm")
	public String writeComm(@ModelAttribute CommDto commDto) {
		postService.writeComm(commDto);

		return "redirect:detail?pi="+commDto.getPostId();
	}
	
	/**
   * 댓글을 수정합니다.
   *
   * @param commDto 수정할 댓글 DTO 객체
   * @return 수정 후 게시글 상세 페이지로 리다이렉트
   */
	@PostMapping("/updateComm")
	public String updateComm(
			@ModelAttribute CommDto commDto) {
		postService.updateComm(commDto);

		return "redirect:detail?pi="+commDto.getPostId();
	}

	/**
   * 댓글을 삭제합니다.
   *
   * @param commDto 삭제할 댓글 DTO 객체
   * @return 삭제 후 게시글 상세 페이지로 리다이렉트
   */
	@PostMapping("/deleteComm")
	public String deleteComm(
			@ModelAttribute CommDto commDto) {

		postService.deleteComm(commDto);
		return "redirect:detail?pi="+commDto.getPostId();
	}

	/**
   * 글쓰기 페이지를 표시합니다.
   *
   * @param model 모델에 카테고리 데이터를 추가
   * @return 글쓰기 페이지
   */
	@GetMapping("/write")
	public String writePost(Model model) {

		// 카테고리 조회
		List<TasteDto> categorys = tasteService.getCategory();
		model.addAttribute("categorys", categorys);

		return "post/write";
	}


	 /**
   * 선택한 카테고리에 맞는 취향 목록을 JSON 형태로 반환합니다.
   *
   * @param categoryId 카테고리 ID
   * @return 선택된 카테고리에 해당하는 취향 목록
   */
	@GetMapping(value = "/getTastesByCategory", produces = "application/json")
	@ResponseBody
	public List<TasteDto> getTastesByCategory(
			@RequestParam(value = "categoryId", required = false, defaultValue = "0") int categoryId) {
		if (categoryId == 0) {
			// 기본값 처리 (예: 빈 리스트 반환)
			return Collections.emptyList();
		}
		return tasteService.getTastesByCategoryId(categoryId);
	}

	/**
   * 글쓰기 처리 후 게시글 상세 페이지로 리다이렉트합니다.
   *
   * @param postDto 게시글 DTO 객체
   * @param selectedTastes 선택한 취향 목록
   * @return 작성 후 게시글 상세 페이지로 리다이렉트
   */
	@PostMapping("/writePostOk")
	public String writePostOk(
			@ModelAttribute PostDto postDto,
			@RequestParam(required = false) List<Integer> selectedTastes) {

		postService.writePost(postDto, selectedTastes);
		Long postId = (long) postDto.getPostId();
		return "redirect:detail?pi="+postId;
	}

	 /**
   * 글 수정 페이지를 위한 취향 목록을 JSON 형태로 반환합니다.
   *
   * @param categoryId 카테고리 ID
   * @param postId 게시글 ID
   * @return 수정할 게시글에 해당하는 취향 목록
   */
	@GetMapping(value = "/getTastesForEditPost", produces = "application/json")
	@ResponseBody
	public List<TasteDto> getTastesForEditPost(
			@RequestParam(value = "categoryId", required = false, defaultValue = "0") int categoryId,
      @RequestParam(value = "postId", required = false, defaultValue = "0") Integer postId) {
	    
		if (categoryId == 0 || postId == 0) {
	        return Collections.emptyList();
	    }

	    List<TasteDto> tastes = 
	    		postService.markPostSelectedTastes(categoryId, postId);  
	    // 기존 취향에 대한 선택 상태 반영

	    return tastes;
	}


	 /**
   * 글 수정 페이지를 표시합니다.
   *
   * @param model 모델에 카테고리 및 게시글 데이터를 추가
   * @param postId 게시글 ID
   * @return 글 수정 페이지
   */
	@GetMapping("/editPost")
	public String editPost(
			@RequestParam(name = "pid") int postId,
			Model model ) {

		// 카테고리 목록 조회
		List<TasteDto> categorys = tasteService.getCategory();
		model.addAttribute("categorys", categorys);

		// 수정할 게시글 정보 가져오기
		PostDto post =	postService.getEditPost(postId);
		model.addAttribute("post", post);

		return "post/edit";
	}

	 /**
   * 글 수정 처리를 완료한 후, 수정된 게시글 상세 페이지로 리다이렉트합니다.
   *
   * @param postDto 수정된 게시글 DTO 객체
   * @param selectedTastes 선택한 취향 목록
   * @return 수정 후 게시글 상세 페이지로 리다이렉트
   */
	@PostMapping("/editPostOk")
	public String editPostOk(
			@ModelAttribute PostDto postDto,
			@RequestParam(required = false) List<Integer> selectedTastes) {

		postService.editPost(postDto, selectedTastes);

		Long postId = (long) postDto.getPostId();
		return "redirect:detail?pi="+postId;
	}

	/**
   * 글을 삭제하고 게시글 목록 페이지로 리다이렉트합니다.
   *
   * @param postId 삭제할 게시글 ID
   * @return 삭제 후 게시글 목록 페이지로 리다이렉트
   */
	@PostMapping("/deletePost")
	public String deletePost(@RequestParam int postId	) {

		postService.deletePost(postId);

		return "redirect:list";
	}
}
