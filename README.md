<img src="https://img.shields.io/badge/androidstudio-3DDC84?style=flat&logo=androidstudio&logoColor=white"/>

# Simple-browser
안드로이드 스튜디오를 활용해 구현한 모바일 웹 브라우저 애플리케이션입니다.
# APIs
카카오톡, firebase API가 사용되었습니다. 카카오톡의 경우 로그인 기능 구현에 이
용되었고 firebase는 realtime database를 이용하여 즐겨찾기 기능을 구현하였습니
다.
# How to use
## Main page
![map1](https://github.com/dipreez/Simple-browser/assets/50349104/fdf696de-a1a7-4518-9362-367b5552a0b8)

애플리케이션 실행 시 가장 먼저 보이는 메인화면입니
다. 일반적인 웹 브라우저처럼 웹 서핑을 할 수도 있
고 위의 툴바에서 url을 직접 입력하여 해당 url로 이
동할 수도 있습니다. 좌측 상단의 버튼을 이용해 메뉴
탭을 열 수 있습니다.
## Menu tab
![map2](https://github.com/dipreez/Simple-browser/assets/50349104/e0b2c273-8286-4c9f-af3e-302868aefcc9)

애플리케이션의 메뉴탭입니다. 로그인, 로그아웃, 즐겨
찾기 추가, 즐겨찾기 목록 보기 기능을 사용할 수 있
습니다. 로그아웃, 즐겨찾기 추가, 즐겨찾기 목록 보기
는 로그인을 한 상태에서만 사용할 수 있고 사용자마
다 자신만의 즐겨찾기를 관리할 수 있습니다.
## Log in
![map3](https://github.com/dipreez/Simple-browser/assets/50349104/0c6f1533-a66f-4451-b69c-9eb989ce37a2)

Login 버튼 클릭 시 이동되는 화면입니다. 카카오톡
API를 이용하여 카카오톡 계정으로 로그인 할 수 있도
록 되어있습니다.
## Bookmarks
![map4](https://github.com/dipreez/Simple-browser/assets/50349104/61012ff6-5445-4d89-92dc-92e23c42ea2c)

Bookmarks 버튼 클릭 시 이동되는 화면입니다. 사용
자가 등록한 즐겨찾기 목록을 확인할 수 있고 특정 즐
겨찾기를 클릭하면 해당 url로 바로 이동합니다.
## Add Bookmark
![map5](https://github.com/dipreez/Simple-browser/assets/50349104/0c735e1c-95e2-4333-84b3-d51d8778d646)

Add to Bookmark 버튼 클릭 시 이동되는 화면입니다.
탐색중이던 웹의 url을 제목을 지정하여 즐겨찾기에
등록할 수 있습니다. url을 직접 수정할 수도 있습니
다.

# Back-end communication
Firebase를 이용하였습니다. 사용자가 즐겨찾기 추가 기능을 이용하는 경우
FirebaseDatabase 객체의 setValue 메소드를 이용하여 사용자가 추가하고자 하는
즐겨찾기의 이름, url, 그리고 사용자 정보를 함께 firebase Realtime Database에 저
장합니다. 즐겨찾기 목록 보기 기능은 ValueEventListener를 이용하여 Firebase
Realtime Database에서 해당 사용자가 등록한 즐겨찾기들을 불러오도록 구현되었
습니다.
