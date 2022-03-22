# :features:register:onboard

사용자 추가 정보를 입력받는 온보딩 feature 입니다. onboard 도중에 건너뛰어서 앱 둘러보기가 가능하게 돼 있고, onboard 도중에 앱을 종료할 수 있으므로 데이터를 매 단계마다 DataStore 를 통해 저장하게 하였습니다.
추후 다시 onboard 으로 들아올 때 마지막 단계로 돌아오고, 입력했던 정보들도 다 복원됩니다.