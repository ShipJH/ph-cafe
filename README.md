# 

# 

## 요구사항 & 구현 

1. 사장님은 시스템에 휴대폰번호와 비밀번호 입력을 통해서 회원 가입을 할 수 있습니다.
    - 사장님의 휴대폰 번호를 올바르게 입력했는지 확인해주세요
    - 비밀번호를 안전하게 보관할 수 있는 장치를 만들어주세요
   ``` 
   1. 사장님의 휴대폰 번호를 올바르게 입력했는지 확인해주세요.
   1-1. 휴대폰 번호 유효성 추가.
   1-2. 휴대폰 번호 중복을 위한 검증 맟 unique 설정 이중처리 추가. 
      
   2. 비밀번호를 안전하게 보관할 수 있는 장치를 만들어주세요.
   * 안전한 비밀번호 설정을 위한 설정 추가. 
   2-1. 비밀번호의 유효성 추가. (최소 8자리 이상, 영문, 숫자, 특수문자 포함) 
   2-2. 비밀번호 확인 추가.
   
   - api 예시
   POST /sign-up
   {
   "phoneNumber": "010-5500-2890",
   "password": "Qwer1234!",
   "passwordCheck": "Qwer1234!"
   }
   ```

2. 사장님은 회원 가입이후, 로그인과 로그아웃을 할 수 있습니다. 
   ```
   1. 로그인 기능 추가.
   POST /sign-in
   {
    "phoneNumber": "010-5500-2890",
    "password": "Qwer1234!"
   }
   
   2. 로그아웃 기능 추가.
   POST /sign-out
   Headers Authorization: Bearer {token}
   
   * 레디스를 따로 적용하진 않았습니다.
   ```
   













