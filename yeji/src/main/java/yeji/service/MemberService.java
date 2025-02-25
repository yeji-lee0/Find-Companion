package yeji.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import yeji.DAO.MemberDao;
import yeji.DAO.PostDao;
import yeji.DAO.TasteDao;
import yeji.DTO.MemberDto;
import yeji.DTO.TasteDto;

@Service
public class MemberService {
	@Autowired
	MemberDao memDao;
	@Autowired
	PostDao postDao;
	@Autowired
	TasteDao tasteDao;
	@Autowired
	TasteService tasteService;

	/**
	 * 회원 ID를 사용하여 회원 정보를 가져옵니다.<br>
	 * 
	 * 회원의 기본 정보, 게시글, 댓글 데이터를 가져오고,<br>
	 * 회원이 선택한 취향을 전체 목록에서 체크한 후, 취향 데이터를 카테고리 및 필드별로 그룹화하여 반환합니다.
	 *
	 * @param memberId 회원 ID
	 * @param currentPostPage 현재 게시글 페이지 번호
	 * @param currentCommPage 현재 댓글 페이지 번호
	 * @param pageSize 페이지당 게시글 및 댓글 수
	 * @return 회원 정보 (기본 정보, 게시글, 댓글, 취향 데이터 포함)
	 */
	@Transactional
	public MemberDto getMember(String memberId, int currentPostPage, int currentCommPage, int pageSize) {
		// 1. 기본 회원 정보 가져오기
		MemberDto member  = memDao.getMember(memberId);

		int postStartRow = (currentPostPage - 1) * pageSize + 1;
		int postEndRow = currentPostPage * pageSize;
		int commStartRow = (currentCommPage - 1) * pageSize + 1;
		int commEndRow = currentCommPage * pageSize;

		// 2. 회원의 게시글 및 댓글 데이터 가져오기
		member.setPostList(postDao.getMemberPostAll(memberId, postStartRow, postEndRow));
		member.setCommList(postDao.getMemberCommAll(memberId, commStartRow, commEndRow));
		member.setPostCount(postDao.getMemberPostCount(memberId));
		member.setCommCount(postDao.getMemberCommCount(memberId));
		

		// 3. 전체 취향 목록 중 회원이 선택한 항목을 체크
		List<TasteDto> mardedMemberSelectedTastes 
		= markMemberSelectedTastes(memberId);

		//4. 취향 데이터를 카테고리 및 필드별로 그룹화
		Map<String, Map<String, List<TasteDto>>> groupedTastes 
		= tasteService.groupTastes(mardedMemberSelectedTastes);
		member.setGroupedtaste(groupedTastes);

		return member;
	}

	
	
	/**
	 * 전체 취향 목록에서 특정 회원이 선택한 항목을 체크하여 반환합니다.
	 * @param memberId 회원 ID
	 * @return 전체 TasteDto 목록 중, 회원이 선택한 항목의 selected 필드를 true로 설정한 리스트
	 */
	public List<TasteDto> markMemberSelectedTastes(String memberId) {
		List<TasteDto> allTasteList = tasteDao.getCategoryFieldTasteAll();

		for (TasteDto taste : allTasteList) {
			boolean selected 
			= tasteDao.isMemberTasteSelected(memberId, taste.getTasteId());
			taste.setSelected(selected);
		}
		return allTasteList;
	}
	

	/**
	 * 특정 회원의 모든 취향 목록을 가져옵니다.
	 * 
	 * @param memberId 회원 ID
	 * @return 지정된 회원의 취향 목록
	 */
	public List<TasteDto> getMemberTasteAll(String memberId){
		return tasteDao.getMemberTasteAll(memberId);
	}
	
	/**
	 * 회원의 취향을 업데이트합니다.
	 * 
	 * @param memberId 회원의 ID
	 * @param selectedTastes 회원이 새로 선택한 취향의 ID 리스트
	 * 
	 * <p>이 메서드는 먼저 회원이 가지고 있는 기존의 모든 취향을 삭제한 후, 
	 * 전달된 `selectedTastes` 리스트에 포함된 취향들을 `MEMBER_TASTE` 테이블에 새로 추가합니다.</p>
	 * <p>만약 `selectedTastes`가 `null`이거나 빈 리스트일 경우, 기존 취향은 삭제만 되고 새로운 취향은 추가되지 않습니다.</p>
	 */
	public void updateMemberTaste(String memberId, List<Integer> selectedTastes) {
		// 기존 회원 취향 모두 삭제
		tasteDao.deleteMemberTasteAll(memberId);
		
		 // 선택된 취향이 없으면 리턴
		if (selectedTastes == null || selectedTastes.isEmpty()) {
			return;
		}
		
		// 선택된 취향을 추가
		for (Integer tasteId : selectedTastes) {
			tasteDao.addMemberTaste(memberId, tasteId);
		}
	}

	/**
	 * 특정 회원이 작성한 게시글 수를 반환합니다.
	 *
	 * @param memberId 회원 ID
	 * @return 해당 회원이 작성한 게시글 수
	 */
	public int getMemberPostCount(String memberId) {
		return postDao.getMemberPostCount(memberId);
	}

	/**
	 * 특정 회원이 작성한 댓글 수를 반환합니다.
	 *
	 * @param memberId 회원 ID
	 * @return 해당 회원이 작성한 댓글 수
	 */
	public int getMemberCommCount(String memberId) {
		return postDao.getMemberCommCount(memberId);
	}

	/**
	 * 회원을 탈퇴시킵니다.
	 * 회원의 취향도 삭제합니다.
	 * @param memberId 회원 ID
	 */
	public void deleteMember(String memberId) {
		memDao.deleteMember(memberId);
		tasteDao.deleteMemberTasteAll(memberId);
	}

	/**
	 * 회원 정보를 수정합니다.
	 * 
	 * @param dto 수정할 회원 정보가 담긴 MemberDto 객체
	 */
	public void updateMember(MemberDto dto) {
		memDao.updateMember(dto);
	}

	/**
	 * 회원 ID와 비밀번호로 로그인합니다.
	 * 
	 * @param memberId 회원 ID
	 * @param pw 회원 비밀번호
	 * @return 로그인 성공 시 회원 정보를 담은 MemberDto 객체, 실패 시 null 반환
	 */
	public MemberDto login(String memberId, String pw) {
		return	memDao.login(memberId, pw);
	}

	/**
	 * 아이디 중복 여부를 확인합니다.
	 * 
	 * @param memberId 확인할 회원 ID
	 * @return true이면 중복된 아이디, false이면 중복되지 않은 아이디
	 */
	public boolean isIdDuplicate(String memberId) {
		return memDao.isIdDuplicate(memberId);
	}

	/**
	 * 새로운 회원을 가입시킵니다.
	 * 
	 * @param dto 회원가입에 필요한 정보를 담은 MemberDto 객체
	 */
	public void join(MemberDto dto) {
		memDao.join(dto);
	}
}
