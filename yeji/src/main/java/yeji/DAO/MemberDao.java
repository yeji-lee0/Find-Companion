package yeji.DAO;

import yeji.DTO.MemberDto;

public interface MemberDao {

	/** 
   * 주어진 아이디가 이미 존재하는지 확인합니다. 
   * 
   * @param id 확인할 회원 아이디
   * @return 아이디가 중복되면 true, 그렇지 않으면 false
   */
	boolean isIdDuplicate(String id);

	/** 
   * 새로운 회원을 데이터베이스에 추가합니다.
   * 
   * @param dto 회원 정보를 담고 있는 MemberDto 객체
   */
	void join(MemberDto dto);

	/** 
   * 주어진 아이디와 비밀번호로 회원을 로그인합니다.
   * 
   * @param id 회원의 아이디
   * @param pw 회원의 비밀번호
   * @return 로그인 성공 시 회원 정보를 담고 있는 MemberDto 객체, 실패 시 null
   */
	MemberDto login(String id, String pw);

	/** 
   * 회원의 정보를 삭제합니다.
   * 
   * @param id 삭제할 회원의 아이디
   */
	void deleteMember(String id);

	/** 
   * 주어진 아이디로 회원 정보를 조회합니다.
   * 
   * @param id 조회할 회원의 아이디
   * @return 회원의 정보를 담고 있는 MemberDto 객체, 존재하지 않으면 null
   */
	MemberDto getMember(String id);

	/** 
   * 회원의 정보를 수정합니다.
   * 
   * @param dto 수정할 회원 정보를 담고 있는 MemberDto 객체
   */
	void updateMember(MemberDto dto);

}
