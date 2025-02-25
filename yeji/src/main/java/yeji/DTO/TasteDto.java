package yeji.DTO;

import lombok.Data;
/**
 * 취향을 나타내는 DTO 클래스입니다.
 * 이 객체는 회원이 선택한 취향에 대한 정보를 담고 있으며, 
 * 카테고리, 필드, 취향의 ID 및 이름과 회원이 선택했는지 여부를 포함합니다.
 * 
 * @author Yeji
 */
@Data
public class TasteDto {
  /** 카테고리 ID */
  private int categoryId;     // 카테고리 ID 
  
  /** 카테고리 이름 (예: 여행, 식사 등) */
  private String categoryName; // 카테고리 이름 (여행, 식사 등)
  
  /** 필드 ID  */
  private int fieldId;        // 필드 ID 
  
  /** 필드 이름 (예: 음식 종류, 대화 방식 등) */
  private String fieldName;   // 필드 이름 (음식 종류, 대화 방식 등)
  
  /** 취향 ID  */
  private int tasteId;        // 취향 ID
  
  /** 취향 이름 (예: 한식, 온라인 대화 등) */
  private String tasteName;   // 취향 이름 (한식, 온라인 대화 등)
  
  /** 회원이 선택한 취향 여부 (true: 선택, false: 미선택) */
  private boolean selected;   // 회원의 선택 여부
}
