document.addEventListener("DOMContentLoaded", function () {
  const tagsContainer = document.querySelector("#tags-container"); // 컨테이너
  const tagsElement = document.querySelector(".tags"); // 원래 태그들

  // 태그 복제
  const clonedTags = tagsElement.cloneNode(true); // 태그 복사
  tagsContainer.appendChild(clonedTags); // 복제된 태그를 컨테이너에 추가

  // 복제된 태그를 기존 태그 뒤에 배치
  clonedTags.style.position = "absolute";
  clonedTags.style.left = `${tagsElement.scrollWidth}px`; // 기존 태그 너비만큼 오른쪽으로 이동
  clonedTags.style.top = "0";

  // 두 태그를 모두 이동시키는 애니메이션
  gsap.to([tagsElement, clonedTags], {
    x: `-${tagsElement.scrollWidth}px`, // 태그 길이만큼 왼쪽으로 이동
    duration: 300, // 속도
    repeat: -1, // 무한 반복
    ease: "linear", // 일정한 속도로
  });
});

// GSAP scrollTo 플러그인 등록
gsap.registerPlugin(ScrollToPlugin);

let lastScrollY = 0; // 마지막 스크롤 위치
let isScrolled = false; // 스크롤 상태 체크 변수

window.addEventListener("scroll", function () {
  // 현재 스크롤 위치
  let scrollPosition = window.scrollY;

  // #tags-container의 위치를 가져오기
  let tagsContainerPosition =
    document.querySelector("#tags-container").offsetTop;

  // 원하는 위치로 조금 더 아래로 이동
  let offset = 100; // 100px 더 아래로 설정

  // 사용자가 아래로 스크롤한 경우
  if (scrollPosition > lastScrollY && !isScrolled) {
    // 스크롤을 내리면 #tags-container 위치에서 조금 더 아래로 이동
    gsap.to(window, {
      scrollTo: { y: tagsContainerPosition + offset, autoKill: false }, // #tags-container보다 100px 더 아래로
      duration: 1.5, // 애니메이션 지속 시간
    });
    isScrolled = true; // 한번 스크롤한 후 isScrolled를 true로 설정
  }

  // // 사용자가 위로 스크롤한 경우 (맨 위로 이동)
  // if (scrollPosition < lastScrollY && scrollPosition !== 0 && isScrolled) {
  //   // 위로 스크롤 시 맨 위로 이동
  //   gsap.to(window, {
  //     scrollTo: { y: 0 }, // 페이지 맨 위로 스크롤
  //     duration: 1.5, // 애니메이션 지속 시간
  //   });
  //   isScrolled = false; // 다시 isScrolled를 false로 설정
  // }

  // 마지막 스크롤 위치 갱신
  lastScrollY = scrollPosition;
});
