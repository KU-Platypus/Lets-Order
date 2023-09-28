# Let's Order!
<img width="300" alt="store1" src="https://github.com/KU-Platypus/SmartKiosk/assets/72004261/1d1c9cd6-fc1a-4635-92c1-98abec7c56a9">

<h2> 프로젝트 소개 </h2>
QR코드를 통한 웹 주문 시스템 구축
<br>
<br>• QR코드를 통해 고객이 해당 음식점에서 메뉴를 주문할 수 있는 웹 포털
<br>• 어플리케이션 설치 없이 QR코드만을 통해 웹 접근을 할 수 있게 구현
<br>• 여러 음식점이 집대성된 통합 포털로, 유저 편의성과 이후의 확장성을 고려함
<br>• 키오스크를 통하지 않으므로, 공간 효율을 극대화하고 시간 지연을 최소화함

<br>개발기간 : 2023.01.10 ~ 2023.06.22
<br>사용 기술 - MySql, Spring
<br>
<h2>세부 기능</h2>
사용자 측면
<br>• 로그인/로그아웃
<br>• 메뉴 주문 / 장바구니
<br>• 유저정보
<br>• 해당 매장 이용 횟수
<br>• 해당 매장 바로가기 QR생성
<br>• 주문내역
<br>• 실시간 주문접수 확인
<br>• 매장 검색
<br>
<br>관리자 측면
<br>• 매장 메뉴 수정/삭제/추가
<br>• 매장 배너 수정
<br>• 주문 접수 / 취소

<h2>적용 방법</h2>
IntellJ로 작업하였습니다.
<br>
<br>* 매장 추가와 어드민 추가 기능이 없어서 수동으로 작업 해야한다.
<br>먼저 회원가입을 한 뒤 DB의 memberdb의 role을 ADMIN로 바꿔준 뒤
<br>storedb에 매장 데이터를 넣은뒤 마지막 member_id 항목에 해당 member의 id를 넣어준다.
<br>이후에 메인 페이지에서 로그인 후 주소창에 /admin 을 붙여 접속하면 관리자 페이지가 나온다.
<br>
<br> 배너와 메뉴등의 이미지는 "C:\shop\item"에 저장된다.

<h2>미리보기</h2>
<img width="300" alt="store1" src=https://github.com/KU-Platypus/SmartKiosk/assets/72004261/dd4b17d2-d3b0-40db-8750-45be79c4332e/>
<img width="300" alt="store1" src=https://github.com/KU-Platypus/SmartKiosk/assets/72004261/0ad3fb15-91d9-45c7-a0ea-e448e72dbedf/>

