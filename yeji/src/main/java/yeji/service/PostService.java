package yeji.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yeji.DAO.PostDao;
import yeji.DAO.TasteDao;
import yeji.DTO.CommDto;
import yeji.DTO.PostDto;
import yeji.DTO.TasteDto;

/**
 * 게시글과 관련된 비즈니스 로직을 처리하는 서비스 클래스입니다.
 * 게시글 작성, 수정, 삭제, 검색, 댓글 처리 등의 기능을 제공합니다.
 * 
 * @author Yeji
 */
@Service
public class PostService {
	@Autowired
	PostDao postDao;
	@Autowired
	TasteDao tasteDao;
	@Autowired
	TasteService tasteService;

	/**
	 * 댓글을 수정합니다.
	 * 
	 * @param commDto 수정할 댓글의 정보가 담긴 DTO
	 */
	public void updateComm(CommDto commDto) {
		postDao.updateComm(commDto);
	}

	/**
	 * 댓글을 작성합니다.
	 * 
	 * @param commDto 작성할 댓글의 정보가 담긴 DTO
	 */
	public void writeComm(CommDto commDto) {
		postDao.writeComm(commDto);
	}

	/**
	 * 특정 게시글의 상세 정보를 가져옵니다.
	 * 
	 * @param postId 게시글 ID
	 * @return 게시글 상세 정보가 담긴 PostDto 객체
	 */
	public PostDto getPostDetail(int postId) {
		PostDto post = postDao.getPostByPostId(postId);

		// 댓글 목록 설정
		List<CommDto> commList = postDao.getCommOnThePost(postId);
		post.setCommList(commList);

		// 카테고리 이름 설정
		String categoryName =	
				tasteDao.getCategoryName(post.getCategoryId());
		post.setCategoryName(categoryName);

		// 취향 목록 설정
		List<TasteDto> tasteList = 
				markPostSelectedTastes(post.getCategoryId(), postId);
		post.setGroupedtaste(tasteService.groupTastes(tasteList));

		return post;
	}

	/**
   * 검색 조건에 따라 게시글 목록을 가져옵니다.
   * 
   * @param searchOption 검색 옵션 (all, title, memberId, content 중 하나)
   * @param searchQuery 검색어
   * @param categoryId 카테고리 ID (null 가능)
   * @param currentPage 현재 페이지 번호
   * @param pageSize 페이지당 게시글 수
   * @return 검색된 게시글 목록
   */
	public List<PostDto> searchPost(
			String searchOption, String searchQuery, Integer categoryId
			, int currentPage, int pageSize){

		PostDto postDto = new PostDto();
		postDto.setCategoryId(categoryId); // 카테고리 ID 설정

		 // 검색 옵션에 따라 검색 조건 설정
		switch (searchOption) {
		case "all": postDto.setAll(searchQuery); break;  // 전체 검색
    case "title": postDto.setTitle(searchQuery); break;  // 제목 검색
    case "memberId": postDto.setMemberId(searchQuery); break;  // 회원 ID 검색
    case "content": postDto.setContent(searchQuery); break;  // 내용 검색
		default: break;
		}

	// 페이지네이션을 위한 시작과 끝 행 번호 계산
		int startRow = (currentPage - 1) * pageSize + 1;// 시작 행 번호
    int endRow = currentPage * pageSize;  // 끝 행 번호

 // 게시글 목록을 DB에서 검색
		List<PostDto> posts 
		= postDao.searchPost(postDto, startRow, endRow);

		 // 각 게시글에 대해 카테고리 이름과 댓글 수 설정
		for (PostDto post : posts) {
			
			String categoryName =	
					tasteDao.getCategoryName(post.getCategoryId()) ;// 카테고리 이름 가져오기
			post.setCategoryName(categoryName);

			int postId = post.getPostId(); 
			post.setCommCount(postDao.getCommCountOnThePost(postId));// 댓글 수 설정
		}

		return posts;	// 검색된 게시글 목록 반환
	}

