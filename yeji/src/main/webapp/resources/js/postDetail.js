window.addEventListener("load", function () {
  // 댓글 입력 폼--------------------------
  const writeCommForm = document.querySelector("form[action='writeComm']");

  const contentInput = writeCommForm.querySelector("textarea");
  const submitButton = writeCommForm.querySelector("input[type='submit']");

  // 댓글 쓰면 글자수 계산, 등록 버튼 활성화
  contentInput.addEventListener("input", function () {
    const msg = writeCommForm.querySelector("span");
    const length = contentInput.value.length; // value 속성을 사용해 글자 수 계산
    msg.textContent = `${length}/300`;

    // 댓글이 한 글자라도 있으면 버튼 활성화, 아니면 비활성화
    submitButton.disabled = length === 0;
  });

  // 댓글 수정 폼--------------------------
  const editButtons = document.querySelectorAll(".edit-btn");
  editButtons.forEach((button) => {
    button.addEventListener("click", function () {
      const commId = this.id.split("-")[1]; // commId 추출
      const textarea = document.querySelector(`#edit-form-${commId} textarea`);
      const charCountSpan = document.querySelector(
        `#edit-${commId}-char-count`
      );

      // 글자수 계산
      textarea.addEventListener("input", function () {
        const length = textarea.value.length;
        charCountSpan.textContent = `${length}/300`;
      });
    });
  });
});
