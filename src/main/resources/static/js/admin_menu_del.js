// CSRF 토큰 가져오기
var csrfToken = document.querySelector('input[name="_csrf"]').value;
// 모달 창 가져오기
var editModal = document.getElementById("editModal");
var addModal = document.getElementById('addModal');
// 모달 창의 닫기 버튼 가져오기
var editCloseBtn = document.querySelector('#editModal .close');
var addCloseBtn = document.querySelector('#addModal .close');

function toggleCheckbox(row) {
    var checkbox = row.querySelector('input[type="checkbox"]');
    checkbox.checked = !checkbox.checked;
    row.classList.toggle('selected', checkbox.checked);

    // 체크박스 상태에 따라 제출 버튼 값 변경
    var submitButton = document.querySelector('.navbar form button[type="submit"]');
    var checked = document.querySelectorAll('input[type="checkbox"]:checked');
    if (checked.length === 0) {
        submitButton.textContent = '미선택';
    } else if (checked.length === 1) {
        submitButton.textContent = '수정하기';
    } else {
        submitButton.textContent = '없애버리기';
    }
}

document.querySelectorAll('td').forEach(function(td) {
    td.addEventListener('click', function() {
        var checkbox = td.querySelector('input[type="checkbox"]');
    });
});

/* from 태그 처리 */
document.querySelector('form').addEventListener('submit', function(event) {
    var checked = document.querySelectorAll('input[type="checkbox"]:checked');
    var form = event.target;
    if (checked.length === 0) {
        // 체크박스가 선택되지 않은 경우 예외 발생
        event.preventDefault();
        alert('선택을 해주세요!');
    } else if (checked.length === 1) {
        // 체크박스가 한 개만 선택된 경우 모달 창 표시
        event.preventDefault();
        editModal.style.display = "block";

        // 체크한 값의 menuid 가져오기
        var menuid = checked[0].value;
        console.log(menuid);

        // AJAX 요청 전송
        $.ajax({
            url: "/admin/menuEdit",
            type: "GET",
            data: {menuid: menuid, _csrf: csrfToken},
            success: function(data) {
                // 모달 창 내용 설정
                $("#editModal .editModal-body").html(data);
            }
        });

        /* Edit 모달의 메뉴삭제 버튼 동작*/
        var deleteBtn = document.querySelector('#btn_menuDel');
        deleteBtn.addEventListener('click', function() {
            console.log("버튼을 눌렀어요");
            checked.forEach(function(checkbox) {
                var input = document.createElement('input');
                input.type = 'hidden';
                input.name = checkbox.name;
                input.value = checkbox.value;

                console.log(input.name);
                console.log(input.value);

                event.target.appendChild(input);
            });
            editModal.style.display = "none";
            alert('성공적으로 제거되었습니다.');
            form.submit();
        });

    } else {
        // 체크박스가 여러 개 선택된 경우 폼 전송
        checked.forEach(function(checkbox) {
            var input = document.createElement('input');
            input.type = 'hidden';
            input.name = checkbox.name;
            input.value = checkbox.value;

            console.log("체크박스 이름"+input.name);
            console.log("체크박스 값"+input.value);
            console.log("===========");

            event.target.appendChild(input);
        });
        alert('성공적으로 제거되었습니다.');
    }
});

var submitButton = document.querySelector('.navbar form button[type="submit"]');
submitButton.textContent = '미선택';
console.log("submitButton: "+ submitButton);

/* 추가하기 모달 */
document.querySelector('#addBtn').addEventListener('click', function() {
    // AJAX 요청 전송
    $.ajax({
        url: "/admin/menuAdd",
        type: "GET",
        data: {_csrf: csrfToken},
        success: function(data) {
            // 모달 창 내용 설정
            $("#addModal .addModal-body").html(data);
        }
    });
    // 모달 창 표시
    addModal.style.display = "block";
});

/* 모달 공통 영역 */
// 닫기 버튼 클릭 시 모달창 닫기
editCloseBtn.addEventListener('click', function() {
    editModal.style.display = 'none';
});
addCloseBtn.addEventListener('click', function() {
    addModal.style.display = 'none';
});

// 모달 창 바깥 클릭 시 모달창 닫기
window.onclick = function(event) {
    if (event.target == editModal) {
        editModal.style.display = "none";
    } else if (event.target == addModal) {
        addModal.style.display = "none";
    }
}