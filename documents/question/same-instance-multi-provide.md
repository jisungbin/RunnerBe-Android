안녕하세요! 밤 중에 죄송합니다 ㅠㅠ 다른 분들과 1시간가량 토론을 해봤는데 명확히 답이 안나와서 찰스님 방에 여쭤보게 됐습니다.

지금 PetRepository 가 있고, 이 PetRepository 에는 dog 와 cat 라는 함수가 있습니다. 또한 Men Feature 와 Girl Feature 가
있습니다. Men Feature 에서는 dog 만 사용하고, Girl Feature 에서는 cat 만 사용하고 있는 상황입니다. 두 개의 모듈이 PetRepository 를
사용하므로 각각 모듈에서 PetRepository 를 provide 해주고 있습니다. 하지만 이렇게 되면 동일 인스턴스를 두 번 provide 해주고 있기 때문에 hilt 오류가
발생합니다.

이를 해결하기 위해 생각해본 방법으론 각 모듈별로 PetRepository provide 에 @Named 를 붙이는것과, DI provide 전용 DI 모듈을 따로 만드는것이
있을거 같습니다. 여러분들은 어떤 방법을 사용하실거 같나요? 아니면 이를 해결할 수 있는 더 좋은 방법이 있을까요?

---

# 답변

<img src="https://user-images.githubusercontent.com/40740128/158842654-f303e1fc-6351-4275-bcba-5f8f1dd0cff6.png" width="33%" alt="answer" />

# 해결 방법

그냥 DI 전용 모듈을 따로 만들기로 함
