// 스크롤 위치 저장
// window.addEventListener("scroll", function () {
//   localStorage.setItem("scrollPosition", window.scrollY);
// });

window.addEventListener("load", function () {
  // 페이지 로드 후 스크롤 위치 복원
  // let scrollPosition = localStorage.getItem("scrollPosition");
  // if (scrollPosition !== null) {
  //   window.scrollTo(0, scrollPosition);
  // }

  // 취향 부분----------------------------------------------------------------
  const tasteListDiv = document.getElementById("taste-list-div");
  const editButton = document.getElementById("edit-taste-btn");
  const tasteFormDiv = document.getElementById("taste-form-div");
  tasteFormDiv.style.display = "none";
  const cancelButton = document.getElementById("cancel-btn");
  const updateButton = document.getElementById("update-btn");

  function toggleTasteListForm() {
    tasteListDiv.style.display =
      tasteListDiv.style.display == "none" ? "block" : "none";
    tasteFormDiv.style.display =
      tasteFormDiv.style.display == "none" ? "block" : "none";
  }

  // 취향 수정 버튼 클릭 시 취향 목록 숨기고 수정 폼 표시
  editButton.addEventListener("click", toggleTasteListForm);

  updateButton.addEventListener("click", toggleTasteListForm);

  // 취향 수정 폼 내 취소 버튼 클릭 시 폼 숨기고 취향 목록 보이기
  cancelButton.addEventListener("click", toggleTasteListForm);

  // "모두 선택" 체크박스 동작
  document.querySelectorAll(".all-check-input").forEach(function (allCheck) {
    allCheck.addEventListener("change", function (event) {
      const field = event.target.getAttribute("data-field"); // 고유 필드값
      const checkboxes = document.querySelectorAll(
        `.taste-input[data-field="${field}"]`
      );

      // "모두 선택" 상태에 따라 각 체크박스 상태 변경
      checkboxes.forEach(function (checkbox) {
        checkbox.checked = event.target.checked;
      });
    });
  });

  // 개별 체크박스 변경 시 "모두 선택" 체크박스 상태 업데이트
  document.querySelectorAll(".taste-input").forEach(function (tasteCheck) {
    tasteCheck.addEventListener("change", function (event) {
      const field = event.target.getAttribute("data-field"); // 고유 필드값
      const allCheck = document.querySelector(
        `.all-check-input[data-field="${field}"]`
      );
      const checkboxes = document.querySelectorAll(
        `.taste-input[data-field="${field}"]`
      );
      const allChecked = Array.from(checkboxes).every((cb) => cb.checked);

      allCheck.checked = allChecked; // 모두 체크되었으면 "모두 선택"도 체크
    });
  });
});
