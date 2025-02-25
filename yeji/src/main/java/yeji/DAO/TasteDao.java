package yeji.DAO;

import java.util.List;

import yeji.DTO.TasteDto;

/**
 * 취향 데이터 접근 객체 (DAO) 인터페이스입니다.
 * 회원과 게시글의 취향 관련 데이터를 처리합니다.
 * 
 * @author Yeji
 */
public interface TasteDao {

	/**
	 * 모든 카테고리와 필드에 해당하는 취향 목록을 가져옵니다.
	 * 
	 * @return 모든 카테고리, 필드에 대한 취향 목록
	 */
	List<TasteDto> getCategoryFieldTasteAll();

	/**
	 * 특정 회원이 선택한 취향 목록을 모두 가져옵니다.
	 * 
	 * @param memberId 회원 ID
	 * @return 지정된 회원이 선택한 취향 목록
	 */
	List<TasteDto> getMemberTasteAll(String memberId);

	/**
	 * 회원이 특정 취향을 선택했는지 확인합니다.
	 * 
	 * @param memberId 회원 ID
	 * @param tasteId 취향 ID
	 * @return 선택했으면 true, 그렇지 않으면 false
	 */
	boolean isMemberTasteSelected(String memberId, int tasteId);

	/**
	 * 특정 회원의 취향을 모두 삭제합니다.
	 * 
	 * @param memberId 회원 ID
	 */
	void deleteMemberTasteAll(String memberId);

	/**
	 * 회원이 선택한 취향을 회원 취향 테이블에 추가합니다.
	 * 
	 * @param memberId 회원의 ID
	 * @param tasteId 회원이 선택한 취향의 ID
	 * 
	 * <p>
	 * 해당 취향이 이미 존재하는지 확인하지 않고, 단순히 회원 ID와 취향 ID를 
	 * MEMBER_TASTE 테이블에 추가합니다.</p>
	 */
	void addMemberTaste(String memberId, int tasteId);


	/**
   * 모든 카테고리 목록을 가져옵니다.
   * 
   * @return 카테고리 목록
   */
	List<TasteDto> getCategory();

	/**
   * 카테고리 ID로 해당 카테고리의 이름을 가져옵니다.
   * 
   * @param categoryId 카테고리 ID
   * @return 해당 카테고리의 이름
   */
	String getCategoryName(int categoryId);

	/**
   * 특정 카테고리에 속하는 취향 목록을 가져옵니다.
   * 
   * @param categoryId 카테고리 ID
   * @return 해당 카테고리에 속하는 취향 목록
   */
	List<TasteDto> getTasteByCategoryId(int categoryId);
	
	
	/**
   * 특정 게시글에 해당하는 취향 목록을 가져옵니다.
   * 
   * @param postId 게시글 ID
   * @return 해당 게시글에 설정된 취향 목록
   */
	List<TasteDto> getPostTasteList(int postId);

	/**
   * 특정 게시글에 특정 취향이 선택되었는지 확인합니다.
   * 
   * @param postId 게시글 ID
   * @param tasteId 취향 ID
   * @return 선택되었으면 true, 그렇지 않으면 false
   */
	boolean isPostTasteSelected(int postId, int tasteId);

	 /**
   * 특정 게시글에 선택한 취향을 추가합니다.
   * 
   * @param postId 게시글 ID
   * @param tasteId 추가할 취향 ID
   */
	void addPostTaste(int postId, int tasteId);

	/**
   * 특정 게시글에 설정된 모든 취향을 삭제합니다.
   * 
   * @param postId 게시글 ID
   */
	void deletePostTasteAll(int postId);

}
