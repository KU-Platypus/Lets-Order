//이미지 변경시 새로운 이미지로 교체
function onAddMenuImgChange() {
   var addMenuImgInput = document.getElementById("imageInput");
   var addMenuImg = document.getElementById("previewImage");
   if (addMenuImgInput.files && addMenuImgInput.files[0]) { //이미지 선택여부
      var reader = new FileReader();    // 파일리더 객체 생성
      reader.onload = function(e) {     // 이벤트 핸들러 정의
         addMenuImg.src = e.target.result;  // 로드된 파일의 데이터 가져와 설정
         }
      reader.readAsDataURL(addMenuImgInput.files[0]);  // 선택한 파일의 데이터 읽음
   }
}
document.querySelector('input[type="submit"]').addEventListener('click', function() {
    alert('추가되었습니다');
});
