안녕하새요, 질문이 있습니다. 현재 app 모듈에선 feature 모듈들의 UI 를 fragment 로 연결만 시켜주고 있습니다. (app - FragmentContainerView, feature - fragment). 따라서 앱 시작도 app 모듈애서 하고, 이 app 모듈의 액티비티 시작점인 스플래시 액티비티에서 아이템을 미리 로드 한 후, 이 아이템을 메인 엑티비티에서 A feature 모듈의 fragment 에 표시하려 합니다. 그러기 위해선 app 모듈에서 로드한 아이템들을 A feature 모듈로 옮겨야 하는데, 어떤 방법이 제일 좋은 방법일까요? app 모듈은 A feature 모듈을 알지만, A feature 모듈은 app 모듈을 모르는 상황 입니다.

1. A 모듈에 DataStore object 를 만들어서 관리한다.
2. DataStore 만 하는 모듈을 새로 만든다.