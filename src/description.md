# Request Vacation API Server
Request Vacation API Server는 휴가 요청을 관리하도록 설계되었습니다.

## Function Specification
- User model과 Login System이 필요합니다. 단, Sign Up 기능은 필요하지 않습니다.
- Users 에게 매년 15일의 연차가 부여됩니다.
- Users 는 매년 연차/반차(0.5일)/반반차(0.25일)에 해당하는 휴가를 신청할 수 있습니다.
- 휴가 신청시 시작일, 종료일(반차/반반차의 경우는 필요없음), 사용 일수, 코멘트(선택 항목)를 입력합니다.
    - 휴가 신청시 남은 연차를 표시합니다.
    - 연차를 모두 사용한 경우 휴가를 신청할 수 없습니다.
    - 추가 기능: 사용 일수를 입력하는 대신 시작일, 종료일을 가지고 공휴일을 제외하고 계산해도 됩니다.
- 아직 시작하지 않은 휴가는 취소할 수 있습니다.

## How to Run
```
./gradlew bootrun

# Using IntelliJ #
1. Sync gradle
2. Run Application
```
## API Endpoints
### Authentication
- POST /login: 사용자가 로그인하고 인증 토큰을 받도록 허용.
```http request
POST /login

payload: {
  username (string),
  passowrd (string)
}
```
- GET /user: 모든 유저 검색.
- GET /user/{user_id}: 특정 유저 검색.

### Vacations
- POST /vacation/request: 해당하는 휴가 조건에 의해 휴가 요청을 생성.
```http request
POST /vacation/request

payload {
  vacationType (string),
  startDate (date),
  endDate (date),
  comment (string)
}
```
- GET /vacation: 특정 유저의 모든 휴가 요청 목록을 검색.
- GET /vacation/{vacation_id}: 특정 휴가 요청의 세부 정보를 검색.
- PUT /vacation/{vacation_id}: 특정 휴가 요청의 세부 정보를 업데이트.
- DELETE /vacation/{vacation_id}: 특정 휴가 요청 취소.

### View
- GET /: Home view 호출.
- GET /vacation/request: Vacation 요청 view 호출.
- POST /vacation/request: Vacation 요청 view 호출 후 남은 연차 반환 view 호출.
- GET /vacation/detail/{user_id}: 특정 유저의 Vacation 목록 view 호출.


## Call Sequence Example
Example: Cancel Vacation

1. [POST /login]: Authenticate user가 Login API 호출.
2. [GET /]: 인증시, Home View 호출 (Auto Redirect)
3. [GET /vacation/request]: Vacation 요청하는 View 호출.
4. [POST /vacation/request]: Submit -> Vacation 요청 API 호출 -> Remain Vacation Counts 확인.
5. [GET /]: Home View 호출.
6. [GET /vacation/detail/{user_id}]: 특정 유저의 Vacation을 조회하는 View 호출.
7. [DELETE /vacation/{vacation_id}]: 특정 Vacation을 취소하는 API 호출.
8. [GET /]: Home View 호출.

## Technologies Used
API 서버는 Java Spring Boot를 사용하여 구축되었으며 데이터의 영구 저장을 위해 DBMS를 사용합니다.

- OpenJDK 17
- Spring Boot 3.0.2
- Gradle 7.6
- Spring Data JPA
- Spring Security
- Thymeleaf
- Lombok
- MySQL
- H2 Hibernate


## Database Schema
API 서버에 사용되는 데이터베이스 스키마는 아래와 같습니다.

```vbnet
users
- user_id (integer, primary key)
- password (string)
- user_name (string)
- role (string)
- available_vac_days (float)
- requested_vac_days (float)
- created_at (date)
- updated_at (date)


vacations
- vacation_id (integer, primary key)
- user_id (integer, foreign key)
- vacation_type (string)
- status (string)
- days_used (float)
- start_date (date)
- end_date (date)
- comment (string)
- created_at (date)
- updated_at (date)
```

ModelMapper

- 서로 다른 object 간의 필드 값을 자동으로 mapping 해주는 library
- 매번 ModelMapper 를 생성하여 사용할 수 있지만, 반복적으로 여러 로직에서 사용됨으로 @Bean 으로 등록하여 사용

### 사용 된 어노테이션

@Entity

- 테이블과 매핑할 클래스에 붙여줍니다.

@Getter

- 필드에 적용되는 접근자/설정자를 생성할수있습니다.

@Buillder

- 객체를 생성할 때 파라미터가 많거나 복잡해질 때 name으로 매칭시켜 파악하기 쉽게 사용합니다.

@DynamicInsert

- null 값인 필드를 제외하고 insert 합니다.

@Table

- 데이터베이스에서 쓸 테이블 이름 또는 매핑하고싶은 테이블 이름을 씁니다.

@NoArgsConstructor

- 파라미터가 없는 기본 생성자를 생성해줍니다.

@AllArgsConstructor

- 모든 필드 값을 파라미터로 받는 생성자를 만들어줍니다.

@RequiredArgsConstructor

- final 이나 @NonNull 인 필드 값만 파라미터로 받는 생성자를 만들어줍니다.

@Id

- primary key를 가지는 변수를 선언하는 것입니다.

@GeneratedValue

- 해당 Id 값을 어떻게 자동으로 생성할지 전략을 선택할 수 있습니다.
- IDENTITY
  - 기본 키 생성을 데이터베이스에 위임하는 방법 (데이터베이스에 의존적)
- SEQUENCE
  - 데이터베이스 시퀀스를 사용해서 기본 키를 할당하는 방법 (데이터베이스에 의존적)
- TABLE
  - 키 생성 테이블을 사용하는 방법
- AUTO
  - 데이터베이스 벤더에 의존하지 않고, 데이터베이스는 기본키를 할당하는 벙법

@Column

- name을 줄 수 있으며 name값을 입력하면 테이블에 입력된 값으로 필드가 매핑이 된다.

@Enumerated

- EnumType.ORIGINAL
  - enum 순서(숫자) 값을 DB에 저장
- EnumType.STRING
  - enum 이름 값을 DB에 저장

@EnableWebMvc

- ViewResolver 값이 자동으로 등록된다.
- **WebMvcConfigurer**
  - 구현하면 @EnableWebMvc 어노테이션이 자동으로 설정해주는 세팅 값에 사용자가 원하는 세팅을 **추가**할 수 있게 된다.

@RestController

- 해당 컨트롤러에서 return 하는 값을 그대로 클라이언트에게 전달해준다.
- 즉, 페이지가 아닌 데이터 자체를 반환할 때 사용하는 어노테이션이다.

@Controller

- View Resolver에 설정한 값 기준으로 return 하는 값과 일치하는 View 를 찾아 반환한다.
- @ResponseBody ****어노테이션을 사용할 시 JSON 형태의 데이터를 반환할 수 있다.

@GetMapping

- HTTP GET 요청을 처리하는 메서드를 맵핑(@RequestMapping) ****하는 어노테이션이다.
  - 데이터를 가져올 때 사용한다.

@PostMapping

- 데이터를 게시할 때 사용한다.

@PutMapping

- 데이터를 수정할 때 사용한다.

@DeleteMapping

- 데이터를 삭제할 때 사용한다.

@PatchMapping

- 데이터를 수정할 때 사용한다.

@ResponseBody

- HTTP 규격에 맞는 응답을 만들어주기 위한 Annotation 이다.
- @ResponseBody 만 사용시에 별도의 뷰를 제공하지 않고, 데이터만 전송하는 형식이다.
- **ResponseEntity**
  - 이와 같은 부분들 해결해 줄 수 있는 것이 **ResponseEntity** 객체다. (객체로 사용)
  - HTTP 요청(Request) 또는 응답(Response)에 해당하는 HttpHeader와 HttpBody를 포함하는 클래스이고, 즉, 응답으로 변환될 정보를 모두 담은 요소들을 객체로 만들어서 반환해준다.

@PathVariable

- url파라미터로 전달받은 value를 메서드의 파라미터로 받을 수 있게 해주는 어노테이션이다.

@RequestMapping

- http request로 들어오는 url을 특정 controller 클래스나 메소드로 연결시키는 역할을 한다.





========================================
Vacation
Repository
- 

Service
- 