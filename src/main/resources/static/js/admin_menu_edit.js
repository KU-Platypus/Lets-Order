//이미지 변경시 새로운 이미지로 교체
function onMenuImgChange() {
   var menuImgInput = document.getElementById("menuImgInput");
   var menuImg = document.getElementById("menuImg");
   var menuImgChanged = document.getElementById("menuImgChanged");// 이미지 변경 여부
   menuImgChanged.value = "true";    // 이미지 변경 확인
   console.log(menuImgChanged.value);
   if (menuImgInput.files && menuImgInput.files[0]) { //이미지 선택여부
      var reader = new FileReader();    // 파일리더 객체 생성
      reader.onload = function(e) {     // 이벤트 핸들러 정의
         menuImg.src = e.target.result;  // 로드된 파일의 데이터 가져와 설정
         }
      reader.readAsDataURL(menuImgInput.files[0]);  // 선책한 파일의 데이터 읽음
   }
}

