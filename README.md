## 환경 
- spring boot 3.2.2
- jdk 17
- kotlin 1.7.22
- gradle
- mysql
- jpa

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
   2-3. 단방향 암호화 추가.

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
   Headers Authorization: bearer {token}
   {}
   
   * 레디스를 따로 적용하진 않았습니다. (로그아웃, 리프래쉬 토큰을 DB에서 제어하도록 간단히 구현하였습니다.)
   ```
   

3. 사장님은 로그인 이후 상품관련 아래의 행동을 할 수 있습니다.
   ```
   요구사항에 상품만을 기준으로 하여, 가게라는 테이블은 만들지 않았습니다.
   개인적인 생각으로라면 가게(1) : 상품(N)의 관계로 만들어 주는것이 좋을것 같습니다.
   
   3-1. 사장님은 상품을 등록할 수 있습니다.
   POST /product
   Headers Authorization: bearer {token}
   {
    "category": "COFFEE",
    "price": 3000,
    "cost" : 1000,
    "name" : "아이스 아메리카노",
    "description" : "맛있는 아이스 아메리카노",
    "barcode": "88012345612345",
    "expirationDate": "2024-03-01 23:01:01",
    "size" : "SMALL"
   }
   
   3-2. 사장님은 상품의 속성을 부분 수정할 수 있습니다.
   PUT /product
   Headers Authorization: bearer {token}
   {
    "id": 1,
    "category": "BEVERAGE",
    "name" : "디카페인 아메리카노",
    "price": 5500
   }
   
   3-3. 사장님은 상품을 삭제할 수 있습니다.
   DELETE /product/{id}
   Headers Authorization: bearer {token}
   
   3-4. 사장님은 등록한 상품의 리스트를 볼 수 있습니다.  
   GET /product
   Headers Authorization: bearer {token}
   query prameter로, name과 lastId를 이용하여 검색할 수 있습니다.
   예시 : /product?name=아메리카노&lastId=1
   
   더보기 형태의 cursor based pagination 를 요구사항으로 적어주셔서
   최신글이 상단에 위치하며, 조회시 상품리스트,lastId,isNextPage 를 내려드립니다.
   lastId를 이용하여 다음 페이지를 조회할 수 있습니다.
   isNextPage 가 false 일 경우 더이상 조회할 페이지가 없습니다.
   
   3-5. 사장님은 등록한 상품의 상세 내역을 볼 수 있습니다. 
   GET /product/{id}
   Headers Authorization: bearer {token}

   3-6. 사장님은 상품 이름을 기반으로 검색할 수 있습니다. 
   이름에 대해서 like 검색과 초성검색을 지원해야 합니다. 
   예) 슈크림 라떼 → 검색가능한 키워드 : 슈크림, 크림, 라떼, ㅅㅋㄹ, ㄹㄸ 
   
   * 
   초성 검색의 조건 때문에 엘라스틱 서치를 이용하려 했으나 사용하지 않았습니다.
   현재 주어진 요구사항의 도메인을 봤을때, 가게라는 값과 그에따른 상품을 매칭시킨다면 큰 부하가 없을 것 같았기 때문입니다. 
   라이브러리에 너무 의존하지 않고 구현하였습니다.
      
   3-7. 로그인하지 않은 사장님의 상품 관련 API에 대한 접근 제한 처리가 되어야 합니다.
   로그인하지 않은 사장님은 상품 관련 API에 접근할 수 없습니다.
   또한 다른 사장님의 상품도 접근 및 행위를 할 수 없습니다. 
  
   ```
   
4. 그 외 구현
   
   언어에 상관없이 Docker를 기반으로 서버를 실행 할 수 있도록 작성해주세요.  
   간단하게 Dockerfile 작성하였습니다. 
   소스 상위에서 아래와 같이 실행하시면 됩니다.
   ```
   $ gradle clean build
   $ docker build -t ph-app .
   $ docker run -p 8080:8080 ph-app 

   위와 같이 실행하시면 됩니다. 
   
   mysql이 설치되어 있지 않다면 에러가 발생할 수 있습니다.
   application-docker.yml 으로 DockerFile에 설정해 두었습니다.
   실행이 안되면 연락주시면 감사하겠습니다.
   ```
   
   DB관련 테이블에 대한 DDL 파일을 소스 디렉토리 안에 넣어주세요.
   [DDL 링크](https://github.com/ShipJH/ph-cafe/blob/main/DB-DDL.md)   
   (링크가 접속이 안된다면 소스 최상단의 DB-DDL.md 파일을 참고해주세요)  
   테스트 케이스를 작성해주세요. -> 간단히 작성하였습니다.  
   JWT토큰을 발행해서 인증을 제어하는 방식으로 구현해주세요. -> 구현하였습니다.  
   API는 아래의 custom response json 형식으로 반환되야합니다.(204 No Content 제외) -> 구현 하였습니다.  
  
  

## 디렉토리 구조.  

```
src
├─main
│  ├─kotlin
│  │  └─ph
│  │      └─cafe
│  │          └─io
│  │              │  PhCafeApplication.kt
│  │              │
│  │              ├─common
│  │              │      BaseEntity.kt
│  │              │      BaseEnum.kt
│  │              │      BaseListRequest.kt
│  │              │      Constants.kt
│  │              │      ResponseDto.kt
│  │              │
│  │              ├─config
│  │              │  │  JacksonDeserializerConfig.kt
│  │              │  │  JpaQueryDslFactory.kt
│  │              │  │  PasswordEncodeConfig.kt
│  │              │  │
│  │              │  └─security
│  │              │      │  AuthenticationFilter.kt
│  │              │      │  ExceptionHandlerFilter.kt
│  │              │      │  SecurityConfig.kt
│  │              │      │
│  │              │      └─jwt
│  │              │              JwtDto.kt
│  │              │              JwtProvider.kt
│  │              │
│  │              ├─domain
│  │              │  ├─product
│  │              │  │  │  ProductController.kt
│  │              │  │  │  ProductRepository.kt
│  │              │  │  │  ProductService.kt
│  │              │  │  │
│  │              │  │  ├─customRepository
│  │              │  │  │      ProductRepositoryCustom.kt
│  │              │  │  │      ProductRepositoryCustomImpl.kt
│  │              │  │  │
│  │              │  │  └─model
│  │              │  │          ProductDto.kt
│  │              │  │          ProductEntity.kt
│  │              │  │          ProductEnum.kt
│  │              │  │
│  │              │  └─user
│  │              │      │  CustomUserDetailsService.kt
│  │              │      │  UserController.kt
│  │              │      │  UserEntityGetService.kt
│  │              │      │  UserRepository.kt
│  │              │      │  UserService.kt
│  │              │      │
│  │              │      └─model
│  │              │              UserDto.kt
│  │              │              UserEntity.kt
│  │              │              UserEnum.kt
│  │              │
│  │              ├─exception
│  │              │      BaseException.kt
│  │              │      ExceptionCode.kt
│  │              │      PasswordException.kt
│  │              │      PhoneNumberException.kt
│  │              │
│  │              ├─handler
│  │              │      ApiExceptionHandler.kt
│  │              │
│  │              └─utils
│  │                      StringUtils.kt
│  │                      ValidUtils.kt
│  │
│  └─resources
│          application.yml
│
└─test
    └─kotlin
        └─ph
            └─cafe
                └─io
                     ProductTest.kt
                     SignUpUnitTest.kt

```

| 폴더명/파일명  	           | 설명	                 | 비고            |
|----------------------|---------------------|---------------|
| PhcafeApplication.kt | 메인 메서드              | 최초 실행 부분      |
| common  	            | 공통으로 쓰는 객체          |               |
| config  	            | 설정 파일을 모아두었음 	      | 		            |
| config/security      | spring security 설정	 | 인증/인가를 담당 	   |
| config/security/jwt  | jwt 관련 부분           |               |
| domain               | 실제 API 구성           | 각각 도메인에 맞게 구성 |
| exception            | 공통적으로 에러 처리를 담당     |  |
| handler              | 핸들러를 담당             |  |
| utils                | 각종 필요한 유틸           |  |

