	 /**
   * 검색 조건에 해당하는 게시글의 총 개수를 반환합니다.
   * 
   * @param searchOption 검색 옵션 (all, title, memberId, content 중 하나)
   * @param searchQuery 검색어
   * @param categoryId 카테고리 ID (null 가능)
   * @return 검색된 게시글의 총 개수
   */
	public int getSearchPostCount(
			String searchOption, String searchQuery, Integer categoryId) {

		PostDto postDto = new PostDto();
		postDto.setCategoryId(categoryId); // 카테고리 ID 설정

		 // 검색 옵션에 따라 검색 조건 설정
    switch (searchOption) {
    case "all": postDto.setAll(searchQuery); break;  // 전체 검색
    case "title": postDto.setTitle(searchQuery); break;  // 제목 검색
    case "memberId": postDto.setMemberId(searchQuery); break;  // 회원 ID 검색
    case "content": postDto.setContent(searchQuery); break;  // 내용 검색
    default: break;
    }

 // 게시글의 총 개수를 DB에서 조회하여 반환
		return postDao.getSearchPostCount(postDto);
	}

	 /**
   * 특정 카테고리의 게시글에서 취향이 선택되었는지 확인하고 설정합니다.
   * 
   * @param categoryId 카테고리 ID
   * @param postId 게시글 ID
   * @return 해당 카테고리의 모든 취향 목록 (선택 여부 포함)
   */
	public List<TasteDto> markPostSelectedTastes(int categoryId, int postId) {

		List<TasteDto> allTasteList = tasteDao.getTasteByCategoryId(categoryId);

		// 각 취향이 선택되었는지 확인하고, 선택된 취향에 대해서 selected 값을 true로 설정
		for (TasteDto taste : allTasteList) {
			boolean selected = 
					tasteDao.isPostTasteSelected(postId, taste.getTasteId());
			taste.setSelected(selected);
		}
		return allTasteList;
	}

	 /**
   * 댓글을 삭제합니다.
   * 
   * @param commDto 삭제할 댓글의 정보가 담긴 DTO
   */
	public void deleteComm(CommDto commDto) {
		postDao.deleteComm(commDto);

	}

	/**
   * 게시글을 작성합니다.
   * 
   * @param postDto 게시글 정보가 담긴 DTO
   * @param selectedTastes 선택한 취향 ID 목록
   */
	public void writePost(
			PostDto postDto, List<Integer> selectedTastes) {

		// 글 등록
		postDao.writePost(postDto); 

		// 선택된 취향이 없으면 리턴
		if (selectedTastes == null || selectedTastes.isEmpty()) {
			return;
		}

		int postId = postDto.getPostId();
		for (Integer tasteId : selectedTastes) {
			tasteDao.addPostTaste(postId, tasteId);	// 글의 취향 등록
		}

	}

	/**
   * 수정할 게시글의 상세 정보를 가져옵니다.
   * 
   * @param postId 게시글 ID
   * @return 수정할 게시글 정보가 담긴 PostDto 객체
   */
	public PostDto getEditPost(int postId) {
		
	// 게시글 ID를 사용하여 게시글 상세 정보를 가져옵니다.
		PostDto post = postDao.getPostByPostId(postId);

	// 게시글의 카테고리 ID를 사용하여 카테고리 이름을 가져옵니다.
		String categoryName =	
				tasteDao.getCategoryName(post.getCategoryId());
		post.setCategoryName(categoryName);

		 // 해당 게시글에 선택된 취향 목록을 가져와서 게시글에 설정합니다.
		List<TasteDto> tasteList = 
				markPostSelectedTastes(post.getCategoryId(), postId);

		 // 취향 목록을 카테고리와 필드별로 그룹화하여 게시글에 설정합니다.
		post.setGroupedtaste(tasteService.groupTastes(tasteList));

		 // 수정할 게시글 정보를 반환합니다.
		return post;
	}


	 /**
   * 게시글을 수정합니다.
   * 
   * @param postDto 수정할 게시글 정보가 담긴 DTO
   * @param selectedTastes 선택한 취향 ID 목록
   */
	public void editPost(PostDto postDto, List<Integer> selectedTastes) {
		
		// 게시글 수정 처리
		postDao.updatePost(postDto);
		
		int postId = postDto.getPostId();
		// 게시글에 선택된 취향 모두 삭제
		tasteDao.deletePostTasteAll(postId);

		// 선택된 취향이 없으면 리턴
		if (selectedTastes == null || selectedTastes.isEmpty()) {
			return;
		}

		// 선택된 취향을 추가
		for (Integer tasteId : selectedTastes) {
			tasteDao.addPostTaste(postId, tasteId);
		}
	}

	/**
   * 게시글을 삭제합니다.
   * 
   * @param postId 삭제할 게시글 ID
   */
	public void deletePost(int postId) {
		// 게시글 삭제
		postDao.deletePost(postId);
		// 게시글에 선택된 취향 모두 삭제
		tasteDao.deletePostTasteAll(postId);
	}


}
