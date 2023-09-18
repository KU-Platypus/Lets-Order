const slider = document.querySelector('#slider');
const wrapper = document.querySelector('.wrapper');
const items = document.querySelector('.items');
const item = document.querySelectorAll('.item');
const currentIndexElement = document.querySelector('.current-index');
const itemCount = 4;
let currentIdx = 0;
let positions = [];
let intervalId;
let isTouching = false; // 터치 중 여부를 나타내는 변수 추가
let dragStarted = false; // 드래그 시작 여부를 나타내는 변수 추가

function initializeData() {
  const isActive = items.classList.contains('active');
  if (isActive) items.classList.remove('active');
  const width = wrapper.clientWidth;
  const interval = item[1].clientWidth;
  const margin = (width - interval) / 2;
  const initX = Math.floor((interval - margin) * -1);
  let pos = [];
  for (let i = 0; i < itemCount; i++) {
    pos.push(initX - interval * i);
  }
  positions = pos;
  items.style.width = itemCount * 100 + '%'; // 항목 수에 맞게 변경
  items.style.left = positions[currentIdx] + 'px';
  slider.style.visibility = 'visible';
  updateCurrentIndex();
}

window.addEventListener('resize', initializeData);
window.addEventListener('load', () => {
  initializeData();
  startAutoSlide();
});

function goToNextItem() {
  if (isTouching || dragStarted) return; // 터치 중 또는 드래그 시작 시에는 넘어가지 않도록 처리

  let nextIdx = currentIdx + 1;
  if (nextIdx === itemCount) {
    nextIdx = 0;
  }
  const nextLeft = positions[nextIdx];

  // 가운데 정렬 애니메이션
  items.style.transition = 'left 0.3s ease-in-out';
  items.style.left = positions[currentIdx] + 'px';

  setTimeout(() => {
    // 사이 여백 정렬 애니메이션
    items.style.transition = 'left 0.3s ease-in-out';
    items.style.left = (positions[currentIdx] - wrapper.clientWidth / 2) + 'px';

    setTimeout(() => {
      // 항목으로 넘어가는 애니메이션
      items.style.transition = 'none'; // 애니메이션 없음
      items.style.left = nextLeft + 'px';
      currentIdx = nextIdx;
      updateCurrentIndex();

      // 위치 변경 후 다시 가운데 정렬 애니메이션
      setTimeout(() => {
        items.style.transition = 'left 0.3s ease-in-out';
        items.style.left = positions[nextIdx] + 'px';
      }, 50); // 0ms 지연
    }, 250);
  }, 300);
}

function startAutoSlide() {
  intervalId = setInterval(goToNextItem, 5000); // 5초마다 다음 항목으로 이동
}

function stopAutoSlide() {
  clearInterval(intervalId);
}

wrapper.addEventListener('mouseover', () => {
  stopAutoSlide(); // 마우스가 영역 위에 있을 때 자동 이동 중지
});

wrapper.addEventListener('mouseout', () => {
  startAutoSlide(); // 마우스가 영역에서 벗어났을 때 다시 자동 이동 시작
});

// 터치-드래그 이벤트
let startX = 0;
let moveX = 0;

wrapper.addEventListener('touchstart', (e) => {
  const rect = wrapper.getBoundingClientRect();
  startX = e.touches[0].clientX - rect.left;
  const isActive = items.classList.contains('active');
  if (!isActive) items.classList.add('active');
  items.addEventListener('touchmove', onTouchMove);
  document.addEventListener('touchend', onTouchEnd);

  // 터치가 시작될 때 터치 중 상태로 설정
  isTouching = true;
  // 드래그가 시작됨을 표시
  dragStarted = true;
});

function onTouchMove(e) {
  if (!isTouching) return; // 터치 중이 아니면 함수 종료
  if (!wrapper.classList.contains('active')) wrapper.classList.add('active');
  const rect = wrapper.getBoundingClientRect();
  moveX = e.touches[0].clientX - rect.left - startX;
  const left = positions[currentIdx] + moveX;
  if ((currentIdx === 0 && moveX > 0) || (currentIdx === itemCount - 1 && moveX < 0)) return;
  items.style.left = left + 'px';
}

