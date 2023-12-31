# (Refund)

- H2 데이터베이스 실행
- H2 웹 콘솔 : [localhost:9900/h2-console](http://localhost:9900/h2-console)
  - JDBC URL : `jdbc:h2:tcp://localhost/~/szs`
- 로컬 서버 실행 후 Swagger UI 접속
- Swagger UI 주소 : [http://localhost:9900/swagger-ui/index.html](http://localhost:9900/swagger-ui/index.html)

# 구현
#### 1. 회원가입

- end_point: `/szs/signup [POST]`
- 허용된 유저만 가입되기 때문에 서버 실행시 허용 유저 데이터가 없는경우 유저 데이터 저장
- DB에 가지고 있는 허용유저(이름, 주민번호)를 검증하여 해당하지 않으면 예외처리
- 가입유저는 비밀번호 & 주민번호 뒷자리가 암호화 저장

#### 2. 로그인

- end_point: `/szs/login [POST]`
- userId, password가 일치하는 데이터가 존재하면 시큐리티 컨텍스트에 저장되며
- JWT토큰을 발급 (jjwt 라이브러리 활용)

#### 3. 내 정보 조회

- end_point: `/szs/me [GET]`
- 서블릿 필터를 활용하여 헤더에 담긴 JWT 토큰을 검증하여 올바른 토큰인 경우 시큐리티 컨텍스트에 저장
- 시큐리티 컨텍스트 저장된 유저의 정보로 유저의 정보 & 권한을 조회

#### 4. 내 연말정산 스크랩

- end_point: `/szs/scrap [POST]`
- 스크랩에 필요한 데이터는 "이름", "주민번호"
- JWT토큰을 이용하여 자신의 정보를 조회함 (이름, 주민번호)
- 주민번호 뒷자리는 암호화되어서 저장되어있기 때문에, PasswordEncoder를 커스텀하여 복호화(decoder) 활용
- 외부 api 연동에 okhttp 라이브러리 활용
- 스크랩한 데이터는 재사용이 가능하도록 "스크랩", "연간 총소득", "소득공제", "급여" 테이블에 저장, 실패시 예외처리
- 스크랩 재시도 시 동일한 스크랩 데이터가 존재 & 동일년도의 연말정산 소득데이터가 존재하면 추가 저장되지 않도록 예외처리

#### 5. 환급금 계산

- end_point: `/szs/refund [POST]`
- JWT토큰을 이용하여 자신의 정보를 조회에 해당하는 "연간 총소득" 데이터를 가져옴
- "연간 총소득"와 "환급" (1대1 매핑)되며 환급 데이터가 없는 경우
- "연간 총 소득"과 연관관계된 "세금공제", "급여" 테이블을 활용하여 환급금을 계산하고 데이터베이스에 저장
- 연도별로 환급금 반환

# 검증 결과

- swagger 및 회원가입, 로그인, 환급금 계산 단위테스트 코드를 작성하여 검증
- 테스트 케이스
  - `signup`
    - 허용유저가 아닌 경우 예외처리
    - 동일한 이름, 주민번호로 가입하는 경우 예외처리
  - `login`
    - 가입되지않은 userId와 password로 로그인시 예외처리
  - `me`
    - 올바르지 않은 jwt토큰 예외처리
  - `scrap`
    - 스크랩 실패 예외처리
    - 스크랩 데이터가 존재하거나 "연간 총소득 데이터"가 존재하는 경우 예외처리
  - `refund`
    - 환급금 데이터 생성 정상확인

# 도메인 디자인

  * 유저 (Users)
    - 사용자 정보를 담는 엔터티, 회원가입 된 사용자 정보와 권한을 관리
  
  * 권한 (Authority)
    - 권한 정보를 관리
  
  * 가입 허용 유저 (AllowableUser)
    - 허용된 유저 정보를 관리
  
  * 연말정산 스크랩 내역 (YearEndTaxScrapHistory)
    - 연말정산 정보를 스크랩한 내역을 관리
  
  * 연간 총 소득 (AnnualIncome)
    - 유저의 연간 총 소득 정보를 관리
   
  * 소득 급여 (IncomeSalary)
    - 유저의 소득 급여 정보를 관리
  
  * 소득 공제 (Deduction)
    - 유저의 소득 공제 정보를 관리
  
  * 환급 (Refund)
    - 유저의 환급 정보를 관리

<img width="1372" alt="image" src="https://github.com/ljh468/refund_project/assets/64997253/9613901a-40e0-4227-9641-0733e688923e">



