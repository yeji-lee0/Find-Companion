window.addEventListener("load", function () {
  const loginForm = document.querySelector("#login-div form");
  const loginMsg = loginForm.querySelector(".msg");
  loginMsg.textContent = "";
  loginMsg.style.padding = "";
  loginMsg.style.border = "";
  loginMsg.style.borderRadius = "";
  loginMsg.style.marginBottom = "";

  loginForm.addEventListener("submit", function (e) {
    const idValue = loginForm.querySelector("#id-input").value;
    const passwordValue = loginForm.querySelector("#password-input").value;

    // 아이디 유효성 검사: 알파벳으로 시작하고 5~20자의 알파벳(숫자 선택)
    const idRegex = /^[a-zA-Z][a-zA-Z0-9]{3,19}$/;

    // 비밀번호 유효성 검사: 알파벳으로 시작 알파벳, 숫자, 특수문자 조합 8~20자
    const passwordRegex =
      /^[a-zA-Z](?=.*[a-zA-Z0-9!@#$%^&*()_+={}\[\]:;"'<>,.?\/\\|-]).{7,19}$/;

    // 유효성 검사 실패 시 메시지 표시
    if (!idRegex.test(idValue) || !passwordRegex.test(passwordValue)) {
      loginMsg.textContent = "아이디와 비밀번호를 다시 입력해주세요.";
      loginMsg.style.padding = "10px 15px";
      loginMsg.style.border = "1px solid #343231";
      loginMsg.style.borderRadius = "20px";
      loginMsg.style.marginBottom = "10px";
      e.preventDefault(); // 제출 방지
      //console.log("유효성 검사 실패");
      return;
    }
  });
});