function onTouchEnd(e) {
  if (wrapper.classList.contains('active')) wrapper.classList.remove('active');
  items.removeEventListener('touchmove', onTouchMove);
  document.removeEventListener('touchend', onTouchEnd);


  // 마지막 항목에서 왼쪽으로 드래그하여 첫 번째 항목으로 이동하는 경우
  if (currentIdx === itemCount - 1 && moveX < 0) {
    let nextIdx = currentIdx + 1;
      if (nextIdx === itemCount) {
        nextIdx = 0;
      }
      const nextLeft = positions[nextIdx];

      // 가운데 정렬 애니메이션
      items.style.transition = 'left 0.3s ease-in-out';
      items.style.left = positions[currentIdx] + 'px';

      setTimeout(() => {
        // 사이 여백 정렬 애니메이션
        items.style.transition = 'left 0.3s ease-in-out';
        items.style.left = (positions[currentIdx] - wrapper.clientWidth / 2) + 'px';

        setTimeout(() => {
          // 항목으로 넘어가는 애니메이션
          items.style.transition = 'none'; // 애니메이션 없음
          items.style.left = nextLeft + 'px';
          currentIdx = nextIdx;
          updateCurrentIndex();

          // 위치 변경 후 다시 가운데 정렬 애니메이션
          setTimeout(() => {
            items.style.transition = 'left 0.3s ease-in-out';
            items.style.left = positions[nextIdx] + 'px';
          }, 50); // 0ms 지연
        }, 250);
      }, 300);
  }

  // 처음 항목에서 오른쪽으로 드래그하여 마지막 항목으로 이동하는 경우
    if (currentIdx === 0 && moveX > 0) {
      // 애니메이션 적용
      const nextIdx = itemCount - 1;
          const nextLeft = positions[nextIdx];

          // 가운데 정렬 애니메이션
          items.style.transition = 'left 0.3s ease-in-out';
          items.style.left = positions[currentIdx] + 'px';

          setTimeout(() => {
            // 사이 여백 정렬 애니메이션
            items.style.transition = 'left 0.3s ease-in-out';
            items.style.left = (positions[currentIdx] + wrapper.clientWidth / 2) + 'px';

            setTimeout(() => {
              // 항목으로 넘어가는 애니메이션
              items.style.transition = 'none'; // 애니메이션 없음
              items.style.left = nextLeft + 'px';
              currentIdx = nextIdx;
              updateCurrentIndex();

              // 위치 변경 후 다시 가운데 정렬 애니메이션
              setTimeout(() => {
                items.style.transition = 'left 0.3s ease-in-out';
                items.style.left = positions[nextIdx] + 'px';
              }, 50); // 0ms 지연
            }, 250);
          }, 300);
    }

  if (moveX > -70 && moveX <= 70) {
    // -70~70 범위 안에서 움직였으면 초기 위치로 이동
    return (items.style.left = positions[currentIdx] + 'px');
  }
  if (moveX > 70 && currentIdx > 0) {
    currentIdx = currentIdx - 1;
    items.style.left = positions[currentIdx] + 'px';
  }
  if (moveX < -70 && currentIdx < itemCount - 1) {
    currentIdx = currentIdx + 1;
    items.style.left = positions[currentIdx] + 'px';
  }


  updateCurrentIndex();

  // 터치가 끝나면 터치 중 상태 해제
  isTouching = false;
  // 드래그가 끝나면 드래그 시작 여부 초기화
  dragStarted = false;
}

function updateCurrentIndex() {
  let currentIndex = currentIdx + 1;
  if (currentIndex > itemCount) {
    currentIndex = itemCount;
  }
  const text = `${currentIndex} / ${itemCount}`; // 현재 항목 / 총 항목 수 형태의 텍스트 생성
  currentIndexElement.textContent = text; // 현재 인덱스 표시 요소에 텍스트 설정
}

window.addEventListener('load', () => {
  initializeData();
  startAutoSlide();
});